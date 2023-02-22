CREATE DATABASE SPRING_MVC_TRAINING;

USE SPRING_MVC_TRAINING;

CREATE TABLE MAIN_BOARD(
	BOARD_ID   BIGINT 		 AUTO_INCREMENT PRIMARY KEY,
    WRITER 	   VARCHAR(50)	 NOT NULL,
    SUBJECT    VARCHAR(100)  NOT NULL,
    CONTENT    VARCHAR(2000) NOT NULL,
    PASSWD     VARCHAR(200)  NOT NULL,
    READ_CNT   INT 			 DEFAULT 0,
    ENROLL_DT  TIMESTAMP 	 DEFAULT NOW()
);

CREATE TABLE REPLY (
	REPLY_ID   BIGINT 		 AUTO_INCREMENT PRIMARY KEY,
	WRITER 	   VARCHAR(50) 	 NOT NULL,
    CONTENT    VARCHAR(2000) NOT NULL,
    PASSWD     VARCHAR(200)  NOT NULL,
	ENROLL_DT  TIMESTAMP 	 DEFAULT NOW(),
	BOARD_ID   BIGINT 		 NOT NULL,
	FOREIGN KEY (BOARD_ID) REFERENCES MAIN_BOARD(BOARD_ID)
	ON DELETE CASCADE
);