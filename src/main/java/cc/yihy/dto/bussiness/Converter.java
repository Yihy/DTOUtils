package cc.yihy.dto.bussiness;

import cc.yihy.dto.entity.ClassModel;
import cc.yihy.dto.entity.ClassProperty;
import com.intellij.openapi.util.io.FileSystemUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiClass;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ZyL on 2017/1/23.
 */
public class Converter {

    private List<ClassProperty> source;
    private List<ClassProperty> target;

    private List<ClassModel> classModels;

    private static VelocityEngine ve;
    static {
        //初始化参数
        Properties properties=new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        //实例化一个VelocityEngine对象

        // 初始化模板引擎
         ve = new VelocityEngine(properties);

        ve.init();
    }

    public Converter(List<ClassProperty> props1, List<ClassProperty> props2) {
        this.target = props2;
        this.source = props1;
        classModels = new ArrayList<>();
        for (int i = 0; i < target.size(); i++) {
            if (target.size() <= source.size()) {
                classModels.add(new ClassModel(target.get(i), source.get(i)));
            } else {

            }
        }
    }

    private int getSizeOfProp(List<ClassProperty> list) {
        int size = 0;
        for (ClassProperty item : list) {
            if (!item.isPlaceHolder()) {
                size++;
            }
        }
        return size;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        if (getSizeOfProp(target) > 0 && getSizeOfProp(source) > 0) {

            InputStream resourceAsStream  = this.getClass().getClassLoader().getResourceAsStream("dto.ft");

            String templateStr = "";
            try {

                templateStr = FileUtil.loadTextAndClose(resourceAsStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 设置变量
            VelocityContext ctx = new VelocityContext();

            PsiClass targetClass = target.get(0).getPsiClass();
            PsiClass sourceClass = source.get(0).getPsiClass();
            ctx.put("entity", sourceClass.getName());
            ctx.put("shortEntityName", sourceClass.getName());
            //如果两个类的Name相同,model取全限定名称
            ctx.put("model", sourceClass.getName().equals(targetClass.getName()) ? targetClass.getQualifiedName() : targetClass.getName());
            ctx.put("shortModelName", targetClass.getName());

            ctx.put("classModels", classModels);

            // 输出
            StringWriter writer = new StringWriter();

            ve.evaluate(ctx, writer, "", templateStr);
            sb.append(writer);
        }
        return sb.toString();
    }
}
