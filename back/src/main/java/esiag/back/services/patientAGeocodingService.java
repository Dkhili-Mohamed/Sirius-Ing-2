package esiag.back.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.patientArepository;

@DependsOn("ambulanceGeocodingService")
@Service
public class patientAGeocodingService {

    private final patientArepository patientArepository;
    private final GeocodingService geocodingService;

    public patientAGeocodingService(patientArepository patientArepository,
                                     GeocodingService geocodingService) {
        this.patientArepository = patientArepository;
        this.geocodingService = geocodingService;
    }

    @PostConstruct
    @Transactional
    public void geocodeAllPatientsA() {

        List<PatientA> patients = patientArepository.findAll();

        for (PatientA patient : patients) {

            if (patient.getAdressepatientA() != null &&
                patient.getPatientAlatitude() == null &&
                patient.getPatientAlongitude() == null) {

                double[] coords = geocodingService.getCoordinates(
                        patient.getAdressepatientA()
                );

                if (coords != null) {
                    patient.setPatientAlatitude(coords[0]);
                    patient.setPatientAlongitude(coords[1]); 
                    patientArepository.save(patient);
                }
            }
        }
    }
}
