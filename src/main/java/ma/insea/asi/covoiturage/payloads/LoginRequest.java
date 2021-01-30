package ma.insea.asi.covoiturage.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    @Size(max = 20, min = 10)
    private String username;

    @NotBlank @Size(max = 120, min = 5)
    private String password;

}
