ALTER TABLE guardians ADD COLUMN user_id BIGINT NOT NULL;
ALTER TABLE guardians ADD CONSTRAINT fk_guardians_users FOREIGN KEY (user_id) REFERENCES users(id);