package ru.itis.androiddevelopment.fragment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import kotlinx.coroutines.launch
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.util.CoroutineBehavior
import ru.itis.androiddevelopment.util.CoroutineManager
import ru.itis.androiddevelopment.util.LaunchMode
import ru.itis.androiddevelopment.util.ThreadPool

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseComposeScreen(
    onRequestPermission: () -> Unit,
    showDialog: Boolean,
    isDialogDismissedPermanently: Boolean,
    onDismissDialog: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val coroutineManager = remember { CoroutineManager() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var numberOfCoroutines by remember { mutableStateOf("") }
    var launchMode by remember { mutableStateOf(LaunchMode.SEQUENTIAL) }
    var coroutineBehavior by remember { mutableStateOf(CoroutineBehavior.CANCEL) }
    var selectedText by remember { mutableStateOf(ThreadPool.DEFAULT.name) }
    var threadPool by remember { mutableStateOf(ThreadPool.DEFAULT) }
    var showError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    if (!isDialogDismissedPermanently) {
        onRequestPermission()
    }

    if (showDialog && !isDialogDismissedPermanently) {
        NotificationDialog(
            onDismiss = onDismissDialog,
            onOpenSettings = onOpenSettings
        )
    }

    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (coroutineBehavior == CoroutineBehavior.CANCEL) {
                coroutineManager.cancelCoroutines()
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = numberOfCoroutines,
            onValueChange = { numberOfCoroutines = it },
            label = { Text(stringResource(R.string.number_of_coroutines_label)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError && numberOfCoroutines.isEmpty()
        )


        Text(stringResource(R.string.launch_mode_label))
        LaunchMode.entries.forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = (launchMode == mode),
                    onClick = { launchMode = mode },
                    modifier = Modifier.weight(0.2f)
                )
                Text(
                    text = mode.name,
                    modifier = Modifier.weight(0.8f)
                )

            }
        }


        Text(stringResource(R.string.coroutine_behavior_label))
        CoroutineBehavior.entries.forEach { behavior ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = (coroutineBehavior == behavior),
                    onClick = { coroutineBehavior = behavior },
                    modifier = Modifier.weight(0.2f)
                )
                Text(
                    text = behavior.name,
                    modifier = Modifier.weight(0.8f)
                )
            }
        }


        Text(stringResource(R.string.thread_pool_label))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ThreadPool.entries.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            threadPool = item
                            selectedText = item.name
                            expanded = false
                        },
                        content = { Text(text = item.name) }
                    )
                }
            }
        }


        Button(
            onClick = {
                if (numberOfCoroutines.isEmpty() || numberOfCoroutines.toIntOrNull() == null) {
                    showError = true
                } else {
                    showError = false
                    val count = numberOfCoroutines.toIntOrNull() ?: 0
                    if (count > 0) {
                        coroutineManager.launchCoroutines(
                            numberOfCoroutines = count,
                            launchMode = launchMode,
                            threadPool = threadPool,
                            onCoroutineCompleted = {
                                coroutineScope.launch {
                                    coroutineManager.showToast(context, context.getString(R.string.all_coroutines_completed_toast))
                                }
                            },
                            onError = { e ->
                                coroutineScope.launch {
                                    val errorMessage = context.getString(R.string.error_toast, e.message ?: R.string.unknown_error)
                                    coroutineManager.showToast(context, errorMessage)
                                }
                            }
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.start_coroutines_button))
        }


        Button(
            onClick = {
                coroutineScope.launch {
                    val canceledCount = coroutineManager.cancelCoroutines()
                    if (canceledCount > 0) {

                        coroutineManager.showToast(context, context.getString(R.string.coroutines_canceled_toast, canceledCount))
                    } else {
                        coroutineManager.showToast(context, context.getString(R.string.no_coroutines_to_cancel_toast))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cancel_coroutines_button))
        }
    }

}
@Composable
fun NotificationDialog(onDismiss: () -> Unit, onOpenSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.notification_permission_title)) },
        text = {
            Text(stringResource(R.string.notification_permission_message))
        },
        confirmButton = {
            TextButton(onClick = onOpenSettings) {
                Text(stringResource(R.string.open_settings_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button))
            }
        },
    )
}