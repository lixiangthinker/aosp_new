package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.utils.GlobalSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParseRepoServiceTests {

    @Autowired
    private ParseRepoService parseRepoService;

    @Test
    public void testParseProject() {
        boolean result = parseRepoService.parseProject();
    }

    @Test
    public void testGetCommitTableName(){
        String result =
                GlobalSettings.getCommitTableName("platform/frameworks/base");
        System.out.println(result);
    }

    @Test
    public void testParseCommit() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strSince = "2018-07-01";
        Date since = sdf.parse(strSince);
        boolean result = parseRepoService.parseCommit("platform/frameworks/base", since);
    }
}
