-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-12-13 19:25:03.852

-- tables
-- Table: agencia
CREATE TABLE agencia (
    id int NOT NULL AUTO_INCREMENT,
    nome text NOT NULL,
    numero int NOT NULL,
    endereco text NOT NULL,
    CONSTRAINT agencia_pk PRIMARY KEY (id)
);

-- Table: cliente
CREATE TABLE cliente (
    id int NOT NULL AUTO_INCREMENT,
    nome text NOT NULL,
    endereco text NOT NULL,
    dataNasc date NOT NULL,
    cpf text NOT NULL,
    rg text NOT NULL,
    dataCadastro date NOT NULL,
    CONSTRAINT cliente_pk PRIMARY KEY (id)
);

-- Table: conta
CREATE TABLE conta (
    id int NOT NULL AUTO_INCREMENT,
    numero int NOT NULL,
    cliente_id int NOT NULL,
    agencia_id int NOT NULL,
    CONSTRAINT conta_pk PRIMARY KEY (id)
);

-- Table: transferencia
CREATE TABLE transferencia (
    id int NOT NULL AUTO_INCREMENT,
    valor double NOT NULL,
    contaOrigem int NOT NULL,
    contaDestino int NOT NULL,
    CONSTRAINT transferencia_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: conta_agencia (table: conta)
ALTER TABLE conta ADD CONSTRAINT conta_agencia FOREIGN KEY conta_agencia (agencia_id)
    REFERENCES agencia (id);

-- Reference: conta_cliente (table: conta)
ALTER TABLE conta ADD CONSTRAINT conta_cliente FOREIGN KEY conta_cliente (cliente_id)
    REFERENCES cliente (id);

-- Reference: transacao_conta (table: transferencia)
ALTER TABLE transferencia ADD CONSTRAINT transacao_conta FOREIGN KEY transacao_conta (contaOrigem)
    REFERENCES conta (id);


-- Inserts
INSERT INTO agencia VALUES (1, "Agencia 1", 1234, "Rua X");
INSERT INTO agencia VALUES (2, "Agencia 2", 5678, "Rua Y");
INSERT INTO cliente VALUES (1, "Lucas","Rua Z", "26/05/1995", "12345678900", "123456789", "12/12/2019");
INSERT INTO conta VALUES (1, 500550, 1, 1);
