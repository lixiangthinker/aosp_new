package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.CommitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface CommitMapper {
    Integer createNewTable(@Param("tableName") String tableName);
    Integer dropTable(@Param("tableName") String tableName);
    Integer existTable(String tableName);

    Integer addCommit(@Param("commit") CommitModel commit, @Param("tableName") String tableName);
    Integer addCommitList(@Param("commitList") List<CommitModel> commitList, @Param("tableName") String tableName);
    List<CommitModel> getCommitsSince(@Param("since") Date since, @Param("tableName") String tableName);
    List<CommitModel> getCommitsSinceUntil(@Param("since") Date since, @Param("until") Date until,@Param("tableName") String tableName);
}
