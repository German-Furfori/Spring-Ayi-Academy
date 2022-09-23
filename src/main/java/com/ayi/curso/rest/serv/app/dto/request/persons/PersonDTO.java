package com.ayi.curso.rest.serv.app.dto.request.persons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel( // Esta es una propiedad que se va a ver en el Swagger
        value = "PersonDTO",
        description = "Represents the data needed to created Persons"
)

public class PersonDTO implements Serializable {

    // el required = true es para que el dato sea obligatorio, es una validación que hace la API

    @ApiModelProperty(position = 1, required = true, notes = "Non negative value, The first name is required.") // Esto también se va a ver en el Swagger, estoy documentando mi API
    private String firstName;

    @ApiModelProperty(position = 2, required = true, notes = "Non negative value, The last name is required.")
    private String lastName;

    @ApiModelProperty(position = 3, required = true, notes = "Non negative value, The type document list is required.")
    private String typeDocument;

    @ApiModelProperty(position = 4, required = true, notes = "Non negative value, The number document is required.")
    private Integer numberDocument;

    @ApiModelProperty(position = 5, required = true, notes = "Non negative value, The number document is required.")
    private LocalDate dateBorn;

    @ApiModelProperty(position = 6, required = true, notes = "Non negative value, The date Created list is required.")
    private LocalDate dateCreated;

    @ApiModelProperty(position = 7, notes = "Non negative value, The Date modified list is required.")
    private LocalDate dateModified;

}
