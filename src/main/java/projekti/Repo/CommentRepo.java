/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Repo;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Entity.Account;
import projekti.Entity.Comment;
import projekti.Entity.Post;

/**
 *
 * @author Santeri
 */
public interface CommentRepo extends JpaRepository<Comment, Long> {
     List<Comment>findByPostIn(Collection<Post> sender,Pageable pageable);
}
