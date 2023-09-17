CREATE TABLE USER (
    ID bigint not null,
    FIRST_NAME varchar(255),
    LAST_NAME varchar(255),
    USERNAME varchar(255) not null unique,
    AGE int,
    EMAIL varchar(255) not null,
    PASSWORD varchar(255) not null,
    PHOTO varchar(255),
    primary key (ID)
);

CREATE TABLE REVIEW (
    ID bigint not null,
    RATING int not null,
    COMMENT varchar(500),
    USER bigint not null,
    primary key (ID),
    constraint FK_REVIEW_USER foreign key (USER_ID) references USER(ID),
);