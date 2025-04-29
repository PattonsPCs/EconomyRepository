import com.anthony.Account;
import com.anthony.Econ;
import com.anthony.EconData;
import com.anthony.commands.BuyCommand;
import com.anthony.configuration.ShopConfig;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/*
@SuppressWarnings({"UnstableApiUsage", "ConstantConditions"})
public class BuyCommandTest {
    private ShopConfig mockShopConfig;
    private Player player;
    private CommandSourceStack source;
    private Account account;
    private BuyCommand buyCommand;
    private final UUID testUuid = UUID.randomUUID();

    @BeforeEach
    void setup() {
        // Mock all dependencies
        JavaPlugin plugin = mock(JavaPlugin.class);
        Econ econ = mock(Econ.class);
        player = mock(Player.class);
        source = mock(CommandSourceStack.class);
        account = mock(Account.class);
        EconData econData = mock(EconData.class);
        mockShopConfig = mock(ShopConfig.class);
        PlayerInventory inventory = mock(PlayerInventory.class);

        // Basic mocks setup
        when(source.getExecutor()).thenReturn(player);
        when(econ.getAccount(player)).thenReturn(account);
        when(account.getBalance()).thenReturn(123);
        when(player.getUniqueId()).thenReturn(testUuid);

        // Price configuration
        when(mockShopConfig.getItemPrice("apple")).thenReturn(10);
        when(mockShopConfig.getItemPrice("iron_chestplate")).thenReturn(12);
        when(mockShopConfig.getItemPrice("diamond_sword")).thenReturn(25);
        when(mockShopConfig.getItemPrice("steak")).thenReturn(15);
        when(mockShopConfig.getItemPrice("wheat")).thenReturn(8);

        // Always return true for canAfford by default
        when(mockShopConfig.canAfford(any(), any(), anyString())).thenReturn(true);

        // Mock ItemStack objects instead of creating real ones
        ItemStack appleStack = mock(ItemStack.class);
        ItemStack chestplateStack = mock(ItemStack.class);
        ItemStack swordStack = mock(ItemStack.class);
        ItemStack steakStack = mock(ItemStack.class);
        ItemStack wheatStack = mock(ItemStack.class);

        // Name these mocks for verification
        when(appleStack.toString()).thenReturn("APPLE");
        when(chestplateStack.toString()).thenReturn("IRON_CHESTPLATE");
        when(swordStack.toString()).thenReturn("DIAMOND_SWORD");
        when(steakStack.toString()).thenReturn("COOKED_BEEF");
        when(wheatStack.toString()).thenReturn("WHEAT");

        // Connect items to their names
        when(mockShopConfig.getItem("apple")).thenReturn(appleStack);
        when(mockShopConfig.getItem("iron_chestplate")).thenReturn(chestplateStack);
        when(mockShopConfig.getItem("diamond_sword")).thenReturn(swordStack);
        when(mockShopConfig.getItem("steak")).thenReturn(steakStack);
        when(mockShopConfig.getItem("wheat")).thenReturn(wheatStack);

        when(player.getInventory()).thenReturn(inventory);
        when(inventory.addItem(any(ItemStack.class))).thenReturn(new HashMap<>());
        // Create command with mocked dependencies
        buyCommand = new BuyCommand(econ, mockShopConfig, econData);
    }


    @Test
    void testExecute_BoughtItemApple(){
        buyCommand.execute(source, new String[]{"apple"});
        assertEquals(113, account.getBalance(), "Balance should be 113 after buying item");
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("You have bought APPLE for 10.")
        ));
    }

    @Test
    void testExecute_BoughtItemIronChestPlate(){
        buyCommand.execute(source, new String[]{"iron_chestplate"});
        assertEquals(111, account.getBalance(), "Balance should be 111 after buying item");
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("You have bought IRON_CHESTPLATE for 12.")
        ));
    }
    @Test
    void testExecute_BoughtItemDiamondSword(){
        buyCommand.execute(source, new String[]{"diamond_sword"});
        assertEquals(98, account.getBalance(), "Balance should be 98 after buying item");
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("You have bought DIAMOND_SWORD for 25.")
        ));
    }
    @Test
    void testExecute_BoughtSteak(){
        buyCommand.execute(source, new String[]{"steak"});
        assertEquals(108, account.getBalance(), "Balance should be 108 after buying item");
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("You have bought COOKED_BEEF for 15.")
        ));
    }
    @Test
    void testExecute_BoughtItemWheat(){
        buyCommand.execute(source, new String[]{"wheat"});
        assertEquals(115, account.getBalance(), "Balance should be 115 after buying item");
        verify(player).sendMessage((Component) argThat(component ->
                component != null && component.toString().contains("You have bought WHEAT for 8.")
        ));
    }

    @Test
    void testExecute_CannotAffordItem() {
        when(account.getBalance()).thenReturn(9);
        // Override the canAfford behavior for this specific test
        when(mockShopConfig.canAfford(eq(player), eq(account), eq("apple"))).thenReturn(false);
        buyCommand.execute(source, new String[]{"apple"});
        verify(account, never()).withdraw(anyInt());
        verify(player).sendMessage(any(Component.class));
    }


    @AfterEach
    void teardown() {
        buyCommand = null;
    }
}

 */
