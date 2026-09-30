package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun JobReadinessScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    val overallReadiness = uiState.userProfile.readinessScore

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
                Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Job Readiness Dashboard",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$overallReadiness%",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = SkyBluePrimary
                    )
                    Text(
                        text = "Overall Placement Ready Index",
                        style = MaterialTheme.typography.labelMedium,
                        color = NavyMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    LinearProgressIndicator(
                        progress = { overallReadiness / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = SuccessEmerald,
                        trackColor = SkyBlueContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Documented Formula: Technical (30%) + Projects (20%) + DSA (15%) + Git (10%) + Resume (10%) + Comm (5%) + Interview (10%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyMuted
                    )
                }
            }
        }

        item {
            Text(
                text = "Scoring Breakdown & Weights",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ReadinessMetricRow("Technical Domain Skills", 80, 30, Icons.Default.Computer, SkyBluePrimary)
                ReadinessMetricRow("Real-World Projects (AgentForge)", 85, 20, Icons.Default.Layers, SuccessEmerald)
                ReadinessMetricRow("Data Structures & Algorithms", 75, 15, Icons.Default.Code, WarningAmber)
                ReadinessMetricRow("Git & GitHub Portfolio", 90, 10, Icons.Default.FolderSpecial, Color(0xFF6D28D9))
                ReadinessMetricRow("Resume Quality & ATS Match", 88, 10, Icons.Default.Description, SkyBlueDark)
                ReadinessMetricRow("Communication & Soft Skills", 80, 5, Icons.Default.Forum, Color(0xFF0D9488))
                ReadinessMetricRow("AI Mock Interview Score", 82, 10, Icons.Default.RecordVoiceOver, WarningAmber)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recommended Placement Actions",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. Solve 3 more Graph DSA problems in Coding Lab to boost DSA weight above 85%", style = MaterialTheme.typography.bodySmall, color = NavyText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("2. Build an autonomous multi-file backend using AgentForge to enhance Project portfolio", style = MaterialTheme.typography.bodySmall, color = NavyText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("3. Complete one Behavioral Mock Interview focusing on production incidents", style = MaterialTheme.typography.bodySmall, color = NavyText)
                }
            }
        }
    }
}

@Composable
fun ReadinessMetricRow(
    title: String,
    score: Int,
    weight: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = NavyDark)
                    Text("$score%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = color)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { score / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = color,
                    trackColor = BorderSubtle
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text("Weight in Total Readiness: $weight%", style = MaterialTheme.typography.labelSmall, color = NavyMuted)
            }
        }
    }
}
