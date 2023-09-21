CREATE TABLE USER (
    ID bigint not null auto_increment,
    FIRST_NAME varchar(255),
    LAST_NAME varchar(255),
    USERNAME varchar(255) not null unique,
    AGE int,
    EMAIL varchar(255) not null,
    PASSWORD varchar(255) not null,
    PHOTO varchar(255),
    ROLE varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE REVIEW (
    ID bigint not null auto_increment,
    RATING int not null,
    COMMENT varchar(500),
    USER bigint not null,
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    BOOK_ID bigint not null,
    primary key (ID),
    constraint FK_REVIEW_USER foreign key (USER) references USER(ID),
    constraint FK_REVIEW_BOOK foreign key (BOOK_ID) references BOOK(ID)
);

CREATE TABLE READING_STATUS (
    ID bigint not null,
    USER_ID bigint not null,
    BOOK_ID bigint not null,
    STATUS varchar(255) not null,
    primary key (ID),
    constraint FK_READING_STATUS_USER_BOOK foreign key (USER_ID) references USER(ID),
    constraint FK_READING_STATUS_BOOK_USER foreign key (BOOK_ID) references BOOK(ID)
);

CREATE TABLE FAVORITE_BOOKS (
    USER_ID bigint not null,
    BOOK_ID bigint not null,
    constraint FK_FAVORITE_BOOKS_USER_BOOK foreign key (USER_ID) references USER(ID),
    constraint FK_FAVORITE_BOOKS_BOOK_USER foreign key (BOOK_ID) references BOOK(ID)
);