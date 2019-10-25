CREATE TABLE IF NOT EXISTS users(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(30) NOT NULL,  
    password   VARCHAR(16) NOT NULL,
    firstname VARCHAR(30),
    lastname  VARCHAR(30),
    email      VARCHAR(45),
    university VARCHAR(30),
    admin     BOOLEAN,

    UNIQUE (username),
    UNIQUE (email)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS role(
    role_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role    VARCHAR(30)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS user_roles(
  user_id INT UNSIGNED NOT NULL,
  role_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (user_id,role_id),
  UNIQUE (role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES role(role_id)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS items(
	refnum INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR (20),
	price DOUBLE(6,2),
	category VARCHAR (30),
	cond VARCHAR (30),
	seller VARCHAR (30) NOT NULL,
	FOREIGN KEY (seller) REFERENCES users(username) ON DELETE CASCADE ON UPDATE CASCADE
) engine=InnoDB;