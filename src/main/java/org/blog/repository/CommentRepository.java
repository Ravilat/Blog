package org.blog.repository;

import org.blog.entity.Comment;
import org.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


    @Query(value = "SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findCommentsByPost(@Param("postId") Integer postId);

    int countCommentByAuthor(User author);

}
