CREATE TABLE shelters(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    zip_code VARCHAR(9) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(2) NOT NULL,
    complement VARCHAR(255),

    PRIMARY KEY(id)
);