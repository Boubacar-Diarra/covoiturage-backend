package ma.insea.asi.covoiturage.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private long id;
    private String token;
    private String username;
    private String email;
    private String telephone;
    private String nom;
    private String description;
}
