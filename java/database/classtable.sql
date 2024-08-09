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