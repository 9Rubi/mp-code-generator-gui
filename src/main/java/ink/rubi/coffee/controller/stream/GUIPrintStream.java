package ink.rubi.coffee.controller.stream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:26 上午
 */
@Slf4j
public class GUIPrintStream extends PrintStream {

    private TextArea guiConsole;

    public GUIPrintStream(OutputStream out, TextArea guiConsole) throws FileNotFoundException, UnsupportedEncodingException {
        super(out, true, StandardCharsets.UTF_8.name());
        this.guiConsole = guiConsole;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        final String message;
        try {
            message = new String(buf, off, len, StandardCharsets.UTF_8.name());
            Platform.runLater(() -> guiConsole.appendText(message));
        } catch (Exception e) {
            log.error("{0}", e);
        }
    }
}