package com.hp.ssm.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsUtil {
    private static final String ACCOUNT_SID =
            "ACc4e910004cf09f1f577f1e2866dd626c";
    private static final String AUTH_TOKEN =
            "b72913fad2d868d27ddc0015ae98662e";

    public static void sendSmsText(String toNumber, String content) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String from = "+12625824665";
        Message message = Message
                .creator(new PhoneNumber(toNumber), new PhoneNumber(from), content)
                .create();
        System.out.println("from:" + from + " to:" + toNumber + " content:" + content + " " + message.getSid());
    }
}
