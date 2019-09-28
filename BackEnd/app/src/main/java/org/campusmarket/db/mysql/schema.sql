CREATE TABLE IF NOT EXISTS users(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(20) NOT NULL,  
    password   VARCHAR(16) NOT NULL,
    firstname VARCHAR(20),
    lastname  VARCHAR(20),
    email      VARCHAR(30),
    university VARCHAR(30),
    admin     BOOLEAN,

    UNIQUE (username),
    UNIQUE (email)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS sessions(
    sessid   VARCHAR(30) NOT NULL PRIMARY KEY,
    userid   INT UNSIGNED NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS items(
		refnum VARCHAR(10) NOT NULL PRIMARY KEY,
		name VARCHAR (20),
		price DOUBLE(6,2),
		category VARCHAR (10),
		cond VARCHAR (10)

) engine=InnoDB;