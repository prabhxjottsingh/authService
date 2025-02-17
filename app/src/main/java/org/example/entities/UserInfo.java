package org.example.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String username;
    
    private String password;  
    
    private String email;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="user_role_mapping",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private Set<UserRole> roles = new HashSet<>();

}
