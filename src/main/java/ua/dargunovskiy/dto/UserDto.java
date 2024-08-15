package ua.dargunovskiy.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String speciality;
}
