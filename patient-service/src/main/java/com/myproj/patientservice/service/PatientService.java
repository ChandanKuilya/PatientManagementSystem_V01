package com.myproj.patientservice.service;

import com.myproj.patientservice.dto.PatientRequestDTO;
import com.myproj.patientservice.dto.PatientResponseDTO;
import com.myproj.patientservice.exception.EmailAlreadyExistsException;
import com.myproj.patientservice.exception.PatientNotFoundException;
import com.myproj.patientservice.mapper.PatientMapper;
import com.myproj.patientservice.model.Patient;
import com.myproj.patientservice.repository.IPatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    final private IPatientRepository patientRepository;
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO>getPatients(){
        List<Patient> patients = patientRepository.findAll();

        //List<PatientResponseDTO> patientResponseDTOS=patients.stream().map(ptnt-> PatientMapper.toDTO(ptnt)).toList();
        //List<PatientResponseDTO> patientResponseDTOS=patients.stream().map(PatientMapper::toDTO).toList(); //using lambda expressions

        //return patientResponseDTOS;


        //using lambda expressions
        return patients.stream().map(PatientMapper::toDTO).toList();

    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email already exists "+patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toEntityModel(patientRequestDTO)
        );

        return PatientMapper.toDTO(newPatient);
    }


    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
     Patient patient=patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with ID: "+ id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A patient with this email already exists "+patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
