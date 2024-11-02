CREATE TABLE "user"(
    id            UUID          PRIMARY KEY,
    name          TEXT          NOT NULL,
    created_at    TIMESTAMP     NOT NULL,
    updated_at    TIMESTAMP     NOT NULL
);

CREATE TABLE post(
    id            UUID          PRIMARY KEY,
    content       TEXT          NOT NULL,
    user_id       UUID          NOT NULL,
    created_at    TIMESTAMP     NOT NULL,
    updated_at    TIMESTAMP     NOT NULL,

    CONSTRAINT "2b8f946f5f664ab08535_fk" FOREIGN KEY (user_id) REFERENCES "user"
);