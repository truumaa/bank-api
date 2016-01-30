CREATE TABLE account (
  id     BIGINT IDENTITY PRIMARY KEY,
  name   VARCHAR(255),
  amount DECIMAL(20, 2)
);

CREATE TABLE payment (
  id                   BIGINT IDENTITY PRIMARY KEY,
  SourceAccountId      BIGINT,
  DestinationAccountId BIGINT,
  amount               DECIMAL(20, 2),
  Description          VARCHAR(255),
  transactionDate      DATETIME
);