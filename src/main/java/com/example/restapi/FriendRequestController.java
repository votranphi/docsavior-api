package com.example.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/friend_request")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService = new FriendRequestService();
}
