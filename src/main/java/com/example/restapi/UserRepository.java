package com.example.restapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u.avatarData FROM User u WHERE u.username = :username")
    String findAvatarDataByUsername(@Param("username") String username);

    @Query("SELECT u.isActive FROM User u WHERE u.username = :username")
    boolean findStatusByUsername(@Param("username") String username);
}
