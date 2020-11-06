/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Santeri
 */

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Post extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Account sender;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    
    //Tässä vika kun käyttäjä ei voi liketa 
   @ManyToMany(cascade = CascadeType.ALL)
    private List<Account> likers = new ArrayList<>();
    
    @NotEmpty
    private String content;
    private Date ptime;
    
}
