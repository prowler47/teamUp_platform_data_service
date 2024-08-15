package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.dto.UserDto;

import java.util.UUID;

@UtilityClass
public class UserDtoUtil {
    public UserDto fromUserToUserDto(UUID id, String firstName, String lastName, String speciality) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setSpeciality(speciality);
        return userDto;
    }
}
