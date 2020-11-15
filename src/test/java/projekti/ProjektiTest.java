package projekti;

import java.util.ArrayList;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import projekti.Entity.Account;
import projekti.Entity.Comment;
import projekti.Entity.Post;
import projekti.Entity.Skill;
import projekti.Repo.AccountRepository;
import projekti.Repo.CommentRepo;
import projekti.Repo.PostRepo;
import projekti.Repo.SkillRepository;
import projekti.Service.AccountService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjektiTest extends org.fluentlenium.adapter.junit.FluentTest{

    @LocalServerPort
    private Integer port;
    
    @Autowired
    AccountRepository accountRepository;
    
 
    @Autowired
    PasswordEncoder passwordEncoder;  
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    CommentRepo commentRepo;
    
    @Autowired
    PostRepo postRepo;
    
    @Autowired
    AccountService accountService;
    
    public Account createUser(){
        Account a = new Account();
        a.setProfilename("ss");
        a.setRealname("ss");
        a.setUsername("ss");
        a.setPassword(passwordEncoder.encode("ss"));
        a.setSkills(new ArrayList<>());
        accountRepository.save(a);
        return a;
    }
    
    
    
    @Test
    @Transactional
    public void userWasCreated() {
        
        Account a = new Account();
        a.setProfilename("sa");
        a.setRealname("sa");
        a.setUsername("sa");
        a.setPassword(passwordEncoder.encode("sa"));
        a.setSkills(new ArrayList<>());
        accountRepository.save(a);
        
        
        Assert.assertEquals(a, accountRepository.findByUsername("sa"));
    }
    
    @Test
    @Transactional
    public void newSkill() {
        Account user = createUser();
        Skill skill = new Skill("coding", "good", 1, user, new ArrayList<>());
        skillRepository.save(skill);
        
        Assert.assertEquals(skill, skillRepository.findAll().get(0));
         
    }
    
    @Test
    @Transactional
    public void newPost() {
        Account user = createUser();
        Post post = new Post();
        post.setSender(user);
        post.setPtime(new Date());
        post.setContent("terve");
        postRepo.save(post);
        Assert.assertEquals(post.getContent(), postRepo.findAll().get(0).getContent());
    }
    
    @Test
    @Transactional
    public void newComment() {
        Account user = createUser();
        Post post = new Post();
        post.setSender(user);
        post.setPtime(new Date());
        post.setContent("terve");
        postRepo.save(post);
        Comment c = new Comment();
        c.setContent("hei");
        c.setPtime(new Date());
        c.setSender(user);
        c.setPost(post);
        commentRepo.save(c);
        Assert.assertEquals(c.getContent(), commentRepo.findAll().get(0).getContent());
    }
    
    

}
