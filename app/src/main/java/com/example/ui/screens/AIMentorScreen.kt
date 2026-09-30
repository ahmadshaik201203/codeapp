package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialData
import com.example.data.model.ChatMessage
import com.example.data.model.GeminiChatRole
import com.example.data.remote.GeminiService
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AIMentorScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    val listState = rememberLazyListState()

    // Auto-scroll to bottom on new message
    LaunchedEffect(uiState.chatMessages.size, uiState.isChatGenerating) {
        if (uiState.chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(16.dp)
    ) {
        // Chatbot Header & Control Panel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SkyBluePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Gemini",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Gemini Chatbot",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SkyBlueSurface,
                                    border = BorderStroke(1.dp, SkyBlueContainer)
                                ) {
                                    Text(
                                        text = uiState.selectedModel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SkyBlueDark,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "${uiState.selectedChatRole.name} • ${uiState.selectedChatRole.description.take(35)}...",
                                style = MaterialTheme.typography.labelSmall,
                                color = NavyMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = { viewModel.clearChatHistory() },
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("clear_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Clear History",
                            tint = NavyMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Model Selection Switcher Bar (Fast vs General vs Complex)
                Text(
                    text = "Active Gemini Model:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ModelChip(
                        name = "⚡ Fast",
                        modelId = GeminiService.MODEL_FAST,
                        isSelected = uiState.selectedModel == GeminiService.MODEL_FAST,
                        modifier = Modifier.weight(1f)
                    ) {
                        viewModel.selectModel(GeminiService.MODEL_FAST)
                    }
                    ModelChip(
                        name = "🌟 General",
                        modelId = GeminiService.MODEL_GENERAL,
                        isSelected = uiState.selectedModel == GeminiService.MODEL_GENERAL,
                        modifier = Modifier.weight(1f)
                    ) {
                        viewModel.selectModel(GeminiService.MODEL_GENERAL)
                    }
                    ModelChip(
                        name = "🧠 Complex",
                        modelId = GeminiService.MODEL_COMPLEX,
                        isSelected = uiState.selectedModel == GeminiService.MODEL_COMPLEX,
                        modifier = Modifier.weight(1f)
                    ) {
                        viewModel.selectModel(GeminiService.MODEL_COMPLEX)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Specialized Chatbot Roles (with system instructions)
                Text(
                    text = "Specialized AI Role (System Instruction):",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(InitialData.geminiChatRoles) { role ->
                        val isChosen = role.id == uiState.selectedChatRole.id
                        FilterChip(
                            selected = isChosen,
                            onClick = { viewModel.selectChatRole(role) },
                            label = { Text(role.name, style = MaterialTheme.typography.labelSmall) },
                            leadingIcon = {
                                Icon(
                                    imageVector = when (role.id) {
                                        "role_architect" -> Icons.Default.AccountTree
                                        "role_mentor" -> Icons.Default.School
                                        "role_speedy" -> Icons.Default.Bolt
                                        else -> Icons.Default.RecordVoiceOver
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                            },
                            modifier = Modifier.testTag("role_chip_${role.id}"),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SkyBlueContainer,
                                selectedLabelColor = SkyBlueDark
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Suggested Prompt Starters
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            val suggestions = listOf(
                "Explain how Kafka ensures message ordering",
                "Design a distributed rate limiter in Redis",
                "Why use custom React hooks instead of utilities?",
                "Interview me: Senior Full-Stack Engineer"
            )
            items(suggestions) { promptText ->
                SuggestionChip(
                    onClick = { viewModel.updateChatInput(promptText) },
                    label = { Text(promptText, maxLines = 1, style = MaterialTheme.typography.labelSmall) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable Multi-Turn Chat Thread
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.chatMessages) { message ->
                GeminiChatBubble(
                    message = message,
                    onCopy = {
                        viewModel.showSnackbar("Message copied to clipboard")
                    }
                )
            }

            if (uiState.isChatGenerating) {
                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SkyBlueContainer)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = SkyBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Generating response with ${uiState.selectedModel}...",
                            style = MaterialTheme.typography.bodySmall,
                            color = SkyBlueDark
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Message Input Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.chatInput,
                onValueChange = { viewModel.updateChatInput(it) },
                placeholder = { Text("Ask Gemini (${uiState.selectedChatRole.name})...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("gemini_chat_input"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PureWhite,
                    unfocusedContainerColor = PureWhite,
                    focusedBorderColor = SkyBluePrimary,
                    unfocusedBorderColor = BorderSubtle
                ),
                maxLines = 4
            )
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                onClick = { viewModel.sendChatMessage() },
                modifier = Modifier.testTag("gemini_send_button"),
                containerColor = SkyBluePrimary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}

@Composable
fun ModelChip(
    name: String,
    modelId: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) SkyBluePrimary else SurfaceBackground,
        border = BorderStroke(1.dp, if (isSelected) SkyBluePrimary else BorderSubtle),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .testTag("model_chip_${modelId.replace('.', '_').replace('-', '_')}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else NavyDark
            )
        }
    }
}

@Composable
fun GeminiChatBubble(
    message: ChatMessage,
    onCopy: () -> Unit
) {
    val isUser = message.role == "user"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(SkyBluePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "Gemini AI",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(modifier = Modifier.widthIn(max = 300.dp)) {
            // Model attribution badge for assistant responses
            if (!isUser && message.modelUsed != null) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = SkyBlueSurface,
                    border = BorderStroke(1.dp, SkyBlueContainer),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = message.modelUsed,
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp,
                        color = SkyBlueDark,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isUser) 16.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 16.dp
                        )
                    )
                    .background(if (isUser) SkyBluePrimary else PureWhite)
                    .border(
                        width = 1.dp,
                        color = if (isUser) Color.Transparent else BorderSubtle,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isUser) Color.White else NavyDark,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.timestamp))
                        Text(
                            text = timeStr,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            color = if (isUser) Color.White.copy(alpha = 0.7f) else NavyMuted
                        )

                        if (!isUser) {
                            IconButton(
                                onClick = onCopy,
                                modifier = Modifier.size(18.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy message",
                                    tint = NavyMuted,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
