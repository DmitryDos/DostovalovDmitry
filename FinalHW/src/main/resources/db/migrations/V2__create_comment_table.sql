CREATE TABLE comment
(
    id          BIGINT PRIMARY KEY,
    name        TEXT NOT NULL,
    articleId   BIGSERIAL REFERENCES article(id) ON DELETE CASCADE NOT NULL
);