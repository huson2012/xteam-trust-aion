package admincommands;

import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.configs.administration.CommandsConfig;
import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.Item;
import com.light.gameserver.model.gameobjects.PersistentState;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.items.ManaStone;
import com.light.gameserver.model.stats.listeners.ItemEquipmentListener;
import com.light.gameserver.model.templates.item.GodstoneInfo;
import com.light.gameserver.model.templates.item.ItemTemplate;
import com.light.gameserver.model.templates.item.ItemType;
import com.light.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.light.gameserver.services.item.ItemPacketService;
import com.light.gameserver.services.item.ItemSocketService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.audit.GMService;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author Tago modified by Wakizashi
 */
public class Equip extends ChatCommand {

    public Equip() {
        super("equip");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (admin.getAccessLevel() < CommandsConfig.EQUIP) {
                    PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
                    return;
                }
        if (params.length != 0) {
            int i = 0;
            if ("help".startsWith(params[i])) {
                if (params[i + 1] == null)
                    showHelp(admin);
                else if ("enchantsss".startsWith(params[i + 1]))
                    showHelpEnchant(admin);
                return;
            }
            Player player = null;
            player = World.getInstance().findPlayer(Util.convertName(params[i]));
            if (player == null) {
                VisibleObject target = admin.getTarget();
                if (target instanceof Player)
                    player = (Player) target;
                else
                    player = admin;
            }
            else
                i++;
            if ("magkamni".startsWith(params[i])) {
                /*if (admin.getAccessLevel() < 1) {
                    PacketSendUtility.sendMessage(admin, "У Вас не достаточно прав для использования этой команды");
                    return;
                }*/
                int manastone = 167000551;
                int quant = 0;
                try {
                    manastone = params[i + 1] == null ? manastone : Integer.parseInt(params[i + 1]);
                    quant = params[i + 2] == null ? quant : Integer.parseInt(params[i + 2]);
                }
                catch (Exception ex2) {
                    showHelpSocket(admin);
                    return;
                }
                socket(admin, player, manastone, quant);
                return;
            }
            if ("enchant".startsWith(params[i])) {
                /*if (admin.getAccessLevel() < CommandsConfig.EQUIP) {
                    PacketSendUtility.sendMessage(admin, "У Вас не достаточно прав для использования этой команды");
                    return;
                }*/
                int enchant = 0;
                try {
                    enchant = params[i + 1] == null ? enchant : Integer.parseInt(params[i + 1]);
                }
                catch (Exception ex) {
                    showHelpEnchant(admin);
                    return;
                }
                enchant(admin, player, enchant);
                return;
            }
            if ("godstone".startsWith(params[i])) {
                if (admin.getAccessLevel() < CommandsConfig.EQUIP) {
                    PacketSendUtility.sendMessage(admin, "У вас не достаточно прав для использования этой команды");
                    return;
                }
                int godstone = 100;
                try {
                    godstone = params[i + 1] == null ? godstone : Integer.parseInt(params[i + 1]);
                }
                catch (Exception ex) {
                    showHelpGodstone(admin);
                    return;
                }
                if (admin.getAccessLevel() == 0) 
                {
                    godstone(admin, player, 1);
                    return;
                }
                else 
                {
                   godstone(admin, player, godstone);
                   return;
                }
            }
        }
        showHelp(admin);
    }

    private void socket(Player admin, Player player, int manastone, int quant) {
        if (manastone != 0 && (manastone < 167000000 || manastone > 168000000)) {
            PacketSendUtility.sendMessage(admin, "You are suposed to give the item id for a"
                + " Manastone or 0 to remove all manastones.");
            return;
        }
        for (Item targetItem : player.getEquipment().getEquippedItemsWithoutStigma()) {
            if (isUpgradeble(targetItem)) {
                if (manastone == 0) {
                    ItemEquipmentListener.removeStoneStats(targetItem.getItemStones(), player.getGameStats());
                    ItemSocketService.removeAllManastone(player, targetItem);
                }
                else {
                    int counter = quant <= 0 ? getMaxSlots(targetItem) : quant;
                    while (targetItem.getItemStones().size() < getMaxSlots(targetItem) && counter >= 0) {
                        ManaStone manaStone = ItemSocketService.addManaStone(targetItem, manastone);
                        ItemEquipmentListener.addStoneStats(targetItem, manaStone, player.getGameStats());
                        counter--;

                    }
                }
                PacketSendUtility.sendPacket(player, new SM_STATS_INFO(player));
                ItemPacketService.updateItemAfterInfoChange(player, targetItem);
                targetItem.setPersistentState(PersistentState.UPDATE_REQUIRED);
            }

        }
        if (manastone == 0) {
            if (player == admin)
                PacketSendUtility.sendMessage(player, "All Manastones removed from all equipped Items");
            else {
                PacketSendUtility.sendMessage(admin,
                    "All Manastones removed from all equipped Items by the Player " + player.getName());
                PacketSendUtility.sendMessage(player, "Admin " + admin.getName()
                    + " removed all manastones from all your equipped Items");
            }
        }
        else {
            if (player == admin)
                PacketSendUtility.sendMessage(player, quant + "x [item: " + manastone
                    + "] were added to free slots on all equipped items");
            else {
                PacketSendUtility.sendMessage(admin, quant + "x [item: " + manastone
                    + "] were added to free slots on all equipped items by the Player " + player.getName());
                PacketSendUtility.sendMessage(player, "Admin " + admin.getName() + " added " + quant + "x [item: " + manastone
                    + "] to free slots on all your equipped items");
            }
        }
    }

    private void godstone(Player admin, Player player, int godstone) {
        Item targetItem = player.getEquipment().getMainHandWeapon();
        if (godstone > 100000000) {
            ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(godstone);
            GodstoneInfo godstoneInfo = itemTemplate.getGodstoneInfo();
            if (godstoneInfo == null) {
                PacketSendUtility.sendMessage(admin, "Wrong GodStone ItemID");
                return;
            }
            targetItem.addGodStone(godstone);
            PacketSendUtility.sendPacket(player, new SM_STATS_INFO(player));
            ItemPacketService.updateItemAfterInfoChange(player, targetItem);
            targetItem.setPersistentState(PersistentState.UPDATE_REQUIRED);
            if (player == admin)
                PacketSendUtility.sendMessage(player, "[Item: " + godstone
                    + "] socketed to your equipped MainHandWeapon [Item: " + targetItem.getItemId() + "]");
            else {
                PacketSendUtility.sendMessage(admin, "[Item: " + godstone + "] socketed to the Player " + player.getName()
                    + "equipped MainHandWeapon [Item: " + targetItem.getItemId() + "]");
                PacketSendUtility.sendMessage(player, "Admin " + admin.getName() + " socketed [Item: " + godstone
                    + "]  to you equipped MainHandWeapon [Item: " + targetItem.getItemId() + "]");
            }
            targetItem.getGodStone().onEquip(player);
        }
        else if (targetItem.getGodStone() != null) {
            targetItem.getGodStone().onUnEquip(player);
            try {
                if (godstone < 100)
                    godstone *= 10;
                if (godstone > 1000)
                    godstone = 1000;
                Class<?> gs = targetItem.getGodStone().getClass();
                Field probability = gs.getDeclaredField("probability");
                Field probabilityLeft = gs.getDeclaredField("probability");
                probability.setAccessible(true);
                probabilityLeft.setAccessible(true);
                probability.setInt(targetItem.getGodStone(), godstone);
                probabilityLeft.setInt(targetItem.getGodStone(), godstone);
            }
            catch (Exception ex2) {
                PacketSendUtility.sendMessage(admin, "Occurs an error.");
                return;
            }
            targetItem.getGodStone().onEquip(player);
            if (player == admin)
                PacketSendUtility.sendMessage(player, "Your godstone on your MainHandWeapon will now activate around "
                    + godstone + " percent of the time.");
            else {
                PacketSendUtility.sendMessage(admin, "Player " + player.getName()
                    + " godstone on MainHandWeapon will now activate around " + godstone + " percent of the time.");
                PacketSendUtility.sendMessage(player, "Admin " + admin.getName()
                    + " blessed your godstone on your MainHandWeapon to now activate around " + godstone
                    + " percent of the time.");
            }
        }
    }
	
    private void enchant(Player admin, Player player, int enchant) {
        for (Item targetItem : player.getEquipment().getEquippedItemsWithoutStigma()) {
            if (isUpgradeble(targetItem)) {
                if (targetItem.getEnchantLevel() == enchant)
                    continue;
                if (enchant > 15)
                    enchant = 15;
                if (enchant < 15)
                    enchant = 15;

                targetItem.setEnchantLevel(enchant);
                if (targetItem.isEquipped()) {
                    player.getGameStats().updateStatsVisually();
                }
                ItemPacketService.updateItemAfterInfoChange(player, targetItem);
            }
        }
        if (AdminConfig.ANN_GM && admin.getAccessLevel() == 0){
            Collection<Player> GMs = GMService.getInstance().getGMs();
	      for (Player gm : GMs) {
                  PacketSendUtility.sendMessage(gm, "Персонаж " + admin.getName() + " затачивает все надетые предметы на " + enchant+" уровень");
              }
        }
        if (player == admin)
            PacketSendUtility.sendMessage(player, "All equipped items were enchanted to level " + enchant);
        else {
            PacketSendUtility.sendMessage(admin, "All equipped items by the Player " + player.getName()
                + " were enchanted to " + enchant);
            PacketSendUtility.sendMessage(player, "Admin " + admin.getName() + " enchanted all your equipped items to level "
                + enchant);
        }
    }

    /**
     * Verify if the item is enchantble and/or socketble
     *
     * @param item
     */
    public static boolean isUpgradeble(Item item) {
        if (item.getItemTemplate().isNoEnchant())
            return false;
        if (item.getItemTemplate().isWeapon())
            return true;
        if (item.getItemTemplate().isArmor()) {
            int at = item.getItemTemplate().getItemSlot();
            if (at == 1 || /* Main Hand */
            at == 2 || /* Sub Hand */
            at == 8 || /* Jacket */
            at == 16 || /* Gloves */
            at == 32 || /* Boots */
            at == 2048 || /* Shoulder */
            at == 4096 || /* Pants */
            at == 131072 || /* Main Off Hand */
            at == 262144) /* Sub Off Hand */
                return true;
        }
        return false;

    }

    /**
     * Returns the max number of manastones that can be socketed
     *
     * @param item
     */
    public static int getMaxSlots(Item item) {
        int slots = 0;
        switch (item.getItemTemplate().getItemQuality()) {
            case COMMON:
            case JUNK:
                slots = 1;
                break;
            case RARE:
                slots = 2;
                break;
            case LEGEND:
                slots = 3;
                break;
            case UNIQUE:
                slots = 4;
                break;
            case EPIC:
                slots = 5;
                break;
            default:
                slots = 0;
                break;
        }
        if (item.getItemTemplate().getItemType() == ItemType.DRACONIC)
            slots += 1;
        if (item.getItemTemplate().getItemType() == ItemType.ABYSS)
            slots += 2;
        return slots;

    }

    private void showHelp(Player admin) {
PacketSendUtility.sendMessage(admin,"Введите //equip enchant 15.\n");
    }

    private void showHelpEnchant(Player admin) {
PacketSendUtility.sendMessage(admin,"Введите //equip enchant 15.\n");
    }

    private void showHelpSocket(Player admin) {
PacketSendUtility.sendMessage(admin,"Введите //equip enchant 15.\n");
    }

    private void showHelpGodstone(Player admin) {
PacketSendUtility.sendMessage(admin,"Введите //equip enchant 15.\n");
    }

    @Override
    public void onFail(Player player, String message) {
        showHelp(player);
    }
}
