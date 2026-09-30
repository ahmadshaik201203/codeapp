package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.data.model.MilestoneStatus
import com.example.data.model.RoadmapMilestone
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun RoadmapScreen(
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
                colors = CardDefaults.cardColors(containerColor = SkyBlueContainer),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueLight))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "Roadmap",
                            tint = SkyBlueDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${uiState.userProfile.careerGoal} Roadmap",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Dynamic AI & ML driven pathway generated from skill gaps, prerequisites, and learning velocity.",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyText
                    )
                }
            }
        }

        // Adaptive Engine Rule Explainer Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Adaptive Learning Engine Rules",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AdaptiveRuleBadge("< 50%", "Revision", ErrorRose, ErrorContainer)
                        AdaptiveRuleBadge("50–70%", "Practice", WarningAmber, WarningContainer)
                        AdaptiveRuleBadge("70–85%", "Normal", SkyBluePrimary, SkyBlueContainer)
                        AdaptiveRuleBadge("> 85%", "Advanced", SuccessEmerald, SuccessContainer)
                    }
                }
            }
        }

        items(uiState.milestones) { milestone ->
            RoadmapMilestoneCard(
                milestone = milestone,
                onStatusChange = { newStatus ->
                    viewModel.updateMilestoneStatus(milestone.id, newStatus)
                }
            )
        }
    }
}

@Composable
fun AdaptiveRuleBadge(range: String, action: String, fg: Color, bg: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(range, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = fg)
        Text(action, style = MaterialTheme.typography.labelSmall, color = NavyDark)
    }
}

@Composable
fun RoadmapMilestoneCard(
    milestone: RoadmapMilestone,
    onStatusChange: (MilestoneStatus) -> Unit
) {
    val (statusColor, statusBg, statusIcon) = when (milestone.status) {
        MilestoneStatus.COMPLETED -> Triple(SuccessEmerald, SuccessContainer, Icons.Default.CheckCircle)
        MilestoneStatus.IN_PROGRESS -> Triple(SkyBluePrimary, SkyBlueContainer, Icons.Default.PlayArrow)
        MilestoneStatus.RECOMMENDED -> Triple(WarningAmber, WarningContainer, Icons.Default.Star)
        MilestoneStatus.ADVANCED -> Triple(Color(0xFF6D28D9), Color(0xFFEDE9FE), Icons.Default.WorkspacePremium)
        MilestoneStatus.LOCKED -> Triple(NavyMuted, BorderSubtle, Icons.Default.Lock)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .testTag("milestone_${milestone.id}"),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(statusBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = milestone.status.name,
                        tint = statusColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Step ${milestone.orderIndex}: ${milestone.title}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Text(
                        text = "${milestone.domain} • ~${milestone.estimatedHours} hrs",
                        style = MaterialTheme.typography.labelSmall,
                        color = NavyMuted
                    )
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusBg,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(
                        text = milestone.status.name.replace("_", " "),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = milestone.description,
                style = MaterialTheme.typography.bodySmall,
                color = NavyText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action row to simulate completing/advancing
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (milestone.status != MilestoneStatus.COMPLETED) {
                    FilledTonalButton(
                        onClick = { onStatusChange(MilestoneStatus.COMPLETED) },
                        modifier = Modifier.testTag("complete_milestone_${milestone.id}"),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = SuccessContainer, contentColor = SuccessEmerald)
                    ) {
                        Text("Mark Completed", style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (milestone.status != MilestoneStatus.ADVANCED) {
                    OutlinedButton(
                        onClick = { onStatusChange(MilestoneStatus.ADVANCED) },
                        modifier = Modifier.testTag("advance_milestone_${milestone.id}")
                    ) {
                        Text("Unlock Advanced", style = MaterialTheme.typography.labelSmall, color = SkyBluePrimary)
                    }
                }
            }
        }
    }
}
