package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.CommitModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommitMapperTests {
    private static final String TEST_TABLE = "tbl_commit_framework_base";
    @Autowired
    CommitMapper mapper;

    @Test
    public void testMapperNotNull() {
        Assert.assertNotNull(mapper);
    }

    @Test
    public void testCreateTable() {
        int result = mapper.createNewTable(TEST_TABLE);
        System.out.println("result = " + result);
        Assert.assertEquals(1, (int)mapper.existTable(TEST_TABLE));
    }

    @Test
    public void testDropTable() {
        int result = mapper.dropTable(TEST_TABLE);
        Assert.assertEquals(0, result);
        Assert.assertEquals(0, (int)mapper.existTable(TEST_TABLE));
    }
    private Timestamp parseStringDate(String strDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(strDate, dateTimeFormatter);
        System.out.println("dateTime = " + dateTime);
        Timestamp timestamp = Timestamp.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("timestamp = " + timestamp);
        return timestamp;
    }
    private CommitModel getCommit() {
        String commitLog = "    Remove unusual static method call\n" +
                "    \n" +
                "    The code is using static method Calendar.getInstance() but via a\n" +
                "    subclass of Calendar. It works, and getInstance() returns a\n" +
                "    GregorianCalendar on Android, but it's odd and was probably unintended.\n" +
                "    \n" +
                "    Noticed during static analysis of SystemUI bytecode.\n" +
                "    \n" +
                "    Bug: 111055375\n" +
                "    Test: build / boot\n" +
                "    Change-Id: I1762cbeb2cc7882868f84ec11100815671cd29ec\n";
        CommitModel commit = new CommitModel();
        commit.setCommitInProject(0);
        commit.setCommitAuthor("Neil Fuller");
        commit.setCommitAuthorMail("nfuller@google.com");
        commit.setCommitAlterDate(parseStringDate("Wed Jul 4 16:41:14 2018 +0100"));
        commit.setCommitDeletedLines(200);
        commit.setCommitChangedLines(1200);
        commit.setCommitAddedLines(1000);
        commit.setCommitLog(commitLog);
        commit.setCommitHashId("9c610f7567bc713e802842bd6c541d22941d8cea");
        commit.setCommitBranch("master");
        return commit;
    }
    @Test
    public void testAddCommit() {
        int result = mapper.createNewTable(TEST_TABLE);
        mapper.addCommit(getCommit(), TEST_TABLE);
    }

    @Test
    public void testAddCommitList() {
        CommitModel commit1 = getCommit();
        CommitModel commit2 = getCommit();
        commit2.setCommitLog("1234567890");
        List<CommitModel> list = new ArrayList<>();
        list.add(commit1);
        list.add(commit2);

        mapper.addCommitList(list, TEST_TABLE);
    }
}
