package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: String = "student_01",
    val name: String,
    val email: String,
    val role: String,
    val careerGoal: String,
    val xp: Int,
    val streak: Int,
    val currentSkillScore: Int,
    val readinessScore: Int
)

@Entity(tableName = "roadmaps")
data class RoadmapMilestoneEntity(
    @PrimaryKey val id: String,
    val title: String,
    val domain: String,
    val orderIndex: Int,
    val status: String, // LOCKED, RECOMMENDED, IN_PROGRESS, COMPLETED, ADVANCED
    val estimatedHours: Int,
    val description: String,
    val skillGapScore: Int
)

@Entity(tableName = "job_applications")
data class JobApplicationEntity(
    @PrimaryKey val id: String,
    val company: String,
    val role: String,
    val jobUrl: String,
    val appliedDate: String,
    val status: String,
    val interviewDate: String,
    val notes: String
)

@Entity(tableName = "agent_projects")
data class AgentProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val prompt: String,
    val status: String,
    val techStackJson: String,
    val filesJson: String,
    val agentLogsJson: String,
    val validationJson: String,
    val repairAttempts: Int,
    val createdAt: Long
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val xpReward: Int
)
