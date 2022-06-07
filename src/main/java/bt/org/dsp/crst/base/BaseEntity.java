package bt.org.dsp.crst.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "modifiedby")
    private String modifiedBy;

    @Column(name = "modifieddate")
    private Date modifiedDate;
}
