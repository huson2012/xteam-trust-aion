package com.light.gameserver.services.myservice;

import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.AionConnection;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.ThreadPoolManager;
import com.light.gameserver.world.World;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex
 */
public class GuardService {

    private static final Logger log = LoggerFactory.getLogger(GuardService.class);

    public static GuardService getInstance() {
        return SingletonHolder.instance;
    }

    public void guardtest(final Player player, AionConnection client) {
        Collection<Player> asd = World.getInstance().getAllPlayers();
        for (Iterator<Player> it = asd.iterator(); it.hasNext();) {
            final Player players = it.next();

            if (players.getClientConnection().getMacAddress() == null ? client.getMacAddress() == null : players.getClientConnection().getMacAddress().equals(client.getMacAddress())) {
                if (players == player) {
                    return;
                } else if (players != player) {
                    if (player.isGM() || players.isGM()) {
                        return;
                    }

                    ThreadPoolManager.getInstance().schedule(new Runnable() {

                        @Override
                        public void run() {
                            if (AdminConfig.GUARD_WINDOW_TRUE) {
                                PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(902247, 0, "" + AdminConfig.MESS_GUARD_XD + ""));
                                player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                                players.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                            } else {
                                player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                                players.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                            }
                        }
                    }, AdminConfig.TIME_KICK);
                }
            }
        }
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final GuardService instance = new GuardService();
    }
}