package esiag.back.repositories;

import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttenteRepository extends JpaRepository<FileAttente, Long> {

}
