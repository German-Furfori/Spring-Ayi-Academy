package com.ayi.curso.rest.serv.app.mappers.impl;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTOFull;
import com.ayi.curso.rest.serv.app.entities.PersonEntity;
import com.ayi.curso.rest.serv.app.mappers.IPersonMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component // Esto es un componente, lo podes usar como tubería
@AllArgsConstructor
public class PersonMapperImpl implements IPersonMapper {
    private final ModelMapper modelMapper;
    @Override
    public PersonResponseDTO entityToDto(PersonEntity entity) { // Los distintos mapeos. De entidad a ResponseDTO

        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        modelMapper.map(entity, personResponseDTO);
        return personResponseDTO;
    }

    @Override
    public PersonEntity dtoToEntity(PersonDTO dto) {
        PersonEntity personEntity = new PersonEntity();
        modelMapper.map(dto, personEntity);
        return personEntity;
    }

    @Override
    public PersonEntity toEntityByRequest(PersonDTO dto) {

        PersonEntity personEntity = new PersonEntity();
        modelMapper.map(dto, personEntity);
        return personEntity;
    }

    @Override
    public PersonResponseDTOFull listPersonDTOs(List<PersonEntity> listPersonEntities) {

        PersonResponseDTOFull listPersonResponseFullDTOs = new PersonResponseDTOFull();
        List<PersonResponseDTO> listPersonResponseDTOs = new ArrayList<>();
        listPersonEntities.forEach((PersonEntity personEntity) -> {
            PersonResponseDTO personResponseDTO = new PersonResponseDTO(
                    personEntity.getIdPerson(),
                    personEntity.getFirstName(),
                    personEntity.getLastName(),
                    personEntity.getTypeDocument(),
                    personEntity.getNumberDocument(),
                    personEntity.getDateBorn(),
                    personEntity.getDateCreated(),
                    personEntity.getDateModified()
            );

            listPersonResponseDTOs.add(personResponseDTO);

        });

        listPersonResponseFullDTOs.setPersonResponseDTO(listPersonResponseDTOs);

        return listPersonResponseFullDTOs;

        // Lo que hace este mapper es recibir una lista de entities y convertirla a UN SOLO objeto PersonResponseDTOFull
        // El cual tiene un atributo que es toda la lista mapeada a PersonResponseDTO, entre otros atributos de paginación
    }
}
