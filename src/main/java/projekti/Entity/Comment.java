/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Comment extends AbstractPersistable<Long> implements Comparable<Comment>{
    
    @ManyToOne
    private Account sender;
    
    @ManyToOne
    private Post post;
    
    @NotEmpty
    private String content;
    
    private Date ptime;

    @Override
    public int compareTo(Comment o) {
        int r = 0;
        if (this.ptime.after(o.ptime)){
            r = -1;
        }
        if (this.ptime.before(o.ptime)){
            r = 1;
        }
        return r;
    }
    
    
}

