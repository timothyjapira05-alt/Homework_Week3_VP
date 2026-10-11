
package com.timothy.week3_visualprogramming

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val Cream = Color(0xFFFFF8F0)
private val CoffeeBrown = Color(0xFF79391F)
private val CoffeeOrange = Color(0xFFD95F27)
private val SoftOrange = Color(0xFFFFE8D6)
private val TextBrown = Color(0xFF654536)

private fun rupiah(value: Int): String {
    val formatted = NumberFormat.getNumberInstance(
        Locale("id", "ID")
    ).format(value)

    return "Rp $formatted"
}

@Composable
fun ViewSoal1Bonus() {
    var jumlah by rememberSaveable {
        mutableStateOf(1)
    }

    var ukuranBesar by rememberSaveable {
        mutableStateOf(false)
    }

    val harga = if (ukuranBesar) 31000 else 25000
    val subtotal = harga * jumlah
    val pajak = subtotal * 10 / 100
    val total = subtotal + pajak

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Kopi Kenangan Senja",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextBrown
            )

            Text(text = "☕", fontSize = 22.sp)
        }

        // Kartu produk
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Area gambar kopi
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            CoffeeBrown,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "☕",
                        fontSize = 64.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Caramel Latte",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBrown
                )

                Text(
                    text = "Espresso shot, steamed milk & caramel syrup",
                    fontSize = 11.sp,
                    color = TextBrown
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${rupiah(harga)} / cup",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeOrange
                )
            }
        }

        // Pilihan ukuran
        Text(
            text = "PILIHAN UKURAN:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextBrown
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { ukuranBesar = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!ukuranBesar)
                        CoffeeBrown else Cream,
                    contentColor = if (!ukuranBesar)
                        Color.White else CoffeeBrown
                )
            ) {
                Text("Regular (+0)", fontSize = 11.sp)
            }

            Button(
                onClick = { ukuranBesar = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (ukuranBesar)
                        CoffeeBrown else Cream,
                    contentColor = if (ukuranBesar)
                        Color.White else CoffeeBrown
                )
            ) {
                Text("Large (+6k)", fontSize = 11.sp)
            }
        }

        // Pengatur jumlah
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Pesanan:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBrown
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (jumlah > 1) jumlah--
                    },
                    modifier = Modifier.size(36.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftOrange,
                        contentColor = CoffeeBrown
                    )
                ) {
                    Text("-", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$jumlah",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBrown
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { jumlah++ },
                    modifier = Modifier.size(36.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftOrange,
                        contentColor = CoffeeBrown
                    )
                ) {
                    Text("+", fontSize = 18.sp)
                }
            }
        }

        // Rincian harga
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFE9C9A8),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF4E4)
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                PriceRow("Subtotal:", rupiah(subtotal))
                PriceRow("Pajak Resto (10%):", rupiah(pajak))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Tagihan:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextBrown
                    )

                    Text(
                        text = rupiah(total),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeOrange
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Tombol tambah ke keranjang
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "$jumlah Caramel Latte berhasil ditambahkan!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CoffeeOrange,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "TAMBAH KE KERANJANG",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PriceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextBrown
        )

        Text(
            text = value,
            fontSize = 12.sp,
            color = TextBrown
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSoal1Bonus() {
    ViewSoal1Bonus()
}
