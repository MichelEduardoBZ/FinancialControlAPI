CREATE TABLE tb_client
(
    id         INT PRIMARY KEY     NOT NULL AUTO_INCREMENT,
    name       VARCHAR(80)         NOT NULL,
    cpf        VARCHAR(14) UNIQUE  NOT NULL,
    email      VARCHAR(120) UNIQUE NOT NULL,
    birth_date DATE                NOT NULL
);

CREATE TABLE tb_account
(
    id                    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    financial_institution VARCHAR(80)     NOT NULL,
    account_type          VARCHAR(80)     NOT NULL,
    amount                NUMERIC,
    client_id             INT             NOT NULL,
    FOREIGN KEY (client_id) REFERENCES tb_client (id)
);


CREATE TABLE tb_recipe
(
    id                    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    value                 NUMERIC         NOT NULL,
    receipt_date          DATE            NOT NULL,
    expected_receipt_date DATE            NOT NULL,
    description           VARCHAR(120),
    recipe_type           VARCHAR(80)     NOT NULL,
    account_id            INT,
    FOREIGN KEY (account_id) REFERENCES tb_account (id)
);

CREATE TABLE tb_expense
(
    id                    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    value                 NUMERIC         NOT NULL,
    expense_date          DATE            NOT NULL,
    expected_expense_date DATE            NOT NULL,
    expense_type          VARCHAR(80)     NOT NULL,
    account_id            INT,
    FOREIGN KEY (account_id) REFERENCES tb_account (id)
);