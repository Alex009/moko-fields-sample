package ru.alex009.moko.fields.myapplication.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindTextTwoWay
import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.bindNotNull
import dev.icerock.moko.resources.desc.StringDesc
import ru.alex009.moko.fields.myapplication.LoginViewModel
import ru.alex009.moko.fields.myapplication.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel = getViewModel { LoginViewModel() }

        binding.loginText.bindTextTwoWay(this, viewModel.login)
        binding.loginText.setOnFocusChangeListener { _, focused ->
            if (focused) return@setOnFocusChangeListener
            viewModel.isLoginInputComplete.value = true
        }
        binding.loginLayout.bindError(this, viewModel.loginError)

        binding.passwordText.bindTextTwoWay(this, viewModel.password)
        binding.passwordText.setOnFocusChangeListener { _, focused ->
            if (focused) return@setOnFocusChangeListener
            viewModel.isPasswordInputComplete.value = true
        }
        binding.passwordLayout.bindError(this, viewModel.passwordError)

        binding.loginBtn.setOnClickListener { viewModel.onLoginPressed() }
    }
}

fun <T : StringDesc?> TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<T>
): Closeable {
    return liveData.bind(lifecycleOwner) {
        this.error = it?.toString(this.context)
        this.isErrorEnabled = it != null
    }
}
