# BE-java-Eduhub

## Overview

BE-java-Eduhub is a backend application for managing educational resources, including courses, professors, students, quizzes, and more. The application is built using Java and Javalin, and it interacts with a PostgreSQL database. It also includes QR code generation for lesson attendance.

## Sommario

BE-java-Eduhub è un'applicazione backend per la gestione delle risorse educative, inclusi corsi, professori, studenti, quiz e altro. L'applicazione è costruita utilizzando Java e Javalin e interagisce con un database PostgreSQL. Include anche la generazione di codici QR per la partecipazione alle lezioni.

## Features / Funzionalità

- Manage courses, professors, students, and lessons
- Create and evaluate quizzes
- Generate QR codes for lesson attendance
- RESTful API endpoints for CRUD operations

- Gestione di corsi, professori, studenti e lezioni
- Creazione e valutazione di quiz
- Generazione di codici QR per la partecipazione alle lezioni
- Endpoint API RESTful per operazioni CRUD

## Technologies Used / Tecnologie Utilizzate

- Java 21
- Javalin
- PostgreSQL
- Lombok
- ZXing (for QR code generation)

- Java 21
- Javalin
- PostgreSQL
- Lombok
- ZXing (per la generazione di codici QR)

## Project Structure / Struttura del Progetto
src/ main/ java/ com/ francesco_giuliante/ infobasic/ controller/ dao/ model/ service/ utility/ resources/ test/ java/



## How to Run / Come Avviare

1. Clone the repository / Clonare il repository:
    ```sh
    git clone [repository-url](https://github.com/FrancescoGiuliante/BE-java-Eduhub.git)
    ```

2. Navigate to the project directory / Navigare nella directory del progetto:
    ```sh
    cd BE-java-Eduhub
    ```

3. Build the project using Maven / Costruire il progetto usando Maven:
    ```sh
    mvn clean install
    ```

4. Run the application / Avviare l'applicazione:
    ```sh
    mvn exec:java -Dexec.mainClass="com.francesco_giuliante.infobasic.Main"
    ```

## Database Configuration / Configurazione del Database

Ensure that PostgreSQL is installed and running. Update the database connection settings in [DatabaseConnection](http://_vscodecontentref_/0) class located at [DatabaseConnection.java](http://_vscodecontentref_/1).

Assicurarsi che PostgreSQL sia installato e in esecuzione. Aggiornare le impostazioni di connessione al database nella classe [DatabaseConnection](http://_vscodecontentref_/2) situata in [DatabaseConnection.java](http://_vscodecontentref_/3).

## API Endpoints / Endpoint API

### Professors / Professori

- `GET /professors` - Get all professors / Ottenere tutti i professori
- `POST /professor` - Create a new professor / Creare un nuovo professore
- `GET /professor/{id}` - Get professor by ID / Ottenere un professore per ID
- `DELETE /professor/{id}` - Delete professor by ID / Eliminare un professore per ID
- `PUT /professor/{id}` - Update professor by ID / Aggiornare un professore per ID

### Students / Studenti

- `GET /students` - Get all students / Ottenere tutti gli studenti
- `POST /student` - Create a new student / Creare un nuovo studente
- `GET /student/{id}` - Get student by ID / Ottenere uno studente per ID
- `DELETE /student/{id}` - Delete student by ID / Eliminare uno studente per ID
- `PUT /student/{id}` - Update student by ID / Aggiornare uno studente per ID

### Courses / Corsi

- `GET /courses` - Get all courses / Ottenere tutti i corsi
- `POST /course` - Create a new course / Creare un nuovo corso
- `GET /course/{id}` - Get course by ID / Ottenere un corso per ID
- `DELETE /course/{id}` - Delete course by ID / Eliminare un corso per ID
- `PUT /course/{id}` - Update course by ID / Aggiornare un corso per ID

### Lessons / Lezioni

- `GET /lessons` - Get all lessons / Ottenere tutte le lezioni
- `POST /lesson` - Create a new lesson / Creare una nuova lezione
- `GET /lesson/{id}` - Get lesson by ID / Ottenere una lezione per ID
- `DELETE /lesson/{id}` - Delete lesson by ID / Eliminare una lezione per ID
- `PUT /lesson/{id}` - Update lesson by ID / Aggiornare una lezione per ID

### Quizzes / Quiz

- `GET /quizzes` - Get all quizzes / Ottenere tutti i quiz
- `POST /quiz` - Create a new quiz / Creare un nuovo quiz
- `GET /quiz/{id}` - Get quiz by ID / Ottenere un quiz per ID
- `DELETE /quiz/{id}` - Delete quiz by ID / Eliminare un quiz per ID
- `PUT /quiz/{id}` - Update quiz by ID / Aggiornare un quiz per ID

### QR Code / Codice QR

- `GET /qr-code/generate/{lessonID}` - Generate QR code for lesson / Generare codice QR per la lezione

## Class Overview / Panoramica delle Classi

### Controllers / Controller

- [ProfessorController](http://_vscodecontentref_/4) - Manages professor-related endpoints / Gestisce gli endpoint relativi ai professori
- `StudentController` - Manages student-related endpoints / Gestisce gli endpoint relativi agli studenti
- [CourseController](http://_vscodecontentref_/5) - Manages course-related endpoints / Gestisce gli endpoint relativi ai corsi
- [LessonController](http://_vscodecontentref_/6) - Manages lesson-related endpoints / Gestisce gli endpoint relativi alle lezioni
- [QuizController](http://_vscodecontentref_/7) - Manages quiz-related endpoints / Gestisce gli endpoint relativi ai quiz
- [QRCodeGeneratorController](http://_vscodecontentref_/8) - Manages QR code generation endpoints / Gestisce gli endpoint per la generazione di codici QR

### DAOs

- `ProfessorDAO` - Data access object for professors / Oggetto di accesso ai dati per i professori
- `StudentDAO` - Data access object for students / Oggetto di accesso ai dati per gli studenti
- [CourseDAO](http://_vscodecontentref_/9) - Data access object for courses / Oggetto di accesso ai dati per i corsi
- `LessonDAO` - Data access object for lessons / Oggetto di accesso ai dati per le lezioni
- [QuizDAO](http://_vscodecontentref_/10) - Data access object for quizzes / Oggetto di accesso ai dati per i quiz

### Services / Servizi

- [ProfessorService](http://_vscodecontentref_/11) - Business logic for professors / Logica di business per i professori
- `StudentService` - Business logic for students / Logica di business per gli studenti
- [CourseService](http://_vscodecontentref_/12) - Business logic for courses / Logica di business per i corsi
- [LessonService](http://_vscodecontentref_/13) - Business logic for lessons / Logica di business per le lezioni
- [QuizService](http://_vscodecontentref_/14) - Business logic for quizzes / Logica di business per i quiz

### Utilities / Utilità

- [DatabaseConnection](http://_vscodecontentref_/15) - Manages database connections / Gestisce le connessioni al database
- [QRCodeGenerator](http://_vscodecontentref_/16) - Generates QR codes / Genera codici QR

## Database Flow / Flusso del Database

The application uses DAOs to interact with the PostgreSQL database. Each DAO class corresponds to a specific entity (e.g., [Professor](http://_vscodecontentref_/17), `Student`, [Course](http://_vscodecontentref_/18)) and provides methods for CRUD operations. The service layer contains the business logic and calls the DAO methods. The controller layer handles HTTP requests and responses, calling the appropriate service methods.

L'applicazione utilizza i DAO per interagire con il database PostgreSQL. Ogni classe DAO corrisponde a una specifica entità (ad esempio, [Professor](http://_vscodecontentref_/19), `Student`, [Course](http://_vscodecontentref_/20)) e fornisce metodi per le operazioni CRUD. Il livello di servizio contiene la logica di business e chiama i metodi DAO. Il livello del controller gestisce le richieste e le risposte HTTP, chiamando i metodi di servizio appropriati.

## License / Licenza

This project is licensed under the MIT License.

Questo progetto è concesso in licenza sotto la Licenza MIT.
