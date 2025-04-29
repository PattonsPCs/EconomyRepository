
import com.anthony.Account;
import com.anthony.EconData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EconDataTest {
    private EconData econData;

    private final ServerMock serverMock = MockBukkit.mock();
    private final Player player = serverMock.addPlayer();
    private final Player player1 = serverMock.addPlayer();
    private final Player player2 = serverMock.addPlayer();

    @BeforeEach
    public void setup() {
        econData = new EconData();
        econData.saveAccount(player.getUniqueId(), 100);
        econData.saveAccount(player1.getUniqueId(), 50);
        econData.saveAccount(player2.getUniqueId(), 10);
    }

    @Test
    public void testLoadBalance(){
        assertEquals(100, econData.loadBalance(player.getUniqueId()), "Balance should be 100 after load for player");
        assertEquals(50, econData.loadBalance(player1.getUniqueId()), "Balance should be 50 after load for player1");
        assertEquals(10, econData.loadBalance(player2.getUniqueId()), "Balance should be 10 after load for player2");
    }

    @Test
    public void testLoadAllAccounts(){
        Map<UUID, Account> accounts = new HashMap<>();
        econData.loadAllAccounts(accounts);
        assertEquals(3, accounts.size(), "Accounts map should contain 3 entries");
        Account account = accounts.get(player.getUniqueId());
        Account account1 = accounts.get(player1.getUniqueId());
        Account account2 = accounts.get(player2.getUniqueId());
        assertEquals(100, account.getBalance(), "Account balance should be 100");
        assertEquals(50, account1.getBalance(), "Account balance should be 50");
        assertEquals(10, account2.getBalance(), "Account balance should be 10");
    }


    @Test
    public void testSaveAccount(){
        econData.saveAccount(player.getUniqueId(), 1000);
        assertEquals(1000, econData.loadBalance(player.getUniqueId()), "Balance should be 1000 after save");
    }


    @AfterEach
    public void teardown() {
        econData = null;
        MockBukkit.unmock();
    }

}
