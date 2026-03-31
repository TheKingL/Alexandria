--------------------------------------------------------
--  DDL for Table AUTEUR
--------------------------------------------------------

  CREATE TABLE "AUTEUR" 
   (	
    "ID_AUTEUR" NUMBER(*,0), 
	"NOM" VARCHAR2(255), 
	"PRENOM" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table GENRE
--------------------------------------------------------

  CREATE TABLE "GENRE" 
   (	
    "ID_GENRE" NUMBER(*,0), 
	"NOM" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table LIVRE
--------------------------------------------------------

  CREATE TABLE "LIVRE" 
   (	
    "ISBN" NUMBER(*,0), 
	"TITRE" VARCHAR2(255), 
	"GENRE" NUMBER(*,0), 
	"NB_PAGES" NUMBER(*,0), 
	"NB_EXEMPLAIRES" NUMBER(*,0), 
	"ID_AUTEUR" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PRET
--------------------------------------------------------

  CREATE TABLE "PRET" 
   (	
    "ID_PRET" NUMBER(*,0), 
	"DATE_EMPRUNT" DATE, 
	"DUREE" NUMBER(*,0), 
	"DATE_RETOUR_EFFECTIVE" DATE, 
	"ISBN" NUMBER(*,0), 
	"ID_USAGER" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table USAGER
--------------------------------------------------------

  CREATE TABLE "USAGER" 
   (	
    "ID_USAGER" NUMBER(*,0), 
	"NOM" VARCHAR2(255), 
	"PRENOM" VARCHAR2(255), 
	"ANNEE_NAISSANCE" NUMBER(*,0), 
	"TARIF_REDUIT" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  Constraints for Table PRET
--------------------------------------------------------

  ALTER TABLE "PRET" ADD PRIMARY KEY ("ID_PRET")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table LIVRE
--------------------------------------------------------

  ALTER TABLE "LIVRE" ADD PRIMARY KEY ("ISBN")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTEUR
--------------------------------------------------------

  ALTER TABLE "AUTEUR" ADD PRIMARY KEY ("ID_AUTEUR")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table GENRE
--------------------------------------------------------

  ALTER TABLE "GENRE" ADD PRIMARY KEY ("ID_GENRE")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table USAGER
--------------------------------------------------------

  ALTER TABLE "USAGER" ADD PRIMARY KEY ("ID_USAGER")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LIVRE
--------------------------------------------------------

  ALTER TABLE "LIVRE" ADD CONSTRAINT "FK_AUTEUR" FOREIGN KEY ("ID_AUTEUR")
	  REFERENCES "AUTEUR" ("ID_AUTEUR") ENABLE;
  ALTER TABLE "LIVRE" ADD CONSTRAINT "FK_GENRE" FOREIGN KEY ("GENRE")
	  REFERENCES "GENRE" ("ID_GENRE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRET
--------------------------------------------------------

  ALTER TABLE "PRET" ADD CONSTRAINT "FK_ISBN" FOREIGN KEY ("ISBN")
	  REFERENCES "LIVRE" ("ISBN") ENABLE;
  ALTER TABLE "PRET" ADD CONSTRAINT "FK_USAGER" FOREIGN KEY ("ID_USAGER")
	  REFERENCES "USAGER" ("ID_USAGER") ENABLE;
