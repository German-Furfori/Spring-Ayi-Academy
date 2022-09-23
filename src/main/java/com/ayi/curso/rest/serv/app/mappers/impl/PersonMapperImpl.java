package com.ayi.curso.rest.serv.app.mappers.impl;

import com.ayi.curso.rest.serv.app.dto.request.persons.PersonDTO;
import com.ayi.curso.rest.serv.app.dto.response.persons.PersonResponseDTO;
import com.ayi.curso.rest.serv.app.entities.PersonEntity;
import com.ayi.curso.rest.serv.app.mappers.IPersonMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
