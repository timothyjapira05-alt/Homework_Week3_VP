package com.timothy.week3_visualprogramming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


enum class ResultCat(val message: String) {
    NotClick("Purr~"),
    Click("Meow!!")
}

@Composable
fun Soal2() {
    var coins by rememberSaveable { mutableStateOf(0) }
    var value by rememberSaveable { mutableStateOf(1) }
    var resultState by rememberSaveable { mutableStateOf(ResultCat.NotClick) }
    var isPressed by remember { mutableStateOf(false) }

    val cost = 10
    val canUpgrade = coins >= cost

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(id = R.drawable.a273765cdfe8bd1bdb0b028c71bf5aae),
            contentDescription = "Background Soal 2",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Coins",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$coins",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                    Text(
                        text = "$value coins per tap",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tap the cat!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Cat Image (press → ganti gambar, release → balik)
                Image(
                    painter = if (isPressed) painterResource(R.drawable._7926b940e8de3b11b3058d43872e4a81_3) else painterResource(R.drawable._7926b940e8de3b11b3058d43872e4a81_2),
                    contentDescription = "cat",
                    modifier = Modifier
                        .size(150.dp)
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    when (event.type) {
                                        PointerEventType.Press -> {
                                            isPressed = true
                                            resultState = ResultCat.Click
                                            coins += value
                                        }
                                        PointerEventType.Release,
                                        PointerEventType.Exit -> {
                                            isPressed = false
                                            resultState = ResultCat.NotClick
                                        }
                                    }
                                }
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = resultState.message,
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(50.dp))
                // Upgrade Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Give Me Your Coin",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Next upgrade: +2 coins per tap",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    if (canUpgrade) Color(0xFF4CAF50) else Color.LightGray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(enabled = canUpgrade) {
                                    coins -= cost
                                    value += 2
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (canUpgrade) "Upgrade for $cost coins" else "Find $cost more coins",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2Preview() {
    Soal2()
}
