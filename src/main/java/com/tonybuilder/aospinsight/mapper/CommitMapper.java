package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.CommitModel;
import org.apache.ibatis.annotations.Param;

import java.time.YearMonth;
import java.util.List;

public interface CommitMapper {
    Integer createNewTable(@Param("tableName") String tableName);
    Integer dropTable(@Param("tableName") String tableName);
    Integer existTable(String tableName);

    Integer addCommit(@Param("commit") CommitModel commit, @Param("tableName") String tableName);
    Integer addCommitList(@Param("commitList") List<CommitModel> commitList, @Param("tableName") String tableName);
    List<CommitModel> getCommitsSince(@Param("since") YearMonth since);
}
