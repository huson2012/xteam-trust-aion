/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light.gameserver.global;

import com.light.gameserver.global.additions.MessagerAddition;
import com.light.gameserver.model.Gender;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.items.storage.Storage;
import com.light.gameserver.network.BannedMacManager;
import com.light.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.light.gameserver.network.loginserver.LoginServer;
import com.light.gameserver.services.PunishmentService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.World;
import com.light.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dean
 */

public class AioNation {
    
  
  public static final Logger log = LoggerFactory.getLogger(AioNation.class);

    public static void banAccount(Player player, String message, int time)
    {
           String reason = "[Aion Light]:" + message;
           String address = player.getClientConnection().getMacAddress();
           String accountIp = player.getClientConnection().getIP();
           int accountId = player.getClientConnection().getAccount().getId();
           int playerId = player.getObjectId();
           int minInDay = 1440;
           int dayCount = (int)(Math.floor((double)(time/minInDay)));
           LoginServer.getInstance().sendBanPacket((byte)1, accountId, accountIp, time, 0);
          
    }
    public static void banIp(Player player, int time)
    {
           String address = player.getClientConnection().getMacAddress();
           String accountIp = player.getClientConnection().getIP();
           int accountId = player.getClientConnection().getAccount().getId();
           int playerId = player.getObjectId();
           int minInDay = 1440;
           int dayCount = (int)(Math.floor((double)(time/minInDay)));
           LoginServer.getInstance().sendBanPacket((byte)2, accountId, accountIp, time, 0);
    }
    public static void MacBan(Player player, int time, String message)
    {
           String reason = "[Aion Light]:" + message;
           String address = player.getClientConnection().getMacAddress();
           String accountIp = player.getClientConnection().getIP();
           int accountId = player.getClientConnection().getAccount().getId();
           int playerId = player.getObjectId();
           int minInDay = 1440;
           int dayCount = (int)(Math.floor((double)(time/minInDay)));
        BannedMacManager.getInstance().banAddress(address, System.currentTimeMillis() + time * 60000, reason);
    }
    public static void disconnect(Player player)
    {
        player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
    }
    /**
     * So This method use so = AioNation.decreaseKinah(player, 500);
     * Where 500 is your 
     * @param player
     * @param price 
     */
    public static void decreaseKinah(Player player, int price) {
        Storage inventory = player.getInventory();
        inventory.decreaseKinah(price);
    }
}
