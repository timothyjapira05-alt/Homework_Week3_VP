package com.timothy.week3_visualprogramming

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class Mode { COLOR, TEXT }
enum class Choice { INK, WORD }
enum class ColorName(val label: String, val color: Color) {
    RED("RED", Color(0xFFFF5252)),
    BLUE("BLUE", Color(0xFF448AFF)),
    GREEN("GREEN", Color(0xFF4CAF50)),
    YELLOW("YELLOW", Color(0xFFFFEB3B)),
    ORANGE("ORANGE", Color(0xFFFF9800)),
    PURPLE("PURPLE", Color(0xFF9C27B0))
}


@Composable
fun Soal3(modifier: Modifier = Modifier) {
    var page by rememberSaveable { mutableStateOf("1") }
    var countdown by rememberSaveable { mutableStateOf(3) }
    var score by rememberSaveable { mutableStateOf(0) }
    var strikes by rememberSaveable { mutableStateOf(0) }
    var bestScore by rememberSaveable { mutableStateOf(0) }

    // soal aktif
    var currentWord by rememberSaveable { mutableStateOf(ColorName.RED) }
    var inkColor by rememberSaveable { mutableStateOf(ColorName.BLUE) }
    var mode by rememberSaveable { mutableStateOf(Mode.COLOR) }
    var leftChoice by rememberSaveable { mutableStateOf(Choice.INK) }

    if (page == "1") {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            Text(
                text = "Welcome\n to\n Color Word Matching",
                color = Color.Black,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            Button(
                onClick = {
                    countdown = 3
                    page = "2"
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9E9E))
            ) {
                Text("Start Game")
            }
        }

    } else if (page == "2") {
        LaunchedEffect(countdown) {
            if (countdown > 0) {
                delay(1000)
                countdown--
            } else {
                delay(700)
                page = "3"
                score = 0
                strikes = 0
                currentWord = ColorName.values().random()
                inkColor = ColorName.values().random()
                mode = if (Random.nextBoolean()) Mode.COLOR else Mode.TEXT
                leftChoice = if (Random.nextBoolean()) Choice.INK else Choice.WORD
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            Text(
                text = if (countdown > 0) "$countdown" else "Start!",
                color = Color.White,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
        }

    } else if (page == "3") {
        var timer by rememberSaveable { mutableStateOf(5) }

        LaunchedEffect(key1 = score, key2 = strikes) {
            timer = 5
            while (timer > 0) {
                delay(1000)
                timer--
            }
            if (page == "3") {
                strikes++
                if (strikes >= 3) {
                    bestScore = maxOf(bestScore, score)
                    page = "4"
                } else {
                    currentWord = ColorName.values().random()
                    inkColor = ColorName.values().random()
                    mode = if (Random.nextBoolean()) Mode.COLOR else Mode.TEXT
                    leftChoice = if (Random.nextBoolean()) Choice.INK else Choice.WORD
                    timer = 5
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(100.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mode: $mode", fontSize = 18.sp)
                Row {
                    Text("✅ $score  ", fontSize = 16.sp)
                    Text("❌ $strikes/3", fontSize = 16.sp, color = Color.Red)
                }
            }

            Spacer(Modifier.height(100.dp))

            Text(
                text = "$timer s",
                fontSize = 28.sp,
                color = Color.Black
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = currentWord.label,
                color = inkColor.color,
                fontSize = 40.sp
            )

            Spacer(Modifier.height(100.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                val rightChoice = if (leftChoice == Choice.INK) Choice.WORD else Choice.INK

                listOf(leftChoice, rightChoice).forEach { choice ->
                    val label = if (choice == Choice.INK) inkColor.label else currentWord.label
                    Button(
                        onClick = {
                            val isCorrect = if (mode == Mode.COLOR) {
                                choice == Choice.INK
                            } else {
                                choice == Choice.WORD
                            }
                            if (isCorrect) {
                                score++
                            } else {
                                strikes++
                            }

                            if (strikes >= 3) {
                                bestScore = maxOf(bestScore, score)
                                page = "4"
                            } else {
                                currentWord = ColorName.values().random()
                                inkColor = ColorName.values().random()
                                mode = if (Random.nextBoolean()) Mode.COLOR else Mode.TEXT
                                leftChoice = if (Random.nextBoolean()) Choice.INK else Choice.WORD
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB0BEC5)
                        ),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(label, color = Color.Black)
                    }
                }
            }
        }
    }
    else if (page == "4") {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            Text("Game Over!", fontSize = 30.sp)
            Spacer(Modifier.height(20.dp))
            Text("Score: $score", fontSize = 22.sp)
            Text("Best Score: $bestScore", fontSize = 22.sp)
            Spacer(Modifier.height(30.dp))
            Button(onClick = { page = "1" }) {
                Text("Restart")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal3Preview() {
    Soal3()
}
