package bt.org.dsp.crst.common;

import bt.org.dsp.crst.base.BaseDao;
import bt.org.dsp.crst.eReceipt.ReceiptListDTO;
import bt.org.dsp.crst.userManager.dto.UserDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CommonRepositoryImpl extends BaseDao implements CommonRepository{
    @Transactional
    public UserDTO findByUsername(String username) {
        String sqlQuery = "SELECT A.username username, A.password, A.group_id AS groupId, A.cid FROM sa_users A WHERE A.username=:username";
        org.hibernate.Query hQuery = hibernateQuery(sqlQuery, UserDTO.class).setParameter("username", username);
        return (UserDTO) hQuery.uniqueResult();
    }

    @Override
    public Integer getIndex() {
        String sqlQuery = "SELECT ref_index FROM receipt_index_ref";
        org.hibernate.Query hQuery = hibernateQuery(sqlQuery);
        return (Integer) hQuery.uniqueResult();
    }

    @Modifying
    @Transactional
    public void addIndex(){
        String sqlQuery = "UPDATE receipt_index_ref SET ref_index=ref_index+1 WHERE id= 1";
        org.hibernate.Query hQuery = hibernateQuery(sqlQuery);
        hQuery.executeUpdate();
    }

    @Override
//    @Transactional
    public List<ReceiptListDTO> findReceiptList() {
        String sqlQuery = "SELECT A.createdby, A.createddate, A.debtor_type AS debtorType, A.report_number AS reportNumber, A.receipt_date AS receiptDate, A.receipt_number AS receiptNumber, received_amt AS receivedAmt, A.receivedfrom, B.group_name FROM receipt_dtls A INNER JOIN sa_user_group B ON A.branch_id = B.id";
        org.hibernate.Query hQuery = hibernateQuery(sqlQuery, ReceiptListDTO.class);
        return (List<ReceiptListDTO>)hQuery.list();
    }

}
