package ink.rubi.coffee.constant

import com.baomidou.mybatisplus.core.toolkit.StringPool
import com.baomidou.mybatisplus.core.toolkit.StringUtils
import com.baomidou.mybatisplus.generator.config.ConstVal
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.apache.velocity.app.VelocityEngine
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*

/**
 * @author Rubi
 * @since 2019-06-05 16:02
 */
object Engine {
    const val custom = "velocity-custom"

    val engines: Map<String, AbstractTemplateEngine> = Collections.unmodifiableMap(object : LinkedHashMap<String, AbstractTemplateEngine>() {
        init {
            put("velocity", VelocityTemplateEngine())
            put("freemarker", FreemarkerTemplateEngine())
            put("beetl", BeetlTemplateEngine())
            put("velocity-custom", VmEngineTemplate())
        }
    })


    private class VmEngineTemplate : AbstractTemplateEngine() {
        private var velocityEngine: VelocityEngine? = null

        override fun init(configBuilder: ConfigBuilder): VmEngineTemplate {
            super.init(configBuilder)
            if (null == velocityEngine) {
                val p = Properties()
                p.setProperty(ConstVal.VM_LOAD_PATH_KEY, "org.apache.velocity.runtime.resource.loader.FileResourceLoader")
                p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY)
                p.setProperty(Velocity.ENCODING_DEFAULT, ConstVal.UTF8)
                p.setProperty(Velocity.INPUT_ENCODING, ConstVal.UTF8)
                p.setProperty("file.resource.loader.unicode", StringPool.TRUE)
                velocityEngine = VelocityEngine(p)
            }
            return this
        }


        @Throws(Exception::class)
        override fun writer(objectMap: Map<String, Any>, templatePath: String, outputFile: String) {
            if (StringUtils.isEmpty(templatePath)) {
                return
            }
            val template = velocityEngine!!.getTemplate(templatePath, ConstVal.UTF8)
            FileOutputStream(outputFile).use { fos -> OutputStreamWriter(fos, ConstVal.UTF8).use { ow -> BufferedWriter(ow).use { writer -> template.merge(VelocityContext(objectMap), writer) } } }
            logger.debug("模板:$templatePath;  文件:$outputFile")
        }


        override fun templateFilePath(filePath: String?): String? {
            return if (null == filePath || filePath.contains(DOT_VM)) {
                filePath
            } else filePath + DOT_VM
        }

        companion object {

            private const val DOT_VM = ".vm"
        }
    }


    fun getEngine(str: String): AbstractTemplateEngine {
        return engines[str]?:throw RuntimeException("can't find that engine")
    }


}


