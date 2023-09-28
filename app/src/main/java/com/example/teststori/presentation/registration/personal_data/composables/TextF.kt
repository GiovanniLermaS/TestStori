package com.example.teststori.presentation.registration.personal_data.composables

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
import com.example.teststori.common.capitalizeWords

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextF(
    name: String,
    onNameChange: (String) -> Unit,
    isEmptyField: Boolean,
    onEmptyFieldChange: (Boolean) -> Unit,
    text: String
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = name,
        onValueChange = {
            onEmptyFieldChange(it.isEmpty())
            onNameChange(it.capitalizeWords())
        },
        label = { Text(text) },
        singleLine = true,
        trailingIcon = {
            if (isEmptyField)
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
        },
        supportingText = {
            if (isEmptyField)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = context.getString(R.string.emptyField),
                    color = MaterialTheme.colorScheme.error
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            if (name.isEmpty())
                onEmptyFieldChange(true)
            else focusManager.moveFocus(FocusDirection.Next)
        }),
    )
}