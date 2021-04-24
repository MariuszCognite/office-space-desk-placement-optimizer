import tornadofx.reloadStylesheetsOnFocus
import ui.MainStyle
import ui.MainView

class App : tornadofx.App(MainView::class, MainStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}
