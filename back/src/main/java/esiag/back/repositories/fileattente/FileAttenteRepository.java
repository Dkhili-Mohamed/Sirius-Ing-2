package esiag.back.repositories.fileattente;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.models.medical.FileAttente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileAttenteRepository extends JpaRepository<FileAttente, Long> {

    @Query("SELECT COUNT(fa) FROM FileAttente fa")
    long countNombrePatients();

    @Query("SELECT COUNT(fa) FROM FileAttente fa WHERE fa.patient.statutPatient = 'URGENT' ")
    long countNombrePatientsUrgents();

    @Query("SELECT COUNT(fa) FROM FileAttente fa WHERE fa.patient.statutPatient = 'INTERMEDIAIRE' ")
    long countNombrePatientsIntermediaires();

    @Query("SELECT COUNT(fa) FROM FileAttente fa WHERE fa.patient.statutPatient = 'NON_URGENT' ")
    long countNombrePatientsNonUrgents();


}
