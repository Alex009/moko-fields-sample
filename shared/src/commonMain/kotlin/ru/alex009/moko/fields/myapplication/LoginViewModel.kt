package ru.alex009.moko.fields.myapplication

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.mediatorOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import ru.alex009.moko.fields.MR

class LoginViewModel : ViewModel() {
    // region Login LiveData
    val login: MutableLiveData<String> = MutableLiveData("")
    val isLoginInputComplete: MutableLiveData<Boolean> = MutableLiveData(false)
    val loginError: LiveData<StringDesc?> =
        mediatorOf(isLoginInputComplete, login) { isLoginInputComplete, login ->
            if (isLoginInputComplete && login.isBlank()) MR.strings.login_blank_error.desc()
            else null
        }
    val isLoginErrorShowed: LiveData<Boolean> = loginError.map { it != null }
    // endregion

    // region Password LiveData
    val password: MutableLiveData<String> = MutableLiveData("")
    val isPasswordInputComplete: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordError: LiveData<StringDesc?> =
        mediatorOf(isPasswordInputComplete, password) { isPasswordInputComplete, password ->
            if (isPasswordInputComplete && password.isBlank()) MR.strings.password_blank_error.desc()
            else null
        }
    val isPasswordErrorShowed: LiveData<Boolean> = passwordError.map { it != null }
    // endregion

    fun onLoginPressed() {
        isLoginInputComplete.value = true
        isPasswordInputComplete.value = true

        if (loginError.value != null || passwordError.value != null) return

        authorize(login = login.value.trim(), password = password.value.trim())
    }

    private fun authorize(login: String, password: String) {
        println("here we should do authorization with $login:$password")
    }
}
