# Alexandria Project 📚✨
Welcome to the Alexandria Project! 🚀 This project is dedicated to creating a comprehensive and user-friendly platform for managing and sharing knowledge. Whether you're a student, a professional, or just a curious mind, Alexandria is here to help you explore the world of information! 🌍💡

## Project Description 📖
The Alexandria Project is a Java-based application designed for managing a library system. It provides functionalities for handling books, authors, genres, users, and loans through a graphical user interface (GUI) and interacts with a database.

## Features 🛠️
-   📚 **Book Management:** Add, view, update, and delete books.
-   🧑‍🏫 **Author Management:** Add, view, update, and delete authors.
-   🎨 **Genre Management:** Add, view, update, and delete genres.
-   👤 **User Management:** Add, view, update, and delete users.
-   🔄 **Loan Management:** Handle book loans to users, track return dates.
-   📊 **Reporting:** View reports like the number of users by birth year.
-   💾 **Persistence:** Data is stored and retrieved from a database.

## Technologies Used 💻
-   ☕ **Java:** The core programming language.
-   🖼️ **Swing:** Used for building the graphical user interface.
-   🔗 **JDBC:** For database connectivity.
-   🔴 **ORACLE:** The database management system used to store data.
-   📅 **JDatePicker:** A third-party library for date selection components in the GUI.

## Project Structure 📂
The project is organized into several packages:

-   `dao`: 📦 Contains Data Access Objects (DAOs) for interacting with the database.
-   `models`: 🧱 Contains the data models representing the entities (Book, Author, Genre, User, Loan).
-   `ui.gui`: 🖥️ Contains the classes for the graphical user interface.
    -   `components`: 🧩 Reusable GUI components.
    -   `forms`: 📝 Forms for adding and editing data.
    -   `views`: 👁️ Views for displaying data details and reports.
-   `utils`: 🔧 Utility classes (e.g., for database connection, date handling).

## Installation and Setup ⚙️

1.  **Database Setup:** 🗄️
    *   Ensure you have ORACLE installed and running.
    *   Create a database named `C##POI7`.
    *   Execute the SQL scripts in the `ressources` to create the necessary tables and procedures (likely for `auteur`, `genre`, `livre`, `usager`, `pret`).

2.  **Clone the Repository:** 🔄
    ```bash
    git clone <repository_url>
    cd projet-java-2025-TheKingL
    ```
3.  **Open in your IDE:** 💡 Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans).

4.  **Configure Database Connection:** 🔌
    *   Locate the database configuration file (likely in the `utils` package or a properties file).
    *   Update the database connection details (URL, username, password) to match your ORACLE setup.

5.  **Add Libraries:** 📚
    *   Add the ORACLE Connector/J library to your project's dependencies. You can usually find this in your IDE's project structure settings.
    *   Add the JDatePicker library to your project's dependencies. You might need to download the JAR file and add it manually.

6.  **Build and Run:** ▶️
    *   Build the project in your IDE.
    *   Run the main application `Main.java` located at the root of `src`.

## Usage 🖱️
Once the application is running, you can navigate through the different management windows (Books, Authors, Genres, Users, Loans) using the GUI. Use the buttons and forms to add, view, update, or delete data.

## Contributing 🤝
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:
1.  **Fork** the repository. 🍴
2.  Create a new **branch** for your feature or bug fix. 🌿
3.  Make your **changes** and **commit** them with descriptive messages. ✍️
4.  **Push** your changes to your forked repository. ⬆️
5.  Submit a **pull request** to the main repository. 📬

## License 📜
This project is licensed under the MIT License. See the [LICENSE file](nolink) for more details.

## Contact 📧
For any questions or inquiries, please contact [me](mailto:loris.cazaux@groupe-esigelec.org)!