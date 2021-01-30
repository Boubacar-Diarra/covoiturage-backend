package ma.insea.asi.covoiturage.controllers;

import ma.insea.asi.covoiturage.models.Demande;
import ma.insea.asi.covoiturage.models.Offre;
import ma.insea.asi.covoiturage.models.User;
import ma.insea.asi.covoiturage.payloads.MessageResponse;
import ma.insea.asi.covoiturage.repository.DemandeRepository;
import ma.insea.asi.covoiturage.repository.OffreRepository;
import ma.insea.asi.covoiturage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@RequestMapping("/api/demande")
@RestController
public class DemandeController {
    private final DemandeRepository demandeRepository;
    private final OffreRepository offreRepository;
    @Autowired
    UserRepository userRepository;

    public DemandeController(DemandeRepository demandeRepository, OffreRepository offreRepository) {
        this.demandeRepository = demandeRepository;
        this.offreRepository = offreRepository;
    }

    @PutMapping("/accepter/{userId}/{offreId}")
    public ResponseEntity<?> accepter(@PathVariable("userId") Long userId, @PathVariable("offreId") Long offreId){
        var demande = demandeRepository.findDemande(userId, offreId);
        demande.setEtat("accepté");
        demandeRepository.save(demande);
        return ResponseEntity.ok(new MessageResponse("Demande accepté avec succès"));
    }

    @PutMapping("/refuser/{userId}/{offreId}")
    public ResponseEntity<?> refuser(@PathVariable("userId") Long userId, @PathVariable("offreId") Long offreId){
        var demande = demandeRepository.findDemande(userId, offreId);
        demande.setEtat("refusé");
        demandeRepository.save(demande);
        return ResponseEntity.ok(new MessageResponse("Demande refusé avec succès"));
    }

    @GetMapping("/demandeurs/{offreId}")
    public ResponseEntity<?> demandeur(@PathVariable("offreId") Long offreId){
        var demandes = demandeRepository.findByOffre(offreRepository.findById(offreId).get());
        var demandeurs = new ArrayList<User>();
        for(Demande d : demandes){
            d.getDemandeur().setOffres(new HashSet<>());
            demandeurs.add(d.getDemandeur());
        }
        return ResponseEntity.ok(demandeurs);
    }

    @GetMapping("/demandes/{offreId}")
    public ResponseEntity<?> demandes(@PathVariable("offreId") Long offreId){
        var demandes = demandeRepository.findByOffre(offreRepository.findById(offreId).get());
        //var demandeurs = new ArrayList<User>();
        for(Demande d : demandes){
            d.setOffre(null);
            d.setDemandeur(null);
            //demandeurs.add(d.getDemandeur());
        }
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/mesdemandes/{userId}")
    public ResponseEntity<?> userDemande(@PathVariable("userId") Long userId){
        var user = userRepository.findById(userId).get();
        var demandes = demandeRepository.findByDemandeur(user);
        for(Demande d : demandes){
            d.setDemandeur(null);
            d.getOffre().setOffreur(null);
            d.getOffre().getVoiture().setOffres(null);
            d.getOffre().setEscales(null);
        }
        return ResponseEntity.ok(demandes);
    }

    @PostMapping("/postuler/{userId}/{offreId}")
    public ResponseEntity<?> postule(@PathVariable("userId") Long userId, @PathVariable("offreId") Long offreId){
        var user = userRepository.findById(userId).get();
        var offre = offreRepository.findById(offreId).get();
        /*if(offre.getDemandeurs() == null)
            offre.setDemandeurs(new HashSet<>());
        offre.getDemandeurs().add(user);
        offreRepository.save(offre);*/
        if(demandeRepository.findDemande(userId, offreId) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Demande déjà effectuée"));
        }
        else{
            var demande = new Demande(new Date(), "en cours", user, offre);
            demandeRepository.save(demande);
            return ResponseEntity.ok(new MessageResponse("Votre demande a été prise en compte"));
        }
    }
    @DeleteMapping("/annuler/{demandeId}")
    public ResponseEntity<?> annuler(@PathVariable("demandeId") long demandeId)
    {
        System.out.println(demandeId);
        try{
            var demande = demandeRepository.findById(demandeId).get();
            demandeRepository.delete(demande);
            return ResponseEntity.ok(new MessageResponse("Demande annulée avec succès"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Une erreur est survenue"));
        }
    }
}
