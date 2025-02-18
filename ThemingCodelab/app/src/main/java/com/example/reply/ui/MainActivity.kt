package com.example.reply.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.reply.data.LocalEmailsDataProvider


class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            ReplyApp(
                replyHomeUIState = uiState,
                closeDetailScreen = {
                    viewModel.closeDetailScreen()
                },
                navigateToDetail = { emailId ->
                    viewModel.setSelectedEmail(emailId)
                }
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ReplyAppPreviewLight() {
    ReplyApp(
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails
        )
    )
}

@Composable
fun Counter(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) {
        Text("Count: $count")
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    Counter(count, onIncrement = { count++ })
}

@Composable
fun SideEffectExample(counter: Int) {
    LaunchedEffect(counter) {
        println("Counter changed: $counter")
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DetailScreen(id)
        }
    }
}

class CounterViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set

    fun increment() {
        count++
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    Counter(viewModel.count, viewModel::increment)
}

@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    Counter(count = viewModel.count, onIncrement = { viewModel.increment() })
}

class CounterViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set

    fun increment() {
        count++
    }
}