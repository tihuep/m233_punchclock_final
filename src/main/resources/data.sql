INSERT INTO `USER_ROLE` (`ROLE_NAME`,`ADMIN`) VALUES ('Administrator', true),('Mitarbeiter', false)

INSERT INTO `CATEGORY` (`CATEGORY_NAME`) VALUES ('Arbeit'),('Ferien'),('Bezahlte Absenz')

INSERT INTO `APPLICATION_USER` (`USERNAME`, `PASSWORD`, `EMAIL`, `USER_ROLE_ID`) VALUES ('admin', '$2a$10$DOdna9BRzTQS71NjtByhZue.a6M4LLRgiGk60YoYXgKXEXB90uB9e', 'admin@punchclock.com', 1)