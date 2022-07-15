package m.farzan.todoapp.ui.tasks

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import m.farzan.todoapp.R
import m.farzan.todoapp.model.Status
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.ui.components.ClickableIcon
import m.farzan.todoapp.ui.components.OutlinedInput
import m.farzan.todoapp.ui.theme.HintColor
import m.farzan.todoapp.ui.theme.TodoAppTheme
import m.farzan.todoapp.ui.theme.backgroundGradient
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeParseException
import m.farzan.todoapp.util.DateConverter.Companion.dateFormatter
import m.farzan.todoapp.util.TimeConverter.Companion.timeFormatter


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    initialDate: String,
    task: Task? = null
) {

    val editing = task != null

    val tittle = remember {
        mutableStateOf(
            if (editing) task!!.tittle else ""
        )
    }
    val date = remember {
        mutableStateOf(initialDate)
    }
    val startTime = remember {
        mutableStateOf(if (editing) task!!.startTime.format(timeFormatter) else "")
    }
    val endTime = remember {
        mutableStateOf(if (editing) task!!.endTime.format(timeFormatter) else "")
    }
    val saveEnabled =
        endTime.value.isNotEmpty() && startTime.value.isNotEmpty() && date.value.isNotEmpty() && tittle.value.isNotEmpty()

    val context = LocalContext.current

    AddTaskScaffold(tittle = if (task == null)
        stringResource(id = R.string.addTaskTittle)
    else
        stringResource(R.string.changeTask), onBackClick = { navController.popBackStack() }) {
        Column {
            Content(tittle, date, startTime, endTime)
            ActionsRow(navController, saveEnabled) {
                try {
                    val start = LocalTime.from(timeFormatter.parse(startTime.value))
                    val end = LocalTime.from(timeFormatter.parse(endTime.value))
                    if (start.isAfter(end)) {
                        Toast.makeText(
                            context,
                            "Start time must before than end time",
                            Toast.LENGTH_LONG
                        ).show()
                        return@ActionsRow
                    }
                    if (editing) {
                        task!!.edit(
                            tittle.value,
                            LocalDate.from(
                                dateFormatter.parse(date.value)
                            ), start, end
                        )
                        taskViewModel.update(task)
                    } else {
                        taskViewModel.add(
                            Task(
                                tittle.value,
                                LocalDate.from(
                                    dateFormatter.parse(date.value)
                                ), start, end, Status.Todo
                            )
                        )
                    }
                    navController.popBackStack()
                } catch (e: DateTimeParseException) {
                    Toast.makeText(context, "Invalid Inputs", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}

@Composable
fun ActionsRow(navController: NavController, saveEnabled: Boolean, onSave: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        TextButton(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.popBackStack() }) {
            Text(text = "Cancel", style = MaterialTheme.typography.button)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            enabled = saveEnabled,
            shape = RoundedCornerShape(10.dp), onClick = {
                onSave()
            }) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Content(
    tittle: MutableState<String>,
    date: MutableState<String>,
    startTime: MutableState<String>,
    endTime: MutableState<String>
) {

    val datePickerState = rememberMaterialDialogState()
    val timePickerState = rememberMaterialDialogState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var isStartTimePicker = true

    MaterialDialog(
        dialogState = datePickerState,
        buttons = {
            positiveButton("Select")
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = try {
                LocalDate.from(
                    dateFormatter.parse(date.value)
                )
            } catch (e: DateTimeParseException) {
                LocalDate.now()
            }
        ){
            date.value = dateFormatter.format(it)
        }
    }

    MaterialDialog(
        dialogState = timePickerState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        this.title(text = if (isStartTimePicker) "Select start time" else "Select end time")
        timepicker(
            is24HourClock = true,
            initialTime = try {
                LocalTime.from(
                    timeFormatter.parse(
                        if (isStartTimePicker)
                            startTime.value
                        else
                            endTime.value
                    )
                )
            } catch (e: DateTimeParseException) {
                LocalTime.now()
            },
            colors = TimePickerDefaults.colors(
                inactiveBackgroundColor = MaterialTheme.colors.background,
                inactivePeriodBackground = MaterialTheme.colors.background,
                borderColor = MaterialTheme.colors.HintColor,
                activeTextColor = MaterialTheme.colors.onPrimary,
                inactiveTextColor = MaterialTheme.colors.onBackground
            )
        ) { time ->
            if (isStartTimePicker)
                startTime.value = time.format(timeFormatter)
            else
                endTime.value = time.format(timeFormatter)

            keyboardController?.show()
        }

    }

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            OutlinedInput(
                modifier = Modifier.fillMaxWidth(),
                valueState = tittle,
                label = "Tittle",
                imeAction = ImeAction.Next,
                onAction = KeyboardActions(onNext = {
                    keyboardController?.hide()
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = FontAwesomeIcons.Solid.Heading,
                        contentDescription = "tittle icon"
                    )
                }
            )

            OutlinedInput(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.hasFocus) {
                            keyboardController?.hide()
                            datePickerState.show()
                        }
                    }
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                valueState = date,
                imeAction = ImeAction.Next,
                onAction = KeyboardActions(onNext = {
                    keyboardController?.hide()
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                label = "Date",
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = FontAwesomeIcons.Solid.Calendar,
                        contentDescription = "tittle icon"
                    )
                },
                trailingIcon = {
                    ClickableIcon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = FontAwesomeIcons.Solid.CalendarCheck,
                        contentDescription = "selectDate",
                        onClick = {
                            keyboardController?.hide()
                            datePickerState.show()
                        }
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {

                OutlinedInput(
                    modifier = Modifier
                        .width(0.dp)
                        .padding(end = 5.dp)
                        .onFocusChanged {
                            if (it.hasFocus) {
                                keyboardController?.hide()
                                isStartTimePicker = true
                                timePickerState.show()
                            }
                        }
                        .weight(1f, true),
                    valueState = startTime,
                    imeAction = ImeAction.Next,
                    onAction = KeyboardActions(onNext = {
                        keyboardController?.hide()
                        focusManager.moveFocus(FocusDirection.Right)
                    }),
                    label = "Start"
                ) {
                    ClickableIcon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = FontAwesomeIcons.Solid.Clock,
                        contentDescription = "select start time",
                        onClick = {
                            keyboardController?.hide()
                            isStartTimePicker = true
                            timePickerState.show()
                        }
                    )
                }

                OutlinedInput(
                    modifier = Modifier
                        .width(0.dp)
                        .onFocusChanged {
                            if (it.hasFocus) {
                                keyboardController?.hide()
                                isStartTimePicker = false
                                timePickerState.show()
                            }
                        }
                        .padding(start = 5.dp)
                        .weight(1f, true),
                    valueState = endTime,
                    imeAction = ImeAction.Done,
                    onAction = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    label = "End"
                ) {
                    ClickableIcon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = FontAwesomeIcons.Solid.Clock,
                        contentDescription = "select end time",
                        onClick = {
                            keyboardController?.hide()
                            isStartTimePicker = false
                            timePickerState.show()
                        }
                    )
                }


            }

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTaskScaffold(tittle: String, onBackClick: () -> Unit, content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier
            .background(
                backgroundGradient()
            ), topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                title = {
                    Text(
                        text = tittle,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                navigationIcon = {
                    ClickableIcon(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(15.dp),
                        imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onBackground.copy(alpha = .8f),
                        onClick = onBackClick
                    )
                },
            )
        }, backgroundColor = Color.Transparent
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskPreview() {
    TodoAppTheme {
        AddTaskScreen(
            navController = rememberNavController(),
            viewModel(),
            dateFormatter.format(LocalDate.now())
        )
    }
}
