package example.rest;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

import example.jpa.Patient;

@Path("/Patient")
public class PatientResource {
	private UserTransaction utx;
	private EntityManager em;

	public PatientResource() {
		utx = getUserTransaction();
		em = getEm();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addUser(@FormDataParam("patientId") Long patientId,
			@FormDataParam("patName") String patName,
			@FormDataParam("patDOB") Date patDOB,
			@FormDataParam("patSex") String patSex,
			@FormDataParam("patAddress") String patAddress,
			@FormDataParam("patPhone") Integer patPhone,
			@FormDataParam("patEmail") String patEmail,
			@FormDataParam("Isdaibetic") String Isdaibetic,
			@FormDataParam("bloodpressure") String bloodpressure,
			@FormDataParam("allergies") String allergies) {

		Patient PatientObj = new Patient();
		PatientObj.setId(patientId);
		PatientObj.setPatName(patName);
		PatientObj.setPatDOB(patDOB);
		PatientObj.setPatSex(patSex);
		PatientObj.setPatAddress(patAddress);
		PatientObj.setPatPhone(patPhone);
		PatientObj.setPatEmail(patEmail);
		PatientObj.setIsdiabetic(false);
		PatientObj.setHasBloodpressure(false);

		// PatientObj.setDoctorId(doctorId);
		try {
			utx.begin();
			em.persist(PatientObj);
			utx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		} finally {
			try {
				if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
					utx.rollback();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * if (fileInputString != null) { System.out.println(fileInputString); }
		 */

		// Save the file

		return Response.ok(PatientObj.toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@QueryParam("patientId") Long patientId) {

		if (patientId == null) {
			List<Patient> list = em.createQuery("SELECT p FROM Patient p",
					Patient.class).getResultList();
			// TODO use JSON util like Gson to render objects and use REST
			// Response Writer
			String json = "{\"id\":\"all\", \"body\":" + list.toString() + "}";
			return list;
		}
		Patient patientObj = null;
		try {
			utx.begin();
			patientObj = em.find(Patient.class, patientId);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		} finally {
			try {
				if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
					utx.rollback();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (patientObj != null)
			return patientObj;
		else
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND)
					.build();
	}

	private UserTransaction getUserTransaction() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (UserTransaction) ic.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	// There are two ways of obtaining the connection information for some
	// services in Java

	// Method 1: Auto-configuration and JNDI
	// The Liberty buildpack automatically generates server.xml configuration
	// stanzas for the SQL Database service which contain the credentials needed
	// to
	// connect to the service. The buildpack generates a JNDI name following
	// the convention of "jdbc/<service_name>" where the <service_name> is the
	// name of the bound service.
	// Below we'll do a JNDI lookup for the EntityManager whose persistence
	// context is defined in web.xml. It references a persistence unit defined
	// in persistence.xml. In these XML files you'll see the
	// "jdbc/<service name>"
	// JNDI name used.

	private EntityManager getEm() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (EntityManager) ic
					.lookup("java:comp/env/openjpa-todo/entitymanager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}