package vn.student.vluxfashion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id", length = 36)
    private String roleId; 

    @Column(name = "role_name", unique = true, length = 30)
    private String roleName;

    @Column(name = "description", length = 128)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<AdminUser> users = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<Api> apis = new HashSet<>();
}
