CREATE TABLE IF NOT EXISTS users(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name  VARCHAR(20) NOT NULL,
    password   VARCHAR(16) NOT NULL,
    first_name VARCHAR(20),
    last_name  VARCHAR(20),
    university VARCHAR (30),
    email VARCHAR (35)
);
CREATE TABLE IF NOT EXISTS admins(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name  VARCHAR(20) NOT NULL,
    password   VARCHAR(16) NOT NULL,
    first_name VARCHAR(20),
    last_name  VARCHAR(20)
);