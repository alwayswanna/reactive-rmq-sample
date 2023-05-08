CREATE TABLE user_message(
    id          uuid      PRIMARY KEY DEFAULT uuid_generate_v4(),
    username    citext    NOT NULL,
    message     citext
)