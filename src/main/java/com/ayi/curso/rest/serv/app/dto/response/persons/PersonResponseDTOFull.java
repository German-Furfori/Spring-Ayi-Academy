package com.ayi.curso.rest.serv.app.dto.response.persons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(
        value = "PersonResponseDTOFull",
        description = "Represents the data needed to created Persons"
)
public class PersonResponseDTOFull { // Creamos esta para la paginación

    @ApiModelProperty(position = 1, notes = "Non negative value, The first name is required.")
    private List<PersonResponseDTO> personResponseDTO; // Voy devolviendo de a lotes (de 20 por ej, como la API de rick and morty)
    @ApiModelProperty(position = 2, notes = "Total pages")
    private Integer totalPages;
    @ApiModelProperty(position = 3, notes = "Current pages")
    private Integer currentPage;
    @ApiModelProperty(position = 4, notes = "Size")
    private Integer size;
    @ApiModelProperty(position = 5, notes = "Total de Elementos de la pagina")
    private Integer totalElements;

}
