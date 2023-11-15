package com.hamon.albochallenge.features.practice_code_excercises.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hamon.albochallenge.components.count.CountCustom

@Composable
fun CodeExercisesScreen() {
    CountCustom(actualCount = 5, maxCount = 25 )
}

@Composable
@Preview
fun PreviewCodeExercisesScreen() {
    CodeExercisesScreen()
}