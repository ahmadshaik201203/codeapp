package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.JobApplication
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun ResumeJobScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var activeSubTab by remember { mutableIntStateOf(0) } // 0: Resume & ATS, 1: JD Matcher, 2: Application Tracker
    var newCompany by remember { mutableStateOf("") }
    var newRole by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        item {
            TabRow(
                selectedTabIndex = activeSubTab,
                containerColor = PureWhite,
                contentColor = SkyBluePrimary
            ) {
                Tab(
                    selected = activeSubTab == 0,
                    onClick = { activeSubTab = 0 },
                    text = { Text("Resume & ATS") }
                )
                Tab(
                    selected = activeSubTab == 1,
                    onClick = { activeSubTab = 1 },
                    text = { Text("JD Matcher") }
                )
                Tab(
                    selected = activeSubTab == 2,
                    onClick = { activeSubTab = 2 },
                    text = { Text("Applications (${uiState.jobApplications.size})") }
                )
            }
        }

        when (activeSubTab) {
            0 -> {
                // Resume Builder & ATS Analyzer
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
                                Column {
                                    Text(
                                        text = uiState.resumeData.fullName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyDark
                                    )
                                    Text(
                                        text = "${uiState.resumeData.email} • ${uiState.resumeData.phone}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = NavyMuted
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SuccessContainer
                                ) {
                                    Text(
                                        text = "ATS: 88%",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessEmerald,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Professional Summary:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(uiState.resumeData.summary, style = MaterialTheme.typography.bodySmall, color = NavyText)

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Technical Skills:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(uiState.resumeData.skills) { s ->
                                    SuggestionChip(onClick = {}, label = { Text(s) })
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Featured Projects:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(uiState.resumeData.projects, style = MaterialTheme.typography.bodySmall, color = NavyText)

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = { viewModel.showSnackbar("Resume PDF Export generated & saved to downloads!") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                            ) {
                                Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = "PDF")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Export Resume PDF")
                            }
                        }
                    }
                }
            }

            1 -> {
                // Job Description Analyzer
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Paste Job Description to Analyze",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = uiState.jdTextInput,
                                onValueChange = { viewModel.updateJDInput(it) },
                                placeholder = { Text("Paste requirements, responsibilities, or tech stack requirements from LinkedIn/Indeed...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("jd_input_field"),
                                minLines = 4,
                                maxLines = 6,
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { viewModel.analyzeJobDescription() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("analyze_jd_button"),
                                enabled = !uiState.isAnalyzingJD,
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                            ) {
                                if (uiState.isAnalyzingJD) {
                                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Analyzing Skill Match...")
                                } else {
                                    Icon(imageVector = Icons.Default.Troubleshoot, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Run Match Analysis")
                                }
                            }
                        }
                    }
                }

                if (uiState.jdAnalysisResult != null) {
                    val result = uiState.jdAnalysisResult
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SuccessEmerald))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = result.roleTitle,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyDark
                                    )
                                    Text(
                                        text = "${result.matchPercentage}% Match",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessEmerald
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text("Matched Skills (${result.matchedSkills.size}):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SuccessEmerald)
                                Text(result.matchedSkills.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = NavyText)

                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Missing / Gap Skills (${result.missingSkills.size}):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = ErrorRose)
                                Text(result.missingSkills.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = NavyText)

                                Spacer(modifier = Modifier.height(10.dp))
                                Text("Preparation Recommendations:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SkyBlueDark)
                                result.preparationRecommendations.forEach { rec ->
                                    Text("• $rec", style = MaterialTheme.typography.bodySmall, color = NavyText)
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // Application Tracker
                item {
                    Button(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("add_job_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Track New Job Application")
                    }
                }

                items(uiState.jobApplications) { app ->
                    val statusColor = when (app.status) {
                        "Interview" -> SuccessEmerald
                        "Assessment" -> WarningAmber
                        "Applied" -> SkyBluePrimary
                        "Selected" -> Color(0xFF6D28D9)
                        else -> NavyMuted
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(app.company, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                                Text(app.role, style = MaterialTheme.typography.bodySmall, color = NavyText)
                                Text("Applied on ${app.appliedDate}", style = MaterialTheme.typography.labelSmall, color = NavyMuted)
                                if (app.notes.isNotBlank()) {
                                    Text(app.notes, style = MaterialTheme.typography.bodySmall, color = SkyBlueDark)
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = statusColor.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = app.status,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = { viewModel.deleteJobApplication(app.id) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = NavyMuted)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Job Application", fontWeight = FontWeight.Bold, color = NavyDark) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newCompany,
                        onValueChange = { newCompany = it },
                        label = { Text("Company Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newRole,
                        onValueChange = { newRole = it },
                        label = { Text("Role Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newCompany.isNotBlank() && newRole.isNotBlank()) {
                            viewModel.addJobApplication(newCompany, newRole, "", "Applied")
                            newCompany = ""
                            newRole = ""
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
