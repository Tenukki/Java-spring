package projekti.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import projekti.Entity.Account;
import projekti.Entity.Skill;
import projekti.Repo.AccountRepository;
import projekti.Repo.SkillRepository;
import projekti.Entity.Connect;
import projekti.Repo.ConnectRepository;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    ConnectRepository connectionRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    @ResponseBody
    public List<Account> list() {
        return accountRepository.findAll();
    }
    
    @GetMapping("/")
    public String index() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        if(!username.equals("anonymousUser")){
            System.out.println(username);
            return "redirect:/profile";
        }
        return "index";
    }

    @PostMapping("/accounts")
    public String newUser(@RequestParam String username, @RequestParam String password,
            @RequestParam String profilename,@RequestParam String realname) {
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/";
        }
        
        Account a = new Account();
        a.setPassword(passwordEncoder.encode(password));
        a.setUsername(username);
        a.setRealname(realname);
        a.setProfilename(profilename);
        a.setSkills(new ArrayList<>());
        accountRepository.save(a);
        return "redirect:/";
    }
    
    
    //Luo uusi skill
    @PostMapping("/account/skills")
    public String addSkill(@RequestParam String headline, @RequestParam String desc) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account account = accountRepository.findByUsername(username);
        Skill skill = new Skill(headline, desc,0, account, new ArrayList<>());
        
        skillRepository.save(skill);
        return "redirect:/";
    }
    
    

    
    


}
