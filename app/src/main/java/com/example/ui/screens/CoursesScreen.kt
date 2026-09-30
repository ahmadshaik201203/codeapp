package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.Lesson
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun CoursesScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var selectedCourse by remember { mutableStateOf(uiState.courses.first()) }
    var selectedLesson by remember { mutableStateOf(uiState.courses.first().lessons.first()) }
    var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        // Course selector row
        item {
            Text(
                text = "Course Curriculum",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.courses) { course ->
                    val isSelected = course.id == selectedCourse.id
                    Card(
                        modifier = Modifier
                            .width(260.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                selectedCourse = course
                                selectedLesson = course.lessons.first()
                                selectedQuizOption = null
                                quizSubmitted = false
                            },
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) SkyBlueContainer else PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(
                            width = 1.dp,
                            brush = androidx.compose.ui.graphics.SolidColor(if (isSelected) SkyBluePrimary else BorderSubtle)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = course.domain,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = course.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { course.progressPercent / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = SkyBluePrimary,
                                trackColor = BorderSubtle
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${course.progressPercent}% Completed • ${course.lessonCount} Lessons",
                                style = MaterialTheme.typography.labelSmall,
                                color = NavyMuted
                            )
                        }
                    }
                }
            }
        }

        // Lesson Selector within selected course
        item {
            Text(
                text = "Modules & Lessons",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(selectedCourse.lessons) { lsn ->
                    val isSelected = lsn.id == selectedLesson.id
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedLesson = lsn
                            selectedQuizOption = null
                            quizSubmitted = false
                        },
                        label = { Text(lsn.title) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SkyBluePrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Lesson Content Card
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
                            text = selectedLesson.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SkyBlueSurface
                        ) {
                            Text(
                                text = "${selectedLesson.durationMinutes} min",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SkyBluePrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Video Player Preview Placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.PlayCircleFilled,
                                contentDescription = "Play Video",
                                tint = SkyBlueLight,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Interactive HD Video Lesson & Transcripts",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Lesson Overview",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedLesson.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Code Implementation",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = CodeBg,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedLesson.codeExample,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = CodeFg,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Lesson Checkpoint Quiz
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = WarningAmber)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Checkpoint Quiz",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedLesson.quizQuestion,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = NavyDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    selectedLesson.quizOptions.forEachIndexed { index, opt ->
                        val isChosen = selectedQuizOption == index
                        val isCorrect = index == selectedLesson.quizCorrectIndex
                        val btnBg = when {
                            quizSubmitted && isCorrect -> SuccessContainer
                            quizSubmitted && isChosen && !isCorrect -> ErrorContainer
                            isChosen -> SkyBlueContainer
                            else -> SurfaceBackground
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    if (!quizSubmitted) selectedQuizOption = index
                                }
                                .background(btnBg)
                                .border(1.dp, if (isChosen) SkyBluePrimary else BorderSubtle, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "${('A' + index)}. $opt",
                                style = MaterialTheme.typography.bodySmall,
                                color = NavyDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (selectedQuizOption != null) {
                                quizSubmitted = true
                                if (selectedQuizOption == selectedLesson.quizCorrectIndex) {
                                    viewModel.addXP(40)
                                    viewModel.showSnackbar("Correct! +40 XP added!")
                                } else {
                                    viewModel.showSnackbar("Incorrect. Review lesson summary and retry.")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_quiz_button"),
                        enabled = selectedQuizOption != null && !quizSubmitted,
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                    ) {
                        Text("Verify Answer")
                    }
                }
            }
        }
    }
}
