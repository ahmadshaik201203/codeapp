package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.ProjectItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun ProjectLabScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var selectedProject by remember { mutableStateOf(uiState.realWorldProjects.first()) }
    var githubInput by remember { mutableStateOf(selectedProject.githubUrl) }
    var showSubmitDialog by remember { mutableStateOf(false) }

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
                    Text(
                        text = "Project Lab & AI Review",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Build real-world multi-tier applications, submit repositories, and receive comprehensive assistive AI architectural reviews.",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyText
                    )
                }
            }
        }

        // Project selector chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.realWorldProjects) { proj ->
                    val isSelected = proj.id == selectedProject.id
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedProject = proj
                            githubInput = proj.githubUrl
                        },
                        label = { Text(proj.title) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SkyBlueContainer,
                            selectedLabelColor = SkyBlueDark
                        )
                    )
                }
            }
        }

        // Selected Project Details
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
                            text = selectedProject.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when (selectedProject.difficulty) {
                                "Beginner" -> SuccessContainer
                                "Intermediate" -> WarningContainer
                                else -> Color(0xFFEDE9FE)
                            }
                        ) {
                            Text(
                                text = selectedProject.difficulty,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = when (selectedProject.difficulty) {
                                    "Beginner" -> SuccessEmerald
                                    "Intermediate" -> WarningAmber
                                    else -> Color(0xFF6D28D9)
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedProject.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyText
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Tech Stack:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(selectedProject.techStack) { tech ->
                            SuggestionChip(
                                onClick = {},
                                label = { Text(tech, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Key Requirements:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    selectedProject.requirements.forEach { req ->
                        Text("• $req", style = MaterialTheme.typography.bodySmall, color = NavyText)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = githubInput,
                        onValueChange = { githubInput = it },
                        label = { Text("GitHub Repository URL") },
                        placeholder = { Text("https://github.com/username/project") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.showSnackbar("Project submission received! AI Review synthesized.")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_project_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Submit")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Submit Project for AI Review")
                    }
                }
            }
        }

        // AI Project Review Card
        if (selectedProject.review != null) {
            item {
                val review = selectedProject.review!!
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SuccessEmerald))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = SuccessEmerald)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AI Assistive Project Evaluation",
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
                            ScoreBox("Architecture", "${review.architectureScore}%")
                            ScoreBox("Code Quality", "${review.codeQualityScore}%")
                            ScoreBox("Documentation", "${review.documentationScore}%")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Key Strengths:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        review.strengths.forEach { s ->
                            Text("✓ $s", style = MaterialTheme.typography.bodySmall, color = SuccessEmerald)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Areas for Improvement:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        review.improvements.forEach { imp ->
                            Text("• $imp", style = MaterialTheme.typography.bodySmall, color = WarningAmber)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = review.summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = NavyMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreBox(label: String, score: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceBackground)
            .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(score, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SkyBluePrimary)
        Text(label, style = MaterialTheme.typography.labelSmall, color = NavyMuted)
    }
}
