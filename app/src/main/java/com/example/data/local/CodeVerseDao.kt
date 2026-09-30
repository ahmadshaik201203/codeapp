package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CodeVerseDao {
    @Query("SELECT * FROM user_profiles WHERE id = 'student_01' LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(user: UserProfileEntity)

    @Query("SELECT * FROM roadmaps ORDER BY orderIndex ASC")
    fun getAllMilestones(): Flow<List<RoadmapMilestoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestones(milestones: List<RoadmapMilestoneEntity>)

    @Query("UPDATE roadmaps SET status = :status WHERE id = :id")
    suspend fun updateMilestoneStatus(id: String, status: String)

    @Query("SELECT * FROM job_applications ORDER BY appliedDate DESC")
    fun getAllApplications(): Flow<List<JobApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(app: JobApplicationEntity)

    @Query("DELETE FROM job_applications WHERE id = :id")
    suspend fun deleteApplication(id: String)

    @Query("SELECT * FROM agent_projects ORDER BY createdAt DESC")
    fun getAllAgentProjects(): Flow<List<AgentProjectEntity>>

    @Query("SELECT * FROM agent_projects WHERE id = :id LIMIT 1")
    suspend fun getAgentProjectById(id: String): AgentProjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgentProject(project: AgentProjectEntity)

    @Query("DELETE FROM agent_projects WHERE id = :id")
    suspend fun deleteAgentProject(id: String)

    @Query("DELETE FROM agent_projects")
    suspend fun clearAllAgentProjects()

    @Query("SELECT * FROM badges")
    fun getAllBadges(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Query("UPDATE badges SET isUnlocked = 1 WHERE id = :id")
    suspend fun unlockBadge(id: String)
}
