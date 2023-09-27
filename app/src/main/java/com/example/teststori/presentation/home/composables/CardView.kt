package com.example.teststori.presentation.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teststori.R
import com.example.teststori.common.convertIntToColombianMoney
import com.example.teststori.common.formatDate
import com.example.teststori.common.timestampToDate
import com.example.teststori.domain.model.Movement
import com.example.teststori.presentation.Screen

@Composable
fun CardView(
    movement: Movement?,
    navController: NavController
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 16.dp)
            .clickable {
                navController.navigate(Screen.DetailScreen.route + "/${movement?.id}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${context.getString(R.string.costTransaction)} ${
                    convertIntToColombianMoney(
                        movement?.ammount ?: 1
                    )
                }"
            )
            Text(
                text = "${context.getString(R.string.dateTransaction)} ${
                    formatDate(
                        timestampToDate(
                            movement?.date
                        )
                    )
                }"
            )
        }
    }
}