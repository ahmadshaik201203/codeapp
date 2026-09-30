package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialData
import com.example.data.model.MilestoneStatus
import com.example.ui.components.MetricCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.ScreenTab
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var showCareerDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        // Welcome Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SkyBluePrimary, SkyBlueDark)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Welcome back, ${uiState.userProfile.name} 👋",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Target Role: ${uiState.userProfile.careerGoal}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = SkyBlueSurface
                                )
                            }
                            IconButton(
                                onClick = { showCareerDialog = true },
                                modifier = Modifier
                                    .testTag("change_career_button")
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Change Career Goal",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Job Readiness Progress Bar
                        Text(
                            text = "Career Readiness Score: ${uiState.userProfile.readinessScore}%",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { uiState.userProfile.readinessScore / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = SuccessEmerald,
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Learn • Build • Practice • Get Job Ready",
                            style = MaterialTheme.typography.bodySmall,
                            color = SkyBlueContainer
                        )
                    }
                }
            }
        }

        // Adaptive Engine Status Banner
        if (uiState.adaptiveStatusMessage.isNotBlank()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = WarningContainer),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(WarningAmber))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoMode,
                            contentDescription = "Adaptive Engine",
                            tint = WarningAmber,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ML Adaptive Engine",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Text(
                                text = uiState.adaptiveStatusMessage,
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyText
                            )
                        }
                    }
                }
            }
        }

        // Key Metrics
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    title = "Skill Mastery",
                    value = "${uiState.userProfile.currentSkillScore}%",
                    subtitle = "ML Evaluated",
                    icon = Icons.Default.Psychology,
                    accentColor = SkyBluePrimary,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Live Streak",
                    value = "${uiState.userProfile.streak} Days",
                    subtitle = "Consistent",
                    icon = Icons.Default.LocalFireDepartment,
                    accentColor = WarningAmber,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Quick Launch Hub
        item {
            Text(
                text = "Core Learning & Building Hub",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // AgentForge Studio Card (Hero Action)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(ScreenTab.AGENT_FORGE) }
                        .testTag("home_agentforge_card"),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueLight))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SkyBlueContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = "AgentForge",
                                tint = SkyBluePrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "AgentForge Multi-Agent Studio",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "NEW",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SkyBluePrimary,
                                    modifier = Modifier
                                        .background(SkyBlueSurface, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Autonomous Planner, Architect, Coder, Validator & Debugger engine",
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyMuted
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = NavyMuted
                        )
                    }
                }

                // Coding Lab Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(ScreenTab.CODING_LAB) }
                        .testTag("home_coding_lab_card"),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SuccessContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = "Coding Lab",
                                tint = SuccessEmerald,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Coding Lab & AI Code Review",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Text(
                                text = "Solve DSA problems with instant test runner and time complexity reviews",
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyMuted
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = NavyMuted
                        )
                    }
                }

                // AI Mentor Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(ScreenTab.AI_MENTOR) }
                        .testTag("home_ai_mentor_card"),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFEDE9FE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Mentor",
                                tint = Color(0xFF6D28D9),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "AI Mentor & Tutor",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Text(
                                text = "Contextual assistance: Explain, Debug, Quiz Me, and Career Guidance",
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyMuted
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = NavyMuted
                        )
                    }
                }

                // Mock Interview Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(ScreenTab.MOCK_INTERVIEW) }
                        .testTag("home_mock_interview_card"),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(WarningContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = "Mock Interview",
                                tint = WarningAmber,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "AI Mock Interview",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Text(
                                text = "Technical, HR & Behavioral practice with instant scoring & feedback",
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyMuted
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = NavyMuted
                        )
                    }
                }
            }
        }

        // Active Roadmap Highlights
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Personalized Roadmap Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )
                TextButton(onClick = { viewModel.navigateTo(ScreenTab.ROADMAP) }) {
                    Text("View All", color = SkyBluePrimary)
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                uiState.milestones.take(4).forEach { milestone ->
                    val (statusColor, statusIcon) = when (milestone.status) {
                        MilestoneStatus.COMPLETED -> Pair(SuccessEmerald, Icons.Default.CheckCircle)
                        MilestoneStatus.IN_PROGRESS -> Pair(SkyBluePrimary, Icons.Default.Pending)
                        MilestoneStatus.RECOMMENDED -> Pair(WarningAmber, Icons.Default.Star)
                        MilestoneStatus.ADVANCED -> Pair(Color(0xFF6D28D9), Icons.Default.WorkspacePremium)
                        MilestoneStatus.LOCKED -> Pair(NavyMuted, Icons.Default.Lock)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = statusIcon,
                                contentDescription = milestone.status.name,
                                tint = statusColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = milestone.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = NavyDark
                                )
                                Text(
                                    text = "${milestone.domain} • ${milestone.estimatedHours} hrs",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NavyMuted
                                )
                            }
                            Text(
                                text = milestone.status.name.replace("_", " "),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        }
                    }
                }
            }
        }
    }

    // Change Career Dialog
    if (showCareerDialog) {
        AlertDialog(
            onDismissRequest = { showCareerDialog = false },
            title = { Text("Select Target Career Path", color = NavyDark, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    InitialData.careerOptions.forEach { career ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setCareerGoal(career)
                                    showCareerDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.userProfile.careerGoal == career,
                                onClick = {
                                    viewModel.setCareerGoal(career)
                                    showCareerDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(career, style = MaterialTheme.typography.bodyMedium, color = NavyDark)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCareerDialog = false }) {
                    Text("Close", color = SkyBluePrimary)
                }
            }
        )
    }
}
