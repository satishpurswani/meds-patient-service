package com.poc.medspatientservice.controller.impl;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.medspatientservice.controller.IMedicinePatientController;
import com.poc.medspatientservice.model.Medication;
import com.poc.medspatientservice.model.Patient;
import com.poc.medspatientservice.service.IMedicinePatientService;

@RestController
@RequestMapping("/medspatient")
public class MedicinePatientController implements IMedicinePatientController {

	@Autowired
	private IMedicinePatientService iMedicinePatientService;
	
	@GetMapping("/getPatientDetailedInfo/{id}")
	public ResponseEntity<?> getPatientInfo(@PathVariable("id") Long id) {
		if(id==null) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(iMedicinePatientService.getPatientInformation(id), HttpStatus.OK);
	}
	
	@PostMapping("/savePatients")
	public ResponseEntity<?> saveAllPatients(@RequestBody List<Patient> patientDetails){
		if(null==patientDetails) {
			return ResponseEntity.badRequest().build();
		}
		List<Patient> p = iMedicinePatientService.storeAllPatients(patientDetails);
		if(null==p) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@PostMapping("/addMeds")
	public ResponseEntity<?> saveAllMedicines(@RequestBody List<Medication> medsDetails){
		if(null==medsDetails) {
			return ResponseEntity.badRequest().build();
		}
		List<Medication> m = iMedicinePatientService.storeAllMedicineInformation(medsDetails);
		if(null==m) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@PostMapping("/savePatientDetailedInfo")
	public ResponseEntity<?> savePatientDetailedInfo(@RequestBody List<Patient> medsDetails){
		if(medsDetails==null && medsDetails.stream().anyMatch( t ->
		t.getMedication().stream().anyMatch(p->p.getName()!=null)
		)) {
			return ResponseEntity.badRequest().build();
		}
		boolean status = iMedicinePatientService.storePatientDetailedInfo(medsDetails);
		return new ResponseEntity<>(false, HttpStatus.OK);
	}
}
