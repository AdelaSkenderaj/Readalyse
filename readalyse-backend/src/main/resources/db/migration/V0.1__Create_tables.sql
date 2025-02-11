CREATE TABLE BOOK (
    ID bigint not null,
    TITLE longtext,
    DESCRIPTION longtext,
    SUMMARY longtext,
    DOWNLOADS bigint,
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    type varchar(255),
    primary key (ID)
);

CREATE TABLE BOOKSHELF (
    ID bigint not null auto_increment,
    NAME varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE LANGUAGE (
    ID bigint not null auto_increment,
    LANGUAGE varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE PERSON (
    ID bigint not null,
    NAME varchar(255),
    ALIAS longtext,
    BIRTHDATE bigint,
    DEATHDATE bigint,
    WEBPAGE varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE RESOURCE (
    ID bigint not null auto_increment,
    BOOK_ID bigint not null,
    URL varchar(255),
    SIZE bigint,
    MODIFIED varchar(255),
    TYPE varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID),
    constraint FK_BOOK_RESOURCE_BOOK foreign key (BOOK_ID) references BOOK(ID)
);

CREATE TABLE SUBJECT (
    ID bigint not null auto_increment,
    NAME varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE AGENT_TYPE (
    ID bigint not null auto_increment,
    NAME varchar(255),
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID)
);

CREATE TABLE READABILITY_SCORES (
    BOOK_ID bigint not null,
    FLESCH_KINCAID_GRADE_LEVEL float,
    FLESCH_READING_EASE float,
    COLEMAN_LIAU_INDEX float,
    SMOG_INDEX float,
    AUTOMATED_READABILITY_INDEX float,
    FORCAST_INDEX float,
    LIX_INDEX float,
    RIX_INDEX float,
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (BOOK_ID),
    constraint FK_READABILITY_SCORES_BOOK foreign key (BOOK_ID) references BOOK(ID)
);

CREATE TABLE JOB_EXECUTION_HISTORY (
    JOB_ID varchar(255) not null,
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (JOB_ID)
);

CREATE TABLE AGENT (
    ID bigint not null auto_increment,
    PERSON bigint not null,
    TYPE bigint not null,
    INSERT_TIME datetime not null,
    UPDATE_TIME datetime not null,
    primary key (ID),
    constraint FK_AGENT_PERSON foreign key (PERSON) references PERSON(ID),
    constraint FK_AGENT_TYPE foreign key (TYPE) references AGENT_TYPE(ID)
);

CREATE TABLE BOOK_AGENT (
    BOOK bigint not null,
    AGENT bigint not null,
    constraint FK_BOOK_AGENT_BOOK foreign key (BOOK) references BOOK(ID),
    constraint FK_BOOK_AGENT_AGENT foreign key (AGENT) references AGENT(ID)
);

CREATE TABLE BOOK_BOOKSHELF (
    BOOK bigint not null,
    BOOKSHELF bigint not null,
    constraint FK_BOOK_BOOKSHELF_BOOK foreign key (BOOK) references BOOK(ID),
    constraint FK_BOOK_BOOKSHELF_BOOKSHELF foreign key (BOOKSHELF) references BOOKSHELF(ID)
);

CREATE TABLE BOOK_LANGUAGE (
    BOOK bigint not null,
    LANGUAGE bigint not null,
    constraint FK_BOOK_LANGUAGE_BOOK foreign key (BOOK) references BOOK(ID),
    constraint FK_BOOK_LANGUAGE_LANGUAGE foreign key (LANGUAGE) references LANGUAGE(ID)
);

CREATE TABLE BOOK_SUBJECT (
   BOOK bigint not null,
   SUBJECT bigint not null,
   constraint FK_BOOK_SUBJECT_BOOK foreign key (BOOK) references BOOK(ID),
   constraint FK_BOOK_SUBJECT_RESOURCE foreign key (SUBJECT) references SUBJECT(ID)
);


