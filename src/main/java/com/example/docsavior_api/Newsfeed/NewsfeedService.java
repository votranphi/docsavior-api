package com.example.docsavior_api.Newsfeed;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // function to ge a sequence of continuous post
    public Page<Newsfeed> getSequenceOfPost(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("likeMinusDislike").descending());
        return newsfeedRepository.findAll(pageable);
    }

    // function to get all my post
    public List<Newsfeed> getAllMyPost(String username) {
        return newsfeedRepository.findByUsername(username);
    }

    // function to get my post
    public Page<Newsfeed> getMyPost(String username, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("likeMinusDislike").descending());

        return newsfeedRepository.findByUsername(username, pageable);
    }

    // function to check if Newsfeed table is empty
    public boolean isEmpty() {
        return newsfeedRepository.count() == 0;
    }

    // function to save new Newsfeed to Newsfeed table, then return its id
    public Integer saveNewNewsfeed(Newsfeed newnewsfeed) {
        return newsfeedRepository.save(newnewsfeed).getId();
    }

    public int NumberOfPosts()
    {
        return newsfeedRepository.findAll().size();
    }
    @Transactional
    public void updateLikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setLikeNumber(target.getLikeNumber()+1);
            target.setLikeMinusDislike(target.getLikeMinusDislike()+1);
            return target;
        });
    }

    @Transactional 
    public void updateDislikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setDislikeNumber(target.getDislikeNumber()+1);
            target.setLikeMinusDislike(target.getLikeMinusDislike()-1);
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
            target.setLikeMinusDislike(target.getLikeMinusDislike()-1);
            return target;
        });
    }

    @Transactional 
    public void updateUndislikeNumber(Integer id)
    {
        newsfeedRepository.findById(id).map(target -> {
            target.setDislikeNumber(target.getDislikeNumber()-1);
            target.setLikeMinusDislike(target.getLikeMinusDislike()+1);
            return target;
        });
    }
}
