package com.light.gameserver.services;

import com.light.gameserver.services.myservice.TimeApService;
import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.audit.GMService;
import java.util.Collection;

/**
 * @author Alex
 */
public final class AnnounService {

    public static AnnounService getInstance() {
        return SingletonHolder.instance;
    }
    private Collection<Player> GMs = GMService.getInstance().getGMs();
    int GM;

    public void onPlayerLogin(Player player) {
        for (Player gm : GMs) {
            GM++;
            if (AdminConfig.CUSTOMTAG_PLAYER_INFO_ENABLE) {
                if (player == gm) {
                    PacketSendUtility.sendBrightYellowMessage(player, "Вы входите в игру");
		    PacketSendUtility.sendBrightYellowMessage(player, "Выдача ап произойдет в "+TimeApService.getInstance().time+":00");
                }

                PacketSendUtility.sendBrightYellowMessage(gm,
                        player.getName() + " входит в игру.\n"
                        + "||===========|INFO: [" + player.getName() + "] |===========||\n"
                        + "AccessLevel " + player.getAccessLevel() + "\n"
                        + "IP: " + player.getClientConnection().getIP() + "\n"
                        + "Раса: " + player.getRace().getRusname() + "\n"
                        + "Класс: " + player.getPlayerClass().getRusname() + "\n"
                        + "Имя аккаунта: " + player.getClientConnection().getAccount().getName() + "\n"
                        + "||============================||\n");
            } else {
                PacketSendUtility.sendBrightYellowMessage(gm,
                        player.getName() + " входит в игру.\n"
                        + "AccessLevel " + player.getAccessLevel() + "\n"
                        + "IP: " + player.getClientConnection().getIP() + "\n");
            }
        }
    }

    public void onPlayerLogedOut(Player player) {
        for (Player gm : GMs) {
            GM--;
            PacketSendUtility.sendBrightYellowMessage(gm,
                    "AccessLevel " + player.getAccessLevel() + " : " + player.getName() + " выходит из игры.\nIP: " + player.getClientConnection().getIP() + "\n");
            if (player == gm) {
                return;
            }
        }
        GMs.remove(player);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final AnnounService instance = new AnnounService();
    }
}