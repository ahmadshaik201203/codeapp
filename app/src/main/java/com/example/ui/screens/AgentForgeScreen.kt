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
import androidx.compose.material.icons.automirrored.filled.*
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
import androidx.compose.foundation.BorderStroke
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.data.model.AgentFile
import com.example.data.model.AgentProject
import com.example.ui.components.AgentActivityItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.CodeVerseViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun AgentForgeScreen(
    uiState: UiState,
    viewModel: CodeVerseViewModel
) {
    var activeWorkspaceTab by remember { mutableIntStateOf(0) } // 0: Editor & Files, 1: Agent Activity, 2: Live Preview
    val project = uiState.agentProject

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        // AgentForge Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SkyBluePrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = "AgentForge",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "AgentForge Studio",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Multi-Agent AI Software Engineering",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SkyBlueLight
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (project.status == "READY") SuccessContainer else WarningContainer
                        ) {
                            Text(
                                text = project.status,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (project.status == "READY") SuccessEmerald else WarningAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Natural language requirements are planned, architected, coded file-by-file, validated, and debugged automatically by specialized agents.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        // Requirements Input Box
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Describe Software Requirements",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.agentPromptInput,
                        onValueChange = { viewModel.updateAgentPrompt(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("agent_prompt_input"),
                        placeholder = { Text("e.g. Build an Expense Tracker with React, charts, category filtering and local storage...") },
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceBackground,
                            unfocusedContainerColor = SurfaceBackground,
                            focusedBorderColor = SkyBluePrimary,
                            unfocusedBorderColor = BorderSubtle
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Preset prompt chips
                    Text(
                        text = "Preset Blueprints:",
                        style = MaterialTheme.typography.labelSmall,
                        color = NavyMuted
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        val presets = listOf(
                            "Calculator with arithmetic operations & responsive UI",
                            "Expense Tracker with categories and charts",
                            "Task Kanban Board with state persistence"
                        )
                        items(presets) { p ->
                            SuggestionChip(
                                onClick = { viewModel.updateAgentPrompt(p) },
                                label = { Text(p, maxLines = 1) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.generateAgentProject() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_agentforge_button"),
                        enabled = !uiState.isAgentGenerating,
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                    ) {
                        if (uiState.isAgentGenerating) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Agent Team In Action (${project.status})...")
                        } else {
                            Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = "Run")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Project with Multi-Agent Team")
                        }
                    }
                }
            }
        }

        // Project Generation Status & Agent Sequence
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Agent Collaboration Pipeline",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AgentPill("1. Plan", Icons.Default.Lightbulb, project.status != "IDLE")
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NavyMuted, modifier = Modifier.size(14.dp))
                        AgentPill("2. Arch", Icons.Default.AccountTree, project.status in listOf("ARCHITECTING", "CODING", "VALIDATING", "DEBUGGING", "READY"))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NavyMuted, modifier = Modifier.size(14.dp))
                        AgentPill("3. Code", Icons.Default.Code, project.status in listOf("CODING", "VALIDATING", "DEBUGGING", "READY"))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NavyMuted, modifier = Modifier.size(14.dp))
                        AgentPill("4. Valid", Icons.Default.CheckCircle, project.status in listOf("VALIDATING", "DEBUGGING", "READY"))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NavyMuted, modifier = Modifier.size(14.dp))
                        AgentPill("5. Debug", Icons.Default.BugReport, project.repairAttempts > 0 || project.status == "READY")
                    }
                }
            }
        }

        // Workspace Navigation Tabs: Editor vs Activity Log vs Live Preview vs History
        item {
            ScrollableTabRow(
                selectedTabIndex = activeWorkspaceTab,
                containerColor = PureWhite,
                contentColor = SkyBluePrimary,
                edgePadding = 8.dp
            ) {
                Tab(
                    selected = activeWorkspaceTab == 0,
                    onClick = { activeWorkspaceTab = 0 },
                    text = { Text("Code & Files (${project.files.size})") }
                )
                Tab(
                    selected = activeWorkspaceTab == 1,
                    onClick = { activeWorkspaceTab = 1 },
                    text = { Text("Agent Logs (${project.agentLogs.size})") }
                )
                Tab(
                    selected = activeWorkspaceTab == 2,
                    onClick = { activeWorkspaceTab = 2 },
                    text = { Text("Live Sandbox") }
                )
                Tab(
                    selected = activeWorkspaceTab == 3,
                    onClick = { activeWorkspaceTab = 3 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("History (${uiState.agentHistory.size})")
                        }
                    }
                )
            }
        }

        // Tab Content
        when (activeWorkspaceTab) {
            0 -> {
                // File Explorer & Editor
                item {
                    // File Explorer chips
                    Text(
                        text = "Project File Explorer:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(project.files) { file ->
                            val isSelected = file.path == uiState.selectedAgentFile?.path
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectAgentFile(file) },
                                label = { Text(file.path) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (file.path.endsWith(".json")) Icons.Default.Settings else Icons.Default.Description,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                },
                                modifier = Modifier.testTag("file_chip_${file.path.replace('/', '_')}"),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SkyBlueContainer,
                                    selectedLabelColor = SkyBlueDark
                                )
                            )
                        }
                    }
                }

                // File Viewer
                item {
                    val currentFile = uiState.selectedAgentFile ?: project.files.firstOrNull()
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CodeBg),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1E293B))
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Code,
                                        contentDescription = null,
                                        tint = SkyBlueLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = currentFile?.path ?: "src/App.tsx",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontFamily = FontFamily.Monospace,
                                        color = Color.White
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.showSnackbar("File content copied to clipboard!")
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy Code",
                                        tint = SkyBlueLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Text(
                                text = currentFile?.content ?: "// No file selected",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                color = CodeFg,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                // Download Project ZIP Action
                item {
                    Button(
                        onClick = {
                            viewModel.showSnackbar("Project ZIP archive generated: ${project.name}.zip ready for download!")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("download_zip_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessEmerald)
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = "Download")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Download Project (${project.files.size} Files, Validated ZIP)")
                    }
                }
            }

            1 -> {
                // Agent Activity Feed
                item {
                    Text(
                        text = "Agent Activity Feed",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                }
                items(project.agentLogs) { log ->
                    AgentActivityItem(log = log)
                }
            }

            2 -> {
                // Interactive Sandbox Simulation
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SuccessEmerald))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Sandboxed Execution Preview: ${project.name}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))

                            // Interactive Mini-App Runner
                            InteractiveMiniAppPreview(projectName = project.name)
                        }
                    }
                }
            }

            3 -> {
                // History Section: Past Project Generation Records from MongoDB Database
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueLight))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SkyBlueContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Storage,
                                        contentDescription = "MongoDB",
                                        tint = SkyBluePrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "MongoDB Project Records",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyDark
                                    )
                                    Text(
                                        text = "Collection: agent_projects • ${uiState.agentHistory.size} Documents",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = NavyMuted
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SuccessContainer
                            ) {
                                Text(
                                    text = "Connected",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessEmerald,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                if (uiState.agentHistory.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(BorderSubtle))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(imageVector = Icons.Default.FolderOpen, contentDescription = null, tint = NavyMuted, modifier = Modifier.size(40.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("No past project generation records found.", style = MaterialTheme.typography.bodyMedium, color = NavyText)
                                Text("Use the prompt input above to generate a new software project.", style = MaterialTheme.typography.bodySmall, color = NavyMuted)
                            }
                        }
                    }
                } else {
                    items(uiState.agentHistory) { histProj ->
                        AgentProjectHistoryCard(
                            project = histProj,
                            isCurrent = histProj.id == project.id,
                            onRestore = {
                                viewModel.restoreAgentProject(histProj)
                                activeWorkspaceTab = 0
                            },
                            onDelete = {
                                viewModel.deleteAgentProject(histProj.id)
                            },
                            onExportZip = {
                                viewModel.showSnackbar("Exported '${histProj.name}.zip' from MongoDB history!")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgentPill(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(if (isActive) SkyBlueContainer else SurfaceBackground)
                .border(1.dp, if (isActive) SkyBluePrimary else BorderSubtle, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = if (isActive) SkyBluePrimary else NavyMuted,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = if (isActive) NavyDark else NavyMuted
        )
    }
}

@Composable
fun AgentProjectHistoryCard(
    project: AgentProject,
    isCurrent: Boolean,
    onRestore: () -> Unit,
    onDelete: () -> Unit,
    onExportZip: () -> Unit
) {
    val (statusBg, statusFg, statusIcon) = when (project.status) {
        "READY" -> Triple(SuccessContainer, SuccessEmerald, Icons.Default.CheckCircle)
        "FAILED" -> Triple(ErrorContainer, ErrorRose, Icons.Default.Cancel)
        else -> Triple(WarningContainer, WarningAmber, Icons.Default.Sync)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = if (isCurrent) 2.dp else 1.dp,
                color = if (isCurrent) SkyBluePrimary else BorderSubtle,
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("history_card_${project.id}"),
        colors = CardDefaults.cardColors(containerColor = PureWhite)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Project Name and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = project.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                        if (isCurrent) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SkyBluePrimary
                            ) {
                                Text(
                                    text = "ACTIVE",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Timestamp",
                            tint = NavyMuted,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatProjectTimestamp(project.createdAt),
                            style = MaterialTheme.typography.labelSmall,
                            color = NavyMuted
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• id: ${project.id.take(10)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = NavyMuted,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Overall Project Status Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusBg
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = statusIcon,
                            contentDescription = project.status,
                            tint = statusFg,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = project.status,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = statusFg
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Requirements / Prompt Box
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = SurfaceBackground,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "REQUIREMENTS PROMPT",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyMuted,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = project.prompt,
                        style = MaterialTheme.typography.bodySmall,
                        color = NavyDark,
                        maxLines = 3
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tech Stack & Generation Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(project.techStack) { tech ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SkyBlueSurface,
                            border = BorderStroke(1.dp, SkyBlueContainer)
                        ) {
                            Text(
                                text = tech,
                                style = MaterialTheme.typography.labelSmall,
                                color = SkyBlueDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${project.files.size} files",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (project.repairAttempts > 0) "${project.repairAttempts} repairs" else "0 repairs",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (project.repairAttempts > 0) WarningAmber else SuccessEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onRestore,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("restore_project_${project.id}"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCurrent) SkyBlueDark else SkyBluePrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Restore,
                        contentDescription = "Restore",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isCurrent) "In Workspace" else "Restore to Workspace",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                OutlinedButton(
                    onClick = onExportZip,
                    modifier = Modifier.testTag("zip_project_${project.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Export ZIP",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ZIP", style = MaterialTheme.typography.labelSmall)
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("delete_project_${project.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete from history",
                        tint = ErrorRose
                    )
                }
            }
        }
    }
}

fun formatProjectTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val relative = when {
        diff < 60_000L -> "Just now"
        diff < 3600_000L -> "${diff / 60_000L}m ago"
        diff < 86400_000L -> "${diff / 3600_000L}h ago"
        diff < 604800_000L -> "${diff / 86400_000L}d ago"
        else -> ""
    }

    val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
    val formattedDate = sdf.format(Date(timestamp))

    return if (relative.isNotBlank()) "$formattedDate ($relative)" else formattedDate
}

@Composable
fun InteractiveMiniAppPreview(projectName: String) {
    var display by remember { mutableStateOf("42") }
    var noteInput by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(listOf("Review AgentForge architecture", "Deploy to staging cluster")) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = SkyBlueSurface),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(SkyBlueContainer))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Live App Simulation",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = SkyBlueDark
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Calculator interactive widget
            if (projectName.contains("Calculator", ignoreCase = true)) {
                Surface(
                    color = CodeBg,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = display,
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily.Monospace,
                            color = CodeFg,
                            modifier = Modifier.align(Alignment.End)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(onClick = { display = "0" }, colors = ButtonDefaults.buttonColors(containerColor = ErrorRose)) { Text("C") }
                            Button(onClick = { display = if (display == "0") "7" else display + "7" }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155))) { Text("7") }
                            Button(onClick = { display = if (display == "0") "8" else display + "8" }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155))) { Text("8") }
                            Button(onClick = { display = (display.toIntOrNull()?.times(2) ?: 84).toString() }, colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)) { Text("×2") }
                        }
                    }
                }
            } else {
                // Interactive Task/Expense Manager Simulation
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = noteInput,
                            onValueChange = { noteInput = it },
                            placeholder = { Text("Add entry...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (noteInput.isNotBlank()) {
                                    notes = notes + noteInput
                                    noteInput = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                        ) {
                            Text("Add")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    notes.forEach { n ->
                        Text("• $n", style = MaterialTheme.typography.bodySmall, color = NavyDark)
                    }
                }
            }
        }
    }
}
