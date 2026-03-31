package ui.gui.views;

import dao.controllers.LivreDAO;
import dao.controllers.PretDAO;
import dao.controllers.UsagerDAO;
import utils.LogManager;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * This class generates a result file with the following information:
 * - Number of users by year of birth
 * - Average loan duration
 * - Number of books containing a specific word in the title
 */
public class GenerateResultFile {

    /**
     * Generates a result file with the following information:
     * - Number of users by year of birth
     * - Average loan duration
     * - Number of books containing a specific word in the title
     */
    public static void generate() {
        // -------- SEARCH WORD --------
        String wordToSearch = JOptionPane.showInputDialog(null, "Input the word to search in the books title :", "Search Word In Books", JOptionPane.QUESTION_MESSAGE);

        // if the user cancels the dialog or enters an empty string
        if (wordToSearch == null) {
            JOptionPane.showMessageDialog(null, "File generation cancelled.", "Cancelled", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (wordToSearch.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a word to search.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // SQL injection check
        if (!wordToSearch.matches("[a-zA-ZÀ-ÿ\\s'-]+")) {
            JOptionPane.showMessageDialog(null, "Invalid characters in input field. Please enter a valid word.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // -------- GENERATE RESULT FILE --------
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        String fileName = "Resultats_Alexandria_" + timeStamp + ".txt";
        File resultFile = new File("ressources/resultfiles", fileName);

        StringBuilder fileContent = new StringBuilder();

        // -------- FILE CONTENT --------
        // -------- Number of users by year of birth --------
        fileContent.append("Nombre d'utilisateurs par année de naissance :\n");
        for (Map.Entry<Integer, Integer> entry : new UsagerDAO().getNbUserByYear().entrySet()) {
            fileContent.append("Année ").append(entry.getKey()).append(" : ").append(entry.getValue()).append(" utilisateurs\n");
        }
        fileContent.append("\n");

        // -------- Average loan duration --------
        fileContent.append("Durée effective moyenne d’un prêt : ");
        double averageLoanDuration = new PretDAO().getAverageEffectiveLoanDuration();
        fileContent.append(averageLoanDuration).append(" jours\n");
        fileContent.append("\n");

        // ------- Number of books containing the search word --------
        fileContent.append("Nombre de livres contenant le mot '").append(wordToSearch).append("' : ");
        int bookCount = new LivreDAO().countBooksContainingWord(wordToSearch);
        fileContent.append(bookCount).append(" livres trouvés\n");

        // -------- WRITE TO FILE --------
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writer.write(fileContent.toString());
            JOptionPane.showMessageDialog(null, "Result file generated successfully : \n" + resultFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            LogManager.getInstance().info("Result file generated successfully: " + resultFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while generating the result file : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LogManager.getInstance().error("Error while generating the result file: " + e.getMessage());
        }
    }
}