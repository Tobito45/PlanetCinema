package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.planetcinema.view.EditViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFilmCard(
    orientation: Int,
    onNavigateUp: () -> Unit,
    viewModel: EditViewModel,
    scope: CoroutineScope
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = if(viewModel.uiState.isAdding) "Add Film" else "Edit Film",
                    fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFBF3641))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 15.dp,
                top = innerPadding.calculateTopPadding() + if (orientation == Configuration.ORIENTATION_LANDSCAPE) 5.dp else 20.dp,
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 15.dp,
                bottom = 20.dp
                ).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.uiState.inputName,
                onValueChange =  { viewModel.editDataFilm(newName = it)},
                label = { Text("Name") },
                isError = if(viewModel.uiState.isError && viewModel.uiState.inputName.isEmpty()) true else false,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 15.dp else 20.dp)
            )
            TextField(
                value = viewModel.uiState.inputAutor,
                onValueChange = { viewModel.editDataFilm(newAutor = it)},
                label = { Text("Author") },
                isError = if(viewModel.uiState.isError && viewModel.uiState.inputAutor.isEmpty()) true else false,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 15.dp else 20.dp)

            )
            TextField(
                value = viewModel.uiState.inputMark,
                onValueChange =  { viewModel.editDataFilm(newMark = it)},
                label = { Text("Mark") },
                isError = if(viewModel.uiState.isError && viewModel.uiState.inputMark.isEmpty()) true else false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
                    .padding(top = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 15.dp else 20.dp)

            )
            TextField(
                value = viewModel.uiState.inputUrl,
                onValueChange =  { viewModel.editDataFilm(newUrl = it)},
                isError = if(viewModel.uiState.isError && viewModel.uiState.inputUrl.isEmpty()) true else false,
                label = { Text("Image (url)") },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 15.dp else 20.dp)
            )
            Button(
                onClick = {
                          scope.launch {
                              if(viewModel.updateFilm())
                                onNavigateUp()
                          }
                },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E3F3F),
                    disabledContainerColor = Color(0xFF181818),
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
                    .padding(top = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 15.dp else 30.dp)
            ) {
                Text(text = "Submit".uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold)

            }
        }
    }
}
