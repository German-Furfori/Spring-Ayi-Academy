package com.ayi.curso.rest.serv.app.services.impl;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTOFull;
import com.ayi.curso.rest.serv.app.entities.PersonEntity;
import com.ayi.curso.rest.serv.app.exceptions.ReadAccessException;
import com.ayi.curso.rest.serv.app.exceptions.WriteAccessException;
import com.ayi.curso.rest.serv.app.mappers.IPersonMapper;
import com.ayi.curso.rest.serv.app.repositories.IPersonRepository;
import com.ayi.curso.rest.serv.app.services.IPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service // Le dice al programa que esto es un servicio
@Slf4j
@Transactional // Esta anotación hace tdo lo que hacíamos antes con JPA (el .begin(), .rollback(), .commit(), etc)
public class PersonServiceImpl implements IPersonService {

    @Autowired // Le digo que es una "tubería", genera un puente entre una cosa y otra para traer o enviar info
    private IPersonRepository personRepository; // Fijate que no hicimos implementación de IPersonRepository, ya con esto es suficiente

    @Autowired
    private IPersonMapper personMapper; // Acá uso los mapper (me transforma una entidad a otra)

    @Override
    public List<PersonResponseDTO> findAllPersons() throws ReadAccessException { // Me devuelve todas las personas de la tabla, y manejo la excepción con ReadAccesException

        List<PersonResponseDTO> personResponseDTOs;

        List<PersonEntity> personEntities = personRepository.findAll();

        if (personEntities == null || personEntities.size() == 0) {
            throw new ReadAccessException("No existen registros en el sistema"); // Tengo que firmar mi método para poder usar Exceptions
            // Esto es una excepción controlada. Si fuese una RunTimeExcetion no sería controlada, me devuelve un quilombo de líneas de error
        }

        personResponseDTOs = personEntities.stream() // Si encuentra info sigue por acá
                .map(lt -> new PersonResponseDTO(
                        lt.getIdPerson(),
                        lt.getFirstName(),
                        lt.getLastName(),
                        lt.getTypeDocument(),
                        lt.getNumberDocument(),
                        lt.getDateBorn(),
                        lt.getDateCreated(),
                        lt.getDateModified() // Tdo esto es lo que estoy enviando al constructor de PersonResponseDTO
                ))
                .collect(Collectors.toList()); // A través de stream, mapeo los campos de personEntities a personResponseDTOs

        return personResponseDTOs;
    }

    @Override
    public PersonResponseDTO findPersonById(Long idPerson) throws ReadAccessException {

        if (idPerson == null || idPerson <= 0) {
            throw new ReadAccessException("Error, el valor del id es nulo o vacío");
        }

        Optional<PersonEntity> entity = personRepository.findById(idPerson); // Ya tengo todos los métodos para buscar, deletear, etc

        if (!entity.isPresent()) {
            throw new WriteAccessException("Error, no existe el id de persona buscado");
        }

        return personMapper.entityToDto(entity.get()); // Retorna un PersonResponseDTO
    }

    @Override // Este lo hice yo, ver después si funciona. Funciona, muestra una lista vacía si no encuentra nada
    public List<PersonResponseDTO> findPersonByName(String firstName, String lastName) {
        List<PersonResponseDTO> personResponseDTOs;

        List<PersonEntity> personEntities = personRepository.getPersonByName(firstName, lastName);

        personResponseDTOs = personEntities.stream() // Acá voy agregando a la lista todas las filas que encuentra con el nombre y apellido que brinde como parámetro
                .map(lt -> new PersonResponseDTO(
                        lt.getIdPerson(),
                        lt.getFirstName(),
                        lt.getLastName(),
                        lt.getTypeDocument(),
                        lt.getNumberDocument(),
                        lt.getDateBorn(),
                        lt.getDateCreated(),
                        lt.getDateModified()
                ))
                .collect(Collectors.toList());

        return personResponseDTOs;
    }

    @Override
    public PersonResponseDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.dtoToEntity(personDTO); // De PersonDTO a PersonEntity

        personRepository.save(entity);

        return personMapper.entityToDto(entity); // De PersonEntity a PersonResponseDTO
    }

    @Override
    public PersonResponseDTO removePersonById(Long id) {
        Optional<PersonEntity> entity = personRepository.findById(id);
        PersonResponseDTO personResponseDTO;

        if(entity.isPresent()) {
            personRepository.deleteById(id);
            personResponseDTO = personMapper.entityToDto(entity.get());
            return personResponseDTO;
        } else {
            throw new RuntimeException("No se encuentra el ID a borrar");
        }
    }

    @Override
    public PersonResponseDTOFull findAllPersonsForPage(Integer page, Integer size) { // Paginación

        PersonResponseDTOFull personResponseDTOFull;

        Pageable pageable = PageRequest.of(page, size); // Pageable: Interfaz para información de paginación

        //PageRequest es una clase, que implementa la interfaz Pageable
        // 	of(int page, int size) Creates a new unsorted PageRequest

        Page<PersonEntity> personEntityPages = personRepository.findAll(pageable); // Findall está sobrecargado, si no le paso nada me busca tdo, si le paso el pageable me busca la página

        if(personEntityPages != null && !personEntityPages.isEmpty()) { // Que no sea nulo y que contenga páginas
            personResponseDTOFull = personMapper.listPersonDTOs(personEntityPages.getContent()); // El mapper lo agregué yo, y le paso el objeto Page que tiene el contenido de la página
            personResponseDTOFull.setSize(personEntityPages.getSize()); // Tamaño de la página
            personResponseDTOFull.setCurrentPage(personEntityPages.getNumber() + 1); // La página actual, revisar esto (primer página 0 o 1)
            personResponseDTOFull.setTotalPages(personEntityPages.getTotalPages());
            personResponseDTOFull.setTotalElements((int) personEntityPages.getTotalElements()); // Me devuelve Long, así que lo parseo
            return personResponseDTOFull;
        } else {
            throw new RuntimeException("Error en el proceso");
        }
    }

    @Override
    public PersonResponseDTO modifyPersonById(Long id, PersonDTO personDTO) {
        Optional<PersonEntity> entityOptional = personRepository.findById(id);
        PersonEntity entity = entityOptional.get();

        if(entityOptional.isPresent()) {
            entity.setFirstName(personDTO.getFirstName());
            entity.setLastName(personDTO.getLastName());
            entity.setTypeDocument(personDTO.getTypeDocument());
            entity.setNumberDocument(personDTO.getNumberDocument());
            entity.setDateBorn(personDTO.getDateBorn());
            entity.setDateModified(LocalDate.now());

            personRepository.save(entity);

            return personMapper.entityToDto(entity);
        } else {
            throw new RuntimeException("No se encuentra el ID a actualizar");
        }
    }
}
