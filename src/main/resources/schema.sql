
CREATE TABLE IF NOT EXISTS MPA (
                                   MPA_ID     INT PRIMARY KEY AUTO_INCREMENT,
                                   NAME       varchar(255),
                                   constraint MPA_PK primary key (mpa_id)
);
CREATE TABLE IF NOT EXISTS USERS(
                                    USER_ID INT PRIMARY KEY AUTO_INCREMENT,
                                    EMAIL varchar(255) NOT NULL UNIQUE,
                                    NAME varchar(255) NOT NULL ,
                                    LOGIN varchar(255) NOT NULL UNIQUE,
                                    BIRTHDAY DATE
);

CREATE TABLE IF NOT EXISTS FILMS(
                                    FILM_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                    NAME varchar(255) NOT NULL ,
                                    DESCRIPTION varchar(255) NOT NULL ,
                                    DURATION INT NOT NULL ,
                                    RELEASE_DATE DATE,
                                    mpa_id INT REFERENCES mpa (mpa_id )
);


CREATE TABLE IF NOT EXISTS GENRES(
                                     GENRE_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                     NAME varchar(255) ,
                                     DESCRIPTION varchar(255)
);

CREATE TABLE IF NOT EXISTS FILM_GENRES(
                                          FILM_ID INT REFERENCES FILMS(FILM_ID) ON DELETE CASCADE ON UPDATE CASCADE,
                                          GENRE_ID INT REFERENCES GENRES(GENRE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
                                          PRIMARY KEY (GENRE_ID,FILM_ID)
);

CREATE TABLE IF NOT EXISTS LIKES(
                                    FILM_ID INT REFERENCES FILMS(FILM_ID),
                                    USER_ID INT REFERENCES USERS(USER_ID),
                                    PRIMARY KEY (USER_ID,FILM_ID)
);
CREATE TABLE IF NOT EXISTS FRIENDS(
                                      USER_ID INT REFERENCES USERS(USER_ID),
                                      FRIEND_ID INT,
                                      STATUS varchar(20)
);