package bt.org.dsp.crst.userManager.model;

import bt.org.dsp.crst.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sa_group_wise_permission")
public class PermissionSetup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @NotNull(message = "Couldn't identify Group ID")
    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    private UserGroup userGroup;
    @ManyToOne
    @JoinColumn(name = "screenId", referencedColumnName = "id")
    private Screen screen;
    private boolean isSave;
    private boolean isView;
    private boolean isEdit;
    private boolean isDelete;

}