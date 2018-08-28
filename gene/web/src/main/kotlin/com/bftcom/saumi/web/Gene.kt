package com.bftcom.saumi.web

import com.bftcom.ice.common.Role
import com.bftcom.ice.common.StateGroup
import com.bftcom.ice.common.UserAccount
import com.bftcom.ice.common.UserPolicy
import com.bftcom.ice.web.admin.*
import com.bftcom.ice.web.admin.stateMachine.StateEditForm
import com.bftcom.ice.web.app.App
import com.bftcom.ice.web.app.App.config
import com.bftcom.ice.web.exceptions.DefaultUncaughtExceptionHandler
import com.bftcom.ice.web.exceptions.setDefaultUncaughtExceptionHandler

object Gene {

    fun init() {
        with(config.main) {
            header = "САУМИ"
        }
        with(config.server) {
            contextPath = "/app"
        }
        with(App.objects) {
            dict(UserAccount) {
                listForm = UserListForm::class
                editForm = UserEditForm::class
            }
            dict(UserPolicy) {
                listForm = UserPolicyListForm::class
                editForm = UserPolicyEditForm::class
            }
            dict(Role) {
                listForm = RoleListForm::class
                editForm = RoleEditForm::class
            }
            dict(StateGroup) {
                editForm = StateEditForm::class
            }

        }
        with(config.menu) {
            menuItem("/admin", title = "Администрирование", icon = "safety", component = AdminPage::class, rolesAllowed = "admin") {
                menuItem("/admin/users", title = "Пользователи", icon = "user", component = UserListForm::class)
                menuItem("/admin/roles", title = "Роли", icon = "lock", component = RoleDetailsListForm::class)
                menuItem("/admin/userPolicies", title = "Политики безопасности", icon = "exception", component = UserPolicyListForm::class)
            }
        }
        with(config.security) {
            authenticationRequired = true
            authorizationRequired = true
        }
        setDefaultUncaughtExceptionHandler(DefaultUncaughtExceptionHandler())
    }
}