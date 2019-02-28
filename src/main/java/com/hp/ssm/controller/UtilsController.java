package com.hp.ssm.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hp.ssm.service.UserService;
import com.hp.ssm.utils.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Controller
public class UtilsController {
    @Autowired
    private UserService userService;


    @GetMapping("/qrCode/{content}")
    public void getImg(@PathVariable String content,HttpServletResponse res) throws IOException, WriterException {
        String secret = GoogleAuthenticator.generateSecretKey();
        if (!userService.checkTwoStepsActiveByEmail(content)){
            userService.updateSecretByEmail(content,secret);
        }
        String secretFormat="otpauth://totp/"+content+"@zbpt?secret="+secret;
        //设置格式
        String format = "png";
        int width = 300;
        int height = 300;
        //定义内容

        //response设置返回内容格式
        res.setContentType("image/" + format);
        if (!"".equals(secret)) {
            //定义二维码的参数
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 2);
            //生成二维码
            OutputStream stream = res.getOutputStream();
            BitMatrix bitMatrix = new QRCodeWriter().encode(secretFormat, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
            stream.flush();
            stream.close();
        }
    }
}
