package ink.rubi.codegenerator.constant;

import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rubi
 * @since 2019-06-05 16:02
 */
public class Engine {
    private static final Map<String, AbstractTemplateEngine> maps = new HashMap<String, AbstractTemplateEngine>() {{
        put("beetl", new BeetlTemplateEngine());
        put("velocity", new VelocityTemplateEngine());
        put("freemarker", new FreemarkerTemplateEngine());
    }};


    public static AbstractTemplateEngine getEngine(String str) {
        return maps.get(str);
    }
}
