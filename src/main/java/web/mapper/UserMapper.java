package web.mapper;

import org.mapstruct.*;
import web.dto.UserDto;
import web.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromPatchDto(UserDto dto, @MappingTarget User user);
}