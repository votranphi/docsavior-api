package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value = "/user_interact")
public class UserInteractController {
    
    @Autowired
    UserInteractService userInteractService = new UserInteractService();

    @PostMapping("/interact")
    public ResponseEntity<?> postLikeUserInteract(@RequestParam String username, @RequestParam Integer idPost, @RequestParam boolean type) {
        UserInteract userInteract = new UserInteract(username, idPost, type);

        if (userInteract.getType()) // Like
        {
            if(userInteractService.findInteractByUsernameAndIdPost(userInteract)) //if find out
            {
                userInteractService.deleteUserInteract(userInteract);
                return ResponseEntity.ok(new Detail("UserInteract_like has been deleted successfully"));
            }
            userInteractService.saveNewUserInteract(userInteract);
            return ResponseEntity.ok(new Detail("UserInteract like successfully"));
        }
        else // Dislike
        {
            if(userInteractService.findInteractByUsernameAndIdPost(userInteract)) // if find out
            {
                userInteractService.deleteUserInteract(userInteract);
                return ResponseEntity.ok(new Detail("UserInteract_dislike has been deleted successfully"));
            }
            userInteractService.saveNewUserInteract(userInteract);
            return ResponseEntity.ok(new Detail("UserInteract dislike successfully"));
        }
    }
    
    
}


