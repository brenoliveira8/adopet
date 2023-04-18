ALTER TABLE shelters ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE shelters ADD CONSTRAINT fk_shelters_users FOREIGN KEY (user_id) REFERENCES users (id);