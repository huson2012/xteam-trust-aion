/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light.gameserver.global.ascension;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.light.gameserver.global.additions.MessagerAddition;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.world.WorldMapInstance;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dean
 */
public class GlobalAscension {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalAscension.class);
    public int elysea = 0;
    public int asmodea = 0;
    long remainingTime;
    Set<Player> regPlayers = new HashSet();
    private static boolean isStarted = false;
    public boolean loadAscension()
    {
        if(isStarted)
            return false;
        isStarted = true;
        if(remainingTime <= 0)
            ascensionSch();
            remainingTime = 30L;
        return true;
    }
    public void setAscensionTimeToStart(int time)
    {
        remainingTime = time;
        MessagerAddition.announceAll("[Ascension]Time to Start set to"+ remainingTime +"minutes.", 0);
    }
    public int getPlayersSize()
    {
      return this.regPlayers.size();
    }
    public void ascensionInf(Player player) {
        
    }
    public void ascensionSch()
    {
            ThreadPoolManager.getInstance().schedule(new Runnable()
    {
      public void run()
      {
        if (remainingTime == 0L)
        {
          onTimerEnd();
        }
        else if (remainingTime == 30L)
        {
          spawnNpc();
          ascensionSch();
        }
        else
        {
          ascensionSch();
        }
        remainingTime -= 1L;
        sendRemainTime();
      }
    }
    , 60000L);
    }
    private void spawnNpc()
    {
        
    }
    public void sendRemainTime()
    {
        if(remainingTime == 2L || remainingTime == 4L || remainingTime == 6L || remainingTime == 8L || remainingTime == 10L || remainingTime == 14L || remainingTime == 18L || remainingTime == 20L || remainingTime == 25L || remainingTime == 30L)
        {
            MessagerAddition.announceAll("[Ascension] Is Already Started!Remaining Time to Start:"+remainingTime+"\nElysea:"+elysea+" Asmodea:"+asmodea+".", 0);
        }
    }
  public void onTimerEnd()
  {
    isStarted = false;
    while (!this.regPlayers.isEmpty())
      makeAscension();
  }
  public void clear()
  {
    Iterator iter = this.regPlayers.iterator();
    while (iter.hasNext())
    {
      Player player = (Player)iter.next();
      //unregisterPlayer(player);
    }
    elysea = 0;
    asmodea = 0;
    regPlayers.clear();
  }
 public synchronized void makeAscension()
  {
    
  }
 public void teleportIn(WorldMapInstance worldMapInstance)
 {
     
 }
    
}
