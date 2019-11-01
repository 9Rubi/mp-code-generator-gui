package ink.rubi.coffee.config

import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-05-19 01:50 上午
 */
object GUIConfig {
    @JvmField
    var isShowLogInGUIWindow = false
    @JvmField
    var isLogConfigInfoWhenGenerateCode = false
    private val log = LoggerFactory.getLogger(GUIConfig.javaClass)

    @Throws(Exception::class)
    fun init() {
        val prop = Properties()
        try {
            GUIConfig::class.java.getResourceAsStream("/conf.properties").use { inputStream -> prop.load(inputStream) }
        } catch (e: IOException) {
            log.error("加载配置文件异常:{0}", e)
        }
        isShowLogInGUIWindow = java.lang.Boolean.valueOf(prop.getProperty("showLogInGUIWindow", "false"))
        isLogConfigInfoWhenGenerateCode = java.lang.Boolean.valueOf(prop.getProperty("logConfigInfoWhenGenerateCode", "false"))
    }
}
