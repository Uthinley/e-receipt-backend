package bt.org.dsp.crst.common;

import bt.org.dsp.crst.lib.ResponseMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBean {
    @Bean
    public ResponseMessage getResponseMessage() {
        return new ResponseMessage();
    }
}
