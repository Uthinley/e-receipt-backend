package bt.org.dsp.crst.eReceipt;

import bt.org.dsp.crst.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "receipt_dtls")
public class ReceiptEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String receiptNumber;

    private Integer reportNumber;

    private Date receiptDate;

    private String receivedBy;

    private Integer receivedAmt;

    private String debtorType;

    private String receivedfrom;
//    private String accountOf;
    private Integer branchId;

}
