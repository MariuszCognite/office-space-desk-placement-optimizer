import tornadofx.reloadStylesheetsOnFocus
import gui.MainStyle
import gui.MainView

class App : tornadofx.App(MainView::class, MainStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}
