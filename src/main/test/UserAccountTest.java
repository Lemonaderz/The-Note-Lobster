
import com.example.thenotelobster.model.UserClasses.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {

    private UserAccount userAccount;


    @BeforeEach
    public void setUp() {
        userAccount = UserAccount.getInstance();
        userAccount.setUser("Email@1", "John", "Password123");

    }
    @Test
    public void testGetInstance() {
        assertEquals(userAccount, UserAccount.getInstance());
    }
    @Test
    public void testSetUser() {
        userAccount.setUser("Email@2", "Jannet", "TheBestPassword");
        assertEquals("Email@2" , userAccount.getEmail());

        assertEquals("Jannet", userAccount.getUserName());

        assertEquals("TheBestPassword", userAccount.getPassword());
    }

    @Test
    public void testGetEmail() {
        assertEquals("Email@1", userAccount.getEmail());
    }
    @Test
    public void testGetUserName() {
        assertEquals("John", userAccount.getUserName());
    }
    @Test
    public void testGetPassword() {
        assertEquals("Password123", userAccount.getPassword());

    }
    @Test
    public void testSetEmail() {
        userAccount.setEmail("Email@3");
        assertEquals("Email@3", userAccount.getEmail());

    }
    @Test
    public void testSetUserName() {
        userAccount.setUserName("Jordi");
        assertEquals("Jordi", userAccount.getUserName());
    }
    @Test
    public void testSetPassword() {
        userAccount.setPassword("SuperSecretPassword1");
        assertEquals("SuperSecretPassword1", userAccount.getPassword());
    }

}