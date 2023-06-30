package lab.cherry.nw.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "`roles`",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class RoleEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch=FetchType.EAGER)
    private Set<UserEntity> users = new HashSet<>();
}
