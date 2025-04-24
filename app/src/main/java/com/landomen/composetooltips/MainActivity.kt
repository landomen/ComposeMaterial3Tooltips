package com.landomen.composetooltips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.landomen.composetooltips.ui.theme.ComposeTooltipsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTooltipsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        PlainTooltipLongPress()

        Spacer(modifier = Modifier.height(32.dp))

        PlainTooltipManual()

        Spacer(modifier = Modifier.height(32.dp))

        PlainTooltipWithCarrotManual()

        Spacer(modifier = Modifier.height(32.dp))

        RichTooltipClick()

        Spacer(modifier = Modifier.height(32.dp))

        CustomRichTooltipClick()

        Spacer(modifier = Modifier.height(32.dp))

        ShowTwoTooltips()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlainTooltipLongPress() {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = { PlainTooltip { Text("This is a simple plain tooltip") } },
        state = rememberTooltipState()
    ) {
        Button(onClick = {}) {
            Text(text = "Show Plain Tooltip on Long Press")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlainTooltipManual() {
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text("This is a simple plain tooltip") } },
            state = tooltipState
        ) {
            Button(onClick = {
                scope.launch {
                    tooltipState.show()
                }
            }) {
                Text(text = "Show Plain Tooltip on Click")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlainTooltipWithCarrotManual() {
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(16.dp),
        tooltip = {
            PlainTooltip(
                caretSize = DpSize(32.dp, 16.dp),
                contentColor = Color.Yellow,
                containerColor = Color.DarkGray,
                shadowElevation = 4.dp,
                tonalElevation = 12.dp,
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(Color.Gray)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("This is a simple customized plain tooltip")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("This is a second Text in the tooltip")
                }
            }
        },
        state = tooltipState
    ) {
        Button(onClick = {
            scope.launch {
                tooltipState.show()
            }
        }) {
            Text(text = "Show Custom Plain Tooltip on Click")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipClick() {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(8.dp),
        tooltip = {
            RichTooltip(
                caretSize = TooltipDefaults.caretSize,
                title = { Text("Title of the tooltip") },
                action = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                tooltipState.dismiss()
                            }
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text("This is the main content of the rich tooltip")
            }
        },
        state = tooltipState
    ) {
        Button(onClick = {
            scope.launch {
                tooltipState.show()
            }
        }) {
            Text(text = "Show Rich Tooltip on Click")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomRichTooltipClick() {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(8.dp),
        tooltip = {
            RichTooltip(
                caretSize = TooltipDefaults.caretSize,
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = Color.Black.copy(alpha = 0.9f),
                    titleContentColor = Color.Green,
                    contentColor = Color.White,
                ),
                shape = RectangleShape,
                title = {
                    Row {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Awesome!")
                    }
                        },
                action = {
                    Row {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    tooltipState.dismiss()
                                }
                            }
                        ) {
                            Text("Dismiss")
                        }

                        TextButton(
                            onClick = {
                                scope.launch {
                                    tooltipState.dismiss()
                                }
                            }
                        ) {
                            Text("Next")
                        }
                    }
                }
            ) {
                Text("You've successfully opened a rich tooltip! 🎉")
            }
        },
        state = tooltipState
    ) {
        Button(onClick = {
            scope.launch {
                tooltipState.show()
            }
        }) {
            Text(text = "Show Custom Rich Tooltip on Click")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTwoTooltips() {
    val tooltipState1 = rememberTooltipState(isPersistent = true)
    val tooltipState2 = rememberTooltipState(
        isPersistent = true,
        mutatorMutex = MutatorMutex()
    )
    val scope = rememberCoroutineScope()

    Button(onClick = {
        scope.launch {
            tooltipState1.show()
        }
        scope.launch {
            tooltipState2.show()

        }
    }) {
        Text(text = "Show Two Tooltips on Click")
    }

    Spacer(Modifier.height(16.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = { PlainTooltip(caretSize = TooltipDefaults.caretSize) { Text("Select option 1") } },
            state = tooltipState1
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = false, onClick = {
                    // ignore
                })

                Text("Option 1")
            }
        }

        Spacer(Modifier.width(32.dp))

        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = { PlainTooltip(caretSize = TooltipDefaults.caretSize) { Text("Select option 2") } },
            state = tooltipState2
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = false, onClick = {
                    // ignore
                })

                Text("Option 2")
            }
        }
    }
}