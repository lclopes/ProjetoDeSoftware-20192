DROP TABLE trabalho7.equipe;
DROP TABLE trabalho7.pais;
DROP TABLE trabalho7.campeonato;
DROP TABLE trabalho7.campeao;
DROP TABLE trabalho7.usuario;
DROP TABLE trabalho7.perfil;

CREATE TABLE trabalho7.pais (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  nome            VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE trabalho7.equipe (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  nome            VARCHAR(30) NOT NULL,
  apelido         VARCHAR(50) NOT NULL,
  ano_fundacao    INT(4) NOT NULL,
  pais_id         INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  CONSTRAINT EQUIPE_PAIS_FK 
  FOREIGN KEY (pais_id)
  REFERENCES trabalho7.pais(id) 
  ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE trabalho7.campeonato (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  nome            VARCHAR(30) NOT NULL,
  dificuldade     VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE trabalho7.campeao (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  equipe_id         INT(11) NOT NULL,
  campeonato_id     INT(11) NOT NULL,
  ano    			INT(4) NOT NULL,
  PRIMARY KEY (id),
  
  CONSTRAINT CAMPEAO_EQUIPE_FK 
  FOREIGN KEY (equipe_id)
  REFERENCES trabalho7.equipe(id) 
  ON DELETE RESTRICT ON UPDATE CASCADE,
  
  CONSTRAINT CAMPEAO_CAMPEONATO_FK 
  FOREIGN KEY (campeonato_id)
  REFERENCES trabalho7.campeonato(id) 
  ON DELETE RESTRICT ON UPDATE CASCADE

)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE trabalho7.usuario (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  conta           VARCHAR(30) NOT NULL,
  senha			  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE trabalho7.perfil (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  usuario_id      INT(11) NOT NULL,
  perfil          VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  
  CONSTRAINT USUARIO_PERFIL_FK 
  FOREIGN KEY (usuario_id)
  REFERENCES trabalho7.usuario(id) 
  ON DELETE RESTRICT ON UPDATE CASCADE
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;






INSERT INTO USUARIO(CONTA, SENHA) VALUES
('admin', 'admin');

INSERT INTO PERFIL(USUARIO_ID, PERFIL) VALUES
(LAST_INSERT_ID(), 'admin'),
(LAST_INSERT_ID(), 'user');


INSERT INTO USUARIO(CONTA, SENHA) VALUES
('felipe', 'felipe');

INSERT INTO PERFIL(USUARIO_ID, PERFIL) VALUES
(LAST_INSERT_ID(), 'user');






INSERT INTO PAIS(NOME)
VALUES('Brasil');

INSERT INTO EQUIPE(NOME, APELIDO, ANO_FUNDACAO, PAIS_ID) VALUES
('Flumiense', 'Time de guerreiros', 1902, LAST_INSERT_ID()),
('Flamengo', 'Mengao', 1895, LAST_INSERT_ID()),
('Sao Paulo', 'Tricolor Paulista', 1930, LAST_INSERT_ID()),
('Palmeiras', 'Porco', 1914, LAST_INSERT_ID()),
('Bahia', 'Bahea', 1931, LAST_INSERT_ID());

INSERT INTO PAIS(NOME)
VALUES('Argentina');

INSERT INTO EQUIPE(NOME, APELIDO, ANO_FUNDACAO, PAIS_ID) VALUES
('Boca Juniors', 'Azul y Oro', 1905, LAST_INSERT_ID()),
('River Plate', 'Los Millionarios', 1901, LAST_INSERT_ID());

INSERT INTO CAMPEONATO(NOME, DIFICULDADE) VALUES
('Libertadores', 'Dificil'),
('Sulamericana', 'Medio'),
('Brasilerao', 'Dificil'),
('Mundial', 'Dificil'),
('Carioca', 'Facil'),
('Paulista', 'Facil');


