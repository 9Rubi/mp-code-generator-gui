package ink.rubi.coffee.controller.stream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:26 上午
 */
@Slf4j
public class GUIPrintStream extends PrintStream {

    private final TextArea guiConsole;
    private static final String SYSTEM_ENCODING = System.getProperty("file.encoding");

    public GUIPrintStream(OutputStream out, TextArea guiConsole) throws UnsupportedEncodingException {
        super(out, true, SYSTEM_ENCODING);
        this.guiConsole = guiConsole;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        final String message;
        try {
            message = new String(buf, off, len, SYSTEM_ENCODING);
            Platform.runLater(() -> guiConsole.appendText(message));
        } catch (Exception e) {
            log.error("{0}", e);
        }
    }
}