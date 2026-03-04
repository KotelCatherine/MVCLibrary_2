package ru.itk.mvclibrary.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

}
