package ru.alex009.moko.fields.myapplication

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.validations.fieldValidation
import dev.icerock.moko.validations.notBlank
import ru.alex009.moko.fields.MR

class LoginViewModel : ViewModel() {
    // region Login LiveData
    val login: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = fieldValidation {
            notBlank(MR.strings.login_blank_error.desc())
        }
    )
    // endregion

    // region Password LiveData
    val password: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = fieldValidation {
            notBlank(MR.strings.password_blank_error.desc())
        }
    )
    // endregion

    fun onLoginPressed() {
        if (listOf(login, password).validate().not()) return

        authorize(login = login.value().trim(), password = password.value().trim())
    }

    private fun authorize(login: String, password: String) {
        println("here we should do authorization with $login:$password")
    }
}
