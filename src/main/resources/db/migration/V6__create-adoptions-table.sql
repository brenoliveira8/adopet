CREATE TABLE adoptions(
    id BIGINT NOT NULL AUTO_INCREMENT,
    guardian_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    adoption_date DATETIME NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY (guardian_id) REFERENCES guardians(id),
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);