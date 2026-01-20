package esiag.back.repositories;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.dto.PatientStatutParcours;
import esiag.back.models.medical.Parcours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcoursRepository extends JpaRepository<Parcours,Long> {

    @Query("""
        SELECT new esiag.back.models.dto.ParcoursPatient(
            a.idActeMedical,
            p.idParcours,
            t.idTypeActeMedical,
            e.idEspace,
            s.idSalle,
            a.ordre,
            t.libelle,
            a.statut,
            e.numeroEspace
        )
        FROM ActeMedical AS a
        JOIN a.parcours AS p
        JOIN a.typeActeMedical AS t
        LEFT JOIN a.salle AS s
        LEFT JOIN s.espace AS e
        WHERE p.idParcours IN (
            SELECT sui.parcours.idParcours
            FROM Suivre sui
            WHERE sui.patient.idPatient = :idPatient
        )
        AND p.statutGlobal <> esiag.back.models.medical.StatutActeMedical.TERMINE
        ORDER BY a.ordre ASC
    """)
    List<ParcoursPatient> getParcoursByPatientId(@Param("idPatient") Long idPatient);

    @Query("SELECT new esiag.back.models.dto.PatientStatutParcours(pa.idParcours, p.idPatient, pa.nomParcours, p.nomPatient, p.prenomPatient, pa.statutGlobal) " +
       "FROM Suivre s " +
       "JOIN s.parcours pa " +
       "JOIN s.patient p")
    List<PatientStatutParcours> findAllPatientStatutParcours();
}
