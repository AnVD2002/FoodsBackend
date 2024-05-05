package com.example.Food.Service.Comment;

import com.example.Food.DTO.Request.CommentRequest;
import com.example.Food.DTO.Request.RepCommentRequest;
import com.example.Food.DTO.Request.UpdateCommentRequest;
import com.example.Food.DTO.Response.CommentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImplCommentService {
    public ResponseEntity<?> comment(CommentRequest commentRequest);
    public ResponseEntity<?> repComment(RepCommentRequest repCommentRequest);
    public ResponseEntity<?> getCommentByFoodID(int foodID);
    public List<CommentResponse> getResponseCommentByFoodID(int foodID);
    public ResponseEntity<?> deleteComment(int commentID);
    public ResponseEntity<?> updateComment(UpdateCommentRequest updateCommentRequest);

}
