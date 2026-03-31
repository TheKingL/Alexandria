import org.junit.jupiter.api.Test;

import dao.ConnexionDAO;

public class DatabaseTest {

    @Test
    public void testConnection() {
        assert ConnexionDAO.getConnection() != null;
    }
}
