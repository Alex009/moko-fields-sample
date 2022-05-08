package ru.alex009.moko.fields.myapplication.android

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindTextTwoWay
import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.resources.desc.StringDesc
import ru.alex009.moko.fields.myapplication.LoginViewModel
import ru.alex009.moko.fields.myapplication.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel = getViewModel { LoginViewModel() }

        binding.loginLayout.bindField(this, viewModel.login)
        binding.passwordLayout.bindField(this, viewModel.password)

        binding.loginBtn.setOnClickListener { viewModel.onLoginPressed() }
    }
}

fun TextInputLayout.bindField(
    lifecycleOwner: LifecycleOwner,
    formField: FormField<String, StringDesc>
) {
    val editText: EditText = editText ?: return

    editText.bindTextTwoWay(lifecycleOwner, formField.data)
    editText.setOnFocusChangeListener { _, focused ->
        if (focused) return@setOnFocusChangeListener
        formField.validate()
    }
    this.bindError(lifecycleOwner, formField.error)
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
