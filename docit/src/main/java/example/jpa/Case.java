package example.jpa;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "case")
@XmlRootElement
public class Case {
	@Id //primary key
	@Column(name = "CASE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic
	@Column(name = "DOC_ID")
	private Integer doctorId;

	@Basic
	@Column(name = "PAT_ID")
	private Integer patientId;
	
	
	@Basic
	@Column(name = "CASE_DESC")
	private String caseDesc;
	 
	
	@Basic
	@Column(name = "CASE_NAME")
	private String caseName;
	
	
	
	@Basic
	@Column(name = "DOC_COMMENTS")
	private String  docComments ;
	 
	
	@Basic
	@Column(name = "PAT_COMMENTS")
	private String patientComments;
	
	
	@Basic
	@Column(name = "CASE_STATUS")
	private String caseStatus;
	
	
	@Basic
	@Column(name = "CASE_CREATION_TS")
	private Date caseCreationTS;
	  
	
	@Basic
	@Column(name = "CASE_UPDATION_TS")
	private Date caseUpdationTS;
	
	@Basic
	@Column(name = "APPOINTMENT_DT")
	private Date appointmentDate;
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Integer getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}


	public Integer getPatientId() {
		return patientId;
	}


	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}


	public String getCaseDesc() {
		return caseDesc;
	}


	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}


	public String getCaseName() {
		return caseName;
	}


	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}


	public String getDocComments() {
		return docComments;
	}


	public void setDocComments(String docComments) {
		this.docComments = docComments;
	}


	public String getPatientComments() {
		return patientComments;
	}


	public void setPatientComments(String patientComments) {
		this.patientComments = patientComments;
	}


	public String getCaseStatus() {
		return caseStatus;
	}


	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}


	public Date getCaseCreationTS() {
		return caseCreationTS;
	}


	public void setCaseCreationTS(Date caseCreationTS) {
		this.caseCreationTS = caseCreationTS;
	}


	public Date getCaseUpdationTS() {
		return caseUpdationTS;
	}


	public void setCaseUpdationTS(Date caseUpdationTS) {
		this.caseUpdationTS = caseUpdationTS;
	}


	
	public String toString() {
		return "id=" + id + ", doctorId=" + doctorId + ", patientId="
				+ patientId + ", caseDesc=" + caseDesc + ", caseName="
				+ caseName + ", docComments=" + docComments
				+ ", patientComments=" + patientComments + ", caseStatus="
				+ caseStatus + "";
	}


	public Date getAppointmentDate() {
		return appointmentDate;
	}


	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
