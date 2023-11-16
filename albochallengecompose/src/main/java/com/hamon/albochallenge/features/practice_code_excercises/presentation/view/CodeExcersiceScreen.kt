package com.hamon.albochallenge.features.practice_code_excercises.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamon.albochallenge.components.count.CountCustom

@Composable
fun CodeExercisesScreen() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        CountCustom(actualCount = 5, maxCount = 25)
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewCodeExercisesScreen() {
    CodeExercisesScreen()
}