/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
public class Connect extends AbstractPersistable<Long> {
    
    
    
    @OneToOne
    private Account sender;
    
    @OneToOne
    private Account receiver;
    
    private boolean status = false;
    
    private Date timeSent;
}
