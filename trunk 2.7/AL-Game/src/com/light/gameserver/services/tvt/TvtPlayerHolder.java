package com.light.gameserver.services.tvt;

import java.util.Collections;
import javolution.util.FastList;
import com.light.gameserver.configs.main.EventsConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.team2.group.PlayerGroup;
import com.light.gameserver.model.team2.group.PlayerGroupMember;
import com.light.gameserver.model.team2.group.PlayerGroupService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.idfactory.IDFactory;


public class TvtPlayerHolder {

    private FastList<Player> players = new FastList<Player>();
    private FastList<PlayerGroup> groups = new FastList<PlayerGroup>();
    private FastList<PlayerGroup> asmosGroupsReady = new FastList<PlayerGroup>();
    private FastList<PlayerGroup> elyosGroupsReady = new FastList<PlayerGroup>();
    private int asmo;
    private int elys;
    private int asmoReg;
    private int elysReg;
    private TvtRegistrator reg;
    private PlayerGroupMember member;

    TvtPlayerHolder(TvtRegistrator reg) {
        this.reg = reg;
    }

    public boolean addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);            
            switch (player.getRace()) {
                case ASMODIANS:
                    addAsmoReg();
                    break;
                case ELYOS:
                    addElysReg();
                    break;
            }
            return true;
        }
        return false;
    }    

    public void removePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            sentUnRegMess(player, "[TvT Event Test]Tvt: ����������� ��������!");
            if (players.size() == 0) {
                players.clear();
            }
        }
    }

    public void clearPlayer() {
        players.clear();
    }

    public FastList<Player> getPlayers() {
        return players;
    }

    public boolean getPlayer(Player player) {
        if (players.contains(player)) {
            return true;
        }
        return false;
    }

    public boolean addGroup(PlayerGroup group) {
        if (!groups.contains(group)) {
            groups.add(group);
            return true;
        }
        return false;
    }

    public void removeGroup(PlayerGroup group) {
        if (groups.contains(group)) {
            groups.remove(group);
            if (groups.size() == 0) {
                groups.clear();
            }
        }
    }

    public void clearGroups() {
        groups.clear();
    }

    public FastList<PlayerGroup> getGroups() {
        return groups;
    }

    public void clearAll() {
        players.clear();
        groups.clear();
        asmosGroupsReady.clear();
        elyosGroupsReady.clear();
    }

    public void makeGroup() {
        Collections.shuffle(players);
        FastList<Player> asmodians = new FastList<Player>();
        FastList<Player> elyos = new FastList<Player>();
        if (players == null) {
            TvtService.getInstance().forceStopTvt(reg.getTvtId());
            return;
        }
        for (Player player : players) {
            if (player.isInGroup2()) {
            	PlayerGroupService.removePlayer(player);
            }
            switch (player.getRace()) {
                case ASMODIANS:
                    asmodians.add(player);
                    addAsmo();
                    break;
                case ELYOS:
                    elyos.add(player);
                    addElys();
                    break;
            }
        }
        Collections.shuffle(asmodians);
        Collections.shuffle(elyos);
        if (getAsmo() > getElys()) {
            int total = getAsmo() - getElys();
            for (int i = 0; i < total; i++) {
                Player asmos = asmodians.get(i);
                removePlayer(asmos);
                asmodians.remove(asmos);
            }
        } else if (getElys() > getAsmo()) {
            int total = getElys() - getAsmo();
            for (int i = 0; i < total; i++) {
                Player elyosTmp = elyos.get(i);
                removePlayer(elyosTmp);
                elyos.remove(elyosTmp);
            }
        }
        if (!canStart()) {
            sendSorryMessage();
            TvtService.getInstance().forceStopTvt(reg.getTvtId());
            return;
        }
        clearAsmoAndElys();
        makeNewGroupAndAddMemberForAsmos(asmodians);
        makeNewGroupAndAddMemberForElyos(elyos);

        for (PlayerGroup asmoGroup : asmosGroupsReady) {
            groups.add(asmoGroup);
        }
        asmosGroupsReady.clear();

        for (PlayerGroup elyosGroup : elyosGroupsReady) {
            groups.add(elyosGroup);
        }
        elyosGroupsReady.clear();
        asmodians.clear();
        elyos.clear();
        for (Player player : players) {
            if (!player.isInGroup2() && !player.isGM()) {
                sentUnRegMess(player, "[TvT Event Test]Tvt: ����������� ��������!");
                removePlayer(player);
            }
        }
        clearAsmoAndElys();
        TvtService.getInstance().portPlayer(reg);
    }

    private void makeNewGroupAndAddMemberForAsmos(FastList<Player> asmodians) {
        if (asmodians.size() > 1) {
            if (asmodians.get(getAsmo() + 1) == null) {
                return;
            }
            asmosGroupsReady.add(makeOneGroup(asmodians.get(getAsmo()), asmodians.get(getAsmo() + 1)));
            PlayerGroup groupForAsmos = asmodians.get(0).getPlayerGroup2();

            for (Player player : asmodians) {
                if (player.getPlayerGroup2() == groupForAsmos) {
                    continue;
                }
                if (groupForAsmos.isFull()) {
                    makeNewGroupAndAddMemberForAsmos(asmodians);
                    return;
                }
                PlayerGroupService.addPlayerToGroup(groupForAsmos, player);
                addAsmo();
            }
        }
    }

    private void makeNewGroupAndAddMemberForElyos(FastList<Player> elyos) {
        if (elyos.size() > 1) {
            if (elyos.get(getElys() + 1) == null) {
                return;
            }
            elyosGroupsReady.add(makeOneGroup(elyos.get(getElys()), elyos.get(getElys() + 1)));
            PlayerGroup groupForElyos = elyos.get(0).getPlayerGroup2();

            for (Player player : elyos) {
                if (player.getPlayerGroup2() == groupForElyos) {
                    continue;
                }
                if (groupForElyos.isFull()) {
                    continue;
                }
                PlayerGroupService.addPlayerToGroup(groupForElyos, player);
                addElys();
            }
        }
    }

    private PlayerGroup makeOneGroup(Player inviter, Player invited) {
        inviter.setPlayerGroup2(new PlayerGroup(member)); //inviter.setPlayerGroup(new PlayerGroup(IDFactory.getInstance().nextId(), inviter)); original
        inviter.getPlayerGroup2().addMember(member);
        PlayerGroupService.addGroupMemberToCache(inviter);
        PlayerGroupService.addGroupMemberToCache(invited);
        return inviter.getPlayerGroup2();
    }

    private int getAsmo() {
        return asmo;
    }

    private int getElys() {
        return elys;
    }

    private void addAsmo() {
        this.asmo++;
    }

    private void addElys() {
        this.elys++;
    }

    public int getAsmoReg() {
        return asmoReg;
    }

    public int getElysReg() {
        return elysReg;
    }

    private void addAsmoReg() {
        this.asmoReg++;
    }

    private void addElysReg() {
        this.elysReg++;
    }

    private void clearAsmoAndElys() {
        this.asmo = 0;
        this.elys = 0;
    }
    public void clearAsmoAndElysReg() {
        this.asmoReg = 0;
        this.elysReg = 0;
    }

    public void info(Player player, boolean admin) {
        int racereg = 0;
        String otherRace = "";

        if (!reg.getIsStarted()) {
            PacketSendUtility.sendWhiteMessageOnCenter(player, "[TvT Event Test]Tvt: � ��������� TvT �� ����������!");
            return;
        }

        TvtPlayerHolder.sentUnRegMess(player, "----------------------------------------\n|       [TvT Event Test]Tvt Info:       |\n----------------------------------------\nTime remaining:"+ Math.round(reg.getRemainingTime() / 60) + 1 +"m.\n");
        String raceinfo = "";
        switch (player.getRace()) {
            case ASMODIANS:
                raceinfo = "Asmodians ";
                otherRace = "Elyos ";
                break;
            case ELYOS:
                raceinfo = "Elyos ";
                otherRace = "Asmodians ";
                break;
        }
        TvtPlayerHolder.sentUnRegMess(player, "Registered "+ raceinfo +":\n----------------------------------------\n");

        for (Player p : players) {
            if (p.getRace() == player.getRace()) {
                racereg++;
                sentUnRegMess(player, p.getName() + " (" + p.getCommonData().getLevel() + ") [" + p.getCommonData().getPlayerClass() + "]\n");
            }
        }
        if (admin) {
        	TvtPlayerHolder.sentUnRegMess(player, "Registered "+ otherRace +"----------------------------------------\n");

            for (Player p : players) {
                if (p.getRace() != player.getRace()) {
                	TvtPlayerHolder.sentUnRegMess(player, p.getName() + " (" + p.getCommonData().getLevel() + ") [" + p.getCommonData().getPlayerClass() + "]\n");
                }
            }
        }

        TvtPlayerHolder.sentUnRegMess(player, "----------------------------------------\nRegistered "+ raceinfo + ": "+racereg+"." + "\n| Registered "+ otherRace +":" + (players.size() - racereg) + " \n| Alltogether Registered: "+ players.size()+"\n| Minimum Players: "+ EventsConfig.TVT_MIN_PLAYERS +"\n----------------------------------------");
    }

    public boolean canStart() {
        if (players.size() >= EventsConfig.TVT_MIN_PLAYERS) {
            if (getAsmo() < 1 || getElys() < 1) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public void sendSorryMessage() {
        for (Player player : players) {
            PacketSendUtility.sendWhiteMessageOnCenter(player, "[TvT Event Test]Tvt: � ���������, ��� TvT �� ���������� �������, ������ ...");
        }
    }

    private static void sentUnRegMess(Player p, String message) {
        PacketSendUtility.sendWhiteMessageOnCenter(p, message);

    }
}