package jp.co.musako.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_history")
@Data
public class UserHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String mail;

    private Integer gender;

    private LocalDate birthdate;

    private String password;

    private String note;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;
}
