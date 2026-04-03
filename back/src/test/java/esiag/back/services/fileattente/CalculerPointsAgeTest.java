package esiag.back.services.fileattente;

import esiag.back.models.medical.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculerPointsAgeTest {

    final private PatientService patientService = new PatientService();


    @Test
    public void testCalculerPointsAgeNull() {
        Patient patientNull = new Patient();
        patientNull.setAgePatient(null);

        int scoreAgeNull = patientService.calculerPointsAge(null);
        assertEquals(0, scoreAgeNull);

    }

    @Test
    public void testCalculerPointsAgeNegatif() {
        Patient patientNegatif = new Patient();
        patientNegatif.setAgePatient(-1);

        int scoreAgeNegatif = patientService.calculerPointsAge(-1);
        assertEquals(0, scoreAgeNegatif);



    }

    @Test
    public void testCalculerPointsAgeZero() {
        Patient patientZero = new Patient();
        patientZero.setAgePatient(0);

        int scoreAgeZero = patientService.calculerPointsAge(0);
        assertEquals(0, scoreAgeZero);

    }



    @Test
    public void testCalculerPointsAgeNourrisson() {
        Patient patientNourrisson = new Patient();
        patientNourrisson.setAgePatient(1);

        int scoreAgeNourrisson = patientService.calculerPointsAge(1);
        assertEquals(4, scoreAgeNourrisson);


    }

    @Test
    public void testCalculerPointsAgePetitEnfant() {
        Patient patientPetitEnfant = new Patient();
        patientPetitEnfant.setAgePatient(4);

        int scoreAgePetitEnfant = patientService.calculerPointsAge(4);
        assertEquals(3, scoreAgePetitEnfant);


    }

    @Test
    public void testCalculerPointsAgeEnfant() {
        Patient patientEnfant = new Patient();
        patientEnfant.setAgePatient(10);

        int scoreAgeEnfant =  patientService.calculerPointsAge(10);
        assertEquals(2, scoreAgeEnfant);


    }

    @Test
    public void testCalculerPointsAgeAdolescent() {
        Patient patientAdolescent = new Patient();
        patientAdolescent.setAgePatient(15);

        int scoreAgeAdolescent =  patientService.calculerPointsAge(15);
        assertEquals(1, scoreAgeAdolescent);


    }

    @Test
    public void testCalculerPointsAgeAdulte() {
        Patient patientAdulte = new Patient();
        patientAdulte.setAgePatient(25);

        int scoreAgeAdulte =  patientService.calculerPointsAge(25);
        assertEquals(0, scoreAgeAdulte);



    }

    @Test
    public void testCalculerPointsAgeSenior() {
        Patient patientSenior = new Patient();
        patientSenior.setAgePatient(65);

        int scoreAgeSenior = patientService.calculerPointsAge(65);
        assertEquals(3, scoreAgeSenior);
    }



}
