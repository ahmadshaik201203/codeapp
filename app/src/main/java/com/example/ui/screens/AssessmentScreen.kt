package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
fun AssessmentScreen(
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
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueLight))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Technical Skill Assessment",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tests programming fundamentals, domain knowledge, and problem solving. Results feed the ML Skill Analysis Engine to personalize your Roadmap.",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyMuted
                    )
                }
            }
        }

        // ML Skill Analysis Result Card
        if (uiState.assessmentScore != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SuccessEmerald))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = SuccessEmerald)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ML Skill Analysis Output",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ML Skill Score:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text("${uiState.assessmentScore}%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = SkyBluePrimary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Strong Skills:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SuccessEmerald)
                        Text(
                            text = if (uiState.assessmentStrongSkills.isNotEmpty()) uiState.assessmentStrongSkills.joinToString(", ") else "Developing...",
                            style = MaterialTheme.typography.bodySmall,
                            color = NavyText
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Weak Skills / Gaps Detected:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = WarningAmber)
                        Text(
                            text = if (uiState.assessmentWeakSkills.isNotEmpty()) uiState.assessmentWeakSkills.joinToString(", ") else "None! Excellent grasp.",
                            style = MaterialTheme.typography.bodySmall,
                            color = NavyText
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Adaptive Recommendation:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SkyBlueDark)
                        Text(uiState.adaptiveStatusMessage, style = MaterialTheme.typography.bodySmall, color = NavyDark)
                    }
                }
            }
        }

        // Assessment Questions
        items(uiState.assessmentQuestions) { q ->
            val userSelected = uiState.assessmentAnswers[q.id]
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SkyBlueSurface
                    ) {
                        Text(
                            text = "${q.category} • ${q.careerTag}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SkyBluePrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Q${q.id}: ${q.question}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    q.options.forEachIndexed { idx, opt ->
                        val isChosen = userSelected == idx
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { viewModel.selectAssessmentAnswer(q.id, idx) }
                                .background(if (isChosen) SkyBlueContainer else SurfaceBackground)
                                .border(1.dp, if (isChosen) SkyBluePrimary else BorderSubtle, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "${('A' + idx)}. $opt",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isChosen) SkyBlueDark else NavyDark,
                                fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.submitAssessment() },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("submit_assessment_button"),
                enabled = uiState.assessmentAnswers.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Submit")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit & Run ML Skill Analysis (${uiState.assessmentAnswers.size}/${uiState.assessmentQuestions.size})")
            }
        }
    }
}
