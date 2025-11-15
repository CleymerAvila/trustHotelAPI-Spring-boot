CREATE TABLE hotels (
	hotel_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL UNIQUE,
    category TINYINT NULL,
    address VARCHAR(200) NOT NULL,
    floors TINYINT NOT NULL,
    phone VARCHAR(10) NOT NULL
);

CREATE TABLE promotions(
	promotion_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    maximum_use INT
);

CREATE TABLE hotel_promotion(
	hotel_id INT,
    promotion_id INT,
    PRIMARY KEY(hotel_id, promotion_id),
    FOREIGN KEY(hotel_id) REFERENCES hotels(hotel_id),
    FOREIGN KEY(promotion_id) REFERENCES promotions(promotion_id)
);

CREATE TABLE rooms (
	room_id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    number VARCHAR(10) NULL UNIQUE,
    current_state VARCHAR(30) NOT NULL,
    floor TINYINT NOT NULL,
    capacity TINYINT NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    hotel_id INT NOT NULL,
	FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id) ON DELETE CASCADE
);
CREATE TABLE users(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE clients (
	client_id INT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    user_id INT NULL,
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE employees (
	employee_id INT PRIMARY KEY AUTO_INCREMENT,
	dni VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    work_shift VARCHAR(20) NOT NULL,
	type VARCHAR(100) NOT NULL,
    bonus DECIMAL(10, 2) NULL,
    main_language VARCHAR(30) NULL,
	department VARCHAR(100) NULL,
    user_id INT NULL,
    hotel_id INT NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    -- foreign keys
    FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE bookings (
	booking_id INT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
	advance_payment DECIMAL(10,2),
    client_id INT NOT NULL,
	room_id INT NOT NULL,
	initial_invoice INT NULL,
    -- foreign keys
	FOREIGN KEY (client_id) REFERENCES clients(client_id),
	FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

CREATE TABLE stayings (
	staying_id INT PRIMARY KEY AUTO_INCREMENT,
    check_in_date DATE NOT NULL,
    check_out_date DATE NULL,
    status VARCHAR(20) NOT NULL,
	total_amount DECIMAL(10,2),
    booking_id INT NOT NULL UNIQUE,
    final_invoice INT NULL,
    -- foreign keys
	FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

CREATE TABLE invoices(
	invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
	invoice_type VARCHAR(10), # INICIAL O FINAL--
    issue_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(10) NULL,
    discount_type VARCHAR(20) NULL,
    applied_discount DECIMAL(10,2) NULL,
    -- FOREIGN KEYS
    FOREIGN KEY(client_id) REFERENCES clients(client_id)
);


CREATE TABLE payments(
	payment_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_method VARCHAR(150) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    issue_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    invoice_id INT NOT NULL,
    FOREIGN KEY(invoice_id) REFERENCES invoices(invoice_id)
);

CREATE TABLE room_services(
	room_service_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE room_service_staying(
	room_service_id INT,
    staying_id INT,
    PRIMARY KEY(staying_id, room_service_id),
    FOREIGN KEY(staying_id) REFERENCES stayings(staying_id),
    FOREIGN KEY(room_service_id) REFERENCES room_services(room_service_id)
);



