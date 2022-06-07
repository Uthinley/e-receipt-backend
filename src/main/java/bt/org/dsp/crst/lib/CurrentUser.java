package bt.org.dsp.crst.lib;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrentUser {
    private Integer userID;
    private String userName;
    private String userCode;
    private String branchCode;
    private String branchName;
    private String userGroup;
    private String employeeCode;
    private BigDecimal employeeId;
    private Integer departmentId;
    private String departmentName;
    private String email;
    private Date serverDate;
}
