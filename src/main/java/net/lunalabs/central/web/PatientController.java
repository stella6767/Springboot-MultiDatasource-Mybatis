package net.lunalabs.central.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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



	//@GetMapping(value = "/patient/search", consumes = MediaType.APPLICATION_JSON_VALUE) //?patientUserId=""
	//@RequestMapping(value = "/patient/search", method = RequestMethod.GET, consumes = "application/json")
	@GetMapping("/patient/search")
	public CMRespDto<?> searchByPatientUserIdOrName(@RequestParam(value="searchType") String searchType, @RequestParam(value="searchWord") String searchWord) {

		
		log.info("searchType: " + searchType +  " , searchWord:  " + searchWord);
		
		return new CMRespDto<>(1, "환자 Search", patientService.searchByIdOrName(searchType, searchWord));
		
		
	}
	
	
	
}
