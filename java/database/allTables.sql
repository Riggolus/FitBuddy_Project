BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, account, achievements, check_in_check_out, exercise, workout, class_schedule, occupancy, class_registration;

DROP SEQUENCE IF EXISTS seq_account_id;

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

CREATE SEQUENCE seq_account_id
INCREMENT BY 1
START WITH 1000
NO MAXVALUE;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


-- Inserting kettlebell exercises for each movement pattern

-- Squat
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Goblet Squat', 'A squat variation where the kettlebell is held at the chest.'),
    ('Kettlebell Front Squat', 'A squat with the kettlebell held in front rack position.'),
    ('Kettlebell Overhead Squat', 'A squat with the kettlebell held overhead.'),
    ('Kettlebell Pistol Squat', 'A single-leg squat with the kettlebell held in front.'),
    ('Kettlebell Squat to Press', 'A squat followed by an overhead press.');

-- Hinge
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Deadlift', 'A hinge movement lifting the kettlebell from the ground.'),
    ('Kettlebell Swing', 'A dynamic hinge movement swinging the kettlebell to shoulder height.'),
    ('Kettlebell Single-Leg Deadlift', 'A single-leg hinge movement with the kettlebell.'),
    ('Kettlebell Good Morning', 'A hinge movement with the kettlebell held behind the neck.'),
    ('Kettlebell Snatch', 'A hinge and pull movement lifting the kettlebell overhead in one motion.');

-- Push
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Press', 'An overhead press with the kettlebell.'),
    ('Kettlebell Push Press', 'A press with assistance from the legs.'),
    ('Kettlebell Floor Press', 'A press performed lying on the floor.'),
    ('Kettlebell Clean and Press', 'A clean followed by an overhead press.'),
    ('Kettlebell Arnold Press', 'A press with rotation of the wrist.');

-- Pull
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Row', 'A rowing movement pulling the kettlebell towards the torso.'),
    ('Kettlebell High Pull', 'A dynamic pull to bring the kettlebell to chest height.'),
    ('Kettlebell Single-Arm Row', 'A single-arm rowing movement.'),
    ('Kettlebell Renegade Row', 'A row performed in a plank position.'),
    ('Kettlebell Upright Row', 'A pull bringing the kettlebell to chin height.');

-- Lunge
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Lunge', 'A forward lunge with the kettlebell held at the chest.'),
    ('Kettlebell Reverse Lunge', 'A reverse lunge with the kettlebell held at the chest.'),
    ('Kettlebell Overhead Lunge', 'A lunge with the kettlebell held overhead.'),
    ('Kettlebell Lateral Lunge', 'A side lunge with the kettlebell held at the chest.'),
    ('Kettlebell Curtsy Lunge', 'A diagonal lunge with the kettlebell held at the chest.');

-- Carry
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Farmer Carry', 'Carrying a kettlebell in each hand while walking.'),
    ('Kettlebell Suitcase Carry', 'Carrying a single kettlebell in one hand while walking.'),
    ('Kettlebell Racked Carry', 'Carrying a kettlebell in the front rack position while walking.'),
    ('Kettlebell Overhead Carry', 'Carrying a kettlebell overhead while walking.'),
    ('Kettlebell Bottoms-Up Carry', 'Carrying a kettlebell upside down in one hand while walking.');

-- Rotation
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Russian Twist', 'A seated rotation movement with the kettlebell.'),
    ('Kettlebell Windmill', 'A rotational movement with the kettlebell held overhead.'),
    ('Kettlebell Halo', 'A circular movement around the head with the kettlebell.'),
    ('Kettlebell Rotational Swing', 'A dynamic rotation with the kettlebell swing.'),
    ('Kettlebell Half-Kneeling Chop', 'A chop movement from high to low in a half-kneeling position.');

-- Anti-Rotation
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Plank Pull-Through', 'A plank position pulling the kettlebell from side to side.'),
    ('Kettlebell Single-Arm Farmer Walk', 'Walking with a kettlebell in one hand.'),
    ('Kettlebell Single-Arm Overhead Carry', 'Walking with a kettlebell held overhead in one hand.'),
    ('Kettlebell Pallof Press', 'Pressing the kettlebell in front while resisting rotation.'),
    ('Kettlebell Single-Arm Deadlift', 'A hinge movement with a kettlebell in one hand.');

-- Gait
INSERT INTO exercise (exercise_name, exercise_description) VALUES
    ('Kettlebell Walking Lunge', 'A lunge while walking forward with the kettlebell.'),
    ('Kettlebell Farmer Walk', 'Walking with a kettlebell in each hand.'),
    ('Kettlebell Suitcase Walk', 'Walking with a kettlebell in one hand.'),
    ('Kettlebell Racked Walk', 'Walking with a kettlebell in the front rack position.'),
    ('Kettlebell Overhead Walk', 'Walking with a kettlebell held overhead.');



COMMIT TRANSACTION;
