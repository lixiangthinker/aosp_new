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
        result = GlobalSettings.getCommitTableName("platform/external/abc-def");
        System.out.println(result);

        // result lenth < 64
        String longName = "tbl_commit_platform_prebuilts_gcc_darwin_x86_aarch64_aarch64_linux_android_4_9";
        System.out.println("longName.length() = " + longName.length());
        if (longName.length() > 64) {
            longName = "tbl_commit___" + longName.substring(longName.length() - 50);
            //tbl_commit_platform_prebuilts_gcc_darwin_x86_aarch64_aarch64_linux_android_4_9
            //tbl_commit___
            System.out.println("new longName.length = " + longName.length());
        }
        System.out.println("longName = " + longName);
    }

    @Test
    public void testParseCommit() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strSince = "2018-07-01";
        Date since = sdf.parse(strSince);
        boolean result = parseRepoService.parseCommit("platform/frameworks/base", since);
    }

    @Test
    public void testParseAllCommit() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strSince = "2018-07-01";
        Date since = sdf.parse(strSince);
        boolean result = parseRepoService.parseAllCommit(since);
    }
}
