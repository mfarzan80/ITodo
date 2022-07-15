package m.farzan.todoapp.ui.tasks

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarWeek
import compose.icons.fontawesomeicons.solid.Moon
import compose.icons.fontawesomeicons.solid.Plus
import compose.icons.fontawesomeicons.solid.Sun
import m.farzan.todoapp.R
import m.farzan.todoapp.model.Status
import m.farzan.todoapp.model.Task
import m.farzan.todoapp.ui.components.*
import m.farzan.todoapp.ui.navigations.Screens
import m.farzan.todoapp.ui.theme.*
import m.farzan.todoapp.util.DateConverter.Companion.dateFormatter
import m.farzan.todoapp.util.DateUtils
import m.farzan.todoapp.util.TimeConverter.Companion.timeFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TasksScreen(navController: NavController, taskViewModel: TaskViewModel) {

    val date = remember {
        mutableStateOf(LocalDate.now())
    }
    val dateForRange = remember {
        mutableStateOf(date.value)
    }

    taskViewModel.changeDate(date.value)
    val tasks = taskViewModel.taskList.collectAsState().value;

    ScaffoldContact(tittle = stringResource(id = R.string.app_name)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopRow(date, dateForRange, tasks.size)
            DatesRow(date, dateForRange)
            if (tasks.isNotEmpty()) {
                TasksColumn(tasks,
                    onEditTask = {
                        navController.navigate(route = Screens.ChangeTaskScreen.name + "/${it.id}")
                    },
                    onDeleteTask = {
                        taskViewModel.remove(task = it)
                    }, onUpdateStatus = { task, status ->
                        task.status = status
                        taskViewModel.update(task)
                    })
            } else {
                HintText(
                    modifier = Modifier.padding(vertical = 30.dp),
                    text = "Click + to add new task"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            IconGradCard(
                onClick = {
                    navController.navigate(
                        route = Screens.AddTaskScreen.name + "/${
                            dateFormatter.format(
                                date.value
                            )
                        }"
                    )
                },
                elevation = 25.dp,
                modifier = Modifier
                    .size(60.dp)
                    .padding(15.dp),
                imageVector = FontAwesomeIcons.Solid.Plus
            )
        }
    }

}

@Composable
fun DatesRow(date: MutableState<LocalDate>, dateForRange: MutableState<LocalDate>) {
    val rowState = rememberLazyListState(4, -10)

    val dates = DateUtils.datesBetween(
        dateForRange.value.minusDays(5),
        dateForRange.value.plusDays(5)
    )
    LazyRow(state = rowState) {
        items(items = dates) {
            val cardColor: Brush?
            val textColor: Color?
            val different = it.dayOfMonth == date.value.dayOfMonth
            if (different) {
                cardColor = gradient(INDEX_TERTIARY)
                textColor = MaterialTheme.colors.onSecondary
            } else {
                cardColor = linearGradient(
                    listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.background
                    )
                )
                textColor = MaterialTheme.colors.onBackground
            }
            Spacer(modifier = Modifier.width(20.dp))
            GradientCard(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(50.dp),
                elevation = if (different) 20.dp else 0.dp,
                elevationColor = if (different) tertiaryGradient[1] else Color.Transparent,
                gradient = cardColor,
                onClick = {
                    date.value = it
                }
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format("%02d", it.dayOfMonth),
                        style = MaterialTheme.typography.h4,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = it.dayOfWeek.name[0] + it.dayOfWeek.name.substring(1, 3)
                            .lowercase(),
                        style = MaterialTheme.typography.body1,
                        color = textColor
                    )
                    Text(
                        modifier = Modifier.height(0.dp),
                        text = "               ",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksColumn(
    tasks: List<Task>,
    onEditTask: (task: Task) -> Unit,
    onDeleteTask: (task: Task) -> Unit,
    onUpdateStatus: (task: Task, status: Status) -> Unit
) {

    val statusToGradient = mapOf(
        Pair(Status.Undone, INDEX_QUANTITY),
        Pair(Status.Doing, INDEX_PRIMARY),
        Pair(Status.Todo, INDEX_TERTIARY),
        Pair(Status.Done, INDEX_SECONDARY),
    )


    LazyColumn(modifier = Modifier.padding(top = 15.dp)) {
        itemsIndexed(tasks) { index, task ->

            val mutableStatus = remember {
                mutableStateOf(task.status)
            }

            val dropMenuExpanded = remember {
                mutableStateOf(false)
            }


            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                HintTextBigger(
                    modifier = Modifier.padding(start = 20.dp),
                    text = task.startTime.format(timeFormatter)
                )
                Box {

                    GradientCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        elevation = 5.dp,
                        gradient = gradient(
                            gradientIndex = statusToGradient[mutableStatus.value]!!,
                            startOffset = Offset(0f, Float.POSITIVE_INFINITY),
                            endOffset = Offset.Zero
                        ),
                        elevationColor = getGradColors(statusToGradient[mutableStatus.value]!!)[0],
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .combinedClickable(onClick = {}, onLongClick = {
                                    dropMenuExpanded.value = true
                                })
                                .fillMaxWidth()
                                .padding(vertical = 15.dp, horizontal = 20.dp)
                        ) {
                            Text(
                                text = task.tittle,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onSecondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(
                                    text = "Until " + task.endTime.format(timeFormatter),
                                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                                    color = HintColorAlpha
                                )

                                val items = arrayOf(
                                    Status.Todo.name,
                                    Status.Doing.name,
                                    Status.Done.name,
                                    Status.Undone.name
                                )

                                Spinner(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                        .border(
                                            width = 1.dp,
                                            color = HintColorAlpha,
                                            shape = RoundedCornerShape(5.dp)
                                        ),
                                    items = items,
                                    defaultIndex = mutableStatus.value.ordinal,
                                    onItemChange = { _, sItem ->
                                        val newStatus = Status.valueOf(sItem)
                                        onUpdateStatus(task, newStatus)
                                        mutableStatus.value = newStatus
                                    }
                                )
                            }
                        }
                    }

                    DropMenu(
                        expanded = dropMenuExpanded,
                        items = listOf("Edit", "Delete"),
                        onItemSelect = {
                            when (it) {
                                "Delete" -> {
                                    onDeleteTask(task)
                                    if (tasks.size > index + 1)
                                        mutableStatus.value = tasks[index + 1].status
                                }
                                "Edit" -> {
                                    onEditTask(task)
                                }
                            }
                        })

                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


@Composable
fun TopRow(date: MutableState<LocalDate>, dateForRange: MutableState<LocalDate>, tasksSize: Int) {

    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(
                "Select"
            )
            negativeButton(
                "Cancel",
            )
        }
    ) {

        datepicker { selectedDate ->
            date.value = selectedDate
            dateForRange.value = selectedDate
        }
    }

    Row(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = date.value.format(DateTimeFormatter.ofPattern("MMMM, d")),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            HintText(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                text = "$tasksSize task today"
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

            IconGradCard(
                modifier = Modifier
                    .size(60.dp)
                    .padding(15.dp),
                onClick = { dialogState.show() },
                elevation = 20.dp,
                imageVector = FontAwesomeIcons.Solid.CalendarWeek
            )

        }


    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldContact(tittle: String, contact: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.background(
            backgroundGradient()
        ), topBar = {
            TopAppBar(backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    Crossfade(targetState = ThemeViewModel.isDark.value) {
                        if (it) {
                            ActionIcon(imageVector = FontAwesomeIcons.Solid.Sun)
                        } else {
                            ActionIcon(imageVector = FontAwesomeIcons.Solid.Moon)
                        }
                    }
                },
                title = {
                    Text(
                        text = tittle,
                        style = TextStyle(fontFamily = bulletto , fontSize = 23.sp , fontWeight = FontWeight.Black),
                        color = MaterialTheme.colors.onBackground
                    )
                }
            )
        }, backgroundColor = Color.Transparent
    ) {
        contact()
    }
}

@Composable
fun ActionIcon(imageVector: ImageVector) {
    ClickableIcon(
        modifier = Modifier
            .size(50.dp)
            .padding(15.dp),
        tint = MaterialTheme.colors.onBackground,
        imageVector = imageVector, contentDescription = "change theme",
        onClick = { ThemeViewModel.onThemeChanged() }
    )
}

@Composable
fun IconGradCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    elevation: Dp = 0.dp,
    imageVector: ImageVector
) {
    GradientCard(
        modifier = Modifier.wrapContentSize(),
        shape = CircleShape,
        elevation = elevation,
        elevationColor = getGradColors(INDEX_SECONDARY)[0],
        gradient = gradient(INDEX_SECONDARY),
        onClick = onClick
    ) {
        Icon(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = "icon",
            tint = White
        )
    }
}