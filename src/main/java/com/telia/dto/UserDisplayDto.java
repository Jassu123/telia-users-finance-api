package com.telia.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Schema(
        description = "UserDisplay Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDisplayDto {

    private Long id;
    private Long personalNumber;
    private String fullName;
    @Past(message = "Date of birth must be in the past")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
}
