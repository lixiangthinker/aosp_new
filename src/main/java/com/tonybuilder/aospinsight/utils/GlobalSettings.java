package com.tonybuilder.aospinsight.utils;

import javax.validation.constraints.NotNull;

public class GlobalSettings {
    public static final String AOSP_SOURCE_PATH_PREFIX = "/home/lixiang/source/aosp/";
    public static final String CACHE_ROOT = AOSP_SOURCE_PATH_PREFIX + ".insightCache";

    public static final int PROJECT_CATEGORY_APP = 0;
    public static final int PROJECT_CATEGORY_FRAMEWORK = 10;
    public static final int PROJECT_CATEGORY_FRAMEWORK_NATIVE = 11;
    public static final int PROJECT_CATEGORY_HAL = 20;
    public static final int PROJECT_CATEGORY_BUILD = 30;
    public static final int PROJECT_CATEGORY_TEST = 40;
    public static final int PROJECT_CATEGORY_TOOLS = 50;
    public static final int PROJECT_CATEGORY_PREBUILTS = 60;
    public static final int PROJECT_CATEGORY_OTHER = 100;

    public static String getCommitTableName(@NotNull String projectName) {
        String[] temp = projectName.split("/");
        if (temp.length == 0 || !"platform".equals(temp[0])) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("tbl_commit_");

        for(int i = 1; i < temp.length; i++) {
            stringBuffer.append(temp[i]);
            if (i+1 != temp.length) {
                stringBuffer.append("_");
            }
        }
        return stringBuffer.toString();
    }
}
