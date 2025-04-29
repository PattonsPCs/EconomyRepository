
import com.anthony.Account;
import com.anthony.EconData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EconDataTest {
    private EconData econData;
    private UUID testUuid;
    private UUID testUuid1;
    private UUID testUuid2;

    @BeforeEach
    void setup() {
        econData = new EconData();
        Player player = mock(Player.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        testUuid = UUID.randomUUID();
        testUuid1 = UUID.randomUUID();
        testUuid2 = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(testUuid);
        when(player1.getUniqueId()).thenReturn(testUuid1);
        when(player2.getUniqueId()).thenReturn(testUuid2);
        econData.saveAccount(player.getUniqueId(), 100);
        econData.saveAccount(player1.getUniqueId(), 50);
        econData.saveAccount(player2.getUniqueId(), 10);
    }

    @Test
    void testLoadBalance(){
        int balance = econData.loadBalance(testUuid);
        assertEquals(100, balance, "Balance should be 100 after load for player");
        balance = econData.loadBalance(testUuid1);
        assertEquals(50, balance, "Balance should be 50 after load for player1");
        balance = econData.loadBalance(testUuid2);
        assertEquals(10, balance, "Balance should be 10 after load for player2");
    }

    @Test
    void testLoadAllAccounts(){
        Map<UUID, Account> accounts = new HashMap<>();
        econData.loadAllAccounts(accounts);
        assertEquals(3, accounts.size(), "Accounts map should contain 3 entries");
        Account account = accounts.get(testUuid);
        Account account1 = accounts.get(testUuid1);
        Account account2 = accounts.get(testUuid2);
        assertEquals(100, account.getBalance(), "Account balance should be 100");
        assertEquals(50, account1.getBalance(), "Account balance should be 50");
        assertEquals(10, account2.getBalance(), "Account balance should be 10");
    }


    @Test
    void testLoadAllAccountsEmptyMap(){
        Map<UUID, Account> accounts = new HashMap<>();
        econData.loadAllAccounts(accounts);
        assertEquals(0, accounts.size(), "Accounts map should be empty");
    }

    @Test
    void testSaveAccount(){
        econData.saveAccount(testUuid, 1000);
        int balance = econData.loadBalance(testUuid);
        assertEquals(1000, balance, "Balance should be 1000 after save");
    }


    @AfterEach
    void teardown() {
        econData = null;
    }




}
