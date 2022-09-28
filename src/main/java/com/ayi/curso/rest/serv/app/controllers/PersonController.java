package com.ayi.curso.rest.serv.app.controllers;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTOFull;
import com.ayi.curso.rest.serv.app.exceptions.ReadAccessException;
import com.ayi.curso.rest.serv.app.services.IPersonService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getAllPersons() {
        // Ésta es una estructura que nos permite intercambiar contenido a nivel de HTTP (cabeceras, body, los errores, tdo lo que tenga que ver con la respuesta de nuestro servicio)

        List<PersonResponseDTO> personResponseDTOs;
        Map<String, Object> response = new HashMap<>(); // Creo un response, que voy a devolver

        // Acá también me exige que añada la firma para la excepción, así que o puedo poner el extends más arriba o directamente handlearla con un try catch
        // Agregué el Map también para el response

        try {
            personResponseDTOs = personService.findAllPersons();
        } catch (ReadAccessException e) {
            response.put("Código de error: ", 1001); // Agrego otro valor al array asociativo
            response.put("Mensaje de error: ", e.getMessage()); // Capturo el mensaje de error
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<?> getPersonById(
            @ApiParam(name = "id", required = true, value = "Person Id", example = "1")
            @PathVariable("id") Long id) { // este "id" es lo que está entre llaves en el getmapping {id}

        Map<String, Object> response = new HashMap<>();

        PersonResponseDTO personResponseDTO;

        try {
            personResponseDTO = personService.findPersonById(id);
        } catch (ReadAccessException e) {
            response.put("Código de error: ", 1002);
            response.put("Mensaje de error: ", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // NOT_FOUND porque no lo encontró o es nulo
        }

        return ResponseEntity.ok(personResponseDTO);
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

        return ResponseEntity.ok(personService.addPerson(personDTO)); // return new ResponseEntity<>(personService.addPerson(person),HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllPersons/{page}/{size}") // Paginación (página y cantidad de registros a mostrar)
    @ApiOperation(
            value = "Retrieves all Lists Persons",
            httpMethod = "GET",
            response = PersonResponseDTO[].class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Body content with basic information about persons",
                    response = PersonResponseDTO[].class),
            @ApiResponse(
                    code = 400,
                    message = "Describes errors on invalid payload received, e.g: missing fields, invalid data formats, etc.")
    })
    public ResponseEntity<?> getAllPersonsForPage( // El ? quiere decir que puede ser cualquier cosa
            @ApiParam(value = "page to display", required = true, example = "1")
            @PathVariable(name = "page") Integer page,
            @ApiParam(value = "number of items per request", required = true, example = "1")
            @PathVariable(name = "size") Integer size) {

        PersonResponseDTOFull personResponseDTOFull;
        Map<String, Object> response = new HashMap<>(); // HashTable de C#, lo uso por si no encuentra nada en el request

        personResponseDTOFull = personService.findAllPersonsForPage(page, size);

        if(personResponseDTOFull == null) { // Si no encontró nada
            response.put("Message", "No se encontró información de Personas en el sistema");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); // NOT FOUND (404), no lo encontró.
        }

        // Si llego acá, entonces encontró algo
        return new ResponseEntity<>(personResponseDTOFull, HttpStatus.OK); // OK (200)

    }

    @PutMapping(value = "/updatePersonById/{id}")
    @ApiOperation(
            value = "Updates a person to the table",
            httpMethod = "PUT",
            response = PersonResponseDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Information created"
            )
    })
    public ResponseEntity<?> updatePersonById(
            @ApiParam(value = "person ID", required = true, example = "1")
            @PathVariable(name = "id") Long id,
            @RequestBody PersonDTO personDTO) {

        PersonResponseDTO personResponseDTO = personService.modifyPersonById(id, personDTO);
        Map<String, Object> response = new HashMap<>();

        if(personResponseDTO == null) { // Si no encontró nada
            response.put("Message", "No se encontró el ID a modificar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); // NOT FOUND (404), no lo encontró.
        }

        return new ResponseEntity<>(personResponseDTO, HttpStatus.CREATED); // CREATED (201)
    }
}
