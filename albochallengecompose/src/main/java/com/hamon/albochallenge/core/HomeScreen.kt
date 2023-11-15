package com.hamon.albochallenge.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(moveToCodeExercisesScreen: () -> Unit, moveToStudioCaseScreen: () -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), onClick = moveToCodeExercisesScreen
        ) {
            Text(text = "Ejercicios practicos de código")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), onClick = moveToCodeExercisesScreen
        ) {
            Text(text = "Caso de estudio")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen(moveToCodeExercisesScreen = {}, moveToStudioCaseScreen = {})
}