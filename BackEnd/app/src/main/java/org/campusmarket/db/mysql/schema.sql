CREATE TABLE IF NOT EXISTS users(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(20) NOT NULL,  #needed to get rid of the underscore for searching by user name
    password   VARCHAR(16) NOT NULL,
    firstname VARCHAR(20),
    lastname  VARCHAR(20),
    email      VARCHAR(30),
    university VARCHAR(30),
    
    UNIQUE (username),
    UNIQUE (email)
);