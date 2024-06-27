package lt.techin.exam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/comments")
@Slf4j
public class UsersCommentController {

    @PostMapping
    public ResponseEntity<?> createComment() {
        return new ResponseEntity<>("comment created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllCommentsByUser() {
        return new ResponseEntity<>("comment", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateComment() {
        return new ResponseEntity<>("comment updated", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment() {
        return new ResponseEntity<>("comment deleted", HttpStatus.NO_CONTENT);
    }
}