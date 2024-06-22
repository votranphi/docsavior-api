package com.example.restapi;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



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
            if(userInteractService.findInteractByUsernameAndIdPost(username, idPost)) //if find out
            {
                userInteractService.deleteUserInteract(userInteract);
                return ResponseEntity.ok(new Detail("UserInteract_like has been deleted successfully"));
            }
            userInteractService.saveNewUserInteract(userInteract);
            return ResponseEntity.ok(new Detail("UserInteract like successfully"));
        }
        else // Dislike
        {
            if(userInteractService.findInteractByUsernameAndIdPost(username, idPost)) // if find out
            {
                userInteractService.deleteUserInteract(userInteract);
                return ResponseEntity.ok(new Detail("UserInteract_dislike has been deleted successfully"));
            }
            userInteractService.saveNewUserInteract(userInteract);
            return ResponseEntity.ok(new Detail("UserInteract dislike successfully"));
        }
    }

    @GetMapping("/likeordislike") // to see if user liked or disliked a post
    public ResponseEntity<?> getIfLike(@RequestParam String username, @RequestParam Integer idPost)
    {
        if(userInteractService.findInteractByUsernameAndIdPost(username, idPost))
        {
            Boolean type = userInteractService.findTypeInteractByUsername(username, idPost).getType();

            if(type)
            {
                return ResponseEntity.ok(new Detail("like"));
            }
            else if (!type)
            {
                return ResponseEntity.ok(new Detail("dislike"));
            }
        }
        return ResponseEntity.ok(new Detail("didn't interact"));
    }
}


