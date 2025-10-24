package com.myproj.patientservice.mapper;

import com.myproj.patientservice.dto.PatientRequestDTO;
import com.myproj.patientservice.dto.PatientResponseDTO;
import com.myproj.patientservice.model.Patient;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient p){
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(p.getId().toString());
        patientDTO.setName(p.getName());
        patientDTO.setAddress(p.getAddress());
        patientDTO.setEmail(p.getEmail());
        patientDTO.setDateOfBirth(p.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toEntityModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;
    }
}
