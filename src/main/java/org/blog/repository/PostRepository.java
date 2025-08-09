package org.blog.repository;

import org.blog.entity.Post;
import org.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    int countPostByAuthor(User author);

    @Query("SELECT p FROM Post p JOIN FETCH p.author")
    List<Post> findAllPostsWithAuthors();

}
