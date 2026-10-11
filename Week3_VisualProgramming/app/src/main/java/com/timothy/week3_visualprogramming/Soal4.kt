package com.timothy.week3_visualprogramming

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class Rps(val emoji: String) {
    ROCK("✊"), PAPER("✋"), SCISSOR("✌")
}


enum class Hasil { WIN, LOSE, DRAW }


@Composable
fun Soal4 () {
    var page by rememberSaveable { mutableStateOf("1") }
    var playerScore by rememberSaveable { mutableStateOf(0) }
    var cpuScore by rememberSaveable { mutableStateOf(0) }
    var bestScore by rememberSaveable { mutableStateOf(0) }
    val bestOf = 5
    val winTarget = bestOf / 2 + 1

    var playerPick by remember { mutableStateOf<Rps?>(null) }
    var cpuPick by remember { mutableStateOf<Rps?>(null) }
    var roundResult by remember { mutableStateOf<Hasil?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (page == "1") {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("🧑 $playerScore — $cpuScore 🤖",
                    fontSize = 22.sp)
                Text("Mode: Best of $bestOf",
                    fontSize = 18.sp)
            }
            Spacer(Modifier.height(100.dp))

            Text("Rock • Paper • Scissors",
                fontSize = 24.sp,
                color = Color.Black)

            Spacer(Modifier.height(100.dp))

            Button(
                onClick = {
                    playerScore = 0
                    cpuScore = 0
                    page = "2"
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9E9E))
            )
            {
                Text("Start")
            }

        } else if (page == "2") {
            // Pick Page
            Text("🧑 $playerScore — $cpuScore 🤖",
                fontSize = 22.sp)

            Spacer(Modifier.height(12.dp))

            Text("Pick your move!",
                fontSize = 20.sp)

            Spacer(Modifier.height(40.dp))

            Text("❔ VS ❔",
                fontSize = 30.sp)

            Spacer(Modifier.height(40.dp))

            val buttons = Rps.values().toList().shuffled()
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                buttons.forEach { choice ->
                    Button(
                        onClick = {
                            playerPick = choice
                            cpuPick = Rps.values().random()
                            if (playerPick == cpuPick) {
                                roundResult = Hasil.DRAW
                            } else if (
                                (playerPick == Rps.ROCK && cpuPick == Rps.SCISSOR) ||
                                (playerPick == Rps.PAPER && cpuPick == Rps.ROCK) ||
                                (playerPick == Rps.SCISSOR && cpuPick == Rps.PAPER)
                            ) {
                                roundResult = Hasil.WIN
                            } else {
                                roundResult = Hasil.LOSE
                            }
                            page = "3"
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF9E9E9E))
                    ) {
                        Text(choice.emoji, fontSize = 30.sp)
                    }
                }
            }

        } else if (page == "3") {
            // Reveal Page
            Text("🧑 ${playerPick?.emoji} VS 🤖 ${cpuPick?.emoji}", fontSize = 28.sp)
            Spacer(Modifier.height(12.dp))
            when (roundResult) {
                Hasil.WIN -> Text("You Win!", color = Color.Green, fontSize = 22.sp)
                Hasil.LOSE -> Text("You Lose!", color = Color.Red, fontSize = 22.sp)
                Hasil.DRAW -> Text("Draw!", color = Color.Gray, fontSize = 22.sp)
                null -> {}
            }

            LaunchedEffect(roundResult) {
                when (roundResult) {
                    Hasil.WIN -> playerScore++
                    Hasil.LOSE -> cpuScore++
                    else -> {}
                }
                delay(700)
                if (playerScore >= winTarget || cpuScore >= winTarget) {
                    if (playerScore - cpuScore > bestScore) bestScore = playerScore - cpuScore
                    page = "4"
                } else {
                    page = "2"
                }
            }
        } else if (page == "4") {
            // Finished Page
            Text("Match Over!", fontSize = 28.sp)
            Spacer(Modifier.height(12.dp))
            if (playerScore > cpuScore) {
                Text("🎉 You Win the Match!", color = Color.Green, fontSize = 22.sp)
            } else {
                Text("😢 You Lose the Match", color = Color.Red, fontSize = 22.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text("Final Score: 🧑 $playerScore - $cpuScore 🤖")
            Text("Best Score: $bestScore")

            Spacer(Modifier.height(24.dp))
            Row {
                Button(onClick = {
                    playerScore = 0
                    cpuScore = 0
                    page = "2"
                }, modifier = Modifier.padding(8.dp)) {
                    Text("Restart")
                }
                Button(onClick = { page = "1" }, modifier = Modifier.padding(8.dp)) {
                    Text("Exit")
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal4Preview() {
    Soal4()
}