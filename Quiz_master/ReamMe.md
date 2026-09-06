Quiz Master

A simple quiz application built with Java, Spring Boot and PostgreSQL. It supports creating questions and quizzes, starting quiz attempts, answering questions, and tracking quiz activity.

@Admin username =admin123
password= admin456

@user can create its own username and password and can access the system.

Tech Stack

- Java 17
- Spring Boot 4.1.1
- PostgreSQL 17
- Spring Data JPA
- Maven
- HTML/CSS/JS

How to Run

1. Install Java

Install Java 17 JDK and make sure it is available in your PATH.

java -version

2. Start PostgreSQL

docker run --name quiz-postgres `
  -e POSTGRES_DB=quizmaster `
  -e POSTGRES_USER=quizuser `
  -e POSTGRES_PASSWORD=quizpass `
  -p 5432:5432 `
  -v quiz-postgres-data:/var/lib/postgresql/data `
  -d postgres:17

3. Clone the Project

git clone https://github.com/arij33tt/Quiz_Master.git
cd Quiz_Master

4. Run the Backend

The backend has two Spring Boot services:

- "Backend/AdminServ" - creates questions and quizzes
- "Backend/UserServ" - handles quiz attempts

Run them from separate terminals:

cd Backend/AdminServ
.\mvnw.cmd spring-boot:run

cd Backend/UserServ
.\mvnw.cmd spring-boot:run

If required, run the services on different ports.

Database

The application uses PostgreSQL 17.

Database: quizmaster
Username: quizuser
Password: quizpass
Host: localhost
Port: 5432

User ID

There is no login system in this assignment. The user is identified using a "userID" stored with the quiz attempt.

API Endpoints

Create a Question

POST /admin/{topicId}
Content-Type: application/json

Example:

{
  "question": "Which keyword is used to inherit a class in Java?",
  "option1": "this",
  "option2": "extends",
  "option3": "implements",
  "option4": "super",
  "correct": ["2"]
}

Create a Quiz

POST /admin/quiz
Content-Type: application/json

Example:

{
  "quizID": 1,
  "topicId": "java",
  "numberOfQuestion": 5,
  "timeLimit": 10
}

Start a Quiz

POST /start/{quizID}

Example:

POST /start/1

Submit an Answer

POST /quiz/{attemptID}
Content-Type: application/json

Example:

{
  "question": "Which keyword is used to inherit a class in Java?",
  "option1": "this",
  "option2": "extends",
  "option3": "implements",
  "option4": "super",
  "answer": 2
}

Heartbeat

Updates the activity of an ongoing quiz attempt.

GET /quiz/{attemptID}/heartbeat

Example:

GET /quiz/1/heartbeat

Test Data

There is currently no separate seed script in the repository. Questions can be created using the question API and then used to create a quiz.

Before Opening the Code

The project is split into two Spring Boot applications. "AdminServ" handles question and quiz creation, while "UserServ" handles quiz attempts. The frontend is kept separately under "Frontend".

Not Finished

The quiz attempt data is stored in the backend, including the score and the questions/answers from the attempt. Currently, the frontend only displays the score for a previous attempt. The detailed questions, selected answers and correct answers are not displayed yet. I would add a history view in the frontend to show this information from the data that is already stored.

A dedicated test-data/seed script is also not included yet. I would add one to automatically create a sample question bank and quiz.

How I Would Run This in Production

For production, I would run both Spring Boot services as Docker containers with PostgreSQL. I would move database credentials to environment variables, add authentication, database migrations, backups, logging and health checks, and put the services behind HTTPS. For this assignment, everything is kept local and simple with no paid or external services.