package esiag.back.services.ambulance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.models.ambulance.PatientA;
import esiag.back.services.DistanceService;

public class AmbulancePatientDistanceServiceTest {

    private DistanceService distanceService = new DistanceService();

    @Test
    public void testCalculerDistance() {

        // Création d'un patient
        PatientA patient = new PatientA();
        patient.setPatientAlatitude(48.8769404);
        patient.setPatientAlongitude(2.3577921);

        // Création dune ambulance
        Ambulance ambulance = new Ambulance();
        ambulance.setAmbulancelatitude(48.883371);
        ambulance.setAmbulancelongitude(2.3718898);

        // Calcul de la distance entre l'ambulance et le patient
        double distance = distanceService.calculerdistance(
                patient.getPatientAlatitude(),
                patient.getPatientAlongitude(),
                ambulance.getAmbulancelatitude(),
                ambulance.getAmbulancelongitude()
        );

        // ici je lance le teste
        assertNotNull(distance);
        assertEquals(1.2546167681999605, distance, 0.0001);
    }
}


