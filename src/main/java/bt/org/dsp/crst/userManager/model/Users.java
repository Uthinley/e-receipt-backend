package bt.org.dsp.crst.userManager.model;


import bt.org.dsp.crst.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sa_users")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @NotNull
    @NotBlank(message = "Username is required")
    private String username;

    private String cid;

    private String password;
    @Column(columnDefinition = "boolean default true")
    private Boolean isFirstLogin;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    private Date passwordExpiryDate;

    private Integer groupId;
    private Integer userType;
    private Integer status;

}
