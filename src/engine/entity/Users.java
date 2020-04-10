package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CollectionType;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "usr")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false)
    @Pattern(regexp="\\w+@\\w+\\.\\w+")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5)
    private String password;

    public Users() {

    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
