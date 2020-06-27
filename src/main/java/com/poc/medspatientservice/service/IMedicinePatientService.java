package com.poc.medspatientservice.service;

import java.util.List;

import com.poc.medspatientservice.model.Medication;
import com.poc.medspatientservice.model.Patient;

public interface IMedicinePatientService {

	Patient getPatientInformation(Long id);

	List<Patient> storeAllPatients(List<Patient> patientDetails);

	List<Medication> storeAllMedicineInformation(List<Medication> medsDetails);

	boolean storePatientDetailedInfo(List<Patient> medsDetails);

}
