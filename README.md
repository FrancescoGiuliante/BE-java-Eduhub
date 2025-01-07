# BE-java-Eduhub

## Overview

BE-java-Eduhub is a backend application for managing educational resources, including courses, professors, students, quizzes, and more. The application is built using Java and Javalin, and it interacts with a PostgreSQL database. It also includes QR code generation for lesson attendance.

## Features 

- Manage courses, professors, students, and lessons
- Create and evaluate quizzes
- Generate QR codes for lesson attendance
- RESTful API endpoints for CRUD operations


## Technologies Used

- Java 21
- Javalin
- PostgreSQL
- Lombok
- ZXing (for QR code generation)


## Project Structure 
src/ main/ java/ com/ francesco_giuliante/ infobasic/ controller/ dao/ model/ service/ utility/ resources/ test/ java/



## How to Run 

1. Clone the repository / Clonare il repository:
    ```sh
    git clone https://github.com/FrancescoGiuliante/BE-java-Eduhub.git
    ```

2. Navigate to the project directory / Navigare nella directory del progetto:
    ```sh
    cd BE-java-Eduhub
    ```


## Database Configuration / Configurazione del Database

Ensure that PostgreSQL is installed and running. Update the database connection settings in [DatabaseConnection](http://_vscodecontentref_/0) class located at [DatabaseConnection.java](http://_vscodecontentref_/1).


## INSERT Courses
INSERT INTO public.courses (description, path)
VALUES
('Graphic Design is a dynamic and creative field that blends art and technology to communicate ideas visually. This path offers you the opportunity to explore the world of design and develop skills that are highly sought after in today’s digital world. Whether you\'re interested in creating logos, websites, advertisements, or even mobile apps, this program will equip you with the tools you need to bring your creative visions to life. Through a combination of theoretical knowledge and practical skills, you will learn to manipulate images, understand typography, master color theory, and much more.\n\nThe Graphic Design curriculum is carefully structured to guide you through the essential elements of design. You’ll start with foundational courses like Introduction to Graphic Design, where you\'ll explore the principles of design and the creative process. As you progress, you\'ll dive deeper into more specialized topics like Adobe Photoshop and Illustrator, Typography, Digital Illustration, and Web Design. You\'ll also learn the key skills for branding and marketing, preparing you for the fast-paced world of professional design.\n\nIn addition to mastering design software, you will be taught how to think critically and creatively, develop a keen eye for detail, and create designs that speak to diverse audiences. You\'ll be working on real-world projects that help you build a strong portfolio to showcase your talent and creativity to potential employers or clients. Whether you are interested in working in advertising agencies, design firms, or as a freelancer, this path opens the door to a wealth of exciting opportunities in a constantly evolving industry.\n\nSome of the subjects you will study include:\n- **Typography Fundamentals**: Learn the art of selecting and arranging type to create visually appealing and readable designs.\n- **Branding and Identity Design**: Understand the principles behind creating and maintaining strong brand identities that resonate with consumers.\n- **Web and Mobile Interface Design**: Gain skills in designing user-friendly, visually appealing websites and apps.\n- **Adobe Creative Suite Mastery**: Become proficient in industry-standard tools like Photoshop, Illustrator, and InDesign.\n- **3D Design and Animation**: Explore the world of motion graphics and 3D design to create dynamic visuals.\n- **Marketing and Advertising Design**: Learn how to design marketing materials that effectively communicate brand messages.\n\nBy the end of the program, you will have a diverse skill set, an impressive portfolio, and the confidence to embark on a successful career in the world of graphic design. Join us and unlock your potential as a creative designer ready to shape the future of visual communication.',
'Graphic Design'),

('Software Development is a constantly evolving and highly rewarding field that empowers you to shape the future through technology. In this path, you\'ll gain the knowledge and skills needed to build powerful software solutions that drive innovation, solve complex problems, and improve the way we live and work. Whether you want to develop mobile apps, web applications, or even software tools for businesses, this program will provide you with a solid foundation in the principles and practices of software development.\n\nThis curriculum is designed to provide both theoretical knowledge and hands-on experience with the latest technologies used by industry professionals. Starting with the fundamentals of programming, you’ll learn languages like Python, Java, and JavaScript, which are key to developing software in today’s world. As you advance through the program, you’ll explore more advanced topics, including data structures, algorithms, software architecture, and cloud computing. By the time you complete this path, you will have the expertise to create efficient, scalable, and secure applications.\n\nThroughout the program, you’ll also have the chance to work on real-world projects, building applications from the ground up and learning the full development lifecycle. This will help you not only understand how software is built but also develop the problem-solving skills necessary to tackle any coding challenge that comes your way. Additionally, you\'ll gain experience with industry-standard tools and practices like version control (Git), Agile development, and testing, ensuring you’re ready to meet the demands of the professional world.\n\nSome of the subjects you will study include:\n- **Introduction to Programming**: Learn the fundamentals of coding and the logic behind creating software with languages like Python and Java.\n- **Web Development**: Build websites and web applications using front-end (HTML, CSS, JavaScript) and back-end (Node.js, Express, databases) technologies.\n- **Mobile Application Development**: Learn how to develop applications for iOS and Android platforms using Swift and Kotlin.\n- **Software Design and Architecture**: Understand the principles of software design, patterns, and architectural structures used in professional software systems.\n- **Algorithms and Data Structures**: Master the concepts of algorithms and data structures to optimize software performance.\n- **Cloud Computing and DevOps**: Gain an understanding of cloud platforms like AWS and Azure, and learn how to deploy and maintain applications in a cloud environment.\n- **Agile Development and Version Control**: Get hands-on experience with version control systems like Git and learn how to work in Agile teams using modern software development methodologies.\n\nBy the end of the program, you\'ll have developed a strong portfolio showcasing your skills, and you will be prepared for a wide range of career opportunities in the fast-paced tech industry. Whether you dream of working for a major tech company, starting your own software business, or becoming a freelance developer, this path will equip you with the tools to succeed. Join us and begin your journey towards becoming a skilled software developer, ready to make an impact in the digital world.',
'Software Development'),

('The Design path is your gateway to a world of creativity and innovation, where you will learn how to transform ideas into visual masterpieces. In this path, you’ll explore the fundamental principles of design, focusing on both digital and print mediums, while mastering the latest tools and techniques to create compelling, user-centered designs that leave a lasting impact. Whether you\'re interested in graphic design, UX/UI design, branding, or web design, this program will equip you with the skills needed to succeed in today\'s fast-paced, design-driven world.\n\nDesign is more than just making things look good; it’s about solving problems, communicating messages effectively, and creating experiences that resonate with audiences. In this program, you will learn how to think critically and creatively, combining aesthetic principles with functionality to produce designs that meet real-world needs. From designing logos to crafting interactive web interfaces, you\'ll gain hands-on experience with the design process, from brainstorming and prototyping to final execution.\n\nSome of the subjects you will study include:\n- **Graphic Design Basics**: Learn the fundamentals of design theory, including typography, color theory, composition, and layout, to create visually compelling designs.\n- **Branding and Identity Design**: Understand the importance of branding and how to create visual identities for businesses, products, and services that stand out in a crowded marketplace.\n- **User Experience (UX) Design**: Dive into the world of UX design, learning how to create intuitive, accessible, and user-friendly digital experiences that meet the needs of your target audience.\n- **User Interface (UI) Design**: Master the art of designing beautiful and functional interfaces, using tools like Figma and Adobe XD to create seamless interactions.\n- **Web Design and Development**: Learn the principles of designing responsive and user-friendly websites, and gain an understanding of basic HTML, CSS, and JavaScript to bring your designs to life.\n- **Illustration and Digital Art**: Explore the world of digital illustration, creating original artwork and designs using software like Adobe Illustrator and Procreate.\n- **Motion Graphics and Animation**: Develop your skills in motion design, learning how to create engaging animations for websites, apps, and social media.\n- **Design Thinking and Problem Solving**: Learn how to approach design challenges using the Design Thinking methodology, which focuses on empathy, iteration, and collaboration to solve real-world problems.\n\nThroughout the program, you\'ll work on real-world projects, building a diverse portfolio that showcases your skills in various design disciplines. You\'ll gain experience working with industry-standard software like Adobe Creative Suite, Figma, Sketch, and InVision, and learn how to communicate your ideas effectively through design presentations.\n\nBy the end of this path, you’ll be ready to launch your career as a designer, whether you want to join a design agency, work in-house for a corporation, or freelance as an independent designer. You will have a strong portfolio, a deep understanding of design principles, and the practical skills to create meaningful designs that can inspire and influence. Join the Design path and begin shaping the future of visual communication with your creativity.',
'Design'),

('The Cyber Security path is designed to equip you with the knowledge and skills needed to protect sensitive information, secure digital systems, and combat growing cyber threats. In this program, you’ll dive deep into the world of information security, learning how to protect networks, data, and applications from malicious attacks. As cyber threats become more sophisticated and frequent, professionals in this field are in high demand, making it an exciting and rewarding career choice.\n\nCyber security is at the forefront of the digital age. From safeguarding personal data to protecting large-scale enterprise systems, the importance of security in today’s connected world cannot be overstated. This program will provide you with the technical expertise and problem-solving abilities required to defend against cyber-attacks, implement effective security measures, and ensure the integrity of critical infrastructures.\n\nSome of the subjects you will study include:\n- **Introduction to Cyber Security**: Learn the fundamental concepts of cyber security, including types of attacks, encryption, and security protocols that protect digital information.\n- **Network Security**: Understand the principles of securing network infrastructures, including firewalls, VPNs, and intrusion detection systems (IDS), to prevent unauthorized access.\n- **Ethical Hacking and Penetration Testing**: Gain hands-on experience in ethical hacking techniques, testing the security of systems by simulating cyber-attacks to identify vulnerabilities before malicious hackers can exploit them.\n- **Cryptography**: Learn the art of protecting information through encryption algorithms and cryptographic protocols to ensure data privacy and integrity in communication.\n- **Incident Response and Forensics**: Understand how to respond to security breaches, investigate cyber incidents, and recover from data loss. Learn about digital forensics tools and techniques used to analyze attacks.\n- **Malware Analysis and Prevention**: Study the different types of malware (viruses, worms, Trojans) and develop strategies to prevent, detect, and remove malicious software.\n- **Security Architecture and Design**: Learn how to design secure systems and applications from the ground up, ensuring that security is embedded into every stage of development and deployment.\n- **Cloud Security**: Explore the unique security challenges of cloud computing and how to protect cloud-based environments, including data storage, access management, and securing cloud applications.\n- **Risk Management and Compliance**: Learn to assess and manage security risks, ensure compliance with relevant laws and regulations, and develop strategies for maintaining data privacy.\n\nThroughout the program, you will have the opportunity to work on real-world scenarios, participating in hands-on labs and simulations to practice your skills. You\'ll gain proficiency in industry-standard security tools like Kali Linux, Wireshark, and Metasploit, and develop a strong understanding of how to defend systems in various environments, from small businesses to large enterprises.\n\nBy the end of this path, you’ll be prepared for a successful career as a cyber security specialist, network security analyst, penetration tester, or security consultant. The program’s comprehensive curriculum ensures you are well-equipped to protect against evolving cyber threats and contribute to the digital security of organizations worldwide. Join the Cyber Security path and become a guardian of the digital world, ensuring privacy, integrity, and security for everyone.',
'Cyber Security');

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

### Utilities

- [DatabaseConnection](http://_vscodecontentref_/15) - Manages database connections / Gestisce le connessioni al database
- [QRCodeGenerator](http://_vscodecontentref_/16) - Generates QR codes / Genera codici QR

## Database Flow 

The application uses DAOs to interact with the PostgreSQL database. Each DAO class corresponds to a specific entity (e.g., [Professor](http://_vscodecontentref_/17), `Student`, [Course](http://_vscodecontentref_/18)) and provides methods for CRUD operations. The service layer contains the business logic and calls the DAO methods. The controller layer handles HTTP requests and responses, calling the appropriate service methods.


## License
This project is licensed under the MIT License.
