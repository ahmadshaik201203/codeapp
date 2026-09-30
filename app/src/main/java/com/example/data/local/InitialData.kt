package com.example.data.local

import com.example.data.model.*

object InitialData {

    val geminiChatRoles = listOf(
        GeminiChatRole(
            id = "role_architect",
            name = "Software Architect",
            model = "gemini-3.1-pro-preview",
            icon = "architecture",
            description = "Complex Tasks • System design, microservices, distributed systems",
            systemInstruction = "You are an elite Software Engineering Architect. You design large-scale distributed systems, microservices, and solve complex concurrency, database sharding, and algorithmic bottlenecks. Provide deep architectural diagrams, trade-off comparisons, and production-grade code."
        ),
        GeminiChatRole(
            id = "role_mentor",
            name = "Career & DSA Mentor",
            model = "gemini-3.5-flash",
            icon = "school",
            description = "General Tasks • Concept explanations, full-stack learning, algorithms",
            systemInstruction = "You are CodeVerse AI Mentor. You teach programming, explain data structures and algorithms, guide through full-stack development, and prepare university students for elite tech careers. Use clear bullet points and illustrative code snippets."
        ),
        GeminiChatRole(
            id = "role_speedy",
            name = "Speedy Assistant",
            model = "gemini-3.1-flash-lite-preview",
            icon = "bolt",
            description = "Fast Tasks • Quick syntax lookups, immediate debugging, rapid answers",
            systemInstruction = "You are a lightning-fast code assistant. Provide concise, direct answers, syntax lookups, and immediate code fixes with zero fluff."
        ),
        GeminiChatRole(
            id = "role_interviewer",
            name = "Staff Tech Interviewer",
            model = "gemini-3.1-pro-preview",
            icon = "record_voice_over",
            description = "Complex Tasks • Realistic behavioral & technical interview simulation",
            systemInstruction = "You are a Senior Principal Engineer at a tier-1 tech firm conducting a rigorous technical interview. Ask deep follow-up questions, evaluate time/space complexity, probe edge cases, and challenge assumptions."
        )
    )

    val careerOptions = listOf(
        "Full-Stack Developer",
        "Java Full-Stack Developer",
        "Python Full-Stack Developer",
        "AI/ML Engineer",
        "Data Scientist",
        "Cloud/DevOps Engineer"
    )

    val defaultMilestones = listOf(
        RoadmapMilestone("m1", "Web Fundamentals (HTML5 & Modern CSS)", "Frontend", 1, MilestoneStatus.COMPLETED, 12, "Semantic layout, flexbox, grid, and accessibility standards.", 10),
        RoadmapMilestone("m2", "JavaScript Essentials & ES6+", "Frontend", 2, MilestoneStatus.COMPLETED, 20, "Closures, Event Loop, Promises, Async/Await, and DOM manipulation.", 15),
        RoadmapMilestone("m3", "React & Modern State Architecture", "Frontend", 3, MilestoneStatus.IN_PROGRESS, 28, "Hooks, Context, Zustand, and component lifecycle optimization.", 35),
        RoadmapMilestone("m4", "Node.js & Express REST APIs", "Backend", 4, MilestoneStatus.RECOMMENDED, 24, "Server architecture, middleware, JWT authentication, and routing.", 45),
        RoadmapMilestone("m5", "Database Systems (SQL & MongoDB)", "Database", 5, MilestoneStatus.LOCKED, 22, "Relational schemas, indexing, aggregation pipelines, and ACID transactions.", 60),
        RoadmapMilestone("m6", "Data Structures & Algorithms", "Core CS", 6, MilestoneStatus.IN_PROGRESS, 40, "Trees, graphs, dynamic programming, and complexity trade-offs.", 40),
        RoadmapMilestone("m7", "Docker & CI/CD Pipelines", "DevOps", 7, MilestoneStatus.LOCKED, 18, "Containerization, GitHub Actions, automated testing, and deployments.", 75),
        RoadmapMilestone("m8", "AI Agent Integration & LLM APIs", "AI/ML", 8, MilestoneStatus.LOCKED, 25, "Prompt engineering, function calling, RAG pipelines, and multi-agent systems.", 80),
        RoadmapMilestone("m9", "System Design & Microservices", "Architecture", 9, MilestoneStatus.LOCKED, 30, "Load balancing, caching strategies, message queues, and scalability.", 85),
        RoadmapMilestone("m10", "Capstone Full-Stack Project", "Engineering", 10, MilestoneStatus.LOCKED, 50, "Autonomous multi-file software engineering project with complete tests.", 90)
    )

    val assessmentQuestions = listOf(
        SkillAssessmentQuestion(
            id = 1,
            category = "JavaScript",
            careerTag = "Full-Stack Developer",
            question = "What is the output of `console.log(typeof null)` and what does it reveal about JavaScript's engine?",
            options = listOf(
                "'object' — a historical legacy bug in JS type tagging",
                "'null' — primitive type representation",
                "'undefined' — because null has no prototype",
                "'number' — treated as zero in binary arithmetic"
            ),
            correctIndex = 0,
            explanation = "In JavaScript, typeof null returns 'object'. This is a well-known legacy behavior from the initial JS implementation where type tags stored 0 for objects, and the null pointer was also 0x00."
        ),
        SkillAssessmentQuestion(
            id = 2,
            category = "React",
            careerTag = "Full-Stack Developer",
            question = "Why should keys passed to list items in React be stable and unique identifiers instead of array indices?",
            options = listOf(
                "Indices prevent React from differentiating item reordering or deletions, causing state bugs",
                "Indices cause TypeScript compilation errors during reconciliation",
                "React keys are required by the browser DOM specification",
                "Keys format the virtual DOM into a binary search tree"
            ),
            correctIndex = 0,
            explanation = "Using array indices as keys can lead to unexpected UI glitches and component state confusion when items are inserted, deleted, or reordered."
        ),
        SkillAssessmentQuestion(
            id = 3,
            category = "Backend & APIs",
            careerTag = "Full-Stack Developer",
            question = "Which HTTP method should be used for an operation that is idempotent and updates or creates a known resource?",
            options = listOf(
                "PUT",
                "POST",
                "PATCH (always non-idempotent)",
                "CONNECT"
            ),
            correctIndex = 0,
            explanation = "PUT is idempotent: sending the exact same PUT request multiple times produces the identical server state as sending it once."
        ),
        SkillAssessmentQuestion(
            id = 4,
            category = "Data Structures",
            careerTag = "Full-Stack Developer",
            question = "What is the average time complexity of searching for an element in a Hash Table with good distribution?",
            options = listOf(
                "O(1)",
                "O(log N)",
                "O(N)",
                "O(N log N)"
            ),
            correctIndex = 0,
            explanation = "With a well-distributed hash function and adequate load factor, hash table lookups execute in O(1) constant time on average."
        ),
        SkillAssessmentQuestion(
            id = 5,
            category = "Databases",
            careerTag = "Full-Stack Developer",
            question = "In relational databases, what does the 'I' in ACID transactions guarantee?",
            options = listOf(
                "Isolation — concurrent transactions do not interfere with each other's execution",
                "Integrity — foreign keys are always verified before commit",
                "Idempotence — duplicate writes are safely ignored",
                "Indexing — all modified fields are immediately re-indexed"
            ),
            correctIndex = 0,
            explanation = "Isolation ensures that concurrent execution of transactions leaves the database in the same state as if they were executed sequentially."
        ),
        SkillAssessmentQuestion(
            id = 6,
            category = "System Architecture",
            careerTag = "Cloud/DevOps Engineer",
            question = "What role does a Reverse Proxy (like NGINX) play in a scalable production web application?",
            options = listOf(
                "SSL termination, load balancing, caching, and shielding internal application servers",
                "Compiling frontend React code on the edge before delivering to users",
                "Executing automated database migrations across replicas",
                "Generating JWT secret tokens for user authentication"
            ),
            correctIndex = 0,
            explanation = "Reverse proxies act as intermediaries handling TLS termination, load distribution among backend instances, compression, and request routing."
        )
    )

    val defaultCourses = listOf(
        Course(
            id = "c1",
            title = "Modern Full-Stack Engineering with React & Node",
            domain = "Full-Stack",
            level = "Intermediate",
            description = "Master end-to-end full-stack development with React 19, TypeScript, Express REST APIs, and production deployment.",
            lessonCount = 8,
            progressPercent = 65,
            isCompleted = false,
            lessons = listOf(
                Lesson(
                    id = "l101",
                    title = "Modern React Hooks & Reactive Patterns",
                    durationMinutes = 24,
                    summary = "Deep dive into useState, useEffect, useMemo, and custom hooks for scalable client state.",
                    codeExample = "const useDebounce = <T>(value: T, delay: number): T => {\n  const [debounced, setDebounced] = useState(value);\n  useEffect(() => {\n    const h = setTimeout(() => setDebounced(value), delay);\n    return () => clearTimeout(h);\n  }, [value, delay]);\n  return debounced;\n};",
                    transcript = "Welcome to Lesson 1! In this module, we will explore why custom hooks allow you to cleanly separate business logic from UI rendering...",
                    quizQuestion = "When does the cleanup function in useEffect execute?",
                    quizOptions = listOf("Before re-running the effect and upon component unmount", "Only when the app crashes", "Synchronously before the initial render", "Whenever state is initialized"),
                    quizCorrectIndex = 0
                ),
                Lesson(
                    id = "l102",
                    title = "Building Robust Express Middleware & JWT Auth",
                    durationMinutes = 30,
                    summary = "Implement token authentication, token refresh rotation, and role-based route guards.",
                    codeExample = "export const verifyToken = (req: Request, res: Response, next: NextFunction) => {\n  const auth = req.headers['authorization'];\n  if (!auth?.startsWith('Bearer ')) return res.status(401).json({ error: 'Unauthorized' });\n  const token = auth.split(' ')[1];\n  jwt.verify(token, process.env.JWT_SECRET!, (err, decoded) => {\n    if (err) return res.status(403).json({ error: 'Token invalid' });\n    req.user = decoded;\n    next();\n  });\n};",
                    transcript = "Security in Node.js requires validating requests early in the middleware pipeline before reaching controller handlers...",
                    quizQuestion = "What status code should be returned when a client provides no credentials?",
                    quizOptions = listOf("401 Unauthorized", "403 Forbidden", "404 Not Found", "500 Internal Error"),
                    quizCorrectIndex = 0
                )
            )
        ),
        Course(
            id = "c2",
            title = "Data Structures & Algorithmic Problem Solving",
            domain = "Core CS",
            level = "All Levels",
            description = "Conquer high-frequency DSA patterns: Two Pointers, Sliding Window, Graph Traversals, and Dynamic Programming.",
            lessonCount = 12,
            progressPercent = 45,
            isCompleted = false,
            lessons = listOf(
                Lesson(
                    id = "l201",
                    title = "Two Pointers & Sliding Window In-Depth",
                    durationMinutes = 28,
                    summary = "Optimize O(N^2) brute force sub-array searches to O(N) linear time.",
                    codeExample = "function maxSubArrayLen(nums: number[], k: number): number {\n  let left = 0, sum = 0, maxLen = 0;\n  for (let right = 0; right < nums.length; right++) {\n    sum += nums[right];\n    while (sum > k && left <= right) {\n      sum -= nums[left++];\n    }\n    maxLen = Math.max(maxLen, right - left + 1);\n  }\n  return maxLen;\n}",
                    transcript = "The sliding window pattern converts nested loop algorithms into linear scans by maintaining a dynamic range...",
                    quizQuestion = "What is the time complexity of a standard sliding window over an array of size N?",
                    quizOptions = listOf("O(N) since each element enters and leaves window at most once", "O(N^2)", "O(log N)", "O(N!)"),
                    quizCorrectIndex = 0
                )
            )
        ),
        Course(
            id = "c3",
            title = "Multi-Agent AI Engineering & LLM Orchestration",
            domain = "AI/ML",
            level = "Advanced",
            description = "Build autonomous multi-agent software development pipelines with Planner, Architect, Coder, and Validator architectures.",
            lessonCount = 6,
            progressPercent = 20,
            isCompleted = false,
            lessons = listOf(
                Lesson(
                    id = "l301",
                    title = "Agentic Software Workflows & State Graphs",
                    durationMinutes = 35,
                    summary = "Orchestrate agent state transitions, tool invocation, and validation loops.",
                    codeExample = "// Agent workflow state\ninterface AgentState {\n  requirements: string[];\n  fileTree: string[];\n  validationErrors: string[];\n  repairAttempts: number;\n}",
                    transcript = "In Agentic Software Engineering, we do not rely on a single monolith prompt. Instead, we structure specialized agents...",
                    quizQuestion = "What is the primary role of a Validator Agent in an AI development team?",
                    quizOptions = listOf("Verify code syntax, structure, dependencies, and requirements before delivery", "Generate user invoices", "Write marketing copy", "Restart the server randomly"),
                    quizCorrectIndex = 0
                )
            )
        )
    )

    val codingProblems = listOf(
        CodingProblem(
            id = "p1",
            title = "Two Sum (Array Hash Map)",
            difficulty = "Easy",
            language = "JavaScript",
            description = "Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.\n\nYou may assume that each input would have exactly one solution, and you may not use the same element twice.",
            starterCode = "function twoSum(nums, target) {\n  const map = new Map();\n  for (let i = 0; i < nums.length; i++) {\n    const complement = target - nums[i];\n    if (map.has(complement)) {\n      return [map.get(complement), i];\n    }\n    map.set(nums[i], i);\n  }\n  return [];\n}",
            testCases = listOf(
                TestCase("nums = [2,7,11,15], target = 9", "[0, 1]"),
                TestCase("nums = [3,2,4], target = 6", "[1, 2]"),
                TestCase("nums = [3,3], target = 6", "[0, 1]")
            ),
            hints = listOf(
                "Can you use a hash map to store previously seen numbers?",
                "For every number `x`, you only need to check if `target - x` exists in the map."
            )
        ),
        CodingProblem(
            id = "p2",
            title = "Valid Parentheses (Stack)",
            difficulty = "Easy",
            language = "JavaScript",
            description = "Given a string `s` containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.\n\nOpen brackets must be closed by the same type of brackets in the correct order.",
            starterCode = "function isValid(s) {\n  const stack = [];\n  const pairs = { ')': '(', '}': '{', ']': '[' };\n  for (const char of s) {\n    if (char === '(' || char === '{' || char === '[') {\n      stack.push(char);\n    } else if (stack.pop() !== pairs[char]) {\n      return false;\n    }\n  }\n  return stack.length === 0;\n}",
            testCases = listOf(
                TestCase("s = '()[]{}'", "true"),
                TestCase("s = '(]'", "false"),
                TestCase("s = '([{}])'", "true")
            ),
            hints = listOf(
                "A LIFO stack matches the most recent opening bracket with incoming closing brackets."
            )
        ),
        CodingProblem(
            id = "p3",
            title = "Reverse Linked List",
            difficulty = "Medium",
            language = "JavaScript",
            description = "Given the head of a singly linked list, reverse the list, and return the reversed list.\nCan you solve it both iteratively and recursively?",
            starterCode = "function reverseList(head) {\n  let prev = null;\n  let curr = head;\n  while (curr !== null) {\n    let next = curr.next;\n    curr.next = prev;\n    prev = curr;\n    curr = next;\n  }\n  return prev;\n}",
            testCases = listOf(
                TestCase("head = [1,2,3,4,5]", "[5,4,3,2,1]"),
                TestCase("head = [1,2]", "[2,1]"),
                TestCase("head = []", "[]")
            ),
            hints = listOf(
                "Keep track of three pointers: previous, current, and next."
            )
        )
    )

    val realWorldProjects = listOf(
        ProjectItem(
            id = "proj_01",
            title = "AgentForge — Multi-Agent Software Development Platform",
            difficulty = "Advanced",
            description = "An agentic software engineering studio that translates natural language requirements into complete, validated, multi-file codebases via Planner, Architect, Coder, Validator, and Debugger agents.",
            techStack = listOf("Kotlin", "Jetpack Compose", "LangGraph Pattern", "LLM APIs", "Room DB"),
            requirements = listOf(
                "Natural language project request",
                "Planner agent requirement extraction",
                "Architect agent file tree generation",
                "Coder agent multi-file generation",
                "Validator agent syntax & dependency check",
                "Debugger agent automated repair loop",
                "Project workspace with file explorer & code viewer",
                "Project ZIP export"
            ),
            githubUrl = "https://github.com/alex-dev/agent-forge-engine",
            demoUrl = "https://agentforge.dev",
            score = 94,
            status = "Reviewed",
            review = AIProjectReview(
                architectureScore = 96,
                codeQualityScore = 92,
                documentationScore = 94,
                strengths = listOf(
                    "Excellent separation of agent concerns with clear state graph",
                    "Robust fallback handling when validator identifies missing exports",
                    "Intuitive file explorer and interactive Monaco-style code viewer"
                ),
                improvements = listOf(
                    "Add automated unit test generation in the Coder stage",
                    "Include Dockerfile generation for containerized execution"
                ),
                summary = "Outstanding full-stack agentic system. Demonstrates deep understanding of multi-agent software lifecycle."
            )
        ),
        ProjectItem(
            id = "proj_02",
            title = "Responsive Expense Tracker & Financial Analytics",
            difficulty = "Intermediate",
            description = "Interactive financial dashboard featuring category breakdown, monthly cash flow charts, budget threshold alerts, and offline storage.",
            techStack = listOf("React", "TypeScript", "Tailwind CSS", "Chart.js", "LocalStorage"),
            requirements = listOf(
                "Add, edit, delete expenses",
                "Filter by category and date ranges",
                "Real-time visual spending breakdown",
                "CSV statement export and import"
            ),
            githubUrl = "https://github.com/alex-dev/expense-pulse",
            demoUrl = "https://expensepulse.app",
            score = 88,
            status = "Reviewed",
            review = AIProjectReview(
                architectureScore = 90,
                codeQualityScore = 86,
                documentationScore = 88,
                strengths = listOf("Clean component composition", "Smooth chart animations"),
                improvements = listOf("Add backend cloud sync with OAuth authentication"),
                summary = "Well-implemented intermediate web project meeting modern design and usability standards."
            )
        ),
        ProjectItem(
            id = "proj_03",
            title = "CloudTask — Real-time Agile Kanban Board",
            difficulty = "Advanced",
            description = "Full-stack collaborative board with drag-and-drop cards, WebSocket live sync, JWT permissions, and sprint burndown analytics.",
            techStack = listOf("Next.js", "Express", "Socket.io", "MongoDB", "Tailwind"),
            requirements = listOf(
                "Drag and drop task cards across columns",
                "Multi-user real-time board updates",
                "Role-based project member permissions",
                "Sprint velocity analytics"
            ),
            githubUrl = "https://github.com/alex-dev/cloudtask-board",
            score = 90,
            status = "Reviewed",
            review = AIProjectReview(
                architectureScore = 92,
                codeQualityScore = 89,
                documentationScore = 89,
                strengths = listOf("Sub-50ms WebSocket broadcast latency", "Resilient optimistic UI updates"),
                improvements = listOf("Add Redis cache layer for high-throughput boards"),
                summary = "Production-grade full-stack project demonstrating strong understanding of event-driven architectures."
            )
        )
    )

    val interviewQuestions = listOf(
        MockInterviewQuestion(
            id = 1,
            role = "Full-Stack Developer",
            category = "Technical",
            difficulty = "Mid-Level",
            question = "Explain how the JavaScript Event Loop handles Promises versus `setTimeout` callbacks. Which queue is executed first?",
            sampleIdealAnswer = "Promises are queued in the Microtask Queue, while setTimeout callbacks are placed in the Macrotask (Callback) Queue. The Event Loop always drains the entire Microtask Queue right after the current call stack finishes and before picking the next task from the Macrotask Queue.",
            keyPoints = listOf("Microtask queue vs Macrotask queue", "Promise.then executes before setTimeout", "Call stack execution order")
        ),
        MockInterviewQuestion(
            id = 2,
            role = "Full-Stack Developer",
            category = "System Design",
            difficulty = "Mid-Level",
            question = "How would you design a caching strategy for an API endpoint that receives 100,000 requests per minute with moderate update frequency?",
            sampleIdealAnswer = "I would adopt a Cache-Aside strategy with Redis. On a GET request, check Redis first; on cache hit, return immediately. On cache miss, fetch from database, populate Redis with a TTL (e.g. 5 minutes). On updates, invalidate or update the cached key to maintain consistency.",
            keyPoints = listOf("Cache-Aside pattern", "Redis / Memcached", "TTL and Cache Invalidation", "Thundering Herd prevention")
        ),
        MockInterviewQuestion(
            id = 3,
            role = "Full-Stack Developer",
            category = "Behavioral",
            difficulty = "All Levels",
            question = "Describe a situation where a critical bug slipped into staging or production. How did you diagnose, resolve, and prevent it?",
            sampleIdealAnswer = "In our project sprint, an unhandled null in an API response broke user checkout. I immediately analyzed the server error logs, identified the missing schema check, deployed a hotfix with optional chaining, and subsequently wrote a comprehensive automated integration test to safeguard the endpoint.",
            keyPoints = listOf("STAR format (Situation, Task, Action, Result)", "Calm analytical debugging", "Root cause post-mortem and automated testing prevention")
        )
    )

    val defaultApplications = listOf(
        JobApplication("app_1", "Stripe", "Frontend Software Engineer", "https://stripe.com/jobs", "2026-09-18", "Interview", "2026-10-05", "Round 2 Technical Interview: React architecture and API integration."),
        JobApplication("app_2", "Google", "Associate Software Engineer", "https://careers.google.com", "2026-09-22", "Assessment", "", "Completed coding assessment (Arrays & DP). Waiting for recruiter feedback."),
        JobApplication("app_3", "Datadog", "Full-Stack Engineer", "https://datadog.com/careers", "2026-09-25", "Applied", "", "Applied with customized resume emphasizing AgentForge and backend systems.")
    )

    val defaultBadges = listOf(
        Badge("b1", "First Project", "Built and submitted your first real-world software project", true, 200),
        Badge("b2", "7-Day Streak", "Consistent coding and learning for 7 consecutive days", true, 150),
        Badge("b3", "JavaScript Pro", "Scored 90%+ in the JavaScript core assessment", true, 250),
        Badge("b4", "Agent Architect", "Built a multi-agent autonomous engineering workflow", true, 500),
        Badge("b5", "Interview Ready", "Completed 3 full AI mock interviews with score > 80%", false, 300),
        Badge("b6", "Full-Stack Builder", "Deployed full-stack app with database and authentication", true, 400)
    )

    val facultyStudentStats = listOf(
        FacultyStudentStat("Alex Rivera", "Full-Stack Developer", 76, listOf("Docker", "System Design"), 3, 85, "Ready for Placements"),
        FacultyStudentStat("Maya Patel", "AI/ML Engineer", 84, listOf("Distributed Systems"), 4, 88, "Top Performer"),
        FacultyStudentStat("Liam Chen", "Cloud/DevOps Engineer", 62, listOf("Kubernetes", "Linux Kernel", "Networking"), 2, 70, "Needs Mentorship"),
        FacultyStudentStat("Sarah Jenkins", "Java Full-Stack", 71, listOf("Microservices", "Spring Security"), 2, 78, "On Track"),
        FacultyStudentStat("Devon Brooks", "Python Full-Stack", 58, listOf("Algorithms", "Async IO", "Database Indexing"), 1, 64, "Revision Triggered")
    )

    val sampleAgentProjects = listOf(
        AgentProject(
            id = "agent_p1",
            name = "Expense Pulse Dashboard",
            prompt = "Build a responsive expense tracker using React with category filtering, visual charts, and local storage.",
            status = "READY",
            techStack = listOf("React", "TypeScript", "Tailwind CSS", "LocalStorage"),
            files = listOf(
                AgentFile(
                    path = "src/App.tsx",
                    language = "typescript",
                    content = """
import React, { useState, useEffect } from 'react';
import { ExpenseForm } from './components/ExpenseForm';
import { ExpenseList } from './components/ExpenseList';
import { ExpenseSummary } from './components/ExpenseSummary';

export interface Expense {
  id: string;
  title: string;
  amount: number;
  category: 'Food' | 'Transport' | 'Housing' | 'Entertainment' | 'Other';
  date: string;
}

export default function App() {
  const [expenses, setExpenses] = useState<Expense[]>(() => {
    const saved = localStorage.getItem('expenses_data');
    return saved ? JSON.parse(saved) : [
      { id: '1', title: 'Groceries', amount: 54.20, category: 'Food', date: '2026-09-28' },
      { id: '2', title: 'Metro Pass', amount: 30.00, category: 'Transport', date: '2026-09-29' }
    ];
  });

  useEffect(() => {
    localStorage.setItem('expenses_data', JSON.stringify(expenses));
  }, [expenses]);

  const addExpense = (exp: Omit<Expense, 'id'>) => {
    const newExp = { ...exp, id: Date.now().toString() };
    setExpenses(prev => [newExp, ...prev]);
  };

  const deleteExpense = (id: string) => {
    setExpenses(prev => prev.filter(e => e.id !== id));
  };

  return (
    <div className="min-h-screen bg-slate-50 p-6 max-w-4xl mx-auto">
      <header className="mb-8">
        <h1 className="text-3xl font-bold text-slate-900">Expense Pulse</h1>
        <p className="text-slate-500">Track spending, balance budgets, and review monthly cash flow.</p>
      </header>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-1">
          <ExpenseForm onAdd={addExpense} />
        </div>
        <div className="md:col-span-2 space-y-6">
          <ExpenseSummary expenses={expenses} />
          <ExpenseList expenses={expenses} onDelete={deleteExpense} />
        </div>
      </div>
    </div>
  );
}
""".trimIndent()
                ),
                AgentFile(
                    path = "src/components/ExpenseForm.tsx",
                    language = "typescript",
                    content = """
import React, { useState } from 'react';
import { Expense } from '../App';

interface Props {
  onAdd: (expense: Omit<Expense, 'id'>) => void;
}

export const ExpenseForm: React.FC<Props> = ({ onAdd }) => {
  const [title, setTitle] = useState('');
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState<Expense['category']>('Food');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !amount) return;
    onAdd({
      title,
      amount: parseFloat(amount),
      category,
      date: new Date().toISOString().split('T')[0]
    });
    setTitle('');
    setAmount('');
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-4">
      <h2 className="text-lg font-semibold text-slate-800">Add New Expense</h2>
      <div>
        <label className="text-xs font-medium text-slate-500 uppercase">Item Description</label>
        <input
          value={title}
          onChange={e => setTitle(e.target.value)}
          placeholder="e.g. AWS Subscription"
          className="w-full mt-1 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-sky-500"
          required
        />
      </div>
      <div>
        <label className="text-xs font-medium text-slate-500 uppercase">Amount ($)</label>
        <input
          type="number"
          step="0.01"
          value={amount}
          onChange={e => setAmount(e.target.value)}
          placeholder="0.00"
          className="w-full mt-1 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-sky-500"
          required
        />
      </div>
      <div>
        <label className="text-xs font-medium text-slate-500 uppercase">Category</label>
        <select
          value={category}
          onChange={e => setCategory(e.target.value as any)}
          className="w-full mt-1 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-sky-500 bg-white"
        >
          <option value="Food">Food & Dining</option>
          <option value="Transport">Transportation</option>
          <option value="Housing">Housing & Utilities</option>
          <option value="Entertainment">Entertainment</option>
          <option value="Other">Other</option>
        </select>
      </div>
      <button
        type="submit"
        className="w-full py-2.5 bg-sky-600 hover:bg-sky-700 text-white font-medium rounded-lg transition"
      >
        Record Expense
      </button>
    </form>
  );
};
""".trimIndent()
                ),
                AgentFile(
                    path = "src/components/ExpenseSummary.tsx",
                    language = "typescript",
                    content = """
import React from 'react';
import { Expense } from '../App';

export const ExpenseSummary: React.FC<{ expenses: Expense[] }> = ({ expenses }) => {
  const total = expenses.reduce((sum, e) => sum + e.amount, 0);
  const byCategory = expenses.reduce((acc, e) => {
    acc[e.category] = (acc[e.category] || 0) + e.amount;
    return acc;
  }, {} as Record<string, number>);

  return (
    <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-sm font-medium text-slate-500">Total Monthly Spend</h3>
        <span className="text-2xl font-bold text-sky-600">${'$'}{total.toFixed(2)}</span>
      </div>
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
        {Object.entries(byCategory).map(([cat, amount]) => (
          <div key={cat} className="p-3 bg-sky-50 rounded-xl border border-sky-100">
            <p className="text-xs text-slate-500">{cat}</p>
            <p className="font-semibold text-slate-800">${'$'}{amount.toFixed(2)}</p>
          </div>
        ))}
      </div>
    </div>
  );
};
""".trimIndent()
                ),
                AgentFile(
                    path = "package.json",
                    language = "json",
                    content = """
{
  "name": "expense-pulse",
  "version": "1.0.0",
  "private": true,
  "dependencies": {
    "react": "^18.3.1",
    "react-dom": "^18.3.1",
    "lucide-react": "^0.383.0"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build"
  }
}
""".trimIndent()
                ),
                AgentFile(
                    path = "README.md",
                    language = "markdown",
                    content = """
# Expense Pulse — Automated Generation by AgentForge

Built by AgentForge multi-agent team:
- **Planner Agent**: Analyzed user requirement, formulated functional requirements.
- **Architect Agent**: Designed React component hierarchy, LocalStorage persistence, and type interfaces.
- **Coder Agent**: Generated `App.tsx`, `ExpenseForm.tsx`, `ExpenseSummary.tsx`, and configuration files.
- **Validator Agent**: Verified 0 syntax errors, valid dependencies, and requirement fulfillment.
- **Debugger Agent**: 0 repairs needed. Validation passed on first pass.
""".trimIndent()
                )
            ),
            agentLogs = listOf(
                AgentLog("PLANNER", "SUCCESS", "Extracted 4 requirements: add expense, delete expense, category breakdown, localStorage persistence."),
                AgentLog("ARCHITECT", "SUCCESS", "Generated architecture: 5 files, React 18, Tailwind CSS, TypeScript typings."),
                AgentLog("CODER", "SUCCESS", "Generated src/App.tsx, components, and package.json."),
                AgentLog("VALIDATOR", "SUCCESS", "Structural validation passed. All components declared and imported correctly."),
                AgentLog("DEBUGGER", "SUCCESS", "No repairs needed. Project verified."),
                AgentLog("SYSTEM", "SUCCESS", "Project is ready for live preview and ZIP export.")
            ),
            validationResult = ValidationResult(
                passed = true,
                errors = emptyList(),
                warnings = emptyList(),
                repairAttempts = 0
            ),
            repairAttempts = 0,
            createdAt = System.currentTimeMillis() - 7200000L // 2 hours ago
        ),
        AgentProject(
            id = "agent_p2",
            name = "Calculator Pro App",
            prompt = "Build a modern interactive Calculator with arithmetic operations, responsive grid, clear button, and error handling.",
            status = "READY",
            techStack = listOf("React", "TypeScript", "Tailwind CSS"),
            files = listOf(
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
                    content = "# Calculator Pro\nAutomated generation by AgentForge Multi-Agent Team with 1 repair pass by Debugger Agent."
                )
            ),
            agentLogs = listOf(
                AgentLog("PLANNER", "SUCCESS", "Requirements: arithmetic keypad, display screen, state persistence."),
                AgentLog("ARCHITECT", "SUCCESS", "Generated architecture: 3 files, grid-based calculator component."),
                AgentLog("CODER", "SUCCESS", "Generated src/App.tsx and package.json."),
                AgentLog("VALIDATOR", "WARNING", "Missing prop type for button click handler."),
                AgentLog("DEBUGGER", "SUCCESS", "Repaired prop typing in App.tsx (Repair pass 1/3)."),
                AgentLog("SYSTEM", "SUCCESS", "Validation Passed. Ready for preview.")
            ),
            validationResult = ValidationResult(
                passed = true,
                errors = emptyList(),
                warnings = listOf("Repaired button handler type"),
                repairAttempts = 1
            ),
            repairAttempts = 1,
            createdAt = System.currentTimeMillis() - 86400000L // 1 day ago
        ),
        AgentProject(
            id = "agent_p3",
            name = "TaskFlow Kanban Management",
            prompt = "Build a collaborative Kanban task board with backlog, in-progress, completed columns and drag simulation.",
            status = "READY",
            techStack = listOf("React", "TypeScript", "Tailwind CSS", "Zustand"),
            files = listOf(
                AgentFile(
                    path = "src/App.tsx",
                    language = "typescript",
                    content = """
import React, { useState } from 'react';

interface Task { id: string; title: string; col: 'todo' | 'doing' | 'done'; }

export default function KanbanApp() {
  const [tasks, setTasks] = useState<Task[]>([
    { id: '1', title: 'Setup database schema', col: 'done' },
    { id: '2', title: 'Implement JWT refresh rotation', col: 'doing' },
    { id: '3', title: 'Write integration test suite', col: 'todo' }
  ]);
  const [newTask, setNewTask] = useState('');

  const addTask = () => {
    if (!newTask) return;
    setTasks(prev => [...prev, { id: Date.now().toString(), title: newTask, col: 'todo' }]);
    setNewTask('');
  };

  const moveTask = (id: string, col: 'todo' | 'doing' | 'done') => {
    setTasks(prev => prev.map(t => t.id === id ? { ...t, col } : t));
  };

  return (
    <div className="min-h-screen bg-slate-900 text-white p-6">
      <h1 className="text-2xl font-bold mb-4">TaskFlow Board</h1>
      <div className="flex gap-2 mb-6">
        <input value={newTask} onChange={e => setNewTask(e.target.value)} placeholder="Add task..." className="bg-slate-800 px-3 py-2 rounded-lg" />
        <button onClick={addTask} className="bg-sky-500 px-4 py-2 rounded-lg font-bold">Add</button>
      </div>
      <div className="grid grid-cols-3 gap-4">
        {(['todo', 'doing', 'done'] as const).map(col => (
          <div key={col} className="bg-slate-800 p-4 rounded-xl">
            <h2 className="uppercase text-xs font-bold text-slate-400 mb-3">{col}</h2>
            {tasks.filter(t => t.col === col).map(t => (
              <div key={t.id} className="bg-slate-700 p-3 rounded-lg mb-2 flex justify-between items-center">
                <span>{t.title}</span>
                {col !== 'done' && <button onClick={() => moveTask(t.id, col === 'todo' ? 'doing' : 'done')} className="text-xs text-sky-400">Next →</button>}
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
}
""".trimIndent()
                ),
                AgentFile(
                    path = "package.json",
                    language = "json",
                    content = """{ "name": "taskflow-kanban", "version": "1.0.0", "dependencies": { "react": "^18.3.1", "react-dom": "^18.3.1" } }"""
                )
            ),
            agentLogs = listOf(
                AgentLog("PLANNER", "SUCCESS", "Requirements: 3 column Kanban, task transitions, local state."),
                AgentLog("ARCHITECT", "SUCCESS", "Architecture: 2 files, atomic task state transitions."),
                AgentLog("CODER", "SUCCESS", "Generated App.tsx and package.json."),
                AgentLog("VALIDATOR", "SUCCESS", "Validation Passed: 0 errors."),
                AgentLog("SYSTEM", "SUCCESS", "Project is ready.")
            ),
            validationResult = ValidationResult(passed = true, errors = emptyList(), warnings = emptyList(), repairAttempts = 0),
            repairAttempts = 0,
            createdAt = System.currentTimeMillis() - 259200000L // 3 days ago
        ),
        AgentProject(
            id = "agent_p4",
            name = "Distributed Cache Gateway",
            prompt = "Build a high-throughput Redis caching reverse proxy with rate limiting and automated circuit breakers.",
            status = "FAILED",
            techStack = listOf("Node.js", "Express", "Redis", "TypeScript"),
            files = listOf(
                AgentFile(
                    path = "src/index.ts",
                    language = "typescript",
                    content = "// Cyclic dependency detected between router and circuit-breaker service"
                ),
                AgentFile(
                    path = "package.json",
                    language = "json",
                    content = """{ "name": "cache-gateway", "version": "0.1.0" }"""
                )
            ),
            agentLogs = listOf(
                AgentLog("PLANNER", "SUCCESS", "Requirements: high-throughput caching, circuit breakers."),
                AgentLog("ARCHITECT", "SUCCESS", "Architecture: Redis adapter, express middleware."),
                AgentLog("CODER", "SUCCESS", "Generated initial modules."),
                AgentLog("VALIDATOR", "ERROR", "Detected cyclic dependency in service bindings."),
                AgentLog("DEBUGGER", "RUNNING", "Attempting resolution 3/3..."),
                AgentLog("DEBUGGER", "ERROR", "Max repair attempts (3) exceeded. Cannot resolve cyclic binding automatically."),
                AgentLog("SYSTEM", "ERROR", "Project generation failed. Review error logs.")
            ),
            validationResult = ValidationResult(
                passed = false,
                errors = listOf("Cyclic dependency in service bindings", "Max repair limit (3) exceeded"),
                warnings = emptyList(),
                repairAttempts = 3
            ),
            repairAttempts = 3,
            createdAt = System.currentTimeMillis() - 432000000L // 5 days ago
        )
    )
}
