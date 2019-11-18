package jp.co.musako.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private Integer gender;

    private LocalDate birthdate;

    private String password;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;
}
