/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Entity.Account;
import projekti.Entity.Comment;
import projekti.Entity.Post;
import projekti.Repo.AccountRepository;
import projekti.Repo.CommentRepo;
import projekti.Repo.ConnectRepository;
import projekti.Repo.PostRepo;
import projekti.Repo.SkillRepository;
import projekti.Service.AccountService;

/**
 *
 * @author Santeri
 */
@Controller
public class PostController {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    ConnectRepository connectionRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    PostRepo postRepo;
    
    @GetMapping("/post")
    public String getPosts(Model model){
        Account account = accountService.getCurrentUser();
        List<Account> users = accountService.getAllFriends(account.getProfilename());
        users.add(account);
        
        Collection<Account> collection = new ArrayList<Account>(users);
        Pageable pageable = PageRequest.of(0, 25, Sort.by("ptime").descending());
        List<Post> post = postRepo.findBySenderIn(collection,pageable);
        
        
        for (Post post1 : post) {
            if(!post1.getComments().isEmpty()){
                if(post1.getComments().size() >= 10){
                    System.out.println("Listassa on yli 10 tai 10");
                    List<Comment> comments = post1.getComments();
                    Collections.sort(comments);
                    post1.setComments(comments.subList(0, 10));
                }else{
                    System.out.println("Listassa 9");
                    Collections.sort(post1.getComments());
                }
                
            }
        }
        
        model.addAttribute("posts",post);  
        return "post";
    }
    
    @PostMapping("/post/new")
    public String newPost(@RequestParam String te ){
        Account myAccount = accountService.getCurrentUser();
        System.out.println(myAccount);
        System.out.println("New comment posted");
        
        Post newPost = new Post();
        newPost.setContent(te);
        newPost.setSender(myAccount);
        newPost.setPtime(new Date());
        postRepo.save(newPost);    
        
        return "redirect:/post";
    }
    
    @PostMapping("/post/comment/{id}")
    public String newComment(@PathVariable Long id,@RequestParam String comment){
        Post post = postRepo.getOne(id);
        Comment newComm = new Comment();
        
        newComm.setPtime(new Date());
        newComm.setSender(accountService.getCurrentUser());
        newComm.setContent(comment);
        newComm.setPost(post);
        commentRepo.save(newComm);
        post.getComments().add(newComm);
        postRepo.save(post);
        return "redirect:/post";
    }
    
    
    
    @PostMapping("/post/like/{id}")
    public String likePost(@PathVariable Long id){
        Post post = postRepo.getOne(id);
        Account myAccount = accountService.getCurrentUser();
        if(post.getLikers().contains(myAccount)){
             return "redirect:/post";
        }
        post.getLikers().add(myAccount);
        postRepo.save(post);
        return "redirect:/post";
    }

    
  

}