-- La liste de tous les livres, triés par titre
SELECT * FROM LIVRE
ORDER BY TITRE;

-- Le nombre de livres dont le titre contient le mot « temps »
SELECT COUNT(*) FROM LIVRE
WHERE TITRE LIKE '%temps%';

-- Les noms et prénoms de tous les usagers ayant plus de 60 ans
SELECT NOM, PRENOM FROM USAGER
WHERE ANNEE_NAISSANCE < (TO_CHAR(SYSDATE, 'YYYY') - 60);

-- Les isbn des livres ayant déjà été empruntés par des usagers bénéciant du tarif réduit
SELECT ISBN FROM PRET P
                     JOIN USAGER U ON P.ID_USAGER = U.ID_USAGER
WHERE U.TARIF_REDUIT = 1;

-- Les informations de tous les usagers ayant actuellement emprunté un ou plusieurs livre(s)
SELECT * FROM USAGER U
WHERE EXISTS(SELECT * FROM PRET P WHERE P.ID_USAGER = U.ID_USAGER);

-- Les informations de tous les usagers ayant déjà rendu un ou plusieurs livre(s) en retard
SELECT * FROM USAGER U
WHERE EXISTS(SELECT * FROM PRET P WHERE P.ID_USAGER = U.ID_USAGER
                                    AND (P.DATE_EMPRUNT + P.DUREE) < P.DATE_RETOUR_EFFECTIVE);