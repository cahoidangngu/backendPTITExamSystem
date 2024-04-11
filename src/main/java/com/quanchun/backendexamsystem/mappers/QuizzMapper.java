package com.quanchun.backendexamsystem.mappers;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizzMapper {

    QuizzMapper MAPPER = Mappers.getMapper(QuizzMapper.class);
    @Named("toResponse")
    @Mappings({
        @Mapping(source = "questions", target = "questions", ignore = true)
    })
    QuizzDTO quizz2QuizzDTO(Quizz quizz);

    @IterableMapping(qualifiedByName = "toResponse")
    List<QuizzDTO> toResponses(List<Quizz> users);

    @Named("toPOJO")
    Quizz quizzDTO2Quizz(QuizzDTO quizzDTO);

    @IterableMapping(qualifiedByName = "toPOJO")
    List<Quizz> toPOJOs(List<QuizzDTO> quizzDTOS);
}
