package com.example.restapi;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserInteractService {
    @Autowired
    private UserInteractRepository userInteractRepository;

    public void saveNewUserInteract(UserInteract userInteract)
    {
        userInteractRepository.save(userInteract);
    }

    @Transactional
    public void deleteUserInteract(UserInteract userInteract)
    {
        userInteractRepository.deleteInteractByUsernameAndIdPost(userInteract.getUsername(), userInteract.getIdPost());
    }

    public boolean findInteractByUsernameAndIdPost(String username, Integer idPost)
    {
        Optional<UserInteract> Optional = userInteractRepository.findInteractByUsernameAndIdPost(username, idPost);
        if (Optional.isPresent())
        {
            return true;    
        }
        return false;
    }

    public UserInteract findTypeInteractByUsername(String username, Integer idPost)
    {
        return userInteractRepository.findUserInteractByUsernameAndIdPost(username, idPost);
    }

}
