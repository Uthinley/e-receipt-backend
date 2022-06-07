package bt.org.dsp.crst.userManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sa_screen")
public class Screen {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private String url;
    private Integer status;
}
