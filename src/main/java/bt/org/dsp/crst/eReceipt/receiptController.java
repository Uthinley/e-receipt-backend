package bt.org.dsp.crst.eReceipt;

import bt.org.dsp.crst.base.BaseController;
import bt.org.dsp.crst.lib.ResponseMessage;
import bt.org.dsp.crst.userManager.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(value = "/receipt")
public class receiptController extends BaseController {

    @Autowired
    private receiptService receiptService;
    private AuthService authService;

    /**
     * save user
     * @param receiptEntity
     * @return responseMessage
     */
    @PostMapping(value = "/save")
    public ResponseMessage save(@RequestBody ReceiptEntity receiptEntity) {
        String currentUser= authService.getCurrentUser().getUsername();
        return receiptService.save(receiptEntity, currentUser);
    }

    @PostMapping(value = "/saveReceipt")
    public ResponseMessage saveReceipt (@RequestParam("ReceiptListDTO") String ReceiptListDTO) throws JsonProcessingException {
        return receiptService.saveReceipt(ReceiptListDTO);
    }

    @GetMapping(value = "/receiptList")
    public List<ReceiptListDTO> getReceiptList(){
        return receiptService.getReceiptList();
    }

}
