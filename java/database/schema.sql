

BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, account, achievements, check_in_check_out, exercise, workout, class_schedule, occupancy, class_registration;

DROP SEQUENCE IF EXISTS seq_account_id;

CREATE SEQUENCE seq_account_id
INCREMENT BY 1
START WITH 1000
NO MAXVALUE;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE account (
    account_id INT NOT NULL DEFAULT nextval('seq_account_id'),
    user_id INT NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    profile_picture VARCHAR(255),
    profile VARCHAR(255),
    goals VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_account PRIMARY KEY (account_id),
    CONSTRAINT FK_account_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE achievements (
	goal_id SERIAL PRIMARY KEY,
	user_id INT REFERENCES users (user_id),
	goal_description TEXT,
	completed BOOLEAN DEFAULT false
);

CREATE TABLE check_in_check_out (
	check_in_out_id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	check_in_time TIMESTAMP NOT NULL,
	check_out_time TIMESTAMP NULL,
	check_in_by INT NULL,
	check_out_by INT null,
	CONSTRAINT FK_check_in_check_out_users FOREIGN KEY (user_id) REFERENCES users(user_id)	
);

CREATE TABLE exercise (
    exercise_id SERIAL PRIMARY KEY,
    exercise_name VARCHAR(100) NOT NULL UNIQUE,
    exercise_description TEXT
);

CREATE TABLE workout (
    workout_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "users"(user_id) ON DELETE CASCADE,
    exercise_id INT NOT NULL REFERENCES exercise(exercise_id) ON DELETE CASCADE,
    sets INT CHECK (sets >= 0),
    reps INT CHECK (reps >= 0),
    weight DECIMAL CHECK (weight >= 0),
    duration INT CHECK (duration >= 0),
	date_of_workout DATE NOT NULL
);

CREATE TABLE class_schedule (
	class_id SERIAL PRIMARY KEY, 
	class_name VARCHAR(100) NOT NULL, 
	instructor_id INT REFERENCES users (user_id) NOT NULL, 
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	description TEXT, 
	is_monday BOOLEAN DEFAULT FALSE,
	is_tuesday BOOLEAN DEFAULT FALSE,
	is_wednesday BOOLEAN DEFAULT FALSE,
	is_thursday BOOLEAN DEFAULT FALSE,
	is_friday BOOLEAN DEFAULT FALSE,
	is_saturday BOOLEAN DEFAULT FALSE,
	is_sunday BOOLEAN DEFAULT FALSE,
	recurrence_end TIMESTAMP,
	created_by INT REFERENCES users(user_id),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE occupancy (
	occupancy_id SERIAL PRIMARY KEY,
	class_id INT REFERENCES class_schedule(class_id) NOT NULL,
	session_date DATE NOT NULL,
	total_capacity INT NOT NULL, 
	current_capacity INT DEFAULT 0

);

CREATE TABLE class_registration (
	registration_id SERIAL PRIMARY KEY, 
	user_id INT REFERENCES users(user_id) NOT NULL,
	occupancy_id INT REFERENCES occupancy(occupancy_id) NOT NULL,
	registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	UNIQUE(user_id, occupancy_id)
);






COMMIT TRANSACTION;
