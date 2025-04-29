

import com.anthony.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setup() {
        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        account = new Account(player.getUniqueId(), 100);
    }

    @Test
    void testInitBalance(){
        assertEquals(100, account.getBalance(), "Initial balance should be 100");
    }

    @Test
    void testWithdraw(){
        account.withdraw(10);
        assertEquals(90, account.getBalance(), "Balance should be 90 after withdrawal");
    }

    @AfterEach
    void teardown() {
        account = null;
    }







}
