package com.timothy.week3_visualprogramming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import kotlin.time.Duration.Companion.milliseconds

// Enum untuk kategori hasil reaksi.
enum class Result(val message: String) {
    VeryFast("DANG YOU ARE SO FAST BRO!"),
    Fast("YOUR REFLEX IS GOOD"),
    Normal("MEH LIKE OTHER PERSON"),
    Slow("YOU LIKE A SNAIL BRO")
}

// Enum untuk setiap tampilan/status permainan.
enum class GameState {
    INITIAL,
    WAITING,
    READY,
    TRIAL_RESULT,
    FAILED,
    FINAL
}

fun getResult(value: Int): Result {
    return when {
        value < 180 -> Result.VeryFast
        value < 280 -> Result.Fast
        value < 450 -> Result.Normal
        else -> Result.Slow
    }
}

@Composable
fun Soal1() {
    var gameState by rememberSaveable {
        mutableStateOf(GameState.INITIAL)
    }

    var startTime by rememberSaveable {
        mutableLongStateOf(0L)
    }

    var reactionTime by rememberSaveable {
        mutableIntStateOf(0)
    }

    var currentTrial by rememberSaveable {
        mutableIntStateOf(0)
    }

    // Angka -1 berarti trial gagal; angka 0 atau lebih adalah waktu valid dalam ms.
    var trialResults by rememberSaveable {
        mutableStateOf(listOf(-1, -1, -1))
    }


    // Saat masuk ke WAITING, tunggu secara acak selama 500-4500 ms.
    LaunchedEffect(gameState, currentTrial) {
        if (gameState == GameState.WAITING) {
            val randomDelay = Random.nextLong(500L, 4501L)
            delay(randomDelay.milliseconds)

            // Jika masih berada di WAITING, pemain belum menekan terlalu cepat.
            if (gameState == GameState.WAITING) {
                startTime = System.currentTimeMillis()
                gameState = GameState.READY
            }
        }
    }

    val validResults = trialResults.filter { it >= 0 }
    val average = if (validResults.isNotEmpty()) {
        validResults.average().toInt()
    } else {
        0
    }

    when (gameState) {
        GameState.INITIAL -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF94DEE5))
                    .clickable {
                        currentTrial = 0
                        reactionTime = 0
                        startTime = 0L
                        trialResults = listOf(-1, -1, -1)
                        gameState = GameState.WAITING
                    }
            ) {
                Text(
                    text = "Reaction",
                    color = Color.White,
                    fontSize = 40.sp
                )

                Image(
                    painter = painterResource(R.drawable.flash_on_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Reaction test icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                )
                //testing saja, sudah masuk atau belum di github
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Test",
                    color = Color.White,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(80.dp))

                Text(
                    text = "Click to start",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }

        GameState.WAITING -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFDCDCDC))
                    .clickable {
                        // Menekan layar sebelum READY berarti trial ini gagal.
                        val updatedResults = trialResults.toMutableList()
                        updatedResults[currentTrial] = -1
                        trialResults = updatedResults
                        gameState = GameState.FAILED
                    }
            ) {
                Text(
                    text = "Get Ready",
                    color = Color.DarkGray,
                    fontSize = 40.sp
                )

                Image(
                    painter = painterResource(R.drawable.warning_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Waiting icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Wait for Green Light",
                    color = Color.DarkGray,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(80.dp))

                Text(
                    text = "DON'T CLICK YET",
                    color = Color.DarkGray,
                    fontSize = 20.sp
                )
            }
        }

        GameState.READY -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF32A852))
                    .clickable {
                        reactionTime =
                            (System.currentTimeMillis() - startTime).toInt()

                        val updatedResults = trialResults.toMutableList()
                        updatedResults[currentTrial] = reactionTime
                        trialResults = updatedResults

                        gameState = GameState.TRIAL_RESULT
                    }
            ) {
                Text(
                    text = "GO!",
                    color = Color.White,
                    fontSize = 40.sp
                )

                Image(
                    painter = painterResource(R.drawable.directions_run_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Go icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Click Now!",
                    color = Color.White,
                    fontSize = 25.sp
                )

                Text(
                    text = "Tap As Fast As You Can!",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }

        GameState.TRIAL_RESULT -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF32A852))
                    .clickable {
                        if (currentTrial < 2) {
                            currentTrial += 1
                            gameState = GameState.WAITING
                        } else {
                            gameState = GameState.FINAL
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Trial ${currentTrial + 1} Complete!",
                    color = Color.White,
                    fontSize = 30.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Time = $reactionTime ms",
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = if (currentTrial < 2) {
                        "Continue to Trial ${currentTrial + 2}"
                    } else {
                        "Show Results"
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(32.dp))

                TrialResultsCard(trialResults)
            }
        }

        GameState.FAILED -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD50000))
                    .clickable {
                        if (currentTrial < 2) {
                            currentTrial += 1
                            gameState = GameState.WAITING
                        } else {
                            gameState = GameState.FINAL
                        }
                    }
            ) {
                Text(
                    text = "FAIL!",
                    color = Color.White,
                    fontSize = 40.sp
                )

                Image(
                    painter = painterResource(R.drawable.slow),
                    contentDescription = "Thumbs down",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You Clicked Too Early!",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (currentTrial < 2) {
                        "Continue to Trial ${currentTrial + 2}"
                    } else {
                        "Show Final Results"
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                TrialResultsCard(trialResults)
            }
        }

        GameState.FINAL -> {
            if (validResults.isNotEmpty()) {
                val category = getResult(average)

                val bgColor = when (category) {
                    Result.VeryFast -> Color(0xFF43A047)
                    Result.Fast -> Color(0xFF1E88E5)
                    Result.Normal -> Color(0xFFFDD835)
                    Result.Slow -> Color(0xFFE53935)
                }

                val image = when (category) {
                    Result.VeryFast -> painterResource(R.drawable.veryfast)
                    Result.Fast -> painterResource(R.drawable.fast)
                    Result.Normal -> painterResource(R.drawable.normal)
                    Result.Slow -> painterResource(R.drawable.slow)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bgColor)
                        .padding(16.dp)
                ) {
                    Text(
                        text = category.message,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(12.dp))

                    Image(
                        painter = image,
                        contentDescription = "Reaction category image",
                        modifier = Modifier
                            .size(220.dp)
                            .padding(12.dp)
                    )

                    Text(
                        text = "Average: $average ms",
                        fontSize = 22.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(12.dp))

                    TrialResultsCard(trialResults, average)

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Click to Start New Test",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            currentTrial = 0
                            reactionTime = 0
                            startTime = 0L
                            trialResults = listOf(-1, -1, -1)
                            gameState = GameState.INITIAL
                        }
                    )
                }
            } else {
                // Jika semua trial gagal, tidak ada rata-rata valid untuk dikategorikan.
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFD50000))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "No Valid Reaction Time",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "All 3 trials failed. Try again!",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(24.dp))

                    TrialResultsCard(trialResults)

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Click to Start New Test",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            currentTrial = 0
                            reactionTime = 0
                            startTime = 0L
                            trialResults = listOf(-1, -1, -1)
                            gameState = GameState.INITIAL
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TrialResultsCard(
    trialResults: List<Int>,
    average: Int? = null
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Trial Results",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(Modifier.height(12.dp))

            (0..2).forEach { index ->
                val result = trialResults[index]
                Text(
                    text = when {
                        result == -1 -> "Trial ${index + 1}: Failed"
                        else -> "Trial ${index + 1}: $result ms"
                    },
                    fontSize = 14.sp,
                    color = when {
                        result == -1 -> Color.Red
                        else -> Color(0xFF2E7D32)
                    },
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }

            if (average != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Average Score\n${average} ms",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE67022),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1Preview() {
    Soal1()
}
