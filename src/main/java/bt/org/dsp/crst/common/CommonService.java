package bt.org.dsp.crst.common;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
public class CommonService {

    /**
     * read excel file and convert to object class
     *
     * @param mFile    -- MultipartFile
     * @param classObj -- Class to map
     * @return List
     * @throws IOException
     */
    public List readExcel(MultipartFile mFile, Class classObj) throws IOException {
//        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings(0).datePattern("dd-rererwerrrrwrwer-yyyy").build();
        InputStream inputStream = mFile.getInputStream();
        List list = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, classObj);
        inputStream.close();
        return list;
    }
}
