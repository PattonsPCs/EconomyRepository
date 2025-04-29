

import com.anthony.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    private Account account;
    private ServerMock server;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
        Player player = server.addPlayer();
        account = new Account(player.getUniqueId(), 100);
    }

    @Test
    public void testInitBalance(){
        assertEquals(100, account.getBalance(), "Initial balance should be 100");
    }

    @Test
    public void testWithdraw(){
        account.withdraw(10);
        assertEquals(90, account.getBalance(), "Balance should be 90 after withdrawal");
    }

    @AfterEach
    public void teardown() {
        account = null;
        MockBukkit.unmock();
    }
}
