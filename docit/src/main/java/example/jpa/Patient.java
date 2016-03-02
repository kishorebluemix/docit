package example.jpa;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "Patient")
/*
 * define O-R mapping of todolist table
 */
public class Patient {



	@Id //primary key
	@Column(name = "PAT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic
	@Column(name = "PAT_NM")
	private String patName;

	@Basic
	@Column(name = "PAT_DOB")
	private Date patDOB;
	
	
	@Basic
	@Column(name = "PAT_SEX")
	private String patSex;
	 
	
	@Basic
	@Column(name = "PAT_ADDRESS")
	private String patAddress;
	
	
	
	@Basic
	@Column(name = "PAT_PHONE")
	private Integer patPhone ;
	 
	
	@Basic
	@Column(name = "PAT_EMAIL")
	private String patEmail;
	
	
	@Basic
	@Column(name = "IS_DIABETIC")
	private Boolean isdiabetic;
	
	
	@Basic
	@Column(name = "BLOOD_PRESSURE ")
	private Boolean hasBloodpressure;
	  
	
	@Basic
	@Column(name = "ALLERGIES ")
	private String allergies;
	
	@JsonIgnore
	@Lob
	@Column( name = "FILE" )
	private byte[] file;

	public byte[] getFile() {
		return file;
	}



	public void setFile(byte[] file) {
		this.file = file;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPatName() {
		return patName;
	}



	public void setPatName(String patName) {
		this.patName = patName;
	}



	public Date getPatDOB() {
		return patDOB;
	}



	public void setPatDOB(Date patDOB) {
		this.patDOB = patDOB;
	}



	public String getPatSex() {
		return patSex;
	}



	public void setPatSex(String patSex) {
		this.patSex = patSex;
	}



	public String getPatAddress() {
		return patAddress;
	}



	public void setPatAddress(String patAddress) {
		this.patAddress = patAddress;
	}



	public Integer getPatPhone() {
		return patPhone;
	}



	public void setPatPhone(Integer patPhone) {
		this.patPhone = patPhone;
	}



	public String getPatEmail() {
		return patEmail;
	}



	public void setPatEmail(String patEmail) {
		this.patEmail = patEmail;
	}



	public String getAllergies() {
		return allergies;
	}



	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}


	
	
	  
	
	public Boolean getIsdiabetic() {
		return isdiabetic;
	}



	public void setIsdiabetic(Boolean isdiabetic) {
		this.isdiabetic = isdiabetic;
	}



	public Boolean getHasBloodpressure() {
		return hasBloodpressure;
	}



	public void setHasBloodpressure(Boolean hasBloodpressure) {
		this.hasBloodpressure = hasBloodpressure;
	}

	
	
	public String toString() {
		return "Patient [id=" + id + ", PatientName=" + patName + ", patDOB="
				+ patDOB + ", patSex=" + patSex + ",patAddress ="
				+ patAddress + ",  patPhone=" +  patPhone
				+ ", patEmail=" +patEmail + ", isdiabetic="
				+isdiabetic + ", bloodpressure=" + hasBloodpressure
				+ ",allergies=" +allergies 
				+"]";
	}
}

