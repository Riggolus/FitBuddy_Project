
CREATE SEQUENCE seq_account_id
INCREMENT BY 1
START WITH 1000
NO MAXVALUE;

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
	CONSTRAINT FK_account_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

