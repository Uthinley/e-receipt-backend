package bt.org.dsp.crst.base;

import bt.org.dsp.crst.lib.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    protected static final int SUCCESSFUL_STATUS = 1;
    protected static final int UNSUCCESSFUL_STATUS = 0;
    @Autowired
    protected ResponseMessage responseMessage;
}
