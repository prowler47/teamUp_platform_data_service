package ua.dargunovskiy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private UUID id;
    private String name;
    private String description;
}
