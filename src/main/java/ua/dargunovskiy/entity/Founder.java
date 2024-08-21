package ua.dargunovskiy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "founders")
@Data
public class Founder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;


    @Column(name = "secret_code")
    private String secretCode;

    @Column(name = "project_id")
    private UUID projectId;

}
