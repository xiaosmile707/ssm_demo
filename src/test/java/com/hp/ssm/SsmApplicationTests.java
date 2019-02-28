package com.hp.ssm;

import com.hp.ssm.dao.CommentDao;
import com.hp.ssm.dao.MissionDao;
import com.hp.ssm.dao.UserDao;
import com.hp.ssm.model.Comment;
import com.hp.ssm.model.Mission;
import com.hp.ssm.model.User;
import com.hp.ssm.quartz.QuartzJob;
import com.hp.ssm.quartz.QuartzManage;
import com.hp.ssm.quartz.TaskTest;
import com.hp.ssm.utils.GetMouthByDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsmApplicationTests {
    @Autowired
    private MissionDao missionDao;
    @Autowired
    private QuartzManage manage;

    @Test
    public void contextLoads() throws Exception {
        Mission mission=new Mission();
        mission.setSubmitId(49);
        mission.setName("xiaosmile");
        mission.setDescription("描述");
        mission.setContent("具体内容");
        mission.setStartTime(new Date());
        mission.setEndTime(new Date());
        mission.setReward(new BigDecimal(20));
        mission.setStatus("未开始");
        missionDao.addMission(mission);
    }

    @Test
    public void testQuartz() throws ClassNotFoundException, InstantiationException, SchedulerException, IllegalAccessException {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setCronExpression("0 0/1 * * * ?");
        quartzJob.setJobName("zuoye");
        quartzJob.setTriggerName("dingshiqi");
        quartzJob.setBeanName(TaskTest.class.getName());
        manage.addJob(quartzJob);
    }

}
