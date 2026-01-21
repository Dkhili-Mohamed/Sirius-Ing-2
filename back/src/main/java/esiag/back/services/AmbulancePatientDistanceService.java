package esiag.back.services;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.ambulancerepository;
import esiag.back.repositories.patientArepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbulancePatientDistanceService {

    private final ambulancerepository ambulancerepository;
    private final patientArepository patientArepository;
    private final DistanceService distanceService;

    public AmbulancePatientDistanceService(ambulancerepository ambulancerepository,
                                           patientArepository patientArepository,
                                           DistanceService distanceService) {
        this.ambulancerepository = ambulancerepository;
        this.patientArepository = patientArepository;
        this.distanceService = distanceService;
    }

    public void calculerDistances() {

        PatientA patient = patientArepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        if (patient == null) {
            System.out.println(" Aucun patientA trouvé ");
            return;
        }

        if (patient.getPatientAlatitude() == null || patient.getPatientAlongitude() == null) {
            System.out.println("Le patientA n'a pas encore de coordonnées GPS.");
            return;
        }

        double latP = patient.getPatientAlatitude();
        double lonP = patient.getPatientAlongitude();


        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée en base.");
            return;
        }

        
        for (Ambulance ambulance : ambulances) {

            if (ambulance.getAmbulancelatitude() == null || ambulance.getAmbulancelongitude() == null) {
                System.out.println("Ambulance " + ambulance.getIdambulance()
                        + " n'a pas encore de coordonnées GPS.");
                continue;
            }

            double distance = distanceService.calculerdistance(
                    latP, lonP,
                    ambulance.getAmbulancelatitude(),
                    ambulance.getAmbulancelongitude()
            );

            System.out.println("La distance entre le patient et l'ambulance "
                    + ambulance.getIdambulance()
                    + " = " + distance + " km");
        }
    }
}
