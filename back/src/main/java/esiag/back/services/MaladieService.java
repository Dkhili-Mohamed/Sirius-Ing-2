package esiag.back.services;

import esiag.back.models.medical.DPI;
import esiag.back.models.medical.Maladie;
import esiag.back.repositories.MaladieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaladieService {

    @Autowired
    private MaladieRepository MaladieRepository;

    public Maladie findByIdMaladie(Long idMaladie) {
        Optional<Maladie> optionalMaladie = MaladieRepository.findById(idMaladie);
        return optionalMaladie.orElse(null);
    }


    public List<Maladie> findAllMaladies(){
        return MaladieRepository.findAll();
    }

    public boolean deleteMaladie(Long idMaladie) {
        Optional<Maladie> optionalMaladie = MaladieRepository.findById(idMaladie);
        if (optionalMaladie.isPresent()) {
            optionalMaladie.ifPresent(Maladie -> MaladieRepository.delete(Maladie));
            return true;
        }
        return false;
    }

    public Maladie saveMaladie(Maladie maladie) {
        return MaladieRepository.save(maladie);
    }
}