package com.ayi.curso.rest.serv.app.services;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTOFull;
import com.ayi.curso.rest.serv.app.exceptions.ReadAccessException;

import java.time.LocalDate;
import java.util.List;

public interface IPersonService {
    List<PersonResponseDTO> findAllPersons() throws ReadAccessException; // Acá también aparece el throws

    PersonResponseDTO findPersonById(Long idPerson) throws ReadAccessException;

    // Este lo hice yo, ver después si funciona
    List<PersonResponseDTO> findPersonByName(String firstName, String lastName);

    PersonResponseDTO addPerson(PersonDTO personDTO);

    PersonResponseDTO removePersonById(Long id);

    PersonResponseDTOFull findAllPersonsForPage(Integer page, Integer size);

    PersonResponseDTO modifyPersonById(Long id, PersonDTO personDTO);
}
