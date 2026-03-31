-- Le nombre d’utilisateurs par année de naissance.
SELECT ANNEE_NAISSANCE, COUNT(*) AS NB_UTILISATEURS FROM USAGER
GROUP BY ANNEE_NAISSANCE;

-- La durée théorique (colonne « durée ») moyenne d’un prêt
SELECT ROUND(AVG(DUREE), 2) AS DUREE_MOYENNE FROM PRET;

-- La durée effective moyenne d’un prêt.
SELECT ROUND(AVG(DATE_RETOUR_EFFECTIVE - DATE_EMPRUNT), 2) AS DUREE_EFFECTIVE_MOYENNE FROM PRET WHERE DATE_RETOUR_EFFECTIVE IS NOT NULL;

-- Le nombre de fois où chaque livre a été prêté. Les livres jamais prêtés devront être inclus dans le résultat nal, avec la valeur « 0 ».
SELECT L.ISBN, L.TITRE, COUNT(P.ID_PRET) AS NB_PRETS FROM LIVRE L
LEFT OUTER JOIN PRET P ON L.ISBN = P.ISBN
GROUP BY L.ISBN, L.TITRE;

-- Pour chaque livre, le nombre d’exemplaires actuellement en stock (= non empruntés)
WITH PRETS_EN_COURS AS (
    SELECT ISBN, COUNT(*) AS NB_PRETS_EN_COURS FROM PRET
    WHERE DATE_RETOUR_EFFECTIVE IS NULL
    GROUP BY ISBN
)
SELECT L.ISBN, L.TITRE, (L.NB_EXEMPLAIRES - NVL(NB_PRETS_EN_COURS, 0)) AS NB_EN_STOCK FROM LIVRE L
LEFT OUTER JOIN PRETS_EN_COURS P ON L.ISBN = P.ISBN;


-- Écrivez la requête permettant de créer l’auteur/autrice de votre choix
CREATE SEQUENCE AUTEUR_SEQ INCREMENT BY 1 START WITH 20;
CREATE OR REPLACE PROCEDURE CREER_AUTEUR(VAL_NOM AUTEUR.NOM%TYPE, VAL_PRENOM AUTEUR.PRENOM%TYPE) IS
    IS_EXISTING NUMBER;
BEGIN
    SELECT COUNT(*) INTO IS_EXISTING FROM AUTEUR WHERE NOM = VAL_NOM AND PRENOM = VAL_PRENOM;
    IF IS_EXISTING = 0 THEN
        INSERT INTO AUTEUR (ID_AUTEUR, NOM, PRENOM) VALUES (AUTEUR_SEQ.NEXTVAL, VAL_NOM, VAL_PRENOM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('L''auteur/autrice a été créé(e) avec succès.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('L''auteur/autrice existe déjà.');
    END IF;
END;

-- Écrivez la requête permettant de créer un livre écrit par cet auteur/autrice.
CREATE OR REPLACE PROCEDURE CREER_LIVRE(
    VAL_ISBN LIVRE.ISBN%TYPE,
    VAL_TITRE LIVRE.TITRE%TYPE,
    VAL_GENRE LIVRE.GENRE%TYPE,
    VAL_NB_PAGES LIVRE.NB_PAGES%TYPE,
    VAL_NB_EXEMPLAIRES LIVRE.NB_EXEMPLAIRES%TYPE,
    VAL_ID_AUTEUR LIVRE.ID_AUTEUR%TYPE) IS
    IS_EXISTING NUMBER;
BEGIN
    SELECT COUNT(*) INTO IS_EXISTING FROM LIVRE WHERE ISBN = VAL_ISBN;
    IF IS_EXISTING = 0 THEN
        INSERT INTO LIVRE (ISBN, TITRE, GENRE, NB_PAGES, NB_EXEMPLAIRES, ID_AUTEUR)
        VALUES (VAL_ISBN, VAL_TITRE, VAL_GENRE, VAL_NB_PAGES, VAL_NB_EXEMPLAIRES, VAL_ID_AUTEUR);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Le livre a été créé avec succès.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Le livre existe déjà.');
    END IF;
END;

-- Exécutez les deux requêtes et vériez que le livre a été correctement inséré.
CALL CREER_AUTEUR('Dupont', 'Jean');
CALL CREER_LIVRE(1234567890123, 'Le Temps', 1, 300, 5, 20);
CALL CREER_LIVRE(1234567890124, 'Le Temps des Cerises', 2, 250, 3, 20);
CALL CREER_LIVRE(1234567890125, 'Le Temps des Fleurs', 3, 200, 2, 20);

-- Écrivez la requête permettant de supprimer l’auteur/autrice que vous avez créé(e). Exécutez-la. Que se passe-t-il ? Pourquoi ?
-- Supprimez maintenant l’auteur/autrice et le livre que vous avez créés.

CREATE OR REPLACE PROCEDURE SUPPRIMER_AUTEUR(VAL_NOM AUTEUR.NOM%TYPE, VAL_PRENOM AUTEUR.PRENOM%TYPE) IS
    IS_EXISTING NUMBER;
    VAL_ID_AUTEUR NUMBER;
BEGIN
    SELECT COUNT(*) INTO IS_EXISTING FROM AUTEUR WHERE NOM = VAL_NOM AND PRENOM = VAL_PRENOM;
    IF IS_EXISTING > 0 THEN
        SELECT ID_AUTEUR INTO VAL_ID_AUTEUR FROM AUTEUR WHERE NOM = VAL_NOM AND PRENOM = VAL_PRENOM;
        DELETE FROM LIVRE WHERE ID_AUTEUR = VAL_ID_AUTEUR;
        DELETE FROM AUTEUR WHERE NOM = VAL_NOM AND PRENOM = VAL_PRENOM;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('L''auteur/autrice a été supprimé(e) avec succès.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('L''auteur/autrice n''existe pas.');
    END IF;
END;

CALL SUPPRIMER_AUTEUR('Dupont', 'Jean');