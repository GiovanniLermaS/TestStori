@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.teststori.common.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.teststori.R
import com.example.teststori.common.isValidEmail

@Composable
fun Email(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailValid: Boolean,
    onEmailValidChange: (Boolean) -> Unit,
    isEmailEmpty: Boolean,
    onEmailEmptyChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = email,
        onValueChange = {
            onEmailValidChange(it.isValidEmail())
            onEmailEmptyChange(it.isEmpty())
            onEmailChange(it)
        },
        label = { Text(context.getString(R.string.email)) },
        singleLine = true,
        trailingIcon = {
            if (isEmailEmpty || !isEmailValid)
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
        },
        supportingText = {
            if (isEmailEmpty)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = context.getString(R.string.emptyField),
                    color = MaterialTheme.colorScheme.error
                ) else if (!isEmailValid)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = context.getString(R.string.wrongEmail),
                    color = MaterialTheme.colorScheme.error
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            if (email.isEmpty())
                onEmailEmptyChange(true)
            else if (email.isValidEmail()) {
                onEmailValidChange(true)
                focusManager.moveFocus(FocusDirection.Next)
            }
        }),
    )
}