package ma.insea.asi.covoiturage.controllers;

import ma.insea.asi.covoiturage.models.User;
import ma.insea.asi.covoiturage.payloads.JwtResponse;
import ma.insea.asi.covoiturage.payloads.LoginRequest;
import ma.insea.asi.covoiturage.payloads.MessageResponse;
import ma.insea.asi.covoiturage.payloads.SignupRequest;
import ma.insea.asi.covoiturage.payloads.request.RegisterRequest;
import ma.insea.asi.covoiturage.repository.UserRepository;
import ma.insea.asi.covoiturage.security.jwt.JwtUtils;
import ma.insea.asi.covoiturage.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(userDetails.getId(),jwt,
                userDetails.getUsername(), userDetails.getEmail(), userDetails.getTelephone(), userDetails.getNom(), userDetails.getDescription()
        ));

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username déjà utiliser"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email déjà utiliser"));
        }

        try{
        // Create new user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setTelephone(signUpRequest.getTelephone());
        user.setUsername(signUpRequest.getUsername());
        user.setDescription(signUpRequest.getDesccription());
        userRepository.save(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Une erreur est survenue, assurez vous que tous les champs sont valides"));
        }

        return ResponseEntity.ok(new MessageResponse("Votre compte a été ajouté avec succè!"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RegisterRequest registerRequest){
        var user = userRepository.findById(registerRequest.getId()).get();

        if(!registerRequest.getUsername().equals(user.getUsername()) && userRepository.existsByEmail(registerRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username déjà utiliser"));
        }
        if (!registerRequest.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email déjà utiliser"));
        }

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setNom(registerRequest.getNom());
        user.setDescription(registerRequest.getDescription());
        user.setTelephone(registerRequest.getTelephone());
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Votre compte a été mise à jour avec succè!"));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){
        var user = userRepository.findById(userId).get();
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        user.setOffres(null);
        return ResponseEntity.ok().body(user);
    }
}
