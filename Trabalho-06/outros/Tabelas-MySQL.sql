https://dev.mysql.com/downloads/

Download MySQL Community Server

Últimas versões:
- 5.5
- 5.6
- 5.7
- 8.0.12 (atual)

DROP TABLE banco.lance;
DROP TABLE banco.produto;

CREATE TABLE banco.produto (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  nome            VARCHAR(30) NOT NULL,
  descricao       VARCHAR(50) DEFAULT '',
  lance_minimo    DECIMAL(8, 2) NOT NULL,
  data_cadastro   DATE NOT NULL,
  data_venda      DATE DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE banco.lance (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  valor             DECIMAL(10, 2) NOT NULL,
  data_criacao      DATE NOT NULL,
  produto_id        INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT PRODUTO_LANCE_FK 
  FOREIGN KEY (produto_id)
  REFERENCES banco.produto(id) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

INSERT INTO produto(NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 20 POL', 'TV SAMSUNG 20 POL TELA PLANA', 2000, now());

INSERT INTO LANCE(VALOR, DATA_CRIACAO, PRODUTO_ID) VALUES
(2100, now(), LAST_INSERT_ID()),
(2200, now(), LAST_INSERT_ID());

INSERT INTO produto(NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 22 POL', 'TV SAMSUNG 22 POL TELA PLANA', 2500, now());

INSERT INTO LANCE(VALOR, DATA_CRIACAO, PRODUTO_ID) VALUES
(2600, now(), LAST_INSERT_ID()),
(2700, now(), LAST_INSERT_ID());

