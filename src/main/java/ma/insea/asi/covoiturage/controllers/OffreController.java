package ma.insea.asi.covoiturage.controllers;

import ma.insea.asi.covoiturage.models.*;
import ma.insea.asi.covoiturage.payloads.MessageResponse;
import ma.insea.asi.covoiturage.payloads.Recherche;
import ma.insea.asi.covoiturage.payloads.request.OffreRequest;
import ma.insea.asi.covoiturage.repository.DemandeRepository;
import ma.insea.asi.covoiturage.repository.EscaleRepository;
import ma.insea.asi.covoiturage.repository.OffreRepository;
import ma.insea.asi.covoiturage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/offre")
public class OffreController {
    private final OffreRepository offreRepository;
    private final UserRepository userRepository;
    private final DemandeRepository demandeRepository;
    @Autowired
    private EscaleRepository escaleRepository;

    public OffreController(OffreRepository offreRepository, UserRepository userRepository, DemandeRepository demandeRepository) {
        this.offreRepository = offreRepository;
        this.userRepository = userRepository;
        this.demandeRepository = demandeRepository;
    }

    @PostMapping("/ajout/{userId}")
    public ResponseEntity<?> ajout(@Valid @RequestBody OffreRequest offreRequest, @PathVariable("userId") long userId){
        //var offreur = userRepository.findById(offreRequest.getOffreur()).get();
        var offreur = userRepository.findById(userId).get();
        var offre = new Offre(
                offreRequest.getPrix(), offreRequest.getNbrePlace(), offreRequest.isEstConducteur(),
                offreRequest.getLieuDepart(), offreRequest.getLieuArrivee(), offreRequest.getDateDepart(), offreRequest.isAlternance(),
                offreur, offreRequest.getEscales(), offreRequest.getVoiture());
        for(Escale e : offre.getEscales())
        {
            e.setId(null);
            e.setOffre(offre);
            System.out.println(e);
        }
        this.offreRepository.save(offre);
        for(Escale e : offre.getEscales())
        {
            e.setOffre(offre);
            System.out.println(e);
            escaleRepository.save(e);
        }
        return ResponseEntity.ok(new MessageResponse("Ajout effectué avec succes"));
    }

    @PostMapping("/recherche")
    public ResponseEntity<?> recherche(@Valid @RequestBody Recherche recherche){
        var offres = offreRepository.findAll();
        var l = new ArrayList<>(offres);
        /*var search = new Offre();
        if(recherche.getPrix() != null)
            search.setPrix(Float.parseFloat(recherche.getPrix()));
        search.setDateDepart(recherche.getDateDepart());
        search.setLieuDepart(recherche.getLieuDepart());
        search.setLieuArrivee(recherche.getLieuArrivee());
        var returnValue = offreRepository.findAll(Example.of(search));
        returnValue.forEach(System.out::println);*/
        //??type voiture after
        try{
            Calendar calendar = Calendar.getInstance();
            for (Offre offre: offres) {
                if(recherche.getPrix() != null && !recherche.getPrix().equals("") && offre.getPrix() != Float.parseFloat(recherche.getPrix())){
                    l.remove(offre);
                }
                if(recherche.getLieuArrivee() != null && !offre.getLieuArrivee().equals(recherche.getLieuArrivee()) ){
                    l.remove(offre);
                }
                if(recherche.getLieuDepart() != null && offre.getLieuDepart() != null && !offre.getLieuDepart().equals(recherche.getLieuDepart()) ){
                    l.remove(offre);
                }
                if(recherche.getTypeVoiture() != null &&  !offre.getVoiture().getType().equals(recherche.getTypeVoiture()) ){
                    l.remove(offre);
                }

                if(recherche.getEscales() != null ){
                    var delete = true;
                    for(Escale e : offre.getEscales()){
                        if(e.getLieu().equals(recherche.getEscales())) {
                            delete = false;
                            break;
                        }
                    }
                    if(delete)
                        l.remove(offre);
                }
                calendar.setTime(recherche.getDateDepart());
                if(recherche.getDateDepart() != null && calendar.get(Calendar.YEAR) != 1 && !compareDate(recherche.getDateDepart(), offre.getDateDepart())){
                    l.remove(offre);
                }
            }
            for (Offre offre : l){
                offre.getOffreur().setOffres(null);
                for (Escale e : offre.getEscales())
                    e.setOffre(null);
                offre.getVoiture().setOffres(null);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Une erreur c'est produite"));
        }
        return ResponseEntity.ok(l);
    }

    @GetMapping("/offres")
    public ResponseEntity<?> getAll(){
        //return ResponseEntity.ok(offreRepository.findAllByDateDepartGreaterThanOrderByDateDepartAsc(new Date()));
        var offres = offreRepository.findAll();
        for (Offre offre : offres){
            offre.getOffreur().setOffres(null);
            for (Escale e : offre.getEscales())
                e.setOffre(null);
            offre.getVoiture().setOffres(null);
        }
        return ResponseEntity.ok(offres);
    }

    @GetMapping("/mesoffres/{userId}")
    public ResponseEntity<?> getUserOffres(@PathVariable("userId") Long userId){
        var offres = userRepository.findById(userId).get().getOffres();
        for (Offre offre : offres){
            offre.setOffreur(null);
            for (Escale e : offre.getEscales())
                e.setOffre(null);
            offre.getVoiture().setOffres(null);
        }
        return ResponseEntity.ok(offres);
    }

    private boolean compareDate(Date d1, Date d2){
        Calendar calendar1 = Calendar.getInstance(), calendar2 = Calendar.getInstance();
        calendar1.setTime(d1);
        calendar2.setTime(d2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&  calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&  calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

}
