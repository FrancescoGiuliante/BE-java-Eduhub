-- Table for storing student information
CREATE TABLE students (
    id SERIAL PRIMARY KEY,  -- Student ID
    user_id INT UNIQUE,  -- Reference to User in Node
    name VARCHAR(255),  -- Student's first name
    last_name VARCHAR(255),  -- Student's last name
    email VARCHAR(255)  -- Student's email address
);

-- Table for storing professor information
CREATE TABLE professors (
    id SERIAL PRIMARY KEY,  -- Professor ID
    user_id INT UNIQUE,  -- Reference to User in Node
    name VARCHAR(255),  -- Professor's first name
    last_name VARCHAR(255),  -- Professor's last name
    email VARCHAR(255),  -- Professor's email address
    biography TEXT  -- Professor's biography
);

-- Table for storing subjects (subjects/courses)
CREATE TABLE subjects (
    id SERIAL PRIMARY KEY,  -- Subject ID
    name VARCHAR(255),  -- Subject name
    description TEXT  -- Subject description
);

-- Table for storing course information
CREATE TABLE courses (
    id SERIAL PRIMARY KEY,  -- Course ID
    description TEXT,  -- Course description
    path VARCHAR(255)  -- Course path (location)
);

-- Table for storing class information
CREATE TABLE classes (
    id SERIAL PRIMARY KEY,  -- Class ID
    path VARCHAR(255),  -- Path (location)
    name VARCHAR(255),  -- Class name
    syllabus TEXT,  -- Class syllabus
    course_id INT,  -- Reference to the course
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE  -- Cascade delete if course is deleted
);

-- Many-to-many relationship table for students and classes
CREATE TABLE class_students (
    class_id INT,
    student_id INT,
    PRIMARY KEY (class_id, student_id),  -- Composite primary key for class-student relationship
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,  -- Cascade delete if class is deleted
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE  -- Cascade delete if student is deleted
);

-- Many-to-many relationship table for professors and classes
CREATE TABLE class_professors (
    class_id INT,
    professor_id INT,
    PRIMARY KEY (class_id, professor_id),  -- Composite primary key for class-professor relationship
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,  -- Cascade delete if class is deleted
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE  -- Cascade delete if professor is deleted
);

-- Table for storing lesson information
CREATE TABLE lessons (
    id SERIAL PRIMARY KEY,  -- Lesson ID
    professor_id INT,  -- Reference to professor
    subject_id INT,  -- Reference to subject
    date DATE,  -- Date of the lesson
    class_id INT,  -- Reference to class
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,  -- Cascade delete if professor is deleted
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,  -- Cascade delete if subject is deleted
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE  -- Cascade delete if class is deleted
);

-- Table for storing lesson participation records
CREATE TABLE lesson_participations (
    lesson_id INT,
    student_id INT,
    PRIMARY KEY (lesson_id, student_id),  -- Composite primary key for lesson-participation relationship
    FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE,  -- Cascade delete if lesson is deleted
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE  -- Cascade delete if student is deleted
);

-- Table for storing weekly program information
CREATE TABLE week_program (
    id SERIAL PRIMARY KEY,  -- Week program ID
    start_date DATE,  -- Start date of the week
    end_date DATE,  -- End date of the week
    class_id INT,  -- Reference to class
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE  -- Cascade delete if class is deleted
);

-- Table for storing rule information
CREATE TABLE rules (
    id SERIAL PRIMARY KEY,  -- Rule ID
    description TEXT,  -- Description of the rule
    value_right_answer DOUBLE PRECISION,  -- Value of the right answer
    value_wrong_answer DOUBLE PRECISION,  -- Value of the wrong answer
    duration INT  -- Duration in minutes for the quiz
);

-- Table for storing quiz information
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,  -- Quiz ID
    rule_id INT,  -- Reference to the rule
    subject_id INT,  -- Reference to subject
    professor_id INT,  -- Reference to professor
    attempts INT,  -- Number of attempts allowed
    average_rating DOUBLE PRECISION,  -- Average rating of the quiz
    FOREIGN KEY (rule_id) REFERENCES rules(id) ON DELETE CASCADE,  -- Cascade delete if rule is deleted
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,  -- Cascade delete if subject is deleted
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE  -- Cascade delete if professor is deleted
);

-- Table for storing question information
CREATE TABLE questions (
    id SERIAL PRIMARY KEY,  -- Question ID
    prompt TEXT,  -- The prompt of the question
    answers TEXT[],  -- List of possible answers (array)
    correct_answer INT,  -- Index of the correct answer
    wrongs INT DEFAULT 0,  -- Number of wrong answers given
    rights INT DEFAULT 0,  -- Number of correct answers given
    professor_id INT,  -- Reference to professor
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE  -- Cascade delete if professor is deleted
);

-- Table for mapping quiz and questions (many-to-many)
CREATE TABLE quiz_questions (
    quiz_id INT,
    question_id INT,
    PRIMARY KEY (quiz_id, question_id),  -- Composite primary key for quiz-question relationship
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,  -- Cascade delete if quiz is deleted
    FOREIGN KEY (question_id) REFERENCES questions(id)  -- Questions are not deleted if quiz is deleted
);

-- Many-to-many relationship table for subjects and professors
CREATE TABLE subject_professors (
    subject_id INT,
    professor_id INT,
    PRIMARY KEY (subject_id, professor_id),  -- Composite primary key for subject-professor relationship
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,  -- Cascade delete if subject is deleted
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE  -- Cascade delete if professor is deleted
);

-- Many-to-many relationship table for classes and subjects
CREATE TABLE class_subjects (
    class_id INT,
    subject_id INT,
    PRIMARY KEY (class_id, subject_id),  -- Composite primary key for class-subject relationship
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,  -- Cascade delete if class is deleted
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE  -- Cascade delete if subject is deleted
);

-- Table for storing student quiz scores
CREATE TABLE votes (
    id SERIAL PRIMARY KEY,  -- Vote ID
    quiz_id INT,  -- Reference to quiz
    student_id INT,  -- Reference to student
    date DATE,  -- Date when the vote was made
    result DOUBLE PRECISION,  -- Result of the vote
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,  -- Cascade delete if quiz is deleted
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE  -- Cascade delete if student is deleted
);
