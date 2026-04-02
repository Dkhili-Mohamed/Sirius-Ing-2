package esiag.back.controllers.ambulance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.ambulance.AmbulanceLogin;
import esiag.back.services.ambulance.AmbulanceLoginService;

@CrossOrigin(origins = "http://172.31.253.208:3000")
@RestController
@RequestMapping("/api/ambulancelogin")
public class AmbulanceLoginController {

    @Autowired
    private AmbulanceLoginService ambulanceLoginService;

    @PostMapping("/login")
    public ResponseEntity<AmbulanceLogin> login(@RequestBody AmbulanceLogin login) {

        AmbulanceLogin resultat = ambulanceLoginService.login(login.getAmbulanceloginadresse(),login.getAmbulanceloginmdps());
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }
}
