package com.hp.ssm;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsmApplicationTests {

    @Test
    public void contextLoads() {
        boolean isDelay = ArrayUtils.contains(new String[]{"TASK","APPROVAL"},"TASK");
        System.out.println(isDelay);
    }

}
