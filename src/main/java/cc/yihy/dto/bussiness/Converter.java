package cc.yihy.dto.bussiness;

import cc.yihy.dto.entity.ClassModel;
import cc.yihy.dto.entity.ClassProperty;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiClass;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * DTO类的生成器
 *
 * @author jianyun.zhao
 * Created at 2020/02/29
 */
public class Converter {

    private List<ClassProperty> source;

    private List<ClassProperty> target;

    private List<ClassModel> classModels;

    private static VelocityEngine engine;

    static {
        //初始化参数
        Properties properties = new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        //实例化一个VelocityEngine对象
        engine = new VelocityEngine(properties);
        engine.init();
    }

    public Converter(List<ClassProperty> props1, List<ClassProperty> props2) {
        this.target = props2;
        this.source = props1;

    }

    public Converter init() {
        classModels = new ArrayList<>();
        int min = Math.min(source.size(), target.size());
        for (int i = 0; i < min; i++) {
            classModels.add(new ClassModel(target.get(i), source.get(i)));
        }
        return this;
    }


    private boolean existProperty(List<ClassProperty> list) {
        return list.stream()
                .anyMatch(item -> !item.isPlaceHolder());
    }

    public String generate() {
        if (!existProperty(target) || !existProperty(source)) {
            return "generate failed！property is empty";
        }
        String templateStr = loadTemplate("dto.ft");
        StringWriter writer = new StringWriter();
        VelocityContext context = getVelocityContext();
        engine.evaluate(context, writer, "", templateStr);
        return writer.toString();
    }

    @NotNull
    private String loadTemplate(String templatePath) {
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(templatePath)) {
            return FileUtil.loadTextAndClose(resourceAsStream);
        } catch (IOException e) {
            throw new IllegalStateException("load template is failed", e);
        }
    }

    @NotNull
    private VelocityContext getVelocityContext() {
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        PsiClass targetClass = target.get(0).getPsiClass();
        PsiClass sourceClass = source.get(0).getPsiClass();
        ctx.put("entity", sourceClass.getName());
        ctx.put("shortEntityName", sourceClass.getName());
        //如果两个类的Name相同,model取全限定名称
        ctx.put("model", Objects.equals(sourceClass.getName(), targetClass.getName()) ? targetClass.getQualifiedName() : targetClass.getName());
        ctx.put("shortModelName", targetClass.getName());
        ctx.put("classModels", classModels);
        return ctx;
    }
}
