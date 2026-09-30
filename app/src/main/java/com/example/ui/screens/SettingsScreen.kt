package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun SettingsScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var revVal by remember { mutableFloatStateOf(uiState.adaptiveConfig.revisionThreshold.toFloat()) }
    var pracVal by remember { mutableFloatStateOf(uiState.adaptiveConfig.practiceThreshold.toFloat()) }
    var normVal by remember { mutableFloatStateOf(uiState.adaptiveConfig.normalThreshold.toFloat()) }

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
                        text = "Adaptive Learning Thresholds (Admin CMS)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Customize the dynamic trigger scores that determine whether the system prescribes revision, extra practice, or advanced topic skips.",
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Revision Threshold (< ${revVal.toInt()}%):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = ErrorRose)
                    Slider(
                        value = revVal,
                        onValueChange = { revVal = it },
                        valueRange = 30f..60f,
                        steps = 5,
                        modifier = Modifier.testTag("slider_revision")
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Practice Problems Threshold (${pracVal.toInt()}%):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = WarningAmber)
                    Slider(
                        value = pracVal,
                        onValueChange = { pracVal = it },
                        valueRange = 50f..80f,
                        steps = 5,
                        modifier = Modifier.testTag("slider_practice")
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Advanced Mastery Skip Threshold (> ${normVal.toInt()}%):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SuccessEmerald)
                    Slider(
                        value = normVal,
                        onValueChange = { normVal = it },
                        valueRange = 75f..95f,
                        steps = 4,
                        modifier = Modifier.testTag("slider_normal")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.updateAdaptiveThresholds(revVal.toInt(), pracVal.toInt(), normVal.toInt())
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_thresholds_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Thresholds to Engine")
                    }
                }
            }
        }

        // Security & API Key Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI Studio Secret & Environment Status",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("GEMINI_API_KEY", style = MaterialTheme.typography.bodySmall, color = NavyText)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SuccessContainer
                        ) {
                            Text(
                                text = "Configured (Secrets)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SuccessEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Fallback Offline Engine", style = MaterialTheme.typography.bodySmall, color = NavyText)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SkyBlueContainer
                        ) {
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
