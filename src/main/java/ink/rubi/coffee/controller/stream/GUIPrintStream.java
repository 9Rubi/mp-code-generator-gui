package ink.rubi.coffee.controller.stream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author : Rubi
 * @version : 2019-05-19 01:26 上午
 */
public class GUIPrintStream extends PrintStream {

    private TextArea guiConsole;

    public GUIPrintStream(OutputStream out, TextArea guiConsole) throws FileNotFoundException {
        super(out);
        this.guiConsole = guiConsole;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        final String message = new String(buf, off, len);
        Platform.runLater(() -> guiConsole.appendText(message));
    }
}