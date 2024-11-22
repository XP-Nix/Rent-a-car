

CREATE TABLE cities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO cities (name) VALUES ('Sofia'), ('Plovdiv'), ('Varna'), ('Burgas');


CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    daily_rate DECIMAL(10, 2) NOT NULL,
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

ALTER TABLE cars
ADD COLUMN deleted BOOLEAN DEFAULT FALSE;


CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(15),
    age INT,
    has_accidents BOOLEAN DEFAULT FALSE
);

ALTER TABLE users
ADD COLUMN deleted BOOLEAN DEFAULT FALSE;



CREATE TABLE offers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    rental_days INT NOT NULL,
    total_price DECIMAL(10, 2),
    accepted BOOLEAN DEFAULT FALSE,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
);