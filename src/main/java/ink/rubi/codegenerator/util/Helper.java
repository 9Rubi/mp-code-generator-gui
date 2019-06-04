package ink.rubi.codegenerator.util;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import ink.rubi.codegenerator.App;

import java.lang.reflect.Field;

/**
 * 解决疑难杂症
 *
 * @author : Rubi
 * @version : 2019-05-19 19:28 下午
 */
public class Helper {
    public static String getPackageConfigParent(PackageConfig packageConfig) {
        Field field = null;
        String result = null;
        try {
            field = packageConfig.getClass().getDeclaredField("parent");
            field.setAccessible(true);
            result = (String) field.get(packageConfig);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            App.getLog().error("{0}", e);
        }
        return result;
    }

}
