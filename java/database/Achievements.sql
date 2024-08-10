CREATE TABLE achievements (
	goal_id SERIAL PRIMARY KEY,
	user_id INT REFERENCES users (user_id),
	goal_description TEXT,
	completed BOOLEAN DEFAULT false
);