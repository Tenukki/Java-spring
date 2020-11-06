/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Repo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Entity.Account;
import projekti.Entity.Skill;

/**
 *
 * @author Santeri
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByAccount(Account a, Sort sort);
    
}
