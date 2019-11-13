package ink.rubi.coffee.modal

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

/**
 * @author : Rubi
 * @version : 2019-05-18 14:36 下午
 */
object AlertBox {
    fun display(title: String, message: String) {
        val window = getNewWindow(title, message)
        window.show()
    }

    fun displayAndWait(title: String, message: String) {
        val window = getNewWindow(title, message)
        window.showAndWait()
    }

    private fun getNewWindow(title: String, message: String): Stage {
        val window = Stage()
        window.initModality(Modality.APPLICATION_MODAL)
        window.title = title
        window.minWidth = 335.0
        window.minHeight = 144.0
        val label = Label(message)
        val closeButton = Button("知道了")
        closeButton.setOnAction { _ -> window.close() }
        val layout = VBox(10.0)
        label.padding = Insets(10.0, 10.0, 10.0, 10.0)
        layout.children.addAll(label, closeButton)
        layout.alignment = Pos.CENTER
        val scene = Scene(layout)
        window.scene = scene
        return window
    }


}
