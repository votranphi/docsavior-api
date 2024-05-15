package com.example.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewfeedService {
    @Autowired
    NewfeedRepository newfeedRepository;

    public Iterable<Newfeed> getAllPosts() {
        return newfeedRepository.findAll();
    }

    public boolean isEmpty() {
        return newfeedRepository.count() == 0;
    }

    public void saveNewPost(Newfeed newNewfeed) {
        newfeedRepository.save(newNewfeed);
    }
}
