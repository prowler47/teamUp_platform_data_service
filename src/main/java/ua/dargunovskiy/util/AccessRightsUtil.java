package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;

@UtilityClass
public class AccessRightsUtil {
    public boolean ifAccessGranted(Founder founder, Project project) {
        return founder.getId().equals(project.getFounderId()) && founder.getSecretCode().equals(project.getSecretCode());
    }
}
