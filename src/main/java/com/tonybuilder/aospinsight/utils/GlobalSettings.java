package com.tonybuilder.aospinsight.utils;

import javax.validation.constraints.NotNull;

public class GlobalSettings {
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
        if (temp.length == 0) {
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("tbl_commit_");

        for(int i = 0; i < temp.length; i++) {
            stringBuffer.append(temp[i]);
            if (i+1 != temp.length) {
                stringBuffer.append("_");
            }
        }

        // abc-def >> abc_def
        String result = stringBuffer.toString();
        result = result.replace("-", "_");

        // dhcpcd-6.8.2 >> dhcpcd_6_8_2
        result = result.replace(".", "_");

        // result lenth < 64
        // tbl_commit_platform_prebuilts_gcc_darwin_x86_aarch64_aarch64_linux_android_4_9
        if (result.length() > 64) {
            result = "tbl_commit___" + result.substring(result.length()-50);
        }
        return result;
    }
}
