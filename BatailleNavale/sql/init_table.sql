
DROP TABLE AGagne;
DROP TABLE Tir;
DROP TABLE Deplacement;
DROP TABLE Coup;
DROP TABLE Bateau;
DROP TABLE Partie;
DROP TABLE Joueur;
DROP SEQUENCE seqIdPartie;

CREATE SEQUENCE seqIdPartie;


CREATE TABLE Joueur (
	Pseudo varchar(20) PRIMARY KEY,
	Nom varchar(20),
	Prenom varchar(20),
	Email varchar(40), CHECK(Email LIKE '%@%'),
	Numero int,
	Rue varchar(40),
	CodePostal varchar(5),
	Ville varchar(20),
	DateNaissance date
);


CREATE TABLE Partie (
	IdPartie int PRIMARY KEY,
	DateDemarrage date NOT NULL,
	Joueur1 varchar(20) NOT NULL,
	Joueur2 varchar(20),
	CONSTRAINT fk_joueur1_partie FOREIGN KEY (Joueur1) REFERENCES Joueur(Pseudo),
	CONSTRAINT fk_joueur2_partie FOREIGN KEY (Joueur2) REFERENCES Joueur(Pseudo)
);


CREATE TABLE Bateau (
	IdBateau int,
	IdPartie int,
	Taille int NOT NULL,
	Proprietaire varchar(20) NOT NULL,
	PosX int NOT NULL, 
	PosY int NOT NULL,
	Orientation char NOT NULL,
	Vie int NOT NULL, CHECK(Vie >= 0 AND Vie <= Taille),
	PosXInit int NOT NULL, CHECK( (PosXInit >= 0) AND (PosXInit <= 9)),
	PosYInit int NOT NULL, CHECK( (PosYInit >= 0) AND (PosYInit <= 9)),
	PRIMARY KEY(IdBateau, IdPartie),
	CONSTRAINT fk_bateau_joueur FOREIGN KEY (Proprietaire) REFERENCES Joueur(Pseudo),
	CONSTRAINT orientation_values CHECK(Orientation IN('N','S','E','O')),
	CONSTRAINT taille_values CHECK(Taille IN(2,3)),
	CONSTRAINT bateau_posX_valide CHECK( ((Orientation ='N' OR Orientation = 'S') AND (PosX >= 0) AND (PosX <= 9))
										OR(Orientation ='E' AND (PosX >= 0) AND (PosX <= (10 - Taille)))
										OR(Orientation ='O' AND (PosX >= (Taille-1)) AND (PosX <= 9))
										),
	CONSTRAINT bateau_posY_valide CHECK( ((Orientation ='E' OR Orientation = 'O') AND (PosY >= 0) AND (PosY <= 9))
										OR(Orientation ='N' AND (PosY >= 0) AND (PosY <= (10 - Taille)))
										OR(Orientation ='S' AND (PosY >= (Taille-1)) AND (PosY <= 9))
										)
);


CREATE TABLE Coup (
	IdPartie int,
	IdCoup int,
	IdBateau int NOT NULL,
	PRIMARY KEY(IdPartie, IdCoup),
	CONSTRAINT fk_coup_partie FOREIGN KEY (IdPartie) REFERENCES Partie(IdPartie)
);


CREATE TABLE Deplacement (
	IdPartie int,
	IdCoup int,
	Sens char NOT NULL, CHECK(Sens IN('A','R','G','D')),
	PRIMARY KEY(IdPartie, IdCoup),
	CONSTRAINT fk_deplacement_partie_coup FOREIGN KEY (IdPartie,IdCoup) REFERENCES Coup(IdPartie,IdCoup)
);


CREATE TABLE Tir (
	IdPartie int,
	IdCoup int,
	PosX int NOT NULL, CONSTRAINT tir_posx_valide CHECK( (PosX >= 0) AND (PosX <= 9)),
	PosY int NOT NULL, CONSTRAINT tir_posy_valide CHECK( (PosY >= 0) AND (PosY <= 9)),
	PRIMARY KEY(IdPartie, IdCoup),
	CONSTRAINT fk_tir_partie_coup FOREIGN KEY (IdPartie,IdCoup) REFERENCES Coup(IdPartie,IdCoup)
);


CREATE TABLE AGagne(
	IdPartie int PRIMARY KEY,
	Vainqueur varchar(20) NOT NULL,
	CONSTRAINT fk_agagne_joueur FOREIGN KEY (Vainqueur) REFERENCES Joueur(Pseudo)
);



INSERT INTO Joueur
VALUES ('abikhatv','Abikhattar','Vincent','abikhatv@ensimag.fr','200','avenue des taillees','38000','Grenoble','04-feb-91');

SELECT *
FROM Joueur;

INSERT INTO Joueur
VALUES ('abikhat','Abikhattar','Vincent','abikhatv@ensimag.fr','200','avenue des taillees','38000','Grenoble','04-feb-91');

INSERT INTO Joueur
VALUES ('lavignje','Lavigne','JB','lavignje@imag.fr','12','rue Alfred Kastler','38000','Grenoble','29-feb-92');

SELECT *
FROM Joueur;


INSERT INTO Partie
VALUES('0',CURRENT_DATE,'abikhatv','abikhat');

INSERT INTO Partie
VALUES('1',CURRENT_DATE,'lavignje','abikhatv');

INSERT INTO Partie
VALUES('2',CURRENT_DATE,'abikhatv','abikhat');

INSERT INTO AGagne
VALUES('0','abikhatv');

INSERT INTO AGagne
VALUES('2','abikhat');


SELECT * 
FROM Partie
WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne);

SELECT Partie.IdPartie,Joueur1,Joueur2,DateDemarrage,Vainqueur 
FROM Partie, AGagne
WHERE Partie.IdPartie = AGagne.IdPartie;

SELECT * 
FROM Partie
WHERE (Joueur1 = 'abikhatv' OR Joueur2 = 'abikhatv') AND (IdPartie NOT IN(SELECT IdPartie FROM AGagne));

--SELECT Joueur, COUNT(*)
--FROM ((SELECT IdPartie,Joueur1 AS Joueur FROM Partie) UNION (SELECT IdPartie,Joueur2 AS Joueur FROM Partie)) AS ListeJoueur
--GROUP BY Joueur;

SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie;

SELECT Joueur, COUNT(*)
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
GROUP BY Joueur;

SELECT MIN(COUNT(*))
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
GROUP BY Joueur;

SELECT Joueur, COUNT(*) AS CNT
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
HAVING COUNT(*) = 
	(SELECT MIN(COUNT(*))
	 FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
	 GROUP BY Joueur)
GROUP BY Joueur;