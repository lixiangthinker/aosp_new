package com.tonybuilder.aospinsight.repo;

import com.tonybuilder.aospinsight.model.CommitModel;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JGitParserTests {
    private static final Logger logger = LoggerFactory.getLogger(JGitParserTests.class);
    @Autowired
    private JGitParser jGitParser;
    @Test
    public void testGetProjectFile() {
        String dir = jGitParser.getProjectDirByName("platform/frameworks/base");
        logger.info(dir);
    }
    @Test
    public void testParseProject() {
        List<CommitModel> result = null;
        try {
            result = jGitParser.parseProject("platform/frameworks/base");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        if (result != null) {
            logger.info("result " + result.size());
        }
    }
    @Test
    public void testParseProjectSinceUntil() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strSince = "2017-11-01";
        String strUntil = "2018-01-01";
        Date since = sdf.parse(strSince);
        Date until = sdf.parse(strUntil);
        List<CommitModel> result = null;
        try {
            result = jGitParser.parseProject("platform/frameworks/base", since, until);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        if (result != null) {
            logger.info("result " + result.size());
        }
    }
}
