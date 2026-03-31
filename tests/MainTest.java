import models.Livre;
import models.Pret;
import models.Usager;
import utils.LogManager;

import java.util.Date;
import java.util.Scanner;

public class MainTest {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean stop = true;

    public static void main(String[] args) {
        LogManager.getInstance();
        while(stop) {
            System.out.println("""
              
                    Que voulez-vous faire ?
                    1. Créer un livre
                    2. Créer un utilisateur
                    3. Créer un prêt
                    4. Quitter""");
            int choix = scanner.nextInt();
            scanner.nextLine();

            if (choix < 1 || choix > 4) continue;

            switch (choix) {
                case 1:
                    creerLivre();
                    break;
                case 2:
                    creerUtilisateur();
                    break;
                case 3:
                    creerPret();
                    break;
                case 4:
                    stop = false;
                    LogManager.getInstance().shutdown();
                    break;
            }
        }
    }

    private static void creerLivre() {
        System.out.println("Création d'un livre");
        System.out.println("ISBN : ");
        int isbn = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Titre : ");
        String titre = scanner.nextLine();

        System.out.println("Auteur : ");
        String auteur = scanner.nextLine();

        System.out.println("Nombre de pages : ");
        int nbPages = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Genre : ");
        String genre = scanner.nextLine();

        System.out.println("Nombre d'exemplaires : ");
        int nbExemplaires = scanner.nextInt();

//        Livre livre = new Livre(isbn, titre, auteur, nbPages, genre, nbExemplaires);
//        LogManager.getInstance().info("Livre créé : " + livre);
    }

    private static void creerUtilisateur() {
        System.out.println("Création d'un utilisateur");
        System.out.println("ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nom : ");
        String nom = scanner.nextLine();

        System.out.println("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.println("Année de naissance : ");
        int anneeNaissance = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Tarif réduit (true/false) : ");
        boolean tarifReduit = scanner.nextBoolean();

        Usager usager = new Usager(id, nom, prenom, anneeNaissance, tarifReduit);
        LogManager.getInstance().info("Utilisateur créé : " + usager);
    }

    private static void creerPret() {
        System.out.println("Création d'un prêt");
        System.out.println("ID du livre : ");
        int idLivre = scanner.nextInt();
        scanner.nextLine();

        System.out.println("ID de l'utilisateur : ");
        int idUsager = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Durée du prêt (en jours) : ");
        int dureePret = scanner.nextInt();

        Livre livre = new Livre(idLivre, null, null, 0, null, 0);
        Usager usager = new Usager(idUsager, null, null, 0, false);

//        Pret pret = new Pret(new Date(), dureePret, null, livre, usager);
//        LogManager.getInstance().info("Prêt créé : " + pret);
    }
}
