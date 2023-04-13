CREATE TABLE guardians(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    city VARCHAR(255) NOT NULL,
    about TEXT,
    photo VARCHAR(255),

    PRIMARY KEY(id)
);