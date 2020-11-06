/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Entity.Account;
import projekti.Entity.Skill;
import projekti.Entity.Connect;

/**
 *
 * @author Santeri
 */
public interface ConnectRepository extends JpaRepository<Connect, Long> {
    Connect findBySenderAndReceiver(Account a,Account b);
    Connect findBySenderOrReceiver(Account a,Account b);
    List<Connect> findByReceiver(Account a);
    List<Connect> findBySender (Account a);
}
