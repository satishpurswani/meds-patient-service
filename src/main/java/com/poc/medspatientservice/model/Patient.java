package com.poc.medspatientservice.model;

import java.time.LocalDate;
import java.util.List;

public class Patient {

	private Long id;

	private String name;

	private int age;

	private String gender;

	private Long accessionNumber;

	private LocalDate dob;
	
	private List<Medication> medication;

	public List<Medication> getMedication() {
		return medication;
	}

	public void setMedication(List<Medication> medication) {
		this.medication = medication;
	}

	public Patient() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(Long accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", accessionNumber="
				+ accessionNumber + ", dob=" + dob + ", medication=" + medication + "]";
	}
	
	
}
