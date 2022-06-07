package bt.org.dsp.crst.userManager.model;

import bt.org.dsp.crst.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sa_refresh_token")
public class RefreshToken extends BaseEntity {
    @Id
    @Column(name = "token")
    private String token;
}
