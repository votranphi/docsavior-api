package com.example.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsfeedService {
    @Autowired
    NewsfeedRepository newfeedRepository;

    // function to get all post from Newsfeed table
    public Iterable<Newsfeed> getAllPosts() {
        return newfeedRepository.findAll();
    }

    // function to check if Newsfeed table is empty
    public boolean isEmpty() {
        return newfeedRepository.count() == 0;
    }

    // function to save new Newsfeed to Newsfeed table
    public void saveNewNewsfeed(Newsfeed newNewfeed) {
        newfeedRepository.save(newNewfeed);
    }
}
