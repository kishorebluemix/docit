package example.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "case_file")
public class CaseFile {
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "ID")
	private long id;
	

	@Column(name = "CASE_ID")
	private long caseId;
	
	@Lob
	@Column( name = "FILE" )
	private byte[] fileContent;
	
	@Basic
	@Column(name = "SHARE")
	private Boolean share;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}
	
}
