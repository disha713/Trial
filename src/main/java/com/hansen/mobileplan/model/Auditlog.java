package com.hansen.mobileplan.model;

import java.util.Date;

//It's just a POJO not entity class
public class Auditlog {
	
	private Long id;
	private String operationType;
	private String entityJson;
	private Date modificationDate;
	
	public Auditlog() {
		super();
	}

	public Auditlog(String operationType, String entityJson , Date date) {
		super();
		this.operationType = operationType;
		this.entityJson = entityJson;
		this.modificationDate = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getEntityJson() {
		return entityJson;
	}

	public void setEntityJson(String entityJson) {
		this.entityJson = entityJson;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

}
