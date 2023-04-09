package com.softbinator.labs.project.schedulingapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = {StringTimeMapper.class})
public interface BookingMapper {

    BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

}
