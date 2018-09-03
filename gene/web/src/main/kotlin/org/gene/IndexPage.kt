package org.gene

import com.bftcom.ice.common.UserAccount
import com.bftcom.ice.web.antd.*
import com.bftcom.ice.web.app.App
import com.bftcom.ice.web.app.config.MenuItem
import com.bftcom.ice.web.css.css
import com.bftcom.ice.web.react.history.history
import com.bftcom.ice.web.react.router.RouteResultProps
import com.bftcom.ice.web.react.router.route
import com.bftcom.ice.web.react.router.switch
import com.bftcom.ice.web.security.SecurityManager
import react.RBuilder
import react.RComponent
import react.RState
import react.buildElement
import react.dom.div
import react.dom.span

class IndexPage(props: RouteResultProps<dynamic>) : RComponent<RouteResultProps<dynamic>, RState>(props) {

    private val menuItems: List<MenuItem> = App.config.menu.menuItems

    private val currentUser = SecurityManager.getCurrentUser()

    override fun RBuilder.render() {
        Layout {
            attrs {
                className = "page"
            }
            Layout.Header {
                attrs {
                    className = "page-header"
                }
                logo()
                mainMenu()
                rightMenu()
            }
            Layout.Content {
                attrs {
                    className = "page-content"
                }
                switch {
                    getAllMenuItems().map { menuItem ->
                        menuItem.component?.let {
                            route(menuItem.path, it)
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.logo() {
        div("logo") {}
    }

    private fun RBuilder.mainMenu() {
        Menu {
            attrs {
                className = "main-menu"
                theme = "dark"
                mode = "horizontal"
                selectedKeys = getCurrentMenuItem()?.let { generateSequence(it, { it.parent }).mapNotNull { it.path }.toList().toTypedArray() }
                onSelect = { history.push(it.key) }
            }
            menuItems(menuItems)
        }
    }

    private fun RBuilder.menuItems(menuItems: List<MenuItem>) {
        filterAllowedMenuItems(menuItems).forEach { menuItem(it) }
    }

    private fun RBuilder.menuItem(menuItem: MenuItem) {
        if (menuItem.children.isEmpty()) {
            MenuItem {
                attrs {
                    key = menuItem.path
                }
                menuItem.icon?.let { icon(it) }
                +menuItem.title
            }
        } else {
            SubMenu {
                attrs {
                    title = buildElement {
                        span {
                            menuItem.icon?.let { icon(it) }
                            +menuItem.title
                        }
                    }
                }
                menuItems(menuItem.children)
            }
        }
    }

    private fun RBuilder.rightMenu() {
        Menu {
            attrs {
                className = "main-menu"
                style = css { float = "right" }
                theme = "dark"
                mode = "horizontal"
                onClick = {
                    when(it.key) {
                        "logout" -> SecurityManager.logout()
                    }
                }
            }
            SubMenu {
                attrs {
                    title = buildElement {
                        span {
                            icon("user")
                            +currentUser[UserAccount.fullName]
                        }
                    }
                }
                MenuItem {
                    attrs {
                        key = "logout"
                    }
                    span {
                        icon("logout")
                        +"Выход"
                    }
                }
            }
        }
    }

    private fun getAllMenuItems(): List<MenuItem> {
        return filterAllowedMenuItems(menuItems).flatMap { filterAllowedMenuItems(it.children).plus(it) }
    }

    private fun filterAllowedMenuItems(items: List<MenuItem>): List<MenuItem> {
        return items.filter { SecurityManager.hasAnyUserRole(it.rolesAllowed) }
    }

    private fun getCurrentMenuItem(): MenuItem? {
        return getAllMenuItems().find { props.location.pathname.startsWith(it.path) }
    }
}