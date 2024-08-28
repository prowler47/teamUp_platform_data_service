package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.dto.UserRequestDto;
import ua.dargunovskiy.entity.UserRequest;

@UtilityClass
public class UserRequestsDtoUtil {
    public UserRequestDto fromUserRequestsToUserRequestsDto(UserRequest userRequest) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(userRequest.getUser().getId());
        userRequestDto.setProjectId(userRequest.getProjectId());
        userRequestDto.setSpeciality(userRequest.getUser().getSpeciality());
        userRequestDto.setCoverLetter(userRequest.getCoverLetter());
        return userRequestDto;
    }
}
