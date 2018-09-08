package com.tonybuilder.aospinsight.repo;

import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.utils.GlobalSettings;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class RepoParser {

    @Value("${aosp.source.root}")
    private String AOSP_ROOT;

    public File getSourceDir() {
        return new File(AOSP_ROOT);
    }

    public List<ProjectModel> parserXml(File xml) {
        List<ProjectModel> result = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(xml);
            Element manifestRoot = document.getRootElement();
            for (Iterator i = manifestRoot.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                if (!"project".equals(element.getName())) {
                    continue;
                }
                //System.out.println("Element: " + element.getName());

                ProjectModel project = new ProjectModel();

                Iterator attrIt = element.attributeIterator();
                while (attrIt.hasNext()) {
                    Attribute a = (Attribute) attrIt.next();
                    if ("path".equals(a.getName())) {
                        project.setProjectPath(a.getValue());
                    } else if ("name".equals(a.getName())) {
                        project.setProjectName(a.getValue());
                    }
                    //System.out.println("Attribute: ["+a.getName()+","+a.getValue()+"]");
                }
                project.setProjectIsExternalSrc(isExternalProject(project));
                project.setProjectIsDiscarded(isProjectDiscarded(project));
                project.setProjectModuleType(getProjectModuleType(project));
                project.setProjectTableName(GlobalSettings.getCommitTableName(project.getProjectName()));
                result.add(project);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Byte isExternalProject(ProjectModel p) {
        String path = p.getProjectPath();
        if (path == null || !path.contains("external")) {
            return 0;
        }

        return 1;
    }

    private Byte isProjectDiscarded(ProjectModel p) {
        if (p == null || p.getProjectPath() == null) {
            return 0;
        }

        File projectPath = new File(AOSP_ROOT, p.getProjectPath());

        if (!projectPath.exists()) {
            return 1;
        }
        return 0;
    }

    private int getProjectModuleType(ProjectModel p) {
        String path = p.getProjectPath();
        if (path == null) {
            return GlobalSettings.PROJECT_CATEGORY_OTHER;
        }

        if (path.startsWith("tools") || path.startsWith("toolchain")) {
            return GlobalSettings.PROJECT_CATEGORY_TOOLS;
        }

        if (path.startsWith("test") || path.startsWith("platform_testing")
                || path.startsWith("cts") || path.startsWith("vts")) {
            return GlobalSettings.PROJECT_CATEGORY_TEST;
        }

        if (path.startsWith("system") || path.startsWith("libnativehelper")
                || path.startsWith("dalvik") || path.startsWith("art")
                || path.startsWith("bionic") || path.startsWith("bootable")) {
            return GlobalSettings.PROJECT_CATEGORY_FRAMEWORK_NATIVE;
        }

        if (path.startsWith("prebuilts")) {
            return GlobalSettings.PROJECT_CATEGORY_PREBUILTS;
        }

        if (path.startsWith("pdk") || path.startsWith("sdk") || path.startsWith("build")) {
            return GlobalSettings.PROJECT_CATEGORY_BUILD;
        }

        if (path.startsWith("packages")) {
            return GlobalSettings.PROJECT_CATEGORY_APP;
        }

        if (path.startsWith("libcore") || path.startsWith("frameworks")) {
            return GlobalSettings.PROJECT_CATEGORY_FRAMEWORK;
        }

        if (path.startsWith("hardware") || path.startsWith("device")) {
            return GlobalSettings.PROJECT_CATEGORY_HAL;
        }
        return GlobalSettings.PROJECT_CATEGORY_OTHER;
    }
}
