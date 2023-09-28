@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.example.teststori.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.teststori.R

@Composable
fun Password(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordEmpty: Boolean,
    onPasswordEmptyChange: (Boolean) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyBoard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val visibilityIcon: ImageVector = if (isPasswordVisible) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Outlined.Visibility
    }
    Box {
        TextField(
            value = password,
            modifier = Modifier.focusRequester(focusRequester),
            onValueChange = {
                onPasswordEmptyChange(it.isEmpty())
                onPasswordChange(it)
            },
            label = { Text(context.getString(R.string.password)) },
            singleLine = true,
            trailingIcon = {
                if (isPasswordEmpty)
                    Icon(
                        Icons.Filled.Error,
                        "error",
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            supportingText = {
                if (isPasswordEmpty)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = context.getString(R.string.emptyField),
                        color = MaterialTheme.colorScheme.error
                    )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (password.isEmpty())
                    onPasswordEmptyChange(true)
                else keyBoard?.hide()
            }),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (password.isNotBlank())
            Icon(
                imageVector = visibilityIcon,
                contentDescription = context.getString(R.string.toggle_password_visibility),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(12.dp)
                    .clickable {
                        onPasswordVisibleChange(!isPasswordVisible)
                    }
            )
    }
}