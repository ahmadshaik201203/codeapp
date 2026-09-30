package com.example.data.model

data class UserProfile(
    val id: String = "student_01",
    val name: String = "Alex Rivera",
    val email: String = "alex.rivera@university.edu",
    val role: String = "Student",
    val careerGoal: String = "Full-Stack Developer",
    val xp: Int = 1250,
    val streak: Int = 7,
    val currentSkillScore: Int = 76,
    val readinessScore: Int = 82
)

data class SkillAssessmentQuestion(
    val id: Int,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val careerTag: String
)

enum class MilestoneStatus {
    LOCKED,
    RECOMMENDED,
    IN_PROGRESS,
    COMPLETED,
    ADVANCED
}

data class RoadmapMilestone(
    val id: String,
    val title: String,
    val domain: String,
    val orderIndex: Int,
    val status: MilestoneStatus,
    val estimatedHours: Int,
    val description: String,
    val skillGapScore: Int
)

data class Course(
    val id: String,
    val title: String,
    val domain: String,
    val level: String,
    val description: String,
    val lessonCount: Int,
    val progressPercent: Int,
    val isCompleted: Boolean,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val summary: String,
    val codeExample: String,
    val transcript: String,
    val quizQuestion: String,
    val quizOptions: List<String>,
    val quizCorrectIndex: Int
)

data class TestCase(
    val input: String,
    val expectedOutput: String,
    val isHidden: Boolean = false
)

data class CodingProblem(
    val id: String,
    val title: String,
    val difficulty: String,
    val language: String,
    val description: String,
    val starterCode: String,
    val testCases: List<TestCase>,
    val hints: List<String>
)

data class AICodeReview(
    val correctness: String,
    val timeComplexity: String,
    val spaceComplexity: String,
    val issues: List<String>,
    val suggestion: String
)

data class CodingSubmission(
    val id: String,
    val problemId: String,
    val code: String,
    val status: String,
    val runtimeMs: Long,
    val aiReview: AICodeReview,
    val timestamp: Long
)

data class AIProjectReview(
    val architectureScore: Int,
    val codeQualityScore: Int,
    val documentationScore: Int,
    val strengths: List<String>,
    val improvements: List<String>,
    val summary: String
)

data class ProjectItem(
    val id: String,
    val title: String,
    val difficulty: String,
    val description: String,
    val techStack: List<String>,
    val requirements: List<String>,
    val githubUrl: String = "",
    val demoUrl: String = "",
    val score: Int = 0,
    val status: String = "Available", // Available, Submitted, Reviewed
    val review: AIProjectReview? = null
)

data class MockInterviewQuestion(
    val id: Int,
    val role: String,
    val category: String, // Technical, HR, Behavioral, Coding, Mixed
    val difficulty: String,
    val question: String,
    val sampleIdealAnswer: String,
    val keyPoints: List<String>
)

data class InterviewEvaluation(
    val technicalAccuracy: Int,
    val relevance: Int,
    val structure: Int,
    val communication: Int,
    val feedback: String,
    val recommendation: String
)

data class JobApplication(
    val id: String,
    val company: String,
    val role: String,
    val jobUrl: String,
    val appliedDate: String,
    val status: String, // Saved, Applied, Assessment, Interview, Selected, Rejected, Withdrawn
    val interviewDate: String = "",
    val notes: String = ""
)

data class ResumeData(
    val fullName: String = "Alex Rivera",
    val email: String = "alex.rivera@university.edu",
    val phone: String = "+1 (555) 234-5678",
    val summary: String = "Aspiring Full-Stack Software Engineer with proven hands-on experience building reactive web & mobile applications, REST APIs, and microservices.",
    val education: String = "B.S. in Computer Science — Tech Institute of Technology (2023 - 2027) | GPA 3.8",
    val skills: List<String> = listOf("Kotlin", "TypeScript", "React", "Node.js", "Express", "MongoDB", "SQL", "Docker", "Git", "Jetpack Compose"),
    val projects: String = "1. AgentForge AI Platform: Autonomous multi-agent coding engine.\n2. CloudTask: Collaborative project tracker with real-time updates.",
    val github: String = "https://github.com/alex-dev",
    val linkedin: String = "https://linkedin.com/in/alexrivera-tech"
)

data class JDAnalysisResult(
    val roleTitle: String,
    val matchPercentage: Int,
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val developingSkills: List<String>,
    val preparationRecommendations: List<String>
)

data class AgentFile(
    val path: String,
    val language: String,
    val content: String
)

data class AgentLog(
    val agentName: String, // PLANNER, ARCHITECT, CODER, VALIDATOR, DEBUGGER, SYSTEM
    val status: String,    // RUNNING, SUCCESS, WARNING, ERROR
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ValidationResult(
    val passed: Boolean,
    val errors: List<String>,
    val warnings: List<String>,
    val repairAttempts: Int
)

data class AgentProject(
    val id: String,
    val name: String,
    val prompt: String,
    val status: String, // IDLE, PLANNING, ARCHITECTING, CODING, VALIDATING, DEBUGGING, READY, FAILED
    val techStack: List<String>,
    val files: List<AgentFile>,
    val agentLogs: List<AgentLog>,
    val validationResult: ValidationResult?,
    val repairAttempts: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val xpReward: Int
)

data class AdaptiveConfig(
    val revisionThreshold: Int = 50,
    val practiceThreshold: Int = 70,
    val normalThreshold: Int = 85
)

data class FacultyStudentStat(
    val name: String,
    val careerGoal: String,
    val currentSkillScore: Int,
    val weakSkills: List<String>,
    val projectsCompleted: Int,
    val interviewScore: Int,
    val status: String
)

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val role: String, // "user" or "model"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val modelUsed: String? = null
)

data class GeminiChatRole(
    val id: String,
    val name: String,
    val model: String,
    val icon: String,
    val description: String,
    val systemInstruction: String
)

data class GeneratedImageItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "AI Generated Asset",
    val prompt: String,
    val base64Data: String? = null,
    val mimeType: String = "image/png",
    val aspectRatio: String = "1:1",
    val isEdit: Boolean = false,
    val originalPrompt: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val model: String = "gemini-3.1-flash-image-preview",
    val category: String = "App Asset"
)
