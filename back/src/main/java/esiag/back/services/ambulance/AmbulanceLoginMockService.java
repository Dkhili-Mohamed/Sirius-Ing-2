package esiag.back.services.ambulance;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.AmbulanceLogin;
import esiag.back.repositories.ambulance.AmbulanceLoginRepository;

@Service
public class AmbulanceLoginMockService {

    private final AmbulanceLoginRepository ambulanceLoginRepository;
    private final Random random = new Random();

    private final List<String> domaines = List.of("@gmail.com", "@yahoo.com");
    private final List<String> lettres = List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
    public AmbulanceLoginMockService(AmbulanceLoginRepository ambulanceLoginRepository) {
        this.ambulanceLoginRepository = ambulanceLoginRepository;
    }

    public void genererAmbulanceLogin() {
        ambulanceLoginRepository.deleteAll();

        for (int i = 0; i < 3; i++) {
            AmbulanceLogin login = new AmbulanceLogin();
            // créer les adresses email
            String email = "user" + (i+1) + domaines.get(random.nextInt(domaines.size()));
            login.setAmbulanceloginadresse(email);
            // créer des mots de passe de 6 lettres et 2 chiffres
            login.setAmbulanceloginmdps(genererMdps());

            ambulanceLoginRepository.save(login);
            System.out.println("Login créé ");
        }
    }

    
    private String genererMdps() {
        String mdps = "";
        for (int i = 0; i < 6; i++) {
            mdps += lettres.get(random.nextInt(lettres.size()));
        }
        
        int chiffre = 10 + random.nextInt(90); 
        mdps += chiffre;

        return mdps;
    }
}
