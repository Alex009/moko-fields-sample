package ru.alex009.moko.fields.myapplication

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.validations.ValidationResult
import dev.icerock.moko.validations.fieldValidation
import dev.icerock.moko.validations.matchRegex
import dev.icerock.moko.validations.maxLength
import dev.icerock.moko.validations.minLength
import dev.icerock.moko.validations.notBlank
import ru.alex009.moko.fields.MR

class LoginViewModel : ViewModel() {
    // region Login LiveData
    val login: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = fieldValidation {
            notBlank(MR.strings.login_blank_error.desc())
            minLength("can't be less 4 char".desc(), 4)
            maxLength("should be less 20 chars".desc(), 20)
            notAdmin("should not be admin!".desc())
            matchRegex("should have @".desc(), Regex(".*@.*"))
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

fun ValidationResult<String>.notAdmin(errorText: StringDesc) = nextValidation { value ->
    if (value.lowercase() != "admin") {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}
