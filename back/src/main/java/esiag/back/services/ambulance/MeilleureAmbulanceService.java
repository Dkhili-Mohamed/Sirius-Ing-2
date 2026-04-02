package esiag.back.services.ambulance;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.models.ambulance.Intervention;
import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.ambulance.ambulancerepository;
import esiag.back.repositories.ambulance.InterventionRepository;
import esiag.back.repositories.ambulance.patientArepository;

@Service
public class MeilleureAmbulanceService {

    private final ambulancerepository ambulancerepository;
    private final InterventionRepository interventionrepository;
    private final patientArepository patientArepository;

    @Autowired
    public MeilleureAmbulanceService(ambulancerepository ambulancerepository, InterventionRepository interventionrepository,
        patientArepository patientArepository) {
        this.ambulancerepository = ambulancerepository;
        this.interventionrepository = interventionrepository;
        this.patientArepository = patientArepository;
    }


    
    public Long MeilleureAmbulance() {
        List<Ambulance> ambulances = ambulancerepository.findAll();
        double max = 0;   
        Long idMeilleure = null;
        Ambulance meilleure = null;

        for (Ambulance a : ambulances) {
            if (a.getNoteglobale() != null && a.getNoteglobale() > max) {
                max = a.getNoteglobale();
                idMeilleure = a.getIdambulance();
                meilleure = a;
            }
        }
        // ajouter l'intervention dans l'historique
        if (meilleure != null) {
            PatientA patient = patientArepository.findLastPatientA();
            if (patient != null) {
                boolean dejaAttribue = interventionrepository.existsByAdresseinterventionAndInterventionstatut(
                    patient.getAdressepatientA(),"Ambulance attribuée");
                if (!dejaAttribue) {
                    Intervention intervention = new Intervention();
                    intervention.setNomintervention(patient.getNompatientA());
                    intervention.setAdresseintervention(patient.getAdressepatientA());
                    intervention.setInterventionstatut("Ambulance attribuée");
                    interventionrepository.save(intervention);
                }
            }
        }
        return idMeilleure;
    }
}
