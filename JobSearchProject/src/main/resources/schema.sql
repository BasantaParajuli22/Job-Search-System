CREATE TABLE IF NOT EXISTS job_category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL
);
