package ua.dargunovskiy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private UUID userId;
    private UUID projectId;
    private String speciality;
    private String coverLetter;
}
