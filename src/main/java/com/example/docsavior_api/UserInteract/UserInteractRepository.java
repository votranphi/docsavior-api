package com.example.docsavior_api.UserInteract;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserInteractRepository extends JpaRepository<UserInteract, Integer> {
    @Query("SELECT f FROM UserInteract f WHERE f.username = :username AND f.idPost = :idPost")
    Optional<UserInteract> findInteractByUsernameAndIdPost(@Param("username") String username, @Param("idPost") Integer idPost); 


    @Query("SELECT f FROM UserInteract f WHERE f.username = :username AND f.idPost = :idPost")
    UserInteract findUserInteractByUsernameAndIdPost(@Param("username") String username, @Param("idPost") Integer idPost); 

    @Modifying
    @Query("DELETE FROM UserInteract f WHERE f.username = :username AND f.idPost = :idPost")
    void deleteInteractByUsernameAndIdPost(@Param("username") String username, @Param("idPost") Integer idPost);

}