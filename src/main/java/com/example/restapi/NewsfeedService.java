package com.example.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsfeedService {
    @Autowired
    NewsfeedRepository newfeedRepository;

    public Iterable<Newsfeed> getAllPosts() {
        return newfeedRepository.findAll();
    }

    public boolean isEmpty() {
        return newfeedRepository.count() == 0;
    }

    public void saveNewPost(Newsfeed newNewfeed) {
        newfeedRepository.save(newNewfeed);
    }

    public void saveNewNewsfeed(Newsfeed newNewsfeed) {
        newfeedRepository.save(newNewsfeed);
    }
}
