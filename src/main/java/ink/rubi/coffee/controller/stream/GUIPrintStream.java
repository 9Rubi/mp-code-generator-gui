package ink.rubi.coffee.controller.stream;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:26 上午
 */
@Slf4j
public class GUIPrintStream extends PrintStream {

    private TextArea guiConsole;

    public GUIPrintStream(OutputStream out, TextArea guiConsole) throws FileNotFoundException, UnsupportedEncodingException {
        super(out, true, ConstVal.UTF8);
        this.guiConsole = guiConsole;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        final String message;
        try {
            message = new String(buf, off, len, ConstVal.UTF8);
            Platform.runLater(() -> guiConsole.appendText(message));
        } catch (Exception e) {
            log.error("{0}", e);
        }
    }
}