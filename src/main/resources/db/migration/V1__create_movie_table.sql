CREATE TABLE movie (
  id BIGINT AUTO_INCREMENT NOT NULL,
   _year INT,
   title VARCHAR(255),
   studios VARCHAR(255),
   producer VARCHAR(255),
   winner VARCHAR(255),
   CONSTRAINT pk_movie PRIMARY KEY (id)
);
