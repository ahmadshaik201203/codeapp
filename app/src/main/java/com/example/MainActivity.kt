package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CodeVerseTopBar
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.ScreenTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CodeVerseApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeVerseApp(
    viewModel: CodeVerseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showMoreSheet by remember { mutableStateOf(false) }

    // Handle system back button: return to Home tab if on another screen
    BackHandler(enabled = uiState.currentTab != ScreenTab.HOME) {
        viewModel.navigateTo(ScreenTab.HOME)
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CodeVerseTopBar(
                title = when (uiState.currentTab) {
                    ScreenTab.HOME -> "Career Hub"
                    ScreenTab.ROADMAP -> "Personalized Path"
                    ScreenTab.COURSES -> "Courses & Lessons"
                    ScreenTab.CODING_LAB -> "Coding Lab & DSA"
                    ScreenTab.AI_MENTOR -> "Gemini Chatbot"
                    ScreenTab.PROJECTS -> "Project Lab"
                    ScreenTab.MOCK_INTERVIEW -> "Mock Interview"
                    ScreenTab.RESUME_JOBS -> "Resume & Job Matcher"
                    ScreenTab.JOB_READINESS -> "Job Readiness"
                    ScreenTab.AGENT_FORGE -> "AgentForge Multi-Agent"
                    ScreenTab.ASSESSMENT -> "Skill Assessment"
                    ScreenTab.COLLEGE_FACULTY -> "Faculty Analytics"
                    ScreenTab.SETTINGS -> "Settings & CMS"
                },
                xp = uiState.userProfile.xp,
                streak = uiState.userProfile.streak,
                onOpenProfile = { viewModel.navigateTo(ScreenTab.SETTINGS) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PureWhite,
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.HOME,
                    onClick = { viewModel.navigateTo(ScreenTab.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    modifier = Modifier.testTag("nav_home"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.ROADMAP,
                    onClick = { viewModel.navigateTo(ScreenTab.ROADMAP) },
                    icon = { Icon(Icons.Default.Timeline, contentDescription = "Roadmap") },
                    label = { Text("Roadmap") },
                    modifier = Modifier.testTag("nav_roadmap"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.CODING_LAB,
                    onClick = { viewModel.navigateTo(ScreenTab.CODING_LAB) },
                    icon = { Icon(Icons.Default.Code, contentDescription = "Coding") },
                    label = { Text("Coding") },
                    modifier = Modifier.testTag("nav_coding"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.AGENT_FORGE,
                    onClick = { viewModel.navigateTo(ScreenTab.AGENT_FORGE) },
                    icon = { Icon(Icons.Default.SmartToy, contentDescription = "AgentForge") },
                    label = { Text("AgentForge") },
                    modifier = Modifier.testTag("nav_agentforge"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentTab == ScreenTab.AI_MENTOR,
                    onClick = { viewModel.navigateTo(ScreenTab.AI_MENTOR) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Gemini AI") },
                    label = { Text("Gemini AI") },
                    modifier = Modifier.testTag("nav_mentor"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = showMoreSheet,
                    onClick = { showMoreSheet = true },
                    icon = { Icon(Icons.Default.GridView, contentDescription = "More") },
                    label = { Text("More") },
                    modifier = Modifier.testTag("nav_more"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SkyBluePrimary,
                        selectedTextColor = SkyBluePrimary,
                        indicatorColor = SkyBlueContainer
                    )
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                ScreenTab.HOME -> HomeScreen(uiState, viewModel)
                ScreenTab.ROADMAP -> RoadmapScreen(uiState, viewModel)
                ScreenTab.COURSES -> CoursesScreen(uiState, viewModel)
                ScreenTab.CODING_LAB -> CodingLabScreen(uiState, viewModel)
                ScreenTab.AI_MENTOR -> AIMentorScreen(uiState, viewModel)
                ScreenTab.PROJECTS -> ProjectLabScreen(uiState, viewModel)
                ScreenTab.MOCK_INTERVIEW -> MockInterviewScreen(uiState, viewModel)
                ScreenTab.RESUME_JOBS -> ResumeJobScreen(uiState, viewModel)
                ScreenTab.JOB_READINESS -> JobReadinessScreen(uiState, viewModel)
                ScreenTab.AGENT_FORGE -> AgentForgeScreen(uiState, viewModel)
                ScreenTab.ASSESSMENT -> AssessmentScreen(uiState, viewModel)
                ScreenTab.COLLEGE_FACULTY -> CollegeFacultyScreen(uiState, viewModel)
                ScreenTab.SETTINGS -> SettingsScreen(uiState, viewModel)
            }
        }
    }

    // Secondary Feature Bottom Sheet
    if (showMoreSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMoreSheet = false },
            containerColor = PureWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "All CodeVerse Features",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    MoreFeatureItem("Courses", Icons.AutoMirrored.Filled.MenuBook, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.COURSES)
                        showMoreSheet = false
                    }
                    MoreFeatureItem("Projects", Icons.Default.Work, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.PROJECTS)
                        showMoreSheet = false
                    }
                    MoreFeatureItem("Interview", Icons.Default.RecordVoiceOver, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.MOCK_INTERVIEW)
                        showMoreSheet = false
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    MoreFeatureItem("Resume & Jobs", Icons.AutoMirrored.Filled.Article, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.RESUME_JOBS)
                        showMoreSheet = false
                    }
                    MoreFeatureItem("Readiness", Icons.Default.Speed, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.JOB_READINESS)
                        showMoreSheet = false
                    }
                    MoreFeatureItem("Assessment", Icons.Default.Psychology, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.ASSESSMENT)
                        showMoreSheet = false
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    MoreFeatureItem("Faculty Portal", Icons.Default.School, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.COLLEGE_FACULTY)
                        showMoreSheet = false
                    }
                    MoreFeatureItem("Settings / CMS", Icons.Default.Tune, Modifier.weight(1f)) {
                        viewModel.navigateTo(ScreenTab.SETTINGS)
                        showMoreSheet = false
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun MoreFeatureItem(title: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SkyBlueContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = SkyBluePrimary, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = NavyDark,
            fontWeight = FontWeight.Medium
        )
    }
}
