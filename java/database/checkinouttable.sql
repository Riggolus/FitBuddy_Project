DROP TABLE IF EXISTS check_in_check_out;

CREATE TABLE check_in_check_out (
	check_in_out_id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	check_in_time TIMESTAMP NOT NULL,
	check_out_time TIMESTAMP NULL,
	check_in_by INT NULL,
	check_out_by INT null,
	CONSTRAINT FK_check_in_check_out_users FOREIGN KEY (user_id) REFERENCES users(user_id)
	
);	


   