CREATE TABLE account (
  id     INTEGER IDENTITY PRIMARY KEY,
  name   VARCHAR(255),
  amount DECIMAL(20, 2)
);

CREATE TABLE payment (
  id                   INTEGER IDENTITY PRIMARY KEY,
  SourceAccountId      INTEGER,
  DestinationAccountId INTEGER,
  amount               DECIMAL(20, 2),
  Description          VARCHAR(255),
  transactionDate      DATETIME
);