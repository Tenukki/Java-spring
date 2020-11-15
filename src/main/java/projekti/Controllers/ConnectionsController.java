/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import projekti.Entity.Connect;
import projekti.Repo.AccountRepository;
import projekti.Repo.ConnectRepository;
import projekti.Repo.SkillRepository;
import projekti.Service.AccountService;

/**
 *
 * @author Santeri
 */
@Controller
public class ConnectionsController {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    ConnectRepository connectionRepository;
    
    @Autowired
    AccountService accountService;

    
    
    //profiiline looppaus tällä
    @GetMapping("/connect")
    public String getConnect(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account a = accountRepository.findByUsername(username);
        List<Account> lista = accountRepository.findAll();
        lista.remove(a);
        model.addAttribute("allUsers",lista);
        model.addAttribute("user",null);
        model.addAttribute("connect",connectionRepository.findByReceiver(a));
        return "connect";
    }
    
    @PostMapping("/connect/find")
    public String findUser(Model model,@RequestParam String name) {
        
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account a = accountRepository.findByProfilename(name);
        Account a2 = accountRepository.findByUsername(username);
        List<Account> lista = accountRepository.findAll();
        lista.remove(a);
        model.addAttribute("allUsers",lista);
        model.addAttribute("user",a);
        model.addAttribute("connect",connectionRepository.findByReceiver(a2));
       
        return "connect";
    }
    
    @PostMapping("/accound/add/{name}")
    public String addNewConnection(@PathVariable String name) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account sender = accountRepository.findByUsername(username);
        Account receiver = accountRepository.findByProfilename(name);
        if(accountService.isempty(sender,receiver)){
            Connect con = new Connect();
        con.setTimeSent(new Date());
        con.setSender(accountRepository.findByUsername(username));
        con.setReceiver(accountRepository.findByProfilename(name));
        connectionRepository.save(con);
        return "redirect:/connect";
           
        }
         return "redirect:/connect";
    }
    
    //hyväksy
    @PostMapping("/accound/connect/accept/{asking}")
    public String acceptConnection(@PathVariable String asking) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account sender = accountRepository.findByProfilename(asking);
        Account reciever = accountRepository.findByUsername(username);
        Connect con = connectionRepository.findBySenderAndReceiver(sender,reciever);
        con.setStatus(true);
        connectionRepository.save(con);
        return "redirect:/profile";
    }
    
    //hylkää
    @PostMapping("/accound/connect/decline/{asking}")
    public String declineConnection(@PathVariable String asking) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account sender = accountRepository.findByProfilename(asking);
        Account reciever = accountRepository.findByUsername(username);
        
        
        Connect con = connectionRepository.findBySenderAndReceiver(sender,reciever);
        connectionRepository.delete(con);
        return "redirect:/profile";
    }
    
    @PostMapping("/accound/connect/delete/{friend}")
    public String deleteConnection(@PathVariable String friend){
       // connectionRepository.deleteById(id);
        System.out.println("DELETE " +friend );
        accountService.deleteFriend(friend);
        return "redirect:/profile";
    }

}