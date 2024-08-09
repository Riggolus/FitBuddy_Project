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