package com.example.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsfeedService {
    @Autowired
    NewsfeedRepository newsfeedRepository;

    //function to get post by id
    public Optional<Newsfeed> getNewsfeedById(Integer id) {
        return newsfeedRepository.findById(id);
    }

    // function to get all post from Newsfeed table
    public List<Newsfeed> getAllPosts() {
        return newsfeedRepository.findAll();
    }

    // function to get my post
    public List<Newsfeed> getMyPost(String username) {
        return newsfeedRepository.findNewsfeedByUsername(username);
    }

    // function to check if Newsfeed table is empty
    public boolean isEmpty() {
        return newsfeedRepository.count() == 0;
    }

    // function to save new Newsfeed to Newsfeed table
    public void saveNewNewsfeed(Newsfeed newnewsfeed) {
        newsfeedRepository.save(newnewsfeed);
    }

    @Transactional
    public void updateLikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setLikeNumber(target.getLikeNumber()+1);
            return target;
        });
    }

    @Transactional 
    public void updateDislikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setDislikeNumber(target.getDislikeNumber()+1);
            return target;
        });
    }

    @Transactional
    public void updateCommentNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setCommentNumber(target.getCommentNumber()+1);
            return target;
        });
    }

    @Transactional 
    public void updateUnCommentNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setCommentNumber(target.getCommentNumber()-1);
            return target;
        });
    }

    @Transactional
    public void updateUnlikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setLikeNumber(target.getLikeNumber()-1);
            return target;
        });
    }

    @Transactional 
    public void updateUndislikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setDislikeNumber(target.getDislikeNumber()-1);
            return target;
        });
    }
}
