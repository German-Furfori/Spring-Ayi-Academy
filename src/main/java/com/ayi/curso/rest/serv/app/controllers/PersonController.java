package com.ayi.curso.rest.serv.app.controllers;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.services.IPersonService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Api(value = "Person API", tags = {"Persons Service"}) // Le decimos a Swagger que hay una transacción nueva, y que se llama Person Service (Es lo que se ve en grande en el Swagger)
@Slf4j // Esto es para el logeo
@RequestMapping(value = "/persons", produces = {MediaType.APPLICATION_JSON_VALUE}) // Le indica al mundo externo que hay un servicio en la dirección web tal que se llama /persons, y lo que tiene que enviar es un JSON
@RestController // Esto es un controlador REST. @Controller es para un controlador MVC, no es REST
public class PersonController { // La puerta de entrada al endpoint

    private IPersonService personService; // Acá traigo la interfaz del servicio

    /*
    @PutMapping
    @PostMapping
    @DeleteMapping
    Las otras 3 opciones posibles
    */
    @GetMapping(value = "/getAllPersons") // El valor de la URL va a ser así: localhost:8080/persons/getAllPersons
    @ApiOperation( // Es parte del Swagger, esto lo va a mostrar ahí
            value = "Retrieves all Lists Persons",
            httpMethod = "GET",
            response = PersonResponseDTO[].class
    )
    @ApiResponses(value = { // Posibles respuestas que puedo tener
            @ApiResponse(code = 200, // Tdo ok
                    message = "Body content with basic information about persons",
                    response = PersonResponseDTO[].class),
            @ApiResponse(
                    code = 400, // Hubo algún error
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    }) // Documento tdo mi Swagger
    public ResponseEntity<List<PersonResponseDTO>> getAllPersons() { // Antes solo devolvíamos la lista de PersonResponseDTO, ahora vamos a guardar esa lista en un ResponseEntity
        // Ésta es una estructura que nos permite intercambiar contenido a nivel de HTTP (cabeceras, body, los errores, tdo lo que tenga que ver con la respuesta de nuestro servicio)

        List<PersonResponseDTO> personResponseDTOs = personService.findAllPersons();
        return ResponseEntity.ok(personResponseDTOs); // ResponseEntity.[ver métodos]

    }

    @GetMapping(
            value = "/getPersonById/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(
            value = "Retrieves data associated to List Master by Id",
            httpMethod = "GET",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Body content with basic information for this Lists Master by Id"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<PersonResponseDTO> getPersonById(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable("id") Long id) { // este "id" es lo que está entre llaves en el getmapping {id}

        return ResponseEntity.ok(personService.findPersonById(id));

    }

    @GetMapping( // Ver si anda
            value = "/getPersonByNames/{firstName}/{lastName}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(
            value = "Retrieves data associated to List Master by Id",
            httpMethod = "GET",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Body content with basic information for this Lists Master by Id"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<List<PersonResponseDTO>> getPersonByNames(
            @ApiParam(name = "firstName", required = true, value = "Person Name")
            @PathVariable("firstName") String firstName,
            @ApiParam(name = "lastName", required = true, value = "Person Last Name")
            @PathVariable("lastName") String lastName) {

        List<PersonResponseDTO> personResponseDTOs = personService.findPersonByName(firstName, lastName);
        return ResponseEntity.ok(personResponseDTOs);
    }

    @DeleteMapping(
            value = "/deleteById/{id}"
    )
    @ApiOperation(
            value = "Retrieves data associated to List Master by Id",
            httpMethod = "DELETE",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Body content with basic information for this Lists Master by Id"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<PersonResponseDTO> deletePersonById(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable("id") Long id) { // este "id" es lo que está entre llaves en el getmapping {id}

        return ResponseEntity.ok(personService.removePersonById(id));
    }

    @PostMapping(value = "/addPerson")
    @ApiOperation(
            value = "Adds a person to the table",
            httpMethod = "POST",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Body content with basic information for this Lists Master by Id"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<PersonResponseDTO> createPerson(@RequestBody PersonDTO personDTO) {

        return ResponseEntity.ok(personService.addPerson(personDTO));
    }
}
