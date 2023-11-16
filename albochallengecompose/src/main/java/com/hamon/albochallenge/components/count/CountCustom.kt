package com.hamon.albochallenge.components.count

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CountCustom(actualCount: Int, maxCount: Int) {

    val result = (actualCount * 100) / maxCount

    Column {
        Row(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 0) Color.Blue else Color.Red,
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 20) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 40) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 60) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .weight(weight = 1f, fill = true)
                    .fillMaxHeight(), color = if (result > 80) Color.Blue else Color.Red,
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
            ) {

            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "0")
            Surface(modifier = Modifier.weight(1f)) {

            }
            Text(text = "0/25 tarjetazos")
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun CountCustomPreview() {
    CountCustom(0, 25)
}