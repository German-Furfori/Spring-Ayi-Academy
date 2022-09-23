package com.ayi.curso.rest.serv.app.services;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IPersonService {
    List<PersonResponseDTO> findAllPersons();

    PersonResponseDTO findPersonById(Long idPerson);

    // Este lo hice yo, ver después si funciona
    List<PersonResponseDTO> findPersonByName(String firstName, String lastName);

    PersonResponseDTO addPerson(PersonDTO personDTO);

    PersonResponseDTO removePersonById(Long id);
}
