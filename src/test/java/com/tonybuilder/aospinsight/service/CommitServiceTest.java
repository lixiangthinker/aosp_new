package com.tonybuilder.aospinsight.service;

import com.github.pagehelper.PageInfo;
import com.tonybuilder.aospinsight.model.CommitModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommitServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CommitServiceTest.class);
    @Autowired
    private CommitService commitService;

    @Test
    public void getCommitsByMonth() {
        List<CommitModel> commits = commitService.getCommitsByMonth(390,
                "2018-01");
        Assert.assertNotNull(commits);
        System.out.println("get "+ commits.size() + " commits");
        for (int i = 0; i < 10; i++) {
            System.out.println(commits.get(i).getCommitLog());
        }
    }

    @Test
    public void getPagedCommitsByMonth() {
        PageInfo<CommitModel> commits = commitService.getPagedCommitsByMonth(390,
                "2018-01", 1, 10);
        Assert.assertNotNull(commits);
        System.out.println("get commits " + commits);
        List<CommitModel> modelList = commits.getList();
        System.out.println("model list size = " + modelList.size());
        for (CommitModel c : modelList){
            System.out.println(c.getCommitSubmitDate());
        }
    }
}