package com.telia.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Schema(
        description = "UserDto Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;
    @Schema(
            description = "User Personal Number"
    )

    private Long personalNumber;
    @Schema(
            description = "User Full name"
    )
    // User Full name should not be null or empty
    @NotEmpty(message = "User Full name should not be null or empty")
    private String fullName;
    @Schema(
            description = "User Date of birth"
    )
    //@NotEmpty(message = "Date of birth shouldn't be empty")
    @Past(message = "Date of birth must be in the past")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    @Schema(
            description = "User Email Adress"
    )
    // User email should not be null or empty
    // Email address should be valid
    @NotEmpty(message = "User email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String emailAddress;
    @Schema(
            description = "user Phone Number"
    )
    private String phoneNumber;
}
