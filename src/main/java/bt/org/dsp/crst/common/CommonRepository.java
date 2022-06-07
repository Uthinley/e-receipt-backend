package bt.org.dsp.crst.common;

import bt.org.dsp.crst.eReceipt.ReceiptListDTO;
import bt.org.dsp.crst.userManager.dto.UserDTO;

import java.util.List;

public interface CommonRepository {
    UserDTO findByUsername(String username);
    Integer getIndex();
    void addIndex();
    List<ReceiptListDTO> findReceiptList();

}
