package ma.insea.asi.covoiturage.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank @NotNull
    @Size(max = 20, min = 10)
    private String username;

    @Email
    @NotBlank @NotNull
    private String email;

    @NotBlank @NotNull @Size(min = 12, max = 12)
    private String telephone;

    @NotBlank @Size(min = 12, max = 12)
    private String desccription;

    @NotBlank @NotNull
    @Size(max = 120, min = 5)
    private String password;
}
