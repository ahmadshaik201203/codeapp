package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun CodingLabScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        // Problem selector chip row
        item {
            Text(
                text = "Select Problem",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.codingProblems) { prob ->
                    val isSelected = prob.id == uiState.selectedProblem.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectCodingProblem(prob) },
                        label = { Text(prob.title) },
                        modifier = Modifier.testTag("problem_chip_${prob.id}"),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SkyBlueContainer,
                            selectedLabelColor = SkyBlueDark
                        )
                    )
                }
            }
        }

        // Problem Description Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.selectedProblem.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (uiState.selectedProblem.difficulty == "Easy") SuccessContainer else WarningContainer
                        ) {
                            Text(
                                text = uiState.selectedProblem.difficulty,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (uiState.selectedProblem.difficulty == "Easy") SuccessEmerald else WarningAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.selectedProblem.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyText
                    )
                }
            }
        }

        // Interactive Code Editor
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CodeBg),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    // Terminal header bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1E293B))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(ErrorRose))
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(WarningAmber))
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(SuccessEmerald))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "solution.js • ${uiState.selectedProblem.language}",
                                style = MaterialTheme.typography.labelSmall,
                                color = SkyBlueLight,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Text(
                            text = "UTF-8",
                            style = MaterialTheme.typography.labelSmall,
                            color = NavyMuted
                        )
                    }

                    // Code input area
                    OutlinedTextField(
                        value = uiState.userCode,
                        onValueChange = { viewModel.updateUserCode(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .testTag("code_editor_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CodeBg,
                            unfocusedContainerColor = CodeBg,
                            focusedTextColor = CodeFg,
                            unfocusedTextColor = CodeFg,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        ),
                        minLines = 8
                    )
                }
            }
        }

        // Actions: Run Test Cases & AI Code Review
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.runCode() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("run_code_button"),
                    enabled = !uiState.isRunningCode,
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                ) {
                    if (uiState.isRunningCode) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Running...")
                    } else {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Run")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Run Code")
                    }
                }

                Button(
                    onClick = { viewModel.requestAICodeReview() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_code_review_button"),
                    enabled = !uiState.isReviewingCode,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D28D9))
                ) {
                    if (uiState.isReviewingCode) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyzing...")
                    } else {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = "AI Review")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI Review")
                    }
                }
            }
        }

        // Execution Output Window
        if (uiState.codeOutput.isNotBlank()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF020617)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Terminal, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Execution Console", style = MaterialTheme.typography.labelSmall, color = SuccessEmerald, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.codeOutput,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace,
                            color = CodeFg,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // AI Code Review Output (Section 14: Correctness, Time Complexity, Space Complexity, Issues, Suggestion)
        if (uiState.aiCodeReview != null) {
            item {
                val review = uiState.aiCodeReview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFC4B5FD)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.RateReview, contentDescription = "Review", tint = Color(0xFF6D28D9))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("AI Code Review Report", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Correctness:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(review.correctness, style = MaterialTheme.typography.bodySmall, color = SuccessEmerald)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Time Complexity:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(review.timeComplexity, style = MaterialTheme.typography.bodySmall, color = SkyBluePrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Space Complexity:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(review.spaceComplexity, style = MaterialTheme.typography.bodySmall, color = NavyMuted)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Issues & Observations:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        review.issues.forEach { issue ->
                            Text("• $issue", style = MaterialTheme.typography.bodySmall, color = NavyText)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Optimization Suggestion:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        Text(review.suggestion, style = MaterialTheme.typography.bodySmall, color = NavyText)
                    }
                }
            }
        }
    }
}
