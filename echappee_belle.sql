DROP DATABASE  ECHAPPEE_BELLE;
CREATE DATABASE ECHAPPEE_BELLE DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ECHAPPEE_BELLE;

CREATE TABLE ROLE (
  idro int,
  nomrole varchar(10),
  PRIMARY KEY (idro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE UTILISATEUR (
  idut int,
  pseudout varchar(10) unique,
  emailut varchar(100),
  mdput varchar(100),
  activeut char(1),
  avatarut longblob,
  idro int,
  PRIMARY KEY (idut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PARTIE (
  idpa int,
  datedebutpa datetime,
  tpsresolution time,
  gagne char(1),
  idut int,
  idsc int,
  PRIMARY KEY (idpa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ENIGME (
  iden int,
  nomen varchar(50),
  texten text,
  imgen longblob,
  reponseen varchar(150),
  aideen text,
  brouillonen CHAR(1) check (brouillonEn in ('O','N')),
  idut int,
  PRIMARY KEY (iden)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SITUER (
  idca int,
  iden int,
  lig int,
  col int,
  PRIMARY KEY (idca, iden)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE CARTE (
  idca int,
  nomca varchar(50),
  planca text,
  brouillonca CHAR(1) check (brouillonCa in ('O','N')),
  idut int,
  idts int,
  PRIMARY KEY (idca)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PARTICIPER (
  idca int,
  idsc int,
  numordre int,
  PRIMARY KEY (idca, idsc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SCENARIO (
  idsc int,
  titresc Varchar(100),
  resumesc text,
  icone longblob,
  tpsmaxsc time,
  datemiseenligne date,
  brouillonsc CHAR(1) check (brouillonSc in ('O','N')),
  idut int,
  PRIMARY KEY (idsc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE TILESET (
  idts int,
  nomts varchar(50),
  tailletuile int,
  imagets longblob,
  PRIMARY KEY (idts)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE TEXTE (
  idtxt int,
  messagetxt text,
  imgtxt longblob,
  idca int,
  idsc int,
  idtt int,
  PRIMARY KEY (idtxt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE TYPETEXTE (
  idtt int,
  nomtt varchar(20),
  PRIMARY KEY (idtt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE UTILISATEUR ADD FOREIGN KEY (idro) REFERENCES ROLE (idro);
ALTER TABLE PARTIE ADD FOREIGN KEY (idsc) REFERENCES SCENARIO (idsc);
ALTER TABLE PARTIE ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE ENIGME ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE SITUER ADD FOREIGN KEY (iden) REFERENCES ENIGME (iden);
ALTER TABLE SITUER ADD FOREIGN KEY (idca) REFERENCES CARTE (idca);
ALTER TABLE CARTE ADD FOREIGN KEY (idts) REFERENCES TILESET (idts);
ALTER TABLE CARTE ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE PARTICIPER ADD FOREIGN KEY (idsc) REFERENCES SCENARIO (idsc);
ALTER TABLE PARTICIPER ADD FOREIGN KEY (idca) REFERENCES CARTE (idca);
ALTER TABLE SCENARIO ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE TEXTE ADD FOREIGN KEY (idtt) REFERENCES TYPETEXTE (idtt);
ALTER TABLE TEXTE ADD FOREIGN KEY (idsc) REFERENCES SCENARIO (idsc);
ALTER TABLE TEXTE ADD FOREIGN KEY (idca) REFERENCES CARTE (idca);
