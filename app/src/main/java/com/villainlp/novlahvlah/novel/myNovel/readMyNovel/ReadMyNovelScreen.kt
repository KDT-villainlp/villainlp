package com.villainlp.novlahvlah.novel.myNovel.readMyNovel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.villainlp.novlahvlah.novel.common.AlertPopup
import com.villainlp.novlahvlah.novel.common.ReadScreenTopBar

@Composable
fun ReadMyBookScreen(
    navController: NavHostController,
    title: String,
    script: String,
    viewModel: ReadMyNovelViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showDialog by viewModel.showDialog.collectAsState()
    val description by viewModel.description.collectAsState()

    OnlyTopBarScaffold(
        title = title,
        navController = navController,
        hasIcon = true,
        content = {
            LazyColumn(
                modifier = it,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Text(
                        text = script,
                        modifier = Modifier.padding(26.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        onClicked = { viewModel.onDialogClicked() }
    )
    if (showDialog){
        AlertPopup(
            title = AlertStrings.Title.script,
            message = description,
            onDismiss = { viewModel.onDismissDialog() },
            onConfirm = { viewModel.onConfirmClicked(navController, title, script) },
            novelTitle = title,
            warningMessage = AlertStrings.WarningMessage.script,
            hasTextField = true,
            tfLabel = AlertStrings.TfLabel.script,
            onTextFieldValueChange = { newDescription -> viewModel.onDescriptionChanged(newDescription) }
        )
    }
}

// ReadMyNovel
@Composable
fun OnlyTopBarScaffold(
    title: String,
    navController: NavHostController,
    hasIcon: Boolean,
    content: @Composable (Modifier) -> Unit,
    onClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            ReadScreenTopBar(
                title = title,
                navController = navController,
                onClicked = { onClicked() },
                hasIcon = hasIcon
            )
        },
    ) {
        content(Modifier.fillMaxSize().padding(it))
    }
}
