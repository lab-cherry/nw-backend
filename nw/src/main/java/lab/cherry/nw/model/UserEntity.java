package lab.cherry.nw.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`users`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class UserEntity implements Serializable {

    @JsonProperty("userId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @JsonProperty("userName")
    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    private String username;

    @JsonProperty("userEmail")
    @Email(message = "Email Should Be Valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    @Size(min = 3, message = "Minimum password length: 8 characters")
    private String password;

    @JsonProperty("userEnabled")
    @Column(name = "enabled")
    private boolean enabled;

    @CreationTimestamp
    private Timestamp created_at;

//    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonProperty("userRoles")
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            )
    )
    private Set<RoleEntity> roles  = new HashSet<>();
}
