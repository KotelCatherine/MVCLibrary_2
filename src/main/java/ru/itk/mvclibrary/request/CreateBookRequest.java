package ru.itk.mvclibrary.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Запрос на создание новой книги")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @Schema(description = "Название книги")
    @JsonProperty("title")
    @NotBlank
    private String title;

    @Schema(description = "Автор книги")
    @JsonProperty("author_id")
    @NotBlank
    private UUID authorId;

}
