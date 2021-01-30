package ma.insea.asi.covoiturage.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 20, min = 10)
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String telephone;

    @NotBlank @Size(max = 120, min = 5)
    private String password;

    @NotBlank
    private String nom;

    @NotBlank
    private String description;

    @OneToMany(targetEntity = Offre.class, mappedBy = "offreur")
    private Set<Offre> offres = new HashSet<>();

}
