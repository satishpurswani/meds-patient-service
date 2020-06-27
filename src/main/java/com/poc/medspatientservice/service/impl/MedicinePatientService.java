package com.poc.medspatientservice.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.poc.medspatientservice.model.Medication;
import com.poc.medspatientservice.model.MedsPatient;
import com.poc.medspatientservice.model.Patient;
import com.poc.medspatientservice.service.IMedicinePatientService;

@Service
public class MedicinePatientService implements IMedicinePatientService {

	@Autowired
	private RestTemplate restTemplate;

	private static final String PATIENT_URL = "http://patient-db-server/patient/";
	private static final String PATIENT_MEDS_RELATION_URL = "http://meds-db-server/patientmedication/getPatientMedsInfo/";
	private static final String MEDICINE_URL = "http://meds-db-server/meds/MedsInfo";
	private static final String ADD_PATIENT_URL = "http://patient-db-server/patient/addPatients";
	private static final String ADD_MEDICINE_URL ="http://meds-db-server/meds/addMeds";
	private static final String ADD_PATIENT_MEDICINE_INFO_URL = "http://meds-db-server/patientmedication/addPatientMedsInfo";
	
	@Override
	public Patient getPatientInformation(Long id) {
		String final_url = PATIENT_URL + id;
		ResponseEntity<Patient> patientEntity = restTemplate.exchange(final_url, HttpMethod.GET, null, Patient.class);
		ResponseEntity<List<MedsPatient>> patientMedsRelation = null;
		ResponseEntity<List<Medication>> medicineEntity = null;
		System.out.println(">>>>>>>>>>>>>>>> patientEntity" + patientEntity);
		if(patientEntity.getStatusCode().equals(HttpStatus.OK) && null!=patientEntity.getBody()){
			patientMedsRelation = restTemplate.exchange(PATIENT_MEDS_RELATION_URL + patientEntity.getBody().getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<MedsPatient>>() {
			});
		}else {
			return null;
		}
		
		System.out.println("============= patientMedsRelation" + patientMedsRelation);
		if(patientMedsRelation.getStatusCode().equals(HttpStatus.OK) && null!=patientMedsRelation.getBody()) {
			List<Medication> requestBody = patientMedsRelation.getBody().stream().mapToLong(m->m.getMedids()).mapToObj( new LongFunction<Medication>() {

				@Override
				public Medication apply(long value) {
					Medication m = new Medication();
					m.setId(value);
					return m;
				}
			}).collect(Collectors.toList());
			medicineEntity = restTemplate.exchange(MEDICINE_URL, HttpMethod.POST, getEntity(requestBody), new ParameterizedTypeReference<List<Medication>>() {
			});
			System.out.println("============= medicineEntity" + medicineEntity);
			
			patientEntity.getBody().setMedication(medicineEntity.getBody());
		}else {
			return patientEntity.getBody();
		}
		return patientEntity.getBody();
	}

	@Override
	public List<Patient> storeAllPatients(List<Patient> patientDetails) {
		
		ResponseEntity<List<Patient>> p = restTemplate.exchange(ADD_PATIENT_URL, HttpMethod.POST, getEntity(patientDetails), new ParameterizedTypeReference<List<Patient>>() {
		});
		if(p.getStatusCode().equals(HttpStatus.OK) && null!=p.getBody()) {
			return p.getBody();
		}
		return null;
	}

	@Override
	public List<Medication> storeAllMedicineInformation(List<Medication> medsDetails) {
		ResponseEntity<List<Medication>> m= restTemplate.exchange(ADD_MEDICINE_URL, HttpMethod.POST, getEntity(medsDetails), new ParameterizedTypeReference<List<Medication>>() {
		});
		if(m.getStatusCode().equals(HttpStatus.OK) && null!=m.getBody()) {
			return m.getBody();
		}
		return null;
	}
	
	private <T> HttpEntity<?> getEntity(T tBody){
		return new HttpEntity<>(tBody, getHeaders());
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Override
	public boolean storePatientDetailedInfo(List<Patient> medsDetails) {
		if(null!=storeAllPatients(medsDetails) && null!=storeAllMedicineInformation(medsDetails.stream().map(new Function<Patient, Medication>() {

			@Override
			public Medication apply(Patient t) {
				for(Medication med : t.getMedication()) {
					return med;
				}
				return null;
			}
		}).collect(Collectors.toList()) )){
			//TODO : save Meds Patient Relation
		}
		return false;
	}
}
