package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.dto.ProjectDto;

import java.util.UUID;

@UtilityClass
public class ProjectDtoUtil {
    public ProjectDto fromProjectToDto(UUID id, String name, String description) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(id);
        projectDto.setName(name);
        projectDto.setDescription(description);
        return projectDto;
    }
}
