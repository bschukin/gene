import com.bftcom.ice.web.components.renderApp
import com.bftcom.ice.web.react.router.hashRouter
import com.bftcom.ice.web.react.router.route
import com.bftcom.ice.web.react.router.switch
import com.bftcom.saumi.web.Gene
import com.bftcom.saumi.web.IndexPage
import kotlin.browser.window

object Main {

    init {
        Gene.init()
    }

    fun render() {
        renderApp {
            hashRouter {
                switch {
                    route("/", IndexPage::class)
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    window.onload = {
        Main.render()
    }
}