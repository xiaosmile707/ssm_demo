package com.hp.ssm.controller;


import com.hp.ssm.model.Comment;
import com.hp.ssm.model.Mission;
import com.hp.ssm.model.PageCollection;
import com.hp.ssm.model.User;
import com.hp.ssm.model.ValidateTable;
import com.hp.ssm.service.CommentService;
import com.hp.ssm.service.MissionService;
import com.hp.ssm.service.UserService;
import com.hp.ssm.service.ValidateService;
import com.hp.ssm.utils.GoogleAuthenticator;
import com.hp.ssm.utils.JavaMD5;
import com.hp.ssm.utils.JavaMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wyq
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateService validateService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/reset")
    public String getReset() {
        return "resetPwd";
    }

    @GetMapping("/twoSteps")
    public String addOrDelTwoSteps() {
        return "user/twoSteps";
    }

    @GetMapping("/realNameAuth")
    public String getRealNameAuth() {
        return "user/realNameAuth";
    }

    @GetMapping("/index")
    public String getIndex(Model model, Integer pageNo) {
        int pagesNo = (pageNo == null) ? 1 : pageNo;
        int pageSize = 4;
        List<Comment> comments = commentService.getShowComments();
        PageCollection<Mission> coll = missionService.getAllMission(pageSize, pagesNo);
        //missions可能为空
        List<Mission> missions = coll.getItems();
        List<Mission> missionList = new ArrayList<>(missions);
        //渲染model返回分页信息
        model.addAttribute("missions", missionList);
        model.addAttribute("pageNo", coll.getPageNo());
        model.addAttribute("totalCount", coll.getTotalCount());
        model.addAttribute("totalPages", coll.getTotalPages());
        model.addAttribute("comments", comments);
        return "index";
    }

    @GetMapping("/twoStepsValidate")
    public String getValidate() {
        return "validation";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/index";
    }

    @PostMapping("/login")
    public String userLoginIn(Model model, User user, HttpServletRequest request) {
        //验证数据
        if (user.getEmail() == null || "".equals(user.getEmail())) {
            model.addAttribute("errorMsg", "邮箱不能为空");
            return "register";
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            model.addAttribute("errorMsg", "密码不能为空");
            return "register";
        }
        user.setPassword(JavaMD5.getMd5Code(user.getPassword()));
        //检查数据库数据是否匹配
        if (userService.checkAccount(user)) {

            User databaseUser = userService.getUserByEmail(user.getEmail());
            //检查账户是否激活
            if (databaseUser.getIsActive()) {

                //根据是否开启两步验证，跳转不同界面
                if (databaseUser.getRnauth()) {
                    //开启两步验证
                    HttpSession session = request.getSession();
                    session.setAttribute("email", databaseUser.getEmail());
                    return "redirect:/user/twoStepsValidate";
                } else {
                    //未开启两步验证
                    HttpSession session = request.getSession();
                    session.setAttribute("user", databaseUser);
                    return "redirect:/user/index";
                }
            } else {
                model.addAttribute("errorMsg", "账户未激活");
                return "login";
            }
        } else {
            model.addAttribute("errorMsg", "密码错误");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(Model model, User user, HttpServletRequest request) {
        //验证非空数据

        if (user.getName() == null || "".equals(user.getName())) {
            model.addAttribute("errorMsg", "用户名不能为空");
            return "register";
        }
        if (user.getEmail() == null || "".equals(user.getEmail())) {
            model.addAttribute("errorMsg", "邮箱不能为空");
            return "register";
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            model.addAttribute("errorMsg", "密码不能为空");
            return "register";
        }
        long exceptionUsers = userService.getExceptionUserByEmail(user.getEmail());
        if (exceptionUsers > 0) {
            model.addAttribute("errorMsg", "用户已存在");
            return "register";
        }


        ValidateTable table = new ValidateTable();
        user.setPassword(JavaMD5.getMd5Code(user.getPassword()));
        userService.addUser(user);
        int userId = user.getId();
        table.setUserId(userId);
        Calendar now = Calendar.getInstance();
        table.setStartTime(now.getTime());
        now.add(Calendar.DATE, 1);
        table.setEndTime(now.getTime());
        String secretCode = JavaMD5.getMd5Code(user.getEmail());
        table.setValidateCode(secretCode);
        validateService.addValidation(table);
        //发送邮件
        String Url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/user/validate?email=" + secretCode;
        String content = "您注册的账号需要激活";
        JavaMail.sendMail(user.getEmail(), Url, content);
        model.addAttribute("errorMsg", "邮件已发往您的邮箱");
        return "register";
    }

    @GetMapping("/validate")
    public String validateEmail(Model model, String email) {
        if (email == null) {
            return "register";
        }
        ValidateTable table = validateService.getTableByValidateCode(email);
        Date endTime = table.getEndTime();
        if (endTime == null) {
            model.addAttribute("errorMsg", "查询参数错误");
            return "register";
        }
        if (endTime.before(new Date())) {
            model.addAttribute("errorMsg", "超出注册规定时间，请重新注册");
            //出错辣，你滴时间超过规定注册时间
            return "register";
        }
        if (endTime.after(new Date())) {
            int userId = table.getUserId();
            int id = table.getId();
            userService.setUserActive(userId);
            validateService.delValidationById(id);
            model.addAttribute("errorMsg", "恭喜你注册成功");
        }

        return "register";
    }

    @PostMapping("/twoStepsValidate")
    public String validate(HttpServletRequest request, Model model, String email, Long twoStepsCode) {
        long time = System.currentTimeMillis();
        String savedSecret = userService.getSecretByEmail(email);
        if (savedSecret == null || "".equals(savedSecret)) {
            model.addAttribute("errorMsg", "secret为空或不存在");
            return "validation";
        } else {
            GoogleAuthenticator ga = new GoogleAuthenticator();
            ga.setWindowSize(5);
            boolean r = ga.check_code(savedSecret, twoStepsCode, time);
            if (r) {
                User databaseUser = userService.getUserByEmail(email);
                HttpSession session = request.getSession();
                session.setAttribute("user", databaseUser);
                return "redirect:/user/index";
            } else {
                model.addAttribute("errorMsg", "twoStepsCode error");
                return "validation";
            }
        }
    }

    @PostMapping("/delTwoSteps")
    public String delTwoSteps(Model model, String email, Long code) {
        long time = System.currentTimeMillis();
        String savedSecret = userService.getSecretByEmail(email);
        if (savedSecret == null || "".equals(savedSecret)) {
            model.addAttribute("errorMsg", "secret为空或不存在");
        } else {
            GoogleAuthenticator ga = new GoogleAuthenticator();
            ga.setWindowSize(5);
            boolean r = ga.check_code(savedSecret, code, time);
            if (r) {
                model.addAttribute("errorMsg", "twoStepsCode correct");
                userService.setTwoStepsUnActive(email);
            } else {
                model.addAttribute("errorMsg", "twoStepsCode error");
            }
        }
        return "user/twoSteps";
    }

    @PostMapping("/addTwoSteps")
    public String addTwoSteps(Model model, String email, Long code) {
        long time = System.currentTimeMillis();
        String savedSecret = userService.getSecretByEmail(email);
        if (savedSecret == null || "".equals(savedSecret)) {
            model.addAttribute("errorMsg", "secret为空或不存在");
        } else {
            GoogleAuthenticator ga = new GoogleAuthenticator();
            ga.setWindowSize(5);
            boolean r = ga.check_code(savedSecret, code, time);
            if (r) {
                model.addAttribute("errorMsg", "twoStepsCode correct");
                userService.setTwoStepsActive(email);
            } else {
                model.addAttribute("errorMsg", "twoStepsCode error");
            }
        }
        return "user/twoSteps";
    }

    @GetMapping("/missionDetail/{missionId}")
    public String getMissionDetail(@PathVariable Integer missionId, Model model) {
        missionId = (missionId == null) ? 1 : missionId;
        Mission mission = missionService.getMissionById(missionId);
        List<Comment> comments = commentService.getCommentsByMissionId(missionId);
        String missionUserName = userService.getUserById(mission.getSubmitId()).getName();
        model.addAttribute("missionUserName", missionUserName);
        model.addAttribute("mission", mission);
        model.addAttribute("comments", comments);
        return "user/missionDetail";
    }

    @GetMapping("/submitMissionDetail/{missionId}")
    public String getSubmitMissionDetail(@PathVariable Integer missionId, Model model) {
        missionId = (missionId == null) ? 1 : missionId;
        Mission mission = missionService.getMissionById(missionId);
        List<Comment> comments = commentService.getCommentsByMissionId(missionId);
        String missionUserName = userService.getUserById(mission.getSubmitId()).getName();
        model.addAttribute("missionUserName", missionUserName);
        model.addAttribute("mission", mission);
        model.addAttribute("comments", comments);
        return "user/submitMissionDetail";
    }

    @GetMapping("/receiveMissionDetail/{missionId}")
    public String getReceiveMissionDetail(@PathVariable Integer missionId, Model model) {
        missionId = (missionId == null) ? 1 : missionId;
        Mission mission = missionService.getMissionById(missionId);
        List<Comment> comments = commentService.getCommentsByMissionId(missionId);
        String missionUserName = userService.getUserById(mission.getSubmitId()).getName();
        model.addAttribute("missionUserName", missionUserName);
        model.addAttribute("mission", mission);
        model.addAttribute("comments", comments);
        return "user/receiveMissionDetail";
    }

    @PostMapping("/addComment")
    public String addComment(Integer missionId, Integer submitId, String content) {
        Comment comment = new Comment();
        comment.setSubmitId(submitId);
        comment.setMissionId(missionId);
        comment.setContent(content);
        commentService.addComment(comment);
        return "redirect:/user/missionDetail/" + missionId;
    }

    @GetMapping("/userDetail/{userId}")
    public String getUserDetail(@PathVariable Integer userId, Model model) {
        userId = (userId == null) ? 1 : userId;
        User user = userService.getUserById(userId);
        List<Mission> missions = missionService.getMissionsById(userId);
        model.addAttribute("user", user);
        model.addAttribute("missions", missions);
        return "user/userDetail";
    }

    @PostMapping("/updateUser")
    public String updateUser(User user) {
        int userId = user.getId();
        userService.updateUser(user);
        return "redirect:/user/userDetail/" + userId;
    }

    @GetMapping("/{userId}/missionList/{missionType}")
    public String getMissionList(Model model, @PathVariable Integer userId, @PathVariable Integer missionType) {
        String status;
        switch (missionType) {
            case 1:
                status = "未开始";
                break;
            case 2:
                status = "正在进行";
                break;
            case 3:
                status = "已完成";
                break;
            default:
                return null;
        }
        List<Mission> missions = missionService.getMissionsByUserIdAndStatus(userId, status);
        model.addAttribute("status", status);
        model.addAttribute("missions", missions);
        return "user/missionList";
    }

    @GetMapping("/addMission")
    public String getAddMission() {
        return "user/addMission";
    }

    @PostMapping("/addMission")
    public String addMission(Mission mission) {
        missionService.addMission(mission);
        return "user/addMission";
    }

    @GetMapping("/submitMissionList/{userId}")
    public String getSubmitMissionList(@PathVariable Integer userId, Model model) {
        List<Mission> missions = missionService.getMissionsBySubmitId(userId);
        model.addAttribute("missions", missions);
        return "user/submitMissionList";
    }

    @PostMapping("/validateMission")
    public String validateMission(Integer missionId, String status) {
        if ("未开始".equals(status)) {
            missionService.validateMission(missionId, "正在进行");
            return "redirect:/user/receiveMissionDetail/" + missionId;
        } else {

            missionService.validateMission(missionId, "已完成");
            return "redirect:/user/submitMissionDetail/" + missionId;
        }
    }

    @PostMapping("/realNameAuth")
    public String realNameAuth(Integer userId, String idNumber) {
        userService.userRealNameAuth(userId, idNumber);
        return "redirect:/user/userDetail/" + userId;
    }

    @PostMapping("/pic/upload")
    public String uploadUserPic(@RequestParam("file") MultipartFile file, Integer userId) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get("E:\\upload/" + file.getOriginalFilename());
        Files.write(path, bytes);
        userService.addUserPic(userId, path.toString());
        return "redirect:/user/userDetail/" + userId;
    }

    @GetMapping("/mission/rate/{missionId}")
    public String addRate(@PathVariable int missionId) {
        userService.addMissionRate(missionId);
        return "redirect:/user/index";
    }

    @InitBinder
    protected void init(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
