/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light.gameserver.services.myservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.services.CronService;
import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.dao.PlayerDAO;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.abyss.AbyssPointsService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.World;
import java.util.*;

/**
 *
 * @author Alex
 */
public class TimeApService {

    private static final Logger log = LoggerFactory.getLogger(TimeApService.class);
    
    private static class SingletonHolder {

        protected static final TimeApService instance = new TimeApService();
    }

    public static TimeApService getInstance() {
        return TimeApService.SingletonHolder.instance;
    }
    
    private Random rand = new Random();
    public int time = rand.nextInt(23) + 12;

    public void start() {
        
        String cron = "0 0 0," + time + " ? * *";
        CronService.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                for (Player pp : World.getInstance().getAllPlayers()) {
                    int count = AdminConfig.COUNT_TIME * DAOManager.getDAO(PlayerDAO.class).getOnlinePlayerCount();
                    AbyssPointsService.addAp(pp, count);
                    PacketSendUtility.sendYellowMessageOnCenter(pp, "Вау, вы везунчик вы получили "+count+" Abyss Point :)");
                    PacketSendUtility.sendMessage(pp, "Администрация сервера благодарит вас за игру.Вам было выдано "+count+" AP.\nПриятной игры на нашем сервере!");
                }
            }
        }, cron);
		log.info("Scheduled Abyss All: based on cron expression: "+cron);

    }
}
