package com.example.restapi;

import java.util.Optional;
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

    public boolean findInteractByUsernameAndIdPost(UserInteract userInteract)
    {
        Optional<UserInteract> Optional = userInteractRepository.findInteractByUsernameAndIdPost(userInteract.getUsername(), userInteract.getIdPost());
        if (Optional.isPresent())
        {
            return true;    
        }
        return false;
    }

}
