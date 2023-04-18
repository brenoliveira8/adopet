CREATE TABLE pets(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    age VARCHAR(255) NOT NULL,
    size VARCHAR(255) NOT NULL,
    behavior VARCHAR(255) NOT NULL,
    adopted BOOLEAN NOT NULL,
    photo VARCHAR(255) NOT NULL,
    shelter_id BIGINT NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY (shelter_id) REFERENCES shelters(id)
);