package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.InitialData
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun CollegeFacultyScreen(
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
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.School, contentDescription = null, tint = SkyBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "College & Faculty Analytics Portal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Department: Computer Science & Engineering • 2026 Batch",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyMuted
                    )
                }
            }
        }

        // Summary Statistics Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CollegeStatCard("Total Enrolled", "128", SkyBluePrimary, Modifier.weight(1f))
                CollegeStatCard("Placement Ready", "74%", SuccessEmerald, Modifier.weight(1f))
                CollegeStatCard("Weak Skill Alert", "18", WarningAmber, Modifier.weight(1f))
            }
        }

        item {
            Text(
                text = "Student Skill Cohort Monitoring",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
        }

        items(InitialData.facultyStudentStats) { student ->
            val statusColor = when (student.status) {
                "Ready for Placements", "Top Performer" -> SuccessEmerald
                "Needs Mentorship" -> ErrorRose
                "Revision Triggered" -> WarningAmber
                else -> SkyBluePrimary
            }

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
                        Column {
                            Text(student.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDark)
                            Text(student.careerGoal, style = MaterialTheme.typography.labelSmall, color = SkyBlueDark)
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = statusColor.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = student.status,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = statusColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Skill Index: ${student.currentSkillScore}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                        Text("Mock Interview: ${student.interviewScore}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = NavyDark)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Weak Skills Detected:", style = MaterialTheme.typography.labelSmall, color = NavyMuted)
                    Text(student.weakSkills.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = ErrorRose)
                }
            }
        }
    }
}

@Composable
fun CollegeStatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = color)
            Text(title, style = MaterialTheme.typography.labelSmall, color = NavyMuted)
        }
    }
}
