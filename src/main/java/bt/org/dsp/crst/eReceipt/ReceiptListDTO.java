package bt.org.dsp.crst.eReceipt;

import lombok.Data;

import java.util.Date;

@Data
public class ReceiptListDTO {
    private Integer id;

    private String receiptNumber;

    private Integer reportNumber;

    private Date receiptDate;

    private String receivedBy;

    private Integer receivedAmt;

    private String debtorType;

    private String from;
    private String accountOf;
    private Integer branchId;
    private Integer sumAmount;
    private String username;

    private String createdby;
    private Date createddate;
    private String group_name;
    private String receivedfrom;

}
