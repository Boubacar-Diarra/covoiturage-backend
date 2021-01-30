package ma.insea.asi.covoiturage.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private Long id;
    private String username;
    private String email;
    private String telephone;
    private String password;
    private String nom;
    private String description;
}
