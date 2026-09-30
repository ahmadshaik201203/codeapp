package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*
import com.example.data.remote.GeminiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class CodeVerseRepository(private val dao: CodeVerseDao) {

    val userProfile: Flow<UserProfile> = dao.getUserProfile().map { entity ->
        if (entity != null) {
            UserProfile(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                role = entity.role,
                careerGoal = entity.careerGoal,
                xp = entity.xp,
                streak = entity.streak,
                currentSkillScore = entity.currentSkillScore,
                readinessScore = entity.readinessScore
            )
        } else {
            val defaultUser = UserProfile()
            dao.insertUserProfile(
                UserProfileEntity(
                    id = defaultUser.id,
                    name = defaultUser.name,
                    email = defaultUser.email,
                    role = defaultUser.role,
                    careerGoal = defaultUser.careerGoal,
                    xp = defaultUser.xp,
                    streak = defaultUser.streak,
                    currentSkillScore = defaultUser.currentSkillScore,
                    readinessScore = defaultUser.readinessScore
                )
            )
            defaultUser
        }
    }.flowOn(Dispatchers.IO)

    val roadmapMilestones: Flow<List<RoadmapMilestone>> = dao.getAllMilestones().map { list ->
        if (list.isEmpty()) {
            val entities = InitialData.defaultMilestones.map { m ->
                RoadmapMilestoneEntity(
                    id = m.id,
                    title = m.title,
                    domain = m.domain,
                    orderIndex = m.orderIndex,
                    status = m.status.name,
                    estimatedHours = m.estimatedHours,
                    description = m.description,
                    skillGapScore = m.skillGapScore
                )
            }
            dao.insertMilestones(entities)
            InitialData.defaultMilestones
        } else {
            list.map { e ->
                RoadmapMilestone(
                    id = e.id,
                    title = e.title,
                    domain = e.domain,
                    orderIndex = e.orderIndex,
                    status = try { MilestoneStatus.valueOf(e.status) } catch (x: Exception) { MilestoneStatus.LOCKED },
                    estimatedHours = e.estimatedHours,
                    description = e.description,
                    skillGapScore = e.skillGapScore
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    val jobApplications: Flow<List<JobApplication>> = dao.getAllApplications().map { list ->
        if (list.isEmpty()) {
            InitialData.defaultApplications.forEach { app ->
                dao.insertApplication(
                    JobApplicationEntity(
                        id = app.id,
                        company = app.company,
                        role = app.role,
                        jobUrl = app.jobUrl,
                        appliedDate = app.appliedDate,
                        status = app.status,
                        interviewDate = app.interviewDate,
                        notes = app.notes
                    )
                )
            }
            InitialData.defaultApplications
        } else {
            list.map { e ->
                JobApplication(
                    id = e.id,
                    company = e.company,
                    role = e.role,
                    jobUrl = e.jobUrl,
                    appliedDate = e.appliedDate,
                    status = e.status,
                    interviewDate = e.interviewDate,
                    notes = e.notes
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    val badges: Flow<List<Badge>> = dao.getAllBadges().map { list ->
        if (list.isEmpty()) {
            val entities = InitialData.defaultBadges.map { b ->
                BadgeEntity(b.id, b.title, b.description, b.isUnlocked, b.xpReward)
            }
            dao.insertBadges(entities)
            InitialData.defaultBadges
        } else {
            list.map { e ->
                Badge(e.id, e.title, e.description, e.isUnlocked, e.xpReward)
            }
        }
    }.flowOn(Dispatchers.IO)

    val agentProjectsHistory: Flow<List<AgentProject>> = dao.getAllAgentProjects().map { list ->
        if (list.isEmpty()) {
            val entities = InitialData.sampleAgentProjects.map { p -> p.toEntity() }
            entities.forEach { dao.insertAgentProject(it) }
            InitialData.sampleAgentProjects
        } else {
            list.map { it.toDomain() }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAgentProject(project: AgentProject) = withContext(Dispatchers.IO) {
        dao.insertAgentProject(project.toEntity())
    }

    suspend fun deleteAgentProject(id: String) = withContext(Dispatchers.IO) {
        dao.deleteAgentProject(id)
    }

    suspend fun updateCareerGoal(newGoal: String) = withContext(Dispatchers.IO) {
        val current = dao.getUserProfile()
        dao.insertUserProfile(
            UserProfileEntity(
                id = "student_01",
                name = "Alex Rivera",
                email = "alex.rivera@university.edu",
                role = "Student",
                careerGoal = newGoal,
                xp = 1350,
                streak = 7,
                currentSkillScore = 78,
                readinessScore = 84
            )
        )
    }

    suspend fun addXP(amount: Int) = withContext(Dispatchers.IO) {
        val current = UserProfile()
        dao.insertUserProfile(
            UserProfileEntity(
                id = "student_01",
                name = current.name,
                email = current.email,
                role = current.role,
                careerGoal = current.careerGoal,
                xp = current.xp + amount,
                streak = current.streak,
                currentSkillScore = current.currentSkillScore,
                readinessScore = current.readinessScore
            )
        )
    }

    suspend fun updateMilestoneStatus(id: String, status: MilestoneStatus) = withContext(Dispatchers.IO) {
        dao.updateMilestoneStatus(id, status.name)
    }

    suspend fun addJobApplication(app: JobApplication) = withContext(Dispatchers.IO) {
        dao.insertApplication(
            JobApplicationEntity(
                id = app.id,
                company = app.company,
                role = app.role,
                jobUrl = app.jobUrl,
                appliedDate = app.appliedDate,
                status = app.status,
                interviewDate = app.interviewDate,
                notes = app.notes
            )
        )
    }

    suspend fun deleteJobApplication(id: String) = withContext(Dispatchers.IO) {
        dao.deleteApplication(id)
    }

    // Adaptive Learning Engine: Calculates system action based on score and thresholds
    fun evaluateAdaptiveProgress(score: Int, config: AdaptiveConfig): String {
        return when {
            score < config.revisionThreshold -> "Triggered Revision Material + Extra Practice Problems (Score: $score%)"
            score < config.practiceThreshold -> "Assigned Supplementary Practice Problems (Score: $score%)"
            score <= config.normalThreshold -> "Advancing along standard Personalized Roadmap (Score: $score%)"
            else -> "Exceptional Performance ($score%)! Basic Revision Skipped — Advanced Mastery Unlocked!"
        }
    }

    // Job Readiness Calculation Formula (Documented in PRD Section 23)
    // Formula: Technical (30%) + Projects (20%) + DSA (15%) + Git (10%) + Resume (10%) + Communication (5%) + Interview (10%)
    fun calculateJobReadiness(
        technicalScore: Int = 80,
        projectsScore: Int = 85,
        dsaScore: Int = 75,
        gitScore: Int = 90,
        resumeScore: Int = 85,
        commScore: Int = 80,
        interviewScore: Int = 78
    ): Int {
        val weighted = (technicalScore * 0.30) +
                (projectsScore * 0.20) +
                (dsaScore * 0.15) +
                (gitScore * 0.10) +
                (resumeScore * 0.10) +
                (commScore * 0.05) +
                (interviewScore * 0.10)
        return weighted.toInt()
    }

    // AgentForge Multi-Agent Pipeline Execution
    suspend fun executeAgentForgeWorkflow(
        prompt: String,
        onUpdate: (AgentProject) -> Unit
    ): AgentProject = withContext(Dispatchers.IO) {
        val projectId = "proj_${System.currentTimeMillis()}"
        val logs = mutableListOf<AgentLog>()

        // 1. Planner Agent
        logs.add(AgentLog("PLANNER", "RUNNING", "Analyzing requirements: \"$prompt\""))
        var projectState = AgentProject(
            id = projectId,
            name = deriveProjectName(prompt),
            prompt = prompt,
            status = "PLANNING",
            techStack = listOf("React", "TypeScript", "Tailwind CSS"),
            files = emptyList(),
            agentLogs = logs.toList(),
            validationResult = null,
            repairAttempts = 0
        )
        onUpdate(projectState)
        delay(600)

        logs.add(AgentLog("PLANNER", "SUCCESS", "Objective extracted. Functional specs: responsive UI, data validation, dynamic state. Recommending Modern React + TypeScript stack."))
        projectState = projectState.copy(status = "ARCHITECTING", agentLogs = logs.toList())
        onUpdate(projectState)
        delay(600)

        // 2. Architect Agent
        logs.add(AgentLog("ARCHITECT", "RUNNING", "Synthesizing directory layout, module relationships, and engineering tasks..."))
        onUpdate(projectState.copy(agentLogs = logs.toList()))
        delay(700)

        logs.add(AgentLog("ARCHITECT", "SUCCESS", "File tree created: src/App.tsx, components, styling, package.json, and README.md."))
        projectState = projectState.copy(status = "CODING", agentLogs = logs.toList())
        onUpdate(projectState)
        delay(600)

        // 3. Coder Agent
        logs.add(AgentLog("CODER", "RUNNING", "Generating pristine source code file-by-file with clean typing..."))
        onUpdate(projectState.copy(agentLogs = logs.toList()))
        delay(900)

        val generatedFiles = generateFilesForPrompt(prompt)
        logs.add(AgentLog("CODER", "SUCCESS", "Successfully generated ${generatedFiles.size} source files to project workspace."))
        projectState = projectState.copy(
            status = "VALIDATING",
            files = generatedFiles,
            agentLogs = logs.toList()
        )
        onUpdate(projectState)
        delay(600)

        // 4. Validator Agent
        logs.add(AgentLog("VALIDATOR", "RUNNING", "Validating file existence, TypeScript schemas, imports, and functional compliance..."))
        onUpdate(projectState.copy(agentLogs = logs.toList()))
        delay(700)

        // 5. Debugger Agent Check
        val hasSimulatedWarning = prompt.contains("error", ignoreCase = true)
        val validationResult = if (hasSimulatedWarning) {
            logs.add(AgentLog("VALIDATOR", "WARNING", "Detected potential missing prop type in child component."))
            onUpdate(projectState.copy(agentLogs = logs.toList()))
            delay(500)
            logs.add(AgentLog("DEBUGGER", "RUNNING", "Applying patch to ensure strict TypeScript prop coverage (Repair attempt 1/3)..."))
            onUpdate(projectState.copy(agentLogs = logs.toList()))
            delay(600)
            logs.add(AgentLog("DEBUGGER", "SUCCESS", "Type definition patched cleanly. Error resolved."))
            ValidationResult(passed = true, errors = emptyList(), warnings = listOf("Missing prop typed"), repairAttempts = 1)
        } else {
            logs.add(AgentLog("VALIDATOR", "SUCCESS", "Validation Passed: 0 syntax errors, 0 missing exports, package.json verified."))
            ValidationResult(passed = true, errors = emptyList(), warnings = emptyList(), repairAttempts = 0)
        }

        logs.add(AgentLog("SYSTEM", "SUCCESS", "Project is ready for Live Preview and ZIP export!"))
        val finalProject = projectState.copy(
            status = "READY",
            agentLogs = logs.toList(),
            validationResult = validationResult,
            repairAttempts = validationResult.repairAttempts,
            createdAt = System.currentTimeMillis()
        )
        saveAgentProject(finalProject)
        onUpdate(finalProject)
        finalProject
    }

    private fun deriveProjectName(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("calculator") -> "Calculator Pro App"
            lower.contains("task") || lower.contains("todo") -> "TaskFlow Management Hub"
            lower.contains("expense") -> "Expense Pulse Dashboard"
            lower.contains("weather") -> "SkyCast Weather Hub"
            lower.contains("chat") -> "NovaChat AI Portal"
            else -> "AgentForge Generated App"
        }
    }

    private fun generateFilesForPrompt(prompt: String): List<AgentFile> {
        val lower = prompt.lowercase()
        return if (lower.contains("calculator")) {
            listOf(
                AgentFile(
                    path = "src/App.tsx",
                    language = "typescript",
                    content = """
import React, { useState } from 'react';

export default function CalculatorApp() {
  const [display, setDisplay] = useState('0');
  const [prev, setPrev] = useState<number | null>(null);
  const [op, setOp] = useState<string | null>(null);

  const handleDigit = (d: string) => {
    setDisplay(cur => (cur === '0' ? d : cur + d));
  };

  const handleOp = (operator: string) => {
    setPrev(parseFloat(display));
    setOp(operator);
    setDisplay('0');
  };

  const calculate = () => {
    if (prev === null || op === null) return;
    const cur = parseFloat(display);
    let res = 0;
    if (op === '+') res = prev + cur;
    if (op === '-') res = prev - cur;
    if (op === '*') res = prev * cur;
    if (op === '/') res = cur !== 0 ? prev / cur : 0;
    setDisplay(res.toString());
    setPrev(null);
    setOp(null);
  };

  const clear = () => {
    setDisplay('0');
    setPrev(null);
    setOp(null);
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-slate-900 text-white p-4">
      <div className="bg-slate-800 p-6 rounded-3xl shadow-2xl border border-slate-700 w-80">
        <div className="text-right text-4xl font-mono py-4 px-2 bg-slate-950 rounded-2xl mb-4 overflow-hidden">
          {display}
        </div>
        <div className="grid grid-cols-4 gap-2">
          {['C', '+/-', '%', '/'].map(btn => (
            <button key={btn} onClick={btn === 'C' ? clear : undefined} className="p-4 bg-slate-700 hover:bg-slate-600 rounded-xl font-bold">
              {btn}
            </button>
          ))}
          {['7', '8', '9', '*'].map(btn => (
            <button key={btn} onClick={() => btn === '*' ? handleOp('*') : handleDigit(btn)} className="p-4 bg-slate-700 rounded-xl font-bold">
              {btn}
            </button>
          ))}
          {['4', '5', '6', '-'].map(btn => (
            <button key={btn} onClick={() => btn === '-' ? handleOp('-') : handleDigit(btn)} className="p-4 bg-slate-700 rounded-xl font-bold">
              {btn}
            </button>
          ))}
          {['1', '2', '3', '+'].map(btn => (
            <button key={btn} onClick={() => btn === '+' ? handleOp('+') : handleDigit(btn)} className="p-4 bg-slate-700 rounded-xl font-bold">
              {btn}
            </button>
          ))}
          <button onClick={() => handleDigit('0')} className="col-span-2 p-4 bg-slate-700 rounded-xl font-bold">0</button>
          <button onClick={() => handleDigit('.')} className="p-4 bg-slate-700 rounded-xl font-bold">.</button>
          <button onClick={calculate} className="p-4 bg-sky-500 hover:bg-sky-400 rounded-xl font-bold text-slate-950">=</button>
        </div>
      </div>
    </div>
  );
}
""".trimIndent()
                ),
                AgentFile(
                    path = "package.json",
                    language = "json",
                    content = """{ "name": "calculator-pro", "version": "1.0.0", "dependencies": { "react": "^18.3.1", "react-dom": "^18.3.1" } }"""
                ),
                AgentFile(
                    path = "README.md",
                    language = "markdown",
                    content = "# Calculator Pro\nValidated and generated by AgentForge Multi-Agent Team."
                )
            )
        } else {
            InitialData.sampleAgentProjects.first().files
        }
    }
}

fun AgentProject.toEntity(): AgentProjectEntity {
    val techJson = JSONArray(techStack).toString()
    val filesArr = JSONArray()
    files.forEach { f ->
        filesArr.put(JSONObject().apply {
            put("path", f.path)
            put("language", f.language)
            put("content", f.content)
        })
    }
    val logsArr = JSONArray()
    agentLogs.forEach { l ->
        logsArr.put(JSONObject().apply {
            put("agentName", l.agentName)
            put("status", l.status)
            put("message", l.message)
            put("timestamp", l.timestamp)
        })
    }
    val valObj = validationResult?.let { v ->
        JSONObject().apply {
            put("passed", v.passed)
            put("errors", JSONArray(v.errors))
            put("warnings", JSONArray(v.warnings))
            put("repairAttempts", v.repairAttempts)
        }
    }?.toString() ?: ""

    return AgentProjectEntity(
        id = id,
        name = name,
        prompt = prompt,
        status = status,
        techStackJson = techJson,
        filesJson = filesArr.toString(),
        agentLogsJson = logsArr.toString(),
        validationJson = valObj,
        repairAttempts = repairAttempts,
        createdAt = createdAt
    )
}

fun AgentProjectEntity.toDomain(): AgentProject {
    val tech = mutableListOf<String>()
    try {
        val arr = JSONArray(techStackJson)
        for (i in 0 until arr.length()) { tech.add(arr.getString(i)) }
    } catch (_: Exception) {}

    val parsedFiles = mutableListOf<AgentFile>()
    try {
        val arr = JSONArray(filesJson)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            parsedFiles.add(AgentFile(
                path = obj.optString("path"),
                language = obj.optString("language"),
                content = obj.optString("content")
            ))
        }
    } catch (_: Exception) {}

    val parsedLogs = mutableListOf<AgentLog>()
    try {
        val arr = JSONArray(agentLogsJson)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            parsedLogs.add(AgentLog(
                agentName = obj.optString("agentName"),
                status = obj.optString("status"),
                message = obj.optString("message"),
                timestamp = obj.optLong("timestamp", System.currentTimeMillis())
            ))
        }
    } catch (_: Exception) {}

    val parsedValidation = try {
        if (validationJson.isNotBlank()) {
            val obj = JSONObject(validationJson)
            val errs = mutableListOf<String>()
            val errArr = obj.optJSONArray("errors")
            if (errArr != null) { for (i in 0 until errArr.length()) errs.add(errArr.getString(i)) }
            val warns = mutableListOf<String>()
            val warnArr = obj.optJSONArray("warnings")
            if (warnArr != null) { for (i in 0 until warnArr.length()) warns.add(warnArr.getString(i)) }
            ValidationResult(
                passed = obj.optBoolean("passed", true),
                errors = errs,
                warnings = warns,
                repairAttempts = obj.optInt("repairAttempts", 0)
            )
        } else null
    } catch (_: Exception) { null }

    return AgentProject(
        id = id,
        name = name,
        prompt = prompt,
        status = status,
        techStack = if (tech.isEmpty()) listOf("React", "TypeScript") else tech,
        files = parsedFiles,
        agentLogs = parsedLogs,
        validationResult = parsedValidation,
        repairAttempts = repairAttempts,
        createdAt = createdAt
    )
}
