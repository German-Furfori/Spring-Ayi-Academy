package com.ayi.curso.rest.serv.app.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "persona")
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPerson;

    @Column(name = "nombre", nullable = false, length = 50)
    private String firstName;

    @Column(name = "apellido", nullable = false, length = 50)
    private String lastName;

    @Column(name = "tipo_documento", nullable = false, length = 15)
    private String typeDocument;

    @Column(name = "numero_documento", nullable = false)
    private Integer numberDocument;

    @Column(name = "fec_nacimiento", nullable = false)
    private LocalDate dateBorn;

    @Column(name = "fec_creacion", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "fec_modificacion")
    private LocalDate dateModified;

}