package core;

import datatable.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class Generator {

    private static final String TEMPLATE_LOC = "/code/";

    private static final VelocityEngine velocityEngine = new VelocityEngine();

    private static final Generator instance = new Generator();

    public static Generator getInstance() {
        return instance;
    }

    ;

    static {

        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(velocityEngine.getClass().getClassLoader());

        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("class.resource.loader.cache", "true");
        properties.setProperty("overrideLogging", "true");
        properties.setProperty("preferFileSystemAccess", "false");
        properties.setProperty("overrideLogging", "true");
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("directive.foreach.counter.name", "velocityCount");
        try {
            velocityEngine.init(properties);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    public static String execute(String templateName, HashMap<String, Object> vars) {
        VelocityContext context = new VelocityContext();
        Iterator<Map.Entry<String, Object>> iter = vars.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            context.put(entry.getKey(), entry.getValue());
        }
        org.apache.velocity.Template template = null;
        try {
            template = velocityEngine.getTemplate(TEMPLATE_LOC + templateName);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseErrorException e) {
            throw new RuntimeException(e);
        } catch (MethodInvocationException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }

    public void create(String sourceDirectory, Table table, String targetPackage, String basePackage, String suffix, String subpackage, String templateName,
                       boolean overwrite) throws IOException {

        String className = Utils.tableNameToClassName(table.getName()) + suffix;
        targetPackage = buildPackage(table, targetPackage);

        String packageName = targetPackage;
        if (StringUtils.isNotEmpty(subpackage)) {
            packageName = targetPackage + "." + subpackage;
        }

        HashMap<String, Object> vars = new HashMap();
        vars.put("table", table);
        vars.put("utils", new Utils());
        vars.put("basePackage", buildPackage(table, basePackage));
        vars.put("specifyPackage", packageName);
        String text = execute(templateName, vars);

        String filename = sourceDirectory + "/" + packageName.replace('.', '/') + "/" + className
                + ".java";

        if (!overwrite) {
            File file = new File(filename);
            if (file.exists()) {
                return;
            }
        }
        createFile(filename, text, overwrite);
    }

    private void createFile(String fileName, String text, boolean overwrite) throws IOException {

        // 文件已存在
        File file = new File(fileName);
        if (file.exists()) {
            if (!overwrite) {
                throw new FileAlreadyExistsException(fileName);
            }
        } else {
            file.getParentFile().mkdirs();
        }

        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }

    }

    private String buildPackage(Table table, String targetPackage) {

        if (StringUtils.isNotEmpty(targetPackage)) {
            targetPackage += ".";
        } else {
            targetPackage = "";
        }
        targetPackage += Utils.tableNameToClassName(table.getName()).toLowerCase();
        return targetPackage;
    }

}
