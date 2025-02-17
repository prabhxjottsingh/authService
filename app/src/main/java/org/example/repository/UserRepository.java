package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUsername(String username);
    UserInfo findByEmail(String email);  // Keep this if you need to find users by email
}
