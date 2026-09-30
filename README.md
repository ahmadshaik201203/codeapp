# CodeVerse 🚀
> **Learn. Build. Practice. Get Job Ready.**

**CodeVerse** is a full-stack AI/ML-powered learning, skill development, and career readiness platform built for college students. It provides structured technical learning, adaptive roadmaps, coding practice, AI mentoring, ATS resume optimization, mock interviews, and the **AgentForge Multi-Agent Software Engineering Studio**.

---

## 🌟 Key Features

### 1. 🎯 Career Hub & ML Skill Analysis
- **Career Tracks:** Select between *Full-Stack Developer*, *Java Full-Stack*, *Python Full-Stack*, *AI/ML Engineer*, *Data Scientist*, and *Cloud/DevOps Engineer*.
- **Skill Gap Detection:** Real-time scoring across foundational and advanced technical competencies with targeted recommendations.
- **Adaptive Learning Engine:** Dynamically tailors roadmaps based on assessment and quiz performance:
  - `< 50%`: Revisions & extra practice.
  - `50% – 70%`: Practice problem reinforcement.
  - `70% – 85%`: Standard progression.
  - `> 85%`: Advanced acceleration (skips basic review).

### 2. 🤖 Gemini Chatbot & AI Mentor
- **Multi-Turn Chatbot:** Instant AI assistance with streaming responses, code explanations, and debugging tips.
- **Dynamic Model Switcher:**
  - `⚡ Fast` — `gemini-3.1-flash-lite-preview`
  - `🌟 General` — `gemini-3.5-flash`
  - `🧠 Complex` — `gemini-3.1-pro-preview`
- **Specialized Personas:** Software Architect, Patient Career Mentor, Senior Technical Interviewer, and Rapid Code Companion.

### 3. 🛠️ AgentForge Multi-Agent Studio
- **Autonomous Multi-Agent Collaboration Pipeline:**
  1. *Requirements Planner Agent*
  2. *Software Architect Agent*
  3. *Full-Stack Coder Agent*
  4. *AST & Syntax Validator Agent*
  5. *Self-Healing Debugger Agent*
- **File Explorer & Monospace Code Viewer:** Inspect multi-file generated architectures (`App.tsx`, `package.json`, `styles.css`, API routes).
- **Interactive Live Sandbox:** Test generated applications directly inside an interactive runner.
- **MongoDB History Section:** Fetches and displays past project generation records from the `agent_projects` collection with timestamps, status pills (`READY`, `VALIDATING`, `FAILED`), requirements excerpts, tech stack tags, and one-click ZIP export.

### 4. 💻 Coding Lab & DSA
- Multi-language interactive code editor (JavaScript, TypeScript, Python, Java, C++).
- Test cases, execution simulator, and AI code review for time/space complexity analysis.

### 5. 💼 Resume ATS Analyzer & Job Matcher
- Live ATS score breakdown with keyword matching and missing section alerts.
- Curated job board matching student skill profiles to open technical roles.

### 6. 🎙️ AI Mock Interviews
- Role-specific technical, behavioral, and system design questions.
- Instant AI evaluation on clarity, technical depth, and recommended improvements.

### 7. 📊 Job Readiness Gauge & Faculty Portal
- Weighted readiness percentage (Technical Skills, Projects, DSA, Git, Resume, Communication).
- Faculty dashboard for monitoring cohort analytics, weak competencies, and project submissions.

---

## 🎨 Design System
- **Theme:** Clean White & Sky Blue modern aesthetic.
- **Primary Colors:**
  - Sky Blue Primary: `#0284C7`
  - Sky Blue Dark: `#0369A1`
  - Sky Blue Container: `#E0F2FE`
  - Surface Background: `#F8FAFC`
  - Navy Dark Text: `#0F172A`
- **Components:** Material 3 Adaptive Layouts, Bottom Navigation with `ModalBottomSheet` for extended utilities, and accessible 48dp+ touch targets.

---

## 🏗️ Architecture & Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Local Database:** Room Database with KSP (`AppDatabase`, `CodeVerseDao`, TypeConverters)
- **Networking:** OkHttp & Coroutines
- **AI Integration:** Google Gemini REST API (`generateContent`)
- **State Management:** MVVM with `ViewModel`, `StateFlow`, and `collectAsStateWithLifecycle`

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Meerkat or AI Studio Build environment
- Android SDK (API level 24 minimum, target API level 36)
- JDK 11 or higher

### API Key Configuration
1. Obtain an API key from [Google AI Studio](https://aistudio.google.com/).
2. Add your key to `.env` or the Secrets panel:
   ```properties
   GEMINI_API_KEY=your_actual_gemini_api_key_here
   ```
3. Build and launch the application:
   ```bash
   gradle assembleDebug
   ```

---

## 📄 License
This project is open-source under the Apache 2.0 License.
