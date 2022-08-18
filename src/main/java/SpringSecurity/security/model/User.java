package SpringSecurity.security.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; // role 의 종류에는 ROLE_USER, ROLE_ADMIN, ROLE_MANAGER 있음
    @CreationTimestamp
    private Timestamp createDate;
}
