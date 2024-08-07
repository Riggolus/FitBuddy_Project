START TRANSACTION;

-- Creating the exercise table with a unique constraint on exercise_name
CREATE TABLE exercise (
    exercise_id SERIAL PRIMARY KEY,
    exercise_name VARCHAR(100) NOT NULL UNIQUE,
    exercise_description TEXT
);

-- Creating the workout table with foreign key constraints
CREATE TABLE workout (
    workout_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "users"(user_id) ON DELETE CASCADE,
    exercise_id INT NOT NULL REFERENCES exercise(exercise_id) ON DELETE CASCADE,
    sets INT CHECK (sets >= 0),
    reps INT CHECK (reps >= 0),
    weight DECIMAL CHECK (weight >= 0),
    duration INT CHECK (duration >= 0)
);

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

COMMIT;
