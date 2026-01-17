package esiag.back.services;

import esiag.back.models.medical.DPI;
import esiag.back.repositories.DPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DPIService {

    @Autowired
    private DPIRepository DPIRepository;

    public DPI findByIdDPI(Long idDPI) {
        Optional<DPI> optionalDPI = DPIRepository.findById(idDPI);
        return optionalDPI.orElse(null);
    }

    public List<DPI> findByPatientId(Long idPatient) {
        return DPIRepository.findByPatientIdPatient(idPatient);
    }


    public List<DPI> findAllDPIs(){
        return DPIRepository.findAll();
    }

    public boolean deleteDPI(Long idDPI) {
        Optional<DPI> optionalDPI = DPIRepository.findById(idDPI);
        if (optionalDPI.isPresent()) {
            optionalDPI.ifPresent(DPI -> DPIRepository.delete(DPI));
            return true;
        }
        return false;
    }

    public DPI saveDPI(DPI dpi) {
        return DPIRepository.save(dpi);
    }
}