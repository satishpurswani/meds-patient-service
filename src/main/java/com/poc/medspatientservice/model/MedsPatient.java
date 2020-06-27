package com.poc.medspatientservice.model;

public class MedsPatient {

	public MedsPatient() {
		super();
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private long patientId;

	private long medids;

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getMedids() {
		return medids;
	}

	public void setMedids(long medids) {
		this.medids = medids;
	}

	public MedsPatient(long patientId, long medids) {
		super();
		this.patientId = patientId;
		this.medids = medids;
	}

	@Override
	public String toString() {
		return "MedsPatient [id=" + id + ", patientId=" + patientId + ", medids=" + medids + "]";
	}

}
