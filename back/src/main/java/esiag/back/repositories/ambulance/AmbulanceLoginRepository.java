package esiag.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esiag.back.models.ambulance.AmbulanceLogin;

@Repository
public interface AmbulanceLoginRepository extends JpaRepository<AmbulanceLogin, Long> {

    AmbulanceLogin findByAmbulanceloginadresseAndAmbulanceloginmdps(String ambulanceloginadresse,String ambulanceloginmdps);

}
