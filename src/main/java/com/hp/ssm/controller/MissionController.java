package com.hp.ssm.controller;

import com.hp.ssm.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/mission")
public class MissionController {
    @Autowired
    private MissionService missionService;

    @PostMapping("/upload/pic")
    public String uploadMissionPic(@RequestParam("file") MultipartFile file, Integer missionId) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get("E:\\upload/" + file.getOriginalFilename());
        Files.write(path, bytes);
        missionService.addMissionPic(missionId,path.toString());
        return "redirect:/user/missionDetail/"+missionId;
    }
}
