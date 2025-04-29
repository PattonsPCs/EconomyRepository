
import com.anthony.Account;
import com.anthony.Econ;
import com.anthony.commands.BalanceCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import net.kyori.adventure.text.Component;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings({"UnstableApiUsage", "ConstantConditions"})
public class BalanceCommandTest {
    private BalanceCommand balanceCommand;
    private Player player;
    private CommandSourceStack source;
    private Account account;
    private final UUID testUuid = UUID.randomUUID();

    @BeforeEach
    void setup() {
        Econ econ = mock(Econ.class);
        player = mock(Player.class);
        source = mock(CommandSourceStack.class);
        account = mock(Account.class);

        when(source.getExecutor()).thenReturn(player);
        when(econ.getAccount(player)).thenReturn(account);
        when(account.getBalance()).thenReturn(123);
        when(player.getUniqueId()).thenReturn(testUuid);

        balanceCommand = new BalanceCommand(econ);
    }

    @Test
    void testExecute_sendsBalanceMessage(){
        balanceCommand.execute(source, new String[0]);
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("Your current balance is 123")
        ));
    }


    @Test
    void testGetBalance(){
        account = new Account(player.getUniqueId(), 123);
        assertEquals(123, account.getBalance(), "Account balance should be 123");
    }


    @AfterEach
    void teardown() {
        balanceCommand = null;
    }
}
