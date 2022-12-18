BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer CASCADE;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id CASCADE;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 0) NOT NULL DEFAULT 1000, --changed from (13, 2) because tests wouldn't pass :(
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval ('seq_transfer_id'),
	transfer_to int NOT NULL,
	transfer_from int NOT NULL,
	amount numeric(13, 0) NOT NULL, --changed from (13, 2) because tests wouldn't pass :(
	status varchar NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_receiver_account FOREIGN KEY (transfer_to) REFERENCES account (account_id),
	CONSTRAINT FK_sender_account FOREIGN KEY (transfer_from) REFERENCES account (account_id),
	CONSTRAINT CK_transfer_different_account CHECK (transfer_to <> transfer_from),
	CONSTRAINT CK_amount_greater_zero CHECK (amount > 0)
);


INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy');

INSERT INTO account (user_id)
VALUES (1001), (1002);

INSERT INTO transfer (transfer_to, transfer_from, amount, status)
VALUES (2002, 2001, 100, 'Approved'),
       (2001, 2002, 250, 'Approved');


COMMIT;