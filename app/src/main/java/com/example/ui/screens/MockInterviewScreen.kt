package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun MockInterviewScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    val question = uiState.interviewQuestions[uiState.currentInterviewIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
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
                            text = "AI Mock Interview",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarningContainer
                        ) {
                            Text(
                                text = "Question ${uiState.currentInterviewIndex + 1} of ${uiState.interviewQuestions.size}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Real-time AI behavioral & technical interviewer evaluating accuracy, structure, and communication.",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyMuted
                    )
                }
            }
        }

        // Question Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueLight))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = SkyBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${question.category} Interview (${question.difficulty})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SkyBlueDark
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Key Evaluation Criteria:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    question.keyPoints.forEach { pt ->
                        Text("• $pt", style = MaterialTheme.typography.bodySmall, color = NavyText)
                    }
                }
            }
        }

        // Student Answer Input
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Answer (Type or Dictate):",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.interviewAnswerInput,
                        onValueChange = { viewModel.updateInterviewAnswer(it) },
                        placeholder = { Text("Structure your response using STAR or key technical concepts...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("interview_answer_input"),
                        minLines = 4,
                        maxLines = 8,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.submitInterviewAnswer() },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("submit_interview_button"),
                            enabled = !uiState.isEvaluatingInterview && uiState.interviewAnswerInput.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                        ) {
                            if (uiState.isEvaluatingInterview) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Evaluating...")
                            } else {
                                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Submit")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Evaluate Answer")
                            }
                        }

                        OutlinedButton(
                            onClick = { viewModel.nextInterviewQuestion() },
                            modifier = Modifier.testTag("next_question_button")
                        ) {
                            Text("Next Question")
                        }
                    }
                }
            }
        }

        // Interview Evaluation Card
        if (uiState.interviewEvaluation != null) {
            item {
                val eval = uiState.interviewEvaluation
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SuccessEmerald))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Assessment, contentDescription = null, tint = SuccessEmerald)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AI Evaluation & Placement Scoring",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ScoreBox("Technical", "${eval.technicalAccuracy}%")
                            ScoreBox("Relevance", "${eval.relevance}%")
                            ScoreBox("Structure", "${eval.structure}%")
                            ScoreBox("Communication", "${eval.communication}%")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Feedback:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        Text(eval.feedback, style = MaterialTheme.typography.bodySmall, color = NavyText)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Placement Recommendation:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        Text(eval.recommendation, style = MaterialTheme.typography.bodySmall, color = SkyBlueDark)
                    }
                }
            }
        }
    }
}
