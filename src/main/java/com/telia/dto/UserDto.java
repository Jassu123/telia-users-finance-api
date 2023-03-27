package com.telia.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Schema(
        description = "UserDto Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private String dateOfBirth;
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
