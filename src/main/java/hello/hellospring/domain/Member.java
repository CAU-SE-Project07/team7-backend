package hello.hellospring.domain;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String password;
    private String email;
}
