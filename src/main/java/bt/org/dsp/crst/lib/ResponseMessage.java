package bt.org.dsp.crst.lib;

import bt.org.dsp.crst.eReceipt.ReceiptListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private Integer status;
    private String text;
    private Object dto;
    private String value;
    private List<ReceiptListDTO> receiptListDTOList;

    // Data for Approval Request
    private String responseText;

    public ResponseMessage(String text) {
        this.text = text;
    }
}
