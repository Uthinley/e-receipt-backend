package bt.org.dsp.crst.eReceipt;

import bt.org.dsp.crst.base.BaseService;
import bt.org.dsp.crst.lib.ResponseMessage;
import bt.org.dsp.crst.userManager.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class receiptService extends BaseService {

    @Autowired
    private iReceiptRepository iReceiptRepository;
    private AuthService authService;


//    @Transactional
    public ResponseMessage save(ReceiptEntity receiptEntity, String currentUser) {
        if (StringUtils.isEmpty(receiptEntity)) {
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setResponseText("Nothing to save.");
            return responseMessage;
        }
        try{
         receiptEntity.setCreatedBy( currentUser );
            receiptEntity.setCreatedDate( new Date());
//            receiptEntity.setBranchId(0);
//            receiptEntity.setModifiedBy( authService.getCurrentUser().getUsername() );
//            receiptEntity.setModifiedDate( new Date() );
            iReceiptRepository.save(receiptEntity);
            responseMessage.setStatus(SUCCESSFUL_STATUS);
            responseMessage.setText("saved successfully.");
            return responseMessage;

        }catch (Exception exception){
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setResponseText("Could not save." + exception);
            return responseMessage;
        }
    }

    @Transactional
    public String generateReceiptNumber(){
        String startLetter = "E";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Integer indexNo = iReceiptRepository.getIndex();
        LocalDate.now().format(formatter);
        String receiptNo = startLetter + LocalDate.now().format(formatter) + indexNo;
        return receiptNo;
    }

    @Transactional
    public ResponseMessage saveReceipt(String receiptDtls) throws JsonProcessingException {
//        List<ReceiptDTO> receiptDTO = (List<ReceiptDTO>) new ObjectMapper().readValue(receiptDtls, ReceiptDTO.class);
        ReceiptDTO receiptDTO = new ObjectMapper().readValue(receiptDtls, ReceiptDTO.class);
        List<ReceiptListDTO> receiptListDTOS = receiptDTO.getReceiptList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer branchId=authService.getCurrentUser().getGroupId();
        if (CollectionUtils.isEmpty(receiptListDTOS)){
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setText("Please enter all the required information or nothing to save.");
            return responseMessage;
        }
        try{
            Integer sum = 0;
            ReceiptListDTO receiptListDTO = new ReceiptListDTO();
            String generatedRN = "";
            for(ReceiptListDTO dto : receiptListDTOS){
                ReceiptEntity receiptEntity = new ReceiptEntity();
                generatedRN = generateReceiptNumber();
                receiptEntity.setReportNumber(dto.getReportNumber());
                receiptEntity.setReceiptDate(dto.getReceiptDate());
                receiptEntity.setReceivedAmt(dto.getReceivedAmt());
                receiptEntity.setReceiptNumber(generateReceiptNumber());
                receiptEntity.setDebtorType(dto.getDebtorType());
                receiptEntity.setReceivedfrom(dto.getFrom());
                receiptEntity.setCreatedBy(authentication.getName());
                receiptEntity.setCreatedDate(new Date());
                receiptEntity.setBranchId(branchId);
                iReceiptRepository.save(receiptEntity);
                receiptListDTO.setReceiptDate(dto.getReceiptDate());
                receiptListDTO.setFrom(dto.getFrom());
                receiptListDTO.setReceiptNumber(generateReceiptNumber());
                sum = sum + dto.getReceivedAmt();

            }
            receiptListDTO.setUsername(authentication.getName());
            receiptListDTO.setSumAmount(sum);
            iReceiptRepository.addIndex();
            responseMessage.setValue(generatedRN);
            responseMessage.setDto(receiptListDTO);
            responseMessage.setReceiptListDTOList(receiptListDTOS);
//            responseMessage.setValue(String.valueOf(receiptDTO));

        }catch (Exception ex){
            responseMessage.setStatus(UNSUCCESSFUL_STATUS);
            responseMessage.setText("Could not save." + ex);
            return responseMessage;
        }
        responseMessage.setStatus(SUCCESSFUL_STATUS);
        responseMessage.setText("saved successfully.");
        return responseMessage;
    }

    @Transactional
    public List<ReceiptListDTO> getReceiptList() {
        return (List<ReceiptListDTO>)iReceiptRepository.findReceiptList();
    }
}
