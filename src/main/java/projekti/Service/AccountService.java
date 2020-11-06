/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.Entity.Account;
import projekti.Entity.Connect;
import projekti.Repo.AccountRepository;
import projekti.Repo.ConnectRepository;
import projekti.Repo.SkillRepository;

/**
 *
 * @author Santeri
 */
@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    ConnectRepository connectionRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    public void deleteFriend(String name){
        Account a = getCurrentUser();
        Account b = accountRepository.findByProfilename(name);
        Connect con = connectionRepository.findBySenderAndReceiver(a, b);
        Connect con2 = connectionRepository.findBySenderAndReceiver(b, a);
        
        if(con == null){
            connectionRepository.delete(con2);
        }else if(con2 == null){
            connectionRepository.delete(con);
        }
        
        
    }
    
    public Account getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return accountRepository.findByUsername(username);
    }
    
    public boolean isempty(Account s, Account r){
        Connect con = connectionRepository.findBySenderAndReceiver(s, r);
        Connect con2 = connectionRepository.findBySenderAndReceiver(r, s);
        if(con == null && con2 == null){
            return true;
        }
        return false;
    }
    
    public List<Account> getAllFriends(String profileName){
        List<Account> friends = new ArrayList<>();
        Account a = accountRepository.findByProfilename(profileName);
        
        for (Connect f : connectionRepository.findBySender(a)){
            if (f.isStatus()&& !friends.contains(f.getReceiver())){                
                friends.add(f.getReceiver());
            }
        }
        for (Connect f : connectionRepository.findByReceiver(a)){
            if (f.isStatus()&& !friends.contains(f.getSender())){
                
                friends.add(f.getSender());
            }
        }        
        return friends;
    }
    
    
    
}