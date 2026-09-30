package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun agentProjectHistory_statusAndTimestampVerification() {
    val project = com.example.data.local.InitialData.sampleAgentProjects.first()
    assertEquals("READY", project.status)
    assertEquals("Expense Pulse Dashboard", project.name)
    assertTrue(project.createdAt > 0L)
    assertTrue(project.files.isNotEmpty())
    assertNotNull(project.validationResult)
    assertTrue(project.validationResult!!.passed)
  }

  @Test
  fun geminiChatRoles_modelsAndSystemInstructionsVerification() {
    val roles = com.example.data.local.InitialData.geminiChatRoles
    assertTrue(roles.isNotEmpty())
    
    val architectRole = roles.first { it.id == "role_architect" }
    assertEquals("gemini-3.1-pro-preview", architectRole.model)
    assertTrue(architectRole.systemInstruction.contains("Architect"))

    val mentorRole = roles.first { it.id == "role_mentor" }
    assertEquals("gemini-3.5-flash", mentorRole.model)

    val speedyRole = roles.first { it.id == "role_speedy" }
    assertEquals("gemini-3.1-flash-lite-preview", speedyRole.model)
  }
}
