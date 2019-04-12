package com.hp.ssm.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hp.ssm.service.MissionService;
import com.hp.ssm.service.UserService;
import com.hp.ssm.utils.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Controller
public class UtilsController {
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;

    @GetMapping("/qrCode/{content}")
    public void getImg(@PathVariable String content, HttpServletResponse res) throws IOException, WriterException {
        String secret = GoogleAuthenticator.generateSecretKey();
        if (!userService.checkTwoStepsActiveByEmail(content)) {
            userService.updateSecretByEmail(content, secret);
        }
        String secretFormat = "otpauth://totp/" + content + "@zbpt?secret=" + secret;
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

    @GetMapping("/user/pic/{userId}")
    public void getUserPic(@PathVariable Integer userId, HttpServletResponse res) throws IOException {
        String userPicAddress = userService.getUserPic(userId);
        File file;
        file = new File("E:\\upload\\NULL.jpg");
        if (userPicAddress != null && !"".equals(userPicAddress)) {
            file = new File(userPicAddress);
        }


        FileInputStream fis = new FileInputStream(file);

        if (fis != null) {
            int i = fis.available(); // 得到文件大小
            byte[] data = new byte[i];
            fis.read(data); // 读数据
            fis.close();
            res.setContentType("image/png");  // 设置返回的文件类型
            OutputStream toClient = res.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        }
    }

    @GetMapping("/mission/pic/{missionId}")
    public void getMissionPic(@PathVariable Integer missionId, HttpServletResponse res) throws IOException {
        String missionPicAddress = missionService.getMissionPic(missionId);
        File file;
        file = new File("E:\\upload\\empty.jpg");
        if (missionPicAddress != null && !"".equals(missionPicAddress)) {
            file = new File(missionPicAddress);
        }


        FileInputStream fis = new FileInputStream(file);

        if (fis != null) {
            int i = fis.available(); // 得到文件大小
            byte[] data = new byte[i];
            fis.read(data); // 读数据
            fis.close();
            res.setContentType("image/png");  // 设置返回的文件类型
            OutputStream toClient = res.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        }
    }

}
