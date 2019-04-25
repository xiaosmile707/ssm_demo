package com.hp.ssm;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsmApplicationTests {
    private static final String ACCOUNT_SID =
            "ACc4e910004cf09f1f577f1e2866dd626c";
    private static final String AUTH_TOKEN =
            "b72913fad2d868d27ddc0015ae98662e";
    @Test
    public void contextLoads() {
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        String to = "+8617695500720";
        String from = "+12625824665";
        String content = "你好，this is come from china tj";
        Message message = Message.creator(new PhoneNumber(to),new PhoneNumber(from),content)
                .create();
        System.out.println(message.getSid());
    }

}
