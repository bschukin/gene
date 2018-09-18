package org.gene

import com.bftcom.ice.web.app.App.config
import com.bftcom.ice.web.exceptions.DefaultUncaughtExceptionHandler
import com.bftcom.ice.web.exceptions.setDefaultUncaughtExceptionHandler
import org.gene.d2.D2BoardPage

object Gene {

    fun init() {
        with(config.security) {
            authenticationRequired = true
            authorizationRequired = false
        }
        with(config.main) {
            header = "САУМИ"
        }
        with(config.server) {
            contextPath = "/app"
        }

        with(config.menu) {
            menuItem("/gene", title = "Модель", icon = "safety", component = D2BoardPage::class)
        }

        setDefaultUncaughtExceptionHandler(DefaultUncaughtExceptionHandler())
    }
}