package net.lunalabs.central.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.patient.Patient;
import net.lunalabs.central.service.mysql.PatientService;
import net.lunalabs.central.web.dto.CMRespDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PatientController {

	@Qualifier("MysqlPatientService")
	private final PatientService patientService;

	@GetMapping("/patient")
	public CMRespDto<?> findAll() {

		return new CMRespDto<>(1, "환자 더미데이터", patientService.findAll());

	}

	@GetMapping("/patient/{id}")
	public CMRespDto<?> findById(@PathVariable int id) {

		return new CMRespDto<>(1, "Id로 환자 조회", patientService.findById(id));
	}

//	@GetMapping("/patient2")
//	public List<Patient> findAll2() {
//		return patientService.findAll2();
//	}

	@GetMapping("/patient") //?patientUserId=""
	public CMRespDto<?> findByPatientUserId(@RequestParam(value="patientUserId") String patientUserId) {

		return new CMRespDto<>(1, "환자 UserId로 조회", patientService.findByPatinetUserId(patientUserId));
	}
	
	
	
	@GetMapping("/patient") //?name=""
	public CMRespDto<?> findByName(@RequestParam(value="name") String name) {

		return new CMRespDto<>(1, "이름으로 환자 조회", patientService.findByName(name));
	}
}
