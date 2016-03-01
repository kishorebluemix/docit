package example.rest;

import java.io.InputStream;
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

import org.apache.commons.io.IOUtils;

import com.sun.jersey.multipart.FormDataParam;

import example.jpa.Doctor;



@Path("/Doctor")
public class DoctorResource {
	private UserTransaction utx;
	private EntityManager em;

	public DoctorResource() {
		utx = getUserTransaction();
		em = getEm();
	}
	
	 @POST  
	  @Path("/add")  
	  @Consumes(MediaType.MULTIPART_FORM_DATA)
	  public Response addUser(  
	      @FormDataParam("doctorId") String doctorId,  
	      @FormDataParam("docName") String   docName,  
	      @FormDataParam("docDOB") Date docDOB,
	      @FormDataParam("docSex") String docSex,
	      @FormDataParam("docClinicAddress") String docClinicAddress,
	      @FormDataParam("docPhone") String docPhone,
	      @FormDataParam("docEmail") String docEmail,
	      @FormDataParam("docExp") Float docExp,
	      @FormDataParam("docEdu") String docEdu,
	      @FormDataParam("docMajSpec") String docMajSpec,
	      @FormDataParam("docHighlights") String docHighlights,
	      @FormDataParam("file") InputStream file){  
		  
		 Doctor doctor = new Doctor();
		 doctor.setId(Integer.parseInt(doctorId));
		 doctor.setDocName(docName);
		 doctor.setDocDOB(docDOB);
		doctor.setDocSex(docSex);
		 doctor.setDocClinicAddress(docClinicAddress);
		 doctor.setDocPhone(docPhone);
		 doctor.setDocEmail(docEmail);
		 doctor.setDocExp(docExp);
		 doctor.setDocEdu(docEdu);
		 doctor.setDocMajSpec(docMajSpec);
		 doctor.setDocHighlights(docHighlights);
	
	try {
				utx.begin();
					if (file != null) {
			byte[] bytes = IOUtils.toByteArray(file );
			
			doctor.setFile(bytes);
			System.out.println("file size is " +bytes.length);
		}

				em.persist(doctor);
				utx.commit();
			
			} catch (Exception e) {
				e.printStackTrace();			
				return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
			} finally {
				try {
					if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
						utx.rollback();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		 
	 
			return Response.ok(doctor.toString()).build();
	  }  
	 
	 
	 
	 @GET
		@Produces(MediaType.APPLICATION_JSON)
		public Object get(@QueryParam("docId") Long	docId , @QueryParam("speciality") String speciality ) {
		List<Doctor> doctors = null;
			if (docId == null && speciality == null) {
				doctors = em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
				//TODO use JSON util like Gson to render objects and use REST Response Writer

				return doctors;
			}
			
			try {
				utx.begin();
				String queryString = "SELECT t FROM Doctor t where ";
				boolean paramAppended = false;
				if (docId != null) {
					queryString += "t.id =" + docId;
					paramAppended = true;
				}
				if (speciality != null && !"".equals(speciality)) {
					
					if (paramAppended) {
						queryString += " and t.docMajSpec ='" + speciality +"'";
					} else {
						queryString += "t.docMajSpec ='" + speciality +"'";
					}
					paramAppended = true;
				}
				doctors = em.createQuery(queryString, Doctor.class).getResultList();
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
			} finally {
				try {
					if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
						utx.rollback();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (doctors != null)
			return doctors;
			else
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
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
		
		
		// There are two ways of obtaining the connection information for some services in Java 
		
		// Method 1: Auto-configuration and JNDI
		// The Liberty buildpack automatically generates server.xml configuration 
		// stanzas for the SQL Database service which contain the credentials needed to 
		// connect to the service. The buildpack generates a JNDI name following  
		// the convention of "jdbc/<service_name>" where the <service_name> is the 
		// name of the bound service. 
		// Below we'll do a JNDI lookup for the EntityManager whose persistence 
		// context is defined in web.xml. It references a persistence unit defined 
		// in persistence.xml. In these XML files you'll see the "jdbc/<service name>"
		// JNDI name used.

		private EntityManager getEm() {
			InitialContext ic;
			try {
				ic = new InitialContext();
				return (EntityManager) ic.lookup("java:comp/env/openjpa-todo/entitymanager");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return null;
		}
	

}
