package bt.org.dsp.crst.eReceipt;

import bt.org.dsp.crst.common.CommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iReceiptRepository extends JpaRepository<ReceiptEntity, Integer>, CommonRepository {

}
