package ink.rubi.coffee.controller.stream

import javafx.application.Platform
import javafx.scene.control.TextArea
import org.slf4j.LoggerFactory
import java.io.OutputStream
import java.io.PrintStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * @author : Rubi
 * @version : 2019-05-19 01:26 上午
 */
class GUIPrintStream @Throws(UnsupportedEncodingException::class)
constructor(out: OutputStream, private val guiConsole: TextArea) : PrintStream(out, true, SYSTEM_ENCODING) {

    override fun write(buf: ByteArray, off: Int, len: Int) {
        val message: String
        try {
            message = String(buf, off, len, Charset.forName(SYSTEM_ENCODING))
            Platform.runLater { guiConsole.appendText(message) }
        } catch (e: Exception) {
            log.error("{0}", e)
        }

    }

    companion object {
        private val SYSTEM_ENCODING = System.getProperty("file.encoding")
        private val log = LoggerFactory.getLogger(GUIPrintStream::class.java);
    }
}