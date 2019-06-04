package ink.rubi.codegenerator.config;

import java.util.Properties;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:50 上午
 */
public class GUIConfig {

    public static boolean showLogInGUIWindow = false;
    public static boolean logConfigInfoWhenGenerateCode = false;

    public static void init() throws Exception {
        Properties prop = new Properties();
        prop.load(GUIConfig.class.getResourceAsStream("/conf.properties"));
        showLogInGUIWindow = Boolean.valueOf(prop.getProperty("showLogInGUIWindow", "false"));
        logConfigInfoWhenGenerateCode = Boolean.valueOf(prop.getProperty("logConfigInfoWhenGenerateCode", "false"));
    }
}
