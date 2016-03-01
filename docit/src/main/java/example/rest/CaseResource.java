package example.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.sun.jersey.multipart.FormDataParam;

import example.jpa.Case;
import example.jpa.CaseFile;

@Path("/case")
public class CaseResource {

	private UserTransaction utx;
	private EntityManager em;

	public CaseResource() {
		utx = getUserTransaction();
		em = getEm();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addCase(@FormDataParam("doctorId") Long doctorId,
			@FormDataParam("patientId") Long patientId,
			@FormDataParam("caseDesc") String caseDesc,
			@FormDataParam("caseName") String caseName,
			
			@FormDataParam("file1") InputStream file1,
			@FormDataParam("file2") InputStream file2,
			@FormDataParam("file3") InputStream file3) throws IOException {

		Case caseObj = new Case();
		caseObj.setDoctorId(doctorId.intValue());
		caseObj.setPatientId(patientId.intValue());
		caseObj.setCaseDesc(caseDesc);
		caseObj.setCaseName(caseName);
		caseObj.setCaseCreationTS(new Date());
		caseObj.setCaseUpdationTS(new Date());
		caseObj.setCaseStatus("OPEN");
		
		CaseFile caseFile1 = null;
		if (file1 != null) {
			byte[] bytes = IOUtils.toByteArray(file1 );
			caseFile1=new CaseFile();
			caseFile1.setFileContent(bytes);
			System.out.println("file size is " +bytes.length);
		}
		
		CaseFile caseFile2 = null;
		if (file2 != null) {
			byte[] bytes = IOUtils.toByteArray(file2 );
			caseFile2=new CaseFile();
			caseFile2.setFileContent(bytes);
			System.out.println("file size is " +bytes.length);
		}
		
	
		
		CaseFile caseFile3 = null;
		if (file3 != null) {
			byte[] bytes = IOUtils.toByteArray(file3 );
			caseFile3=new CaseFile();
			caseFile3.setFileContent(bytes);
			System.out.println("file size is " +bytes.length);
		}

		try {
			utx.begin();
			em.persist(caseObj);
			em.flush();

			utx.commit();

			if(caseFile1 != null) {
				utx.begin();
				System.out.println("caseId" + caseObj.getId());
				caseFile1.setCaseId(caseObj.getId());
				em.persist(caseFile1);
				utx.commit();
			}
			if(caseFile2 != null) {
				utx.begin();
				System.out.println("caseId" + caseObj.getId());
				caseFile2.setCaseId(caseObj.getId());
				em.persist(caseFile2);
				utx.commit();
			}
			if(caseFile3 != null) {
				utx.begin();
				System.out.println("caseId" + caseObj.getId());
				caseFile3.setCaseId(caseObj.getId());
				em.persist(caseFile3);
				utx.commit();
			}




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

		return Response.ok(caseObj.toString()).build();
		
	}
	
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
public Object updateCase(@FormDataParam("docComments") String docComments,
				@FormDataParam("patientComments") String patientComments,
				@FormDataParam("caseID") Long caseId)
	{
		try
	{	Case caseo = null;
		
		if(caseId != null)
		{
			utx.begin();
			caseo = em.find(Case.class, caseId);
			if(docComments != null && !"".equals(docComments))
			{
				caseo.setDocComments(docComments);
				
			}
			if(patientComments != null && !"".equals(patientComments))
			{
				caseo.setPatientComments(patientComments);
			}
		caseo.setCaseUpdationTS(new Date());
			em.merge(caseo);
		utx.commit();
		}
		return caseo;
	}

		catch (Exception e) {
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
	}
	




@GET
	@Path("/caseFileIds")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getCaseFileIds(@QueryParam("caseId") Long caseId) {

		List<Long> caseFileIds = null;
		try {
			utx.begin();

			
			String queryString = "SELECT t.id FROM CaseFile t where t.caseId = " + caseId;
			TypedQuery<Long> typedQuery =em.createQuery(queryString, Long.class);
						caseFileIds = typedQuery.getResultList();
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
		if (caseFileIds != null)
			return caseFileIds;
		else
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();

	    

	    // uncomment line below to send non-streamed
	    // return Response.ok(imageData).build();

	    // uncomment line below to send streamed
	     
	}
	

	@GET
	@Path("/image")
	@Produces("image/png")
	public Response getFullImage(@QueryParam("caseFileId") Long caseFileId) {

		CaseFile caseFile = null;
		try {
			utx.begin();
			caseFile = em.find(CaseFile.class, caseFileId);


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
		if (caseFile != null)
			return Response.ok(new ByteArrayInputStream(caseFile.getFileContent())).build();
		else
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();

	    

	    // uncomment line below to send non-streamed
	    // return Response.ok(imageData).build();

	    // uncomment line below to send streamed
	     
	}
	
	
	


	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@QueryParam("caseId") Long caseId,
			@QueryParam("caseName") String caseName,
			@QueryParam("doctorId") Long doctorId,
			@QueryParam("patientId") Long patientId,
			@QueryParam("status") String status) {
		List<Case> cases = null;
		if (caseId == null && caseName == null && doctorId == null && patientId ==null && status == null) {
			cases = em.createQuery("SELECT t FROM Case t", Case.class)
					.getResultList();
			return cases;
		}
		Case caseObj = null;
		try {
			utx.begin();
			String queryString = "SELECT t FROM Case t where ";
			boolean paramAppended = false;
			if (caseId != null) {
				queryString += "t.id =" + caseId;
				paramAppended = true;
			}
			if (caseName != null && !"".equals(caseName)) {
				
				if (paramAppended) {
					queryString += " and t.caseName ='" + caseName +"'";
				} else {
					queryString += "t.caseName ='" + caseName +"'";
				}
				paramAppended = true;
			}
			
			if (status != null && !"".equals(status)) {
				
				if (paramAppended) {
					queryString += " and t.caseStatus ='" + status +"'";
				} else {
					queryString += "t.caseStatus ='" + status +"'";
				}
				paramAppended = true;
			}

			if (doctorId != null) {
				
				if (paramAppended) {
					queryString += " and t.doctorId =" + doctorId;
				} else {
					queryString += " t.doctorId =" + doctorId;
				}
				paramAppended = true;
			}

			if (patientId != null) {
				
				if (paramAppended) {
					queryString += " and t.patientId =" + patientId;
				} else {
					queryString += "t.patientId =" + patientId;
				}
				paramAppended = true;
			}

			cases = em.createQuery(queryString, Case.class).getResultList();
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
		if (cases != null)
			return cases;
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
