package ru.itk.mvclibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Schema(description = "Данные об авторе")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    @Schema(description = "Идентификатор автора")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Имя автора")
    @JsonProperty("firstname")
    private String firstName;

    @Schema(description = "Фамилия автора")
    @JsonProperty("lastname")
    private String lastName;

}
