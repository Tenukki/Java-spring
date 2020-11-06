/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.Entity.Account;
import projekti.Entity.Skill;
import projekti.Repo.AccountRepository;
import projekti.Repo.SkillRepository;
import projekti.Entity.Connect;
import projekti.Repo.ConnectRepository;
import projekti.Service.AccountService;

/**
 *
 * @author Santeri
 */

@Controller
public class ProfileController {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    ConnectRepository connectRepository;
    
    @Autowired
    AccountService accountService;
    @GetMapping("/profile")
    public String getOwnProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account a = accountRepository.findByUsername(username);
        
      //  Pageable pageable = PageRequest.of(0,Sort.by("vote").ascending());
        List<Skill> skills = skillRepository.findByAccount(a,Sort.by(Sort.Direction.DESC, "vote"));
        List<Account> friends = accountService.getAllFriends(a.getProfilename());
        
        
        model.addAttribute("connect",connectRepository.findByReceiver(a));
        model.addAttribute("account", a);
        model.addAttribute("skills",skills);
        model.addAttribute("friends",friends);
        model.addAttribute("own", true);
        return "profile";
    }
    
    //henkilökohtainen profiili
    @GetMapping("/profile/{name}")
    public String getProfile(Model model,@PathVariable String name) {
        Account a = accountRepository.findByProfilename(name);
        List<Skill> skills = skillRepository.findByAccount(a,Sort.by(Sort.Direction.DESC, "vote"));
        
        List<Account> friends = accountService.getAllFriends(a.getProfilename());
        
        model.addAttribute("account", a);
        model.addAttribute("skills",skills);
        model.addAttribute("own", false);
        model.addAttribute("friends",friends);
        return "profile";
    }
    
    
    
    @PostMapping("/files")
    public String newPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account a = accountRepository.findByUsername(username);
        if(!file.isEmpty()){
        a.setContent(file.getBytes());
        accountRepository.save(a);
        }
        return "redirect:/profile";
    }
    
    @PostMapping("/files/replace")
    public String replacePhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Account a = accountService.getCurrentUser();
        if(!file.isEmpty()){
            a.setContent(null);
            a.setContent(file.getBytes());
            accountRepository.save(a);
        }
        return "redirect:/profile";
    }
    
    //delte photo
    @PostMapping("/files/delete")
    public String deletePhoto(){
        Account a = accountService.getCurrentUser();
        if(a.getContent() != null){
            a.setContent(null);
            accountRepository.save(a);
        }
        return "redirect:/profile";
    }
    
    //Anna accountin id ja se etsii kuvat
    @GetMapping(path = "/files/{id}", produces = "image/png")
    @ResponseBody
    public byte[] getPhoto(@PathVariable Long id) {
        return accountRepository.getOne(id).getContent();
    }
    
    @PostMapping("/skill/vote/{id}")
    public String voteSkill(@PathVariable Long id) {
        Account a = accountService.getCurrentUser();
        Skill skill = skillRepository.getOne(id);
        if(!skill.getLikers().contains(a)){
            skill.getLikers().add(a);
            skill.setVote(skill.getVote()+1);
            skillRepository.save(skill);
        }
        
        if(a.getProfilename().equals(skill.getAccount().getProfilename())){
            return "redirect:/profile";
        }
        
        return "redirect:/profile/"+skill.getAccount().getProfilename();
    }
    
    
    
    
    
    

   

}