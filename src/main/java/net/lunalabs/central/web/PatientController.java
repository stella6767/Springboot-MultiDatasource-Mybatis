package net.lunalabs.central.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.mysql.Patient;
import net.lunalabs.central.service.mysql.PatientService;


@Slf4j
@RequiredArgsConstructor
@RestController
public class PatientController {

	
	@Qualifier("MysqlPatientService")
	private final PatientService patientService;
	
	
	
	@GetMapping("/patient")
	public List<Patient> findAll() {
		
		return patientService.findAll();
		
	}
	
	@GetMapping("/patient2")
	public List<net.lunalabs.central.domain.oracle.Patient> findAll2() {
		
		return patientService.findAll2();
		
	}
	
}
