package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    val steps = listOf(
        Step(
            text = R.string.lemon_select,
            image = R.drawable.lemon_tree,
            contentDescription = R.string.lemon_tree_content_description,
            action = {
                currentStep = 2
                squeezeCount = (2..4).random()
            }
        ),
        Step(
            text = R.string.lemon_squeeze,
            image = R.drawable.lemon_squeeze,
            contentDescription = R.string.lemon_content_description,
            action = {
                if (--squeezeCount == 0) currentStep = 3
            }
        ),
        Step(
            text = R.string.lemon_drink,
            image = R.drawable.lemon_drink,
            contentDescription = R.string.lemonade_content_description,
            action = { currentStep = 4 }
        ),
        Step(
            text = R.string.lemon_empty_glass,
            image = R.drawable.lemon_restart,
            contentDescription = R.string.empty_glass_content_description,
            action = { currentStep = 1 }
        )
    )

    Scaffold(topBar = { AppBar(title = "Lemonade") }) { padding ->
        val step = steps[currentStep - 1]
        LemonContent(step, Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(title, style = MaterialTheme.typography.titleLarge) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color(0xFFFFEB46) // Bright Lemon Yellow color
        )
    )
}

@Composable
fun LemonContent(step: Step, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = step.action,
            shape = RoundedCornerShape(16)
        ) {
            Image(
                painter = painterResource(step.image),
                contentDescription = stringResource(step.contentDescription)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(step.text))
    }
}

data class Step(
    val text: Int,
    val image: Int,
    val contentDescription: Int,
    val action: () -> Unit
)

@Preview
@Composable
fun LemonPreview() {
    LemonadeTheme { LemonadeApp() }
}
