package com.example.Food.Service.Comment;

import com.example.Food.DTO.Request.CommentRequest;
import com.example.Food.DTO.Request.RepCommentRequest;
import com.example.Food.DTO.Request.UpdateCommentRequest;
import com.example.Food.DTO.Response.CommentResponse;
import com.example.Food.Entity.Comment;
import com.example.Food.Entity.Food.Foods;
import com.example.Food.Entity.User.User;
import com.example.Food.Repository.CommentRepository;
import com.example.Food.Repository.FoodsRepository;
import com.example.Food.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ImplCommentService{

    @Autowired
    private FoodsRepository foodsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public ResponseEntity<?> comment(CommentRequest commentRequest) {
        Optional<Foods> food = foodsRepository.findById(commentRequest.getFoodID());
        Optional<User> user = userRepository.findById((commentRequest.getUserID()));
        if(user.isPresent()){
            if(food.isPresent()){
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setFood(food.get());
                comment.setCreateAt(LocalDate.now());
                comment.setContent(commentRequest.getContent());
                commentRepository.save(comment);
                return new ResponseEntity<>(CommentResponse.builder()
                        .userID(user.get().getUserID())
                        .commentID(comment.getCommentID())
                        .foodID(commentRequest.getFoodID())
                        .content(commentRequest.getContent())
                        .createAt(LocalDate.now())
                        .userName(user.get().getUserName())
                        .build(), HttpStatus.OK);
            }
            return new ResponseEntity<>("not exist food", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("not exist ", HttpStatus.NOT_FOUND);
    }
    @Override
    public ResponseEntity<?> repComment(RepCommentRequest repCommentRequest) {
        Optional<Foods> food = foodsRepository.findById(repCommentRequest.getFoodID());
        Optional<User> user = userRepository.findById((repCommentRequest.getUserID()));
        Optional<Comment> comment = commentRepository.findById(repCommentRequest.getParentID());
        if(user.isPresent()){
            if(food.isPresent()){
                if(comment.isPresent()){
                    Comment newComment = new Comment();
                    newComment.setUser(user.get());
                    newComment.setFood(food.get());
                    newComment.setParentID(repCommentRequest.getParentID());
                    newComment.setCreateAt(LocalDate.now());
                    newComment.setContent(repCommentRequest.getContent());
                    commentRepository.save(newComment);
                    return new ResponseEntity<>(CommentResponse.builder()
                            .userID(user.get().getUserID())
                            .commentID(newComment.getCommentID())
                            .foodID(food.get().getFoodID())
                            .parentID(repCommentRequest.getParentID())
                            .content(repCommentRequest.getContent())
                            .createAt(LocalDate.now())
                            .userName(user.get().getUserName())
                            .build(),HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("not exist food", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("not exist user ", HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<?> getCommentByFoodID(int foodID){
        return new ResponseEntity<>(commentRepository.getCommentByFoodID(foodID),HttpStatus.OK);
    }
    @Override
    public List<CommentResponse> getResponseCommentByFoodID(int foodID) {
        List<Comment> commentList = commentRepository.getCommentByFoodID(foodID);
        List<CommentResponse> responseList = commentList.stream().map(comment -> {
            CommentResponse response = new CommentResponse();
            response.setUserID(comment.getUser().getUserID());
            response.setUserName(comment.getUser().getUserName());
            response.setFoodID(comment.getFood().getFoodID());
            response.setParentID(comment.getParentID());
            response.setContent(comment.getContent());
            response.setCreateAt(comment.getCreateAt());
            response.setCommentID(comment.getCommentID());
            return response;
        }).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public ResponseEntity<?> deleteComment(int commentID) {
        commentRepository.deleteById(commentID);
        commentRepository.deleteByCommentID(commentID);
        return new ResponseEntity<>("Xoa thanh cong", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateComment(UpdateCommentRequest updateCommentRequest) {
        Optional<Comment> comment = commentRepository.findById(updateCommentRequest.getCommentID());
        if(comment.isPresent()){
            comment.get().setContent(updateCommentRequest.getContent());
            comment.get().setCreateAt(LocalDate.now());
            commentRepository.save(comment.get());
            return new ResponseEntity<>(CommentResponse.builder()
                    .userID(comment.get().getUserID())
                    .content(updateCommentRequest.getContent())
                    .foodID(comment.get().getFoodID())
                    .createAt(LocalDate.now())
                    .commentID(comment.get().getCommentID())
                    .commentID(comment.get().getParentID()).build(),HttpStatus.OK);
        }
        return new ResponseEntity<>("not exist comment", HttpStatus.NOT_FOUND);
    }
}
