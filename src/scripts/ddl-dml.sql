CREATE TABLE IF NOT EXISTS public.class_professors
(
    class_id integer NOT NULL,
    professor_id integer NOT NULL,
    CONSTRAINT class_professors_pkey PRIMARY KEY (class_id, professor_id)
);

CREATE TABLE IF NOT EXISTS public.class_students
(
    class_id integer NOT NULL,
    student_id integer NOT NULL,
    CONSTRAINT class_students_pkey PRIMARY KEY (class_id, student_id)
);

CREATE TABLE IF NOT EXISTS public.class_subjects
(
    class_id integer NOT NULL,
    subject_id integer NOT NULL,
    CONSTRAINT class_subjects_pkey PRIMARY KEY (class_id, subject_id)
);

CREATE TABLE IF NOT EXISTS public.classes
(
    id serial NOT NULL,
    path character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    syllabus text COLLATE pg_catalog."default",
    course_id integer,
    CONSTRAINT classes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.courses
(
    id serial NOT NULL,
    description text COLLATE pg_catalog."default",
    path character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.lesson_participations
(
    lesson_id integer NOT NULL,
    student_id integer NOT NULL,
    CONSTRAINT lesson_participations_pkey PRIMARY KEY (lesson_id, student_id)
);

CREATE TABLE IF NOT EXISTS public.lessons
(
    id serial NOT NULL,
    professor_id integer,
    subject_id integer,
    date date,
    class_id integer,
    CONSTRAINT lessons_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.professors
(
    id serial NOT NULL,
    user_id integer,
    name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    biography text COLLATE pg_catalog."default",
    CONSTRAINT professors_pkey PRIMARY KEY (id),
    CONSTRAINT unique_user_id_professor UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS public.questions
(
    id serial NOT NULL,
    prompt text COLLATE pg_catalog."default",
    answers text[] COLLATE pg_catalog."default",
    correct_answer text COLLATE pg_catalog."default",
    wrongs integer DEFAULT 0,
    rights integer DEFAULT 0,
    professor_id integer,
    CONSTRAINT questions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.quiz_questions
(
    quiz_id integer NOT NULL,
    question_id integer NOT NULL,
    CONSTRAINT quiz_questions_pkey PRIMARY KEY (quiz_id, question_id)
);

CREATE TABLE IF NOT EXISTS public.quizzes
(
    id serial NOT NULL,
    subject_id integer,
    professor_id integer,
    attempts integer DEFAULT 0,
    average_rating double precision,
    rule_id integer,
    question_ids_value_x2 integer[],
    question_ids_value_x3 integer[],
    CONSTRAINT quizzes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.rules
(
    id serial NOT NULL,
    professor_id integer NOT NULL,
    value_right_answer numeric NOT NULL,
    value_wrong_answer numeric NOT NULL,
    duration integer NOT NULL,
    CONSTRAINT rules_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.students
(
    id serial NOT NULL,
    user_id integer,
    name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT unique_user_id_student UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS public.subject_professors
(
    subject_id integer NOT NULL,
    professor_id integer NOT NULL,
    CONSTRAINT subject_professors_pkey PRIMARY KEY (subject_id, professor_id)
);

CREATE TABLE IF NOT EXISTS public.subjects
(
    id serial NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    CONSTRAINT subjects_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.votes
(
    id serial NOT NULL,
    quiz_id integer,
    student_id integer,
    date date,
    result double precision,
    CONSTRAINT votes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.week_program
(
    id serial NOT NULL,
    start_date date,
    end_date date,
    class_id integer,
    CONSTRAINT week_program_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.class_professors
    ADD CONSTRAINT class_professors_class_id_fkey FOREIGN KEY (class_id)
    REFERENCES public.classes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.class_professors
    ADD CONSTRAINT class_professors_professor_id_fkey FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.class_students
    ADD CONSTRAINT class_students_class_id_fkey FOREIGN KEY (class_id)
    REFERENCES public.classes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.class_students
    ADD CONSTRAINT class_students_student_id_fkey FOREIGN KEY (student_id)
    REFERENCES public.students (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.class_subjects
    ADD CONSTRAINT class_subjects_class_id_fkey FOREIGN KEY (class_id)
    REFERENCES public.classes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.class_subjects
    ADD CONSTRAINT class_subjects_subject_id_fkey FOREIGN KEY (subject_id)
    REFERENCES public.subjects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.classes
    ADD CONSTRAINT classes_course_id_fkey FOREIGN KEY (course_id)
    REFERENCES public.courses (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.lesson_participations
    ADD CONSTRAINT lesson_participations_lesson_id_fkey FOREIGN KEY (lesson_id)
    REFERENCES public.lessons (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.lesson_participations
    ADD CONSTRAINT lesson_participations_student_id_fkey FOREIGN KEY (student_id)
    REFERENCES public.students (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.lessons
    ADD CONSTRAINT lessons_class_id_fkey FOREIGN KEY (class_id)
    REFERENCES public.classes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.lessons
    ADD CONSTRAINT lessons_professor_id_fkey FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.lessons
    ADD CONSTRAINT lessons_subject_id_fkey FOREIGN KEY (subject_id)
    REFERENCES public.subjects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.questions
    ADD CONSTRAINT fk_professor_id FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.quiz_questions
    ADD CONSTRAINT quiz_questions_question_id_fkey FOREIGN KEY (question_id)
    REFERENCES public.questions (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT VALID;
CREATE INDEX IF NOT EXISTS "fki_Q"
    ON public.quiz_questions(question_id);


ALTER TABLE IF EXISTS public.quiz_questions
    ADD CONSTRAINT quiz_questions_quiz_id_fkey FOREIGN KEY (quiz_id)
    REFERENCES public.quizzes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.quizzes
    ADD CONSTRAINT quizzes_professor_id_fkey FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.quizzes
    ADD CONSTRAINT quizzes_rule_id_fkey FOREIGN KEY (rule_id)
    REFERENCES public.rules (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE SET NULL;


ALTER TABLE IF EXISTS public.quizzes
    ADD CONSTRAINT quizzes_subject_id_fkey FOREIGN KEY (subject_id)
    REFERENCES public.subjects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.rules
    ADD CONSTRAINT rules_professor_id_fkey FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.subject_professors
    ADD CONSTRAINT subject_professors_professor_id_fkey FOREIGN KEY (professor_id)
    REFERENCES public.professors (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.subject_professors
    ADD CONSTRAINT subject_professors_subject_id_fkey FOREIGN KEY (subject_id)
    REFERENCES public.subjects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.votes
    ADD CONSTRAINT votes_quiz_id_fkey FOREIGN KEY (quiz_id)
    REFERENCES public.quizzes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.votes
    ADD CONSTRAINT votes_student_id_fkey FOREIGN KEY (student_id)
    REFERENCES public.students (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.week_program
    ADD CONSTRAINT week_program_class_id_fkey FOREIGN KEY (class_id)
    REFERENCES public.classes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;

