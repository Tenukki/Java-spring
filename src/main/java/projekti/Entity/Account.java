package projekti.Entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import static javax.swing.text.StyleConstants.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    private String username;
    
    @NotEmpty
    private String realname;
    
    @NotEmpty
    private String profilename;
    
    @NotEmpty
    private String password;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>();
    
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content = null;
    
    //Tässä vika
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Post> liked = new ArrayList<>();
    
    @OneToMany
    private List<Post> post;

}
