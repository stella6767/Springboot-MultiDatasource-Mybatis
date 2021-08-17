package net.lunalabs.central.service.oracle;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.mapper.oracle.PatientMapper;


@RequiredArgsConstructor
@Service
public class PatientService {

	private final PatientMapper patientMapper;

	
	
}
