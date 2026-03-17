package ru.itk.mvclibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Данные о книге")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    @Schema(description = "Идентификатор книги")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Название книги")
    @JsonProperty("title")
    private String title;

    @Schema(description = "Автор книги")
    @JsonProperty("author")
    private AuthorDto author;

}
