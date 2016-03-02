package example.jpa;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Lob;

import org.codehaus.jackson.annotate.JsonIgnore;

	@Entity
	@Table(name = "Doctor")
	/*
	 * define O-R mapping of todolist table
	 */
	public class Doctor {
		
		
		
		 
		@Id //primary key
		@Column(name = "DOC_ID")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		
		@Basic
		@Column(name = "DOC_NM")
		private String docName;

		@Basic
		@Column(name = "DOC_DOB")
		private Date docDOB;
		
		
		
		
		@Basic
		@Column(name = "DOC_CLINIC_ADDRESS")
		private String docClinicAddress;
		
		@Basic
		@Column(name = "DOC_SEX")
		private String docSex;
		
		@JsonIgnore
		@Lob
		@Column( name = "FILE" )
		private byte[] file;
		
		@Basic
		@Column(name = "DOC_PHONE")
		private String docPhone ;
		 
		
		@Basic
		@Column(name = "DOC_EMAIL")
		private String docEmail;
		
		
		@Basic
		@Column(name = "DOC_EXP")
		private Float docExp;
		
		
		@Basic
		@Column(name = "DOC_EDU ")
		private String docEdu;
		  
		
		@Basic
		@Column(name = "DOC_MAJ_SPEC ")
		private String docMajSpec;
		

		@Basic
		@Column(name = "DOC_HIGHLIGHTS ")
		private String docHighlights;
		
		
		
		
		public long getId() {
			return id;
		}



		public void setId(long id) {
			this.id = id;
		}



		public String getDocName() {
			return docName;
		}



		public void setDocName(String docName) {
			this.docName = docName;
		}



		public Date getDocDOB() {
			return docDOB;
		}



		public void setDocDOB(Date docDOB) {
			this.docDOB = docDOB;
		}
		
		


		public String getDocClinicAddress() {
			return docClinicAddress;
		}



		public void setDocClinicAddress(String docClinicAddress) {
			this.docClinicAddress = docClinicAddress;
		}


		public String getDocSex() {
			return docSex;
		}
		
		public void setDocSex(String docSex) {
			this.docSex = docSex;
		}



		public byte[] getFile() {
			return file;
		}

	    public void setFile(byte[] file) {
		this.file = file;
	    }
		
		public String getDocPhone() {
			return docPhone;
		}



		public void setDocPhone(String docPhone) {
			this.docPhone = docPhone;
		}



		public String getDocEmail() {
			return docEmail;
		}



		public void setDocEmail(String docEmail) {
			this.docEmail = docEmail;
		}






		public String getDocEdu() {
			return docEdu;
		}



		public void setDocEdu(String docEdu) {
			this.docEdu = docEdu;
		}



		public String getDocMajSpec() {
			return docMajSpec;
		}



		public void setDocMajSpec(String docMajSpec) {
			this.docMajSpec = docMajSpec;
		}



	


		
		public String toString() {
			return "Doctor [id=" + id + ", doctorName=" + docName + ", docDOB="
					+ docDOB + ",docClinicAddress ="
					+ docClinicAddress + ",  docPhone=" +  docPhone
					+ ", docEmail=" +docEmail + ", doc_Exp="
					+docExp + ",docEdu=" +docEdu
					+ ",docMajSpec=" +docMajSpec 
					+ ",docSex=" +docSex + ", file=" +file 
					+ ",doc_Highlights=" +docHighlights +"]";
		}



		public Float getDocExp() {
			return docExp;
		}



		public void setDocExp(Float docExp) {
			this.docExp = docExp;
		}



		public String getDocHighlights() {
			return docHighlights;
		}



		public void setDocHighlights(String docHighlights) {
			this.docHighlights = docHighlights;
		}
	}


