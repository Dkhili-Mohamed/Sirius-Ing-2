package esiag.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.AmbulanceLogin;
import esiag.back.repositories.AmbulanceLoginRepository;

@Service
public class AmbulanceLoginService {

    @Autowired
    private AmbulanceLoginRepository ambulanceLoginRepository;

    public AmbulanceLogin login(String adresse, String mdps) {
        return ambulanceLoginRepository.findByAmbulanceloginadresseAndAmbulanceloginmdps(adresse, mdps);
    }

}