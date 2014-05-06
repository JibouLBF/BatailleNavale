
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
	Joueur2 varchar(20) NOT NULL,
	Tour varchar(20),
	CONSTRAINT fk_joueur1_partie FOREIGN KEY (Joueur1) REFERENCES Joueur(Pseudo),
	CONSTRAINT fk_joueur2_partie FOREIGN KEY (Joueur2) REFERENCES Joueur(Pseudo),
	CONSTRAINT fk_tour_partie FOREIGN KEY (Tour) REFERENCES Joueur(Pseudo),
	CONSTRAINT j1_diff_j2 CHECK(Joueur1 != Joueur2)
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
	PosXInit int NOT NULL, CHECK( (PosXInit >= 1) AND (PosXInit <= 10)),
	PosYInit int NOT NULL, CHECK( (PosYInit >= 1) AND (PosYInit <= 10)),
	NbCoupRestant int NOT NULL, CHECK (NbCoupRestant <= Vie),
	PRIMARY KEY(IdBateau, IdPartie),
	CONSTRAINT fk_bateau_joueur FOREIGN KEY (Proprietaire) REFERENCES Joueur(Pseudo),
	CONSTRAINT orientation_values CHECK(Orientation IN('N','S','E','O')),
	CONSTRAINT taille_values CHECK(Taille IN(2,3)),
	CONSTRAINT bateau_posX_valide CHECK( ((Orientation ='N' OR Orientation = 'S') AND (PosX >= 1) AND (PosX <= 10))
										OR(Orientation ='E' AND (PosX >= 1) AND (PosX <= (11 - Taille)))
										OR(Orientation ='O' AND (PosX >= Taille) AND (PosX <= 10))
										),
	CONSTRAINT bateau_posY_valide CHECK( ((Orientation ='E' OR Orientation = 'O') AND (PosY >= 1) AND (PosY <= 10))
										OR(Orientation ='N' AND (PosY >= 1) AND (PosY <= (11 - Taille)))
										OR(Orientation ='S' AND (PosY >= Taille) AND (PosY <= 10))
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
	PosX int NOT NULL, CONSTRAINT tir_posx_valide CHECK( (PosX >= 1) AND (PosX <= 10)),
	PosY int NOT NULL, CONSTRAINT tir_posy_valide CHECK( (PosY >= 1) AND (PosY <= 10)),
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

INSERT INTO Joueur
VALUES ('teikitel','Tutu','Loic','teikitel@imag.fr','6','rue Paul','38000','Grenoble','12-nov-93');

INSERT INTO Joueur
VALUES ('lavignj','Lavigne','JB','lavignje@imag.fr','12','rue Alfred Kastler','38000','Grenoble','29-feb-92');

SELECT *
FROM Joueur;


INSERT INTO Partie
VALUES(seqIdPartie.nextval,CURRENT_DATE,'abikhatv','abikhat',NULL);

INSERT INTO Partie
VALUES(seqIdPartie.nextval,CURRENT_DATE,'teikitel','abikhatv',NULL);

INSERT INTO Partie
VALUES(seqIdPartie.nextval,CURRENT_DATE,'abikhatv','abikhat',NULL);

INSERT INTO AGagne
VALUES('1','abikhatv');

INSERT INTO AGagne
VALUES('3','abikhat');


SELECT * 
FROM Partie
WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne);

SELECT Partie.IdPartie,Joueur1,Joueur2,DateDemarrage,Vainqueur 
FROM Partie, AGagne
WHERE Partie.IdPartie = AGagne.IdPartie;

SELECT * 
FROM Partie
WHERE (Joueur1 = 'abikhatv' OR Joueur2 = 'abikhatv') AND (IdPartie NOT IN(SELECT IdPartie FROM AGagne));


-- "FUSION DES COLONNES Joueur1 et Joueur2 en une seule"
SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie;

-- Joueur / Compte le nombre de parties jouées 
SELECT Joueur, COUNT(*)
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
GROUP BY Joueur;

-- Minimum du nombre de parties jouées (sans pseudo)
SELECT MIN(COUNT(*))
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
GROUP BY Joueur;

-- Pseudo du joueur qui a joué le moins de partie / nombre de partie
SELECT Joueur, COUNT(*) AS CNT
FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
HAVING COUNT(*) = 
	(SELECT MIN(COUNT(*))
	 FROM (SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie)
	 GROUP BY Joueur)
GROUP BY Joueur;


-- Joueurs inscrits qui ne sont pas en train de jouer
SELECT Pseudo
FROM Joueur
WHERE Pseudo NOT IN(SELECT Joueur1 
					FROM Partie
					WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  AND Pseudo NOT IN(SELECT Joueur2 
					FROM Partie
					WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne));

-- Joueur qui ne jouent pas / Nombre de parties jouées 
SELECT Pseudo, COUNT(*)
FROM ((SELECT Pseudo
		FROM Joueur
		WHERE Pseudo NOT IN(SELECT Joueur1 
							FROM Partie
							WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  		AND Pseudo NOT IN(SELECT Joueur2 
							FROM Partie
							WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  		)
	LEFT OUTER JOIN
	(SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie) ON Joueur = Pseudo)
GROUP BY Pseudo;



-- Tableau des joueurs ayant joué le moins de partie jusqu'a présent
SELECT Pseudo, COUNT(*) AS CNT
FROM ((SELECT Pseudo
			FROM Joueur
			WHERE Pseudo NOT IN(SELECT Joueur1 
								FROM Partie
								WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  			AND Pseudo NOT IN(SELECT Joueur2 
								FROM Partie
								WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  		)
		LEFT OUTER JOIN
		(SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie) ON Joueur = Pseudo)
HAVING COUNT(*) = 
				(SELECT MIN(COUNT(*))
				 FROM ((SELECT Pseudo
						FROM Joueur
						WHERE Pseudo NOT IN(SELECT Joueur1 
											FROM Partie
											WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  						  AND Pseudo NOT IN(SELECT Joueur2 
										  	FROM Partie
											WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))
  						)
						LEFT OUTER JOIN
						(SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie) ON Joueur = Pseudo)
				GROUP BY Pseudo)
GROUP BY Pseudo;



SELECT Coup.IdCoup, Coup.IdBateau, Deplacement.Sens, Tir.PosX, Tir.PosY, Bateau.Proprietaire
FROM (((Coup LEFT OUTER JOIN Deplacement ON Coup.IdCoup = Deplacement.IdCoup AND Coup.IdPartie = Deplacement.IdPartie) 
	LEFT OUTER JOIN Tir ON Coup.IdCoup = Tir.IdCoup AND Coup.IdPartie = Tir.IdPartie) LEFT OUTER JOIN Bateau ON Coup.IdBateau = Bateau.IdBateau)
WHERE Coup.IdPartie = 4
ORDER BY Coup.IdCoup DESC ;
