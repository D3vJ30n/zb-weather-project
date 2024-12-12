import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private static class TestData {
        private String message;

        public TestData() {
            this.message = "test";
        }

        public String getMessage() {
            return message;
        }
    }

    @Test
    void jacksonTest() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TestData data = new TestData();
            String json = mapper.writeValueAsString(data);
            assertEquals("{\"message\":\"test\"}", json);
        } catch (Exception e) {
            fail("Jackson Fail: " + e.getMessage());
        }
    }

    @Test
    void mariadbTest() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://192.168.219.101:3306/testdb1",
                "root",
                "Jdm4568396*"
            );
            assertNotNull(conn);
            conn.close();
        } catch (Exception e) {
            fail("MariaDB Fail: " + e.getMessage());
        }
    }

    @Test
    void lombokTest() {
        TestData data = new TestData();
        assertEquals("test", data.getMessage());
    }
}