CREATE TABLE processed_user (
    screen_name VARCHAR(36) PRIMARY KEY,
    processed_tweets INTEGER,
    last_processed_tweet INTEGER,
    created_date TIMESTAMP(6) NOT NULL,
    modified_date TIMESTAMP(6) NOT NULL
);