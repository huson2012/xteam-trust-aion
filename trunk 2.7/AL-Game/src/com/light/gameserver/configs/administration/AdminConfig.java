/*
 * This file is part of aion-lightning <aion-lightning.org>.
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.configs.administration;

import com.aionemu.commons.configuration.Property;

/**
 * @author ATracer
 */
public class AdminConfig {

	/**
	 * Admin properties
	 */
    
    @Property(key = "gameserver.count.time", defaultValue = "1000")
    public static int COUNT_TIME;
    @Property(key = "gameserver.aps.loc", defaultValue = "300000")
    public static int AP_LOC2;
    @Property(key = "gameserver.ap.loc", defaultValue = "50000")
    public static int AP_LOC;
     @Property(key = "gameserver.home", defaultValue = "false")
    public static boolean WINNER_TRUE_HOME;
    @Property(key = "gameserver.transform.full.all", defaultValue = "false")
    public static boolean LOC_TRANSFORM;
    @Property(key = "gameserver.wictim.nagradaraceme", defaultValue = "false")
     public static boolean WINNER_TRUE_LOC_RACE_ME;
     @Property(key = "gameserver.wictim.locgetrace", defaultValue = "false")
     public static boolean WINNER_TRUE_LOC_RACE;
    @Property(key = "gameserver.wictim.loc", defaultValue = "false")
    public static boolean WICTIM_TRUE_LOC;
    @Property(key = "gameserver.win.loc", defaultValue = "false")
	public static boolean WINNER_TRUE_LOC;
      @Property(key = "gameserver.sys.mail", defaultValue = "true")
	public static boolean SYS_MAIL;
        @Property(key = "gameserver.pvp,locid", defaultValue = "10000000")
	public static int PVP_LOCID;
       @Property(key = "gameserver.max.use.search", defaultValue = "150")
	public static int SEARCH_INFO;
        @Property(key = "gameserver.enchant.stone.announce.gm", defaultValue = "false")
	public static boolean ANN_GM;
        @Property(key = "gameserver.administration.locid", defaultValue = "300030000")
	public static int LOC_ID;
	@Property(key = "gameserver.administration.gmlevel", defaultValue = "3")
	public static int GM_LEVEL;
	@Property(key = "gameserver.administration.baseshield", defaultValue = "3")
	public static int COMMAND_BASESHIELD;
	@Property(key = "gameserver.administration.flight.freefly", defaultValue = "3")
	public static int GM_FLIGHT_FREE;
	@Property(key = "gameserver.administration.flight.unlimited", defaultValue = "3")
	public static int GM_FLIGHT_UNLIMITED;
	@Property(key = "gameserver.administration.doors.opening", defaultValue = "3")
	public static int DOORS_OPEN;
	@Property(key = "gameserver.administration.auto.res", defaultValue = "3")
	public static int ADMIN_AUTO_RES;
	@Property(key = "gameserver.administration.instancereq", defaultValue = "3")
	public static int INSTANCE_REQ;
	@Property(key = "gameserver.administration.view.player", defaultValue = "3")
	public static int ADMIN_VIEW_DETAILS;
	@Property(key = "gameserver.administration.guardwindow.timekick", defaultValue = "60 * 1000") //sec
	public static int TIME_KICK;

	/**
	 * Admin options
	 */
	@Property(key = "gameserver.administration.guard.window", defaultValue = "true")
	public static boolean GUARD_WINDOW;
	@Property(key = "gameserver.administration.guard.windowtrue", defaultValue = "true")
	public static boolean GUARD_WINDOW_TRUE; 
	@Property(key = "gameserver.administration.guard.windowname", defaultValue = "пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ!")
	public static String MESS_GUARD_XD;
	@Property(key = "gameserver.administration.invis.gm.connection", defaultValue = "false")
	public static boolean INVISIBLE_GM_CONNECTION;
	@Property(key = "gameserver.administration.enemity.gm.connection", defaultValue = "Normal")
	public static String ENEMITY_MODE_GM_CONNECTION;
	@Property(key = "gameserver.administration.invul.gm.connection", defaultValue = "false")
	public static boolean INVULNERABLE_GM_CONNECTION;
	@Property(key = "gameserver.administration.vision.gm.connection", defaultValue = "false")
	public static boolean VISION_GM_CONNECTION;
	@Property(key = "gameserver.administration.whisper.gm.connection", defaultValue = "false")
	public static boolean WHISPER_GM_CONNECTION;
	@Property(key = "gameserver.administration.quest.dialog.log", defaultValue = "false")
	public static boolean QUEST_DIALOG_LOG;
	@Property(key = "gameserver.administration.trade.item.restriction", defaultValue = "false")
	public static boolean ENABLE_TRADEITEM_RESTRICTION;

	/**
	 * Custom TAG based on access level
	 */
	 
	@Property(key = "gameserver.customtag.enable", defaultValue = "false")
	public static boolean CUSTOMTAG_ENABLE;
        @Property(key = "gameserver.customtag.access0", defaultValue = "%s")
	public static String CUSTOMTAG_ACCESS0;
	@Property(key = "gameserver.customtag.access1", defaultValue = "<GM> %s")
	public static String CUSTOMTAG_ACCESS1;
	@Property(key = "gameserver.customtag.access2", defaultValue = "<HEADGM> %s")
	public static String CUSTOMTAG_ACCESS2;
	@Property(key = "gameserver.customtag.access3", defaultValue = "<ADMIN> %s")
	public static String CUSTOMTAG_ACCESS3;
	@Property(key = "gameserver.customtag.access4", defaultValue = "<TAG_HERE> %s")
	public static String CUSTOMTAG_ACCESS4;
	@Property(key = "gameserver.customtag.access5", defaultValue = "<TAG_HERE> %s")
	public static String CUSTOMTAG_ACCESS5;
        @Property(key = "gameserver.customtag.access6", defaultValue = "<ACCESS_6> %s")
	public static String CUSTOMTAG_ACCESS6;
        @Property(key = "gameserver.customtag.access7", defaultValue = "<ACCESS_7> %s")
	public static String CUSTOMTAG_ACCESS7;
        @Property(key = "gameserver.customtag.access8", defaultValue = "<ACCESS_8> %s")
	public static String CUSTOMTAG_ACCESS8;
        @Property(key = "gameserver.customtag.access9", defaultValue = "<ACCESS_9> %s")
	public static String CUSTOMTAG_ACCESS9;
	@Property(key = "gameserver.customplayertag.enable", defaultValue = "false")
	public static boolean CUSTOMTAG_PLAYER_ENABLE;
	@Property(key = "gameserver.customplayerinfo.enable", defaultValue = "false")
	public static boolean CUSTOMTAG_PLAYER_INFO_ENABLE;
	@Property(key = "gameserver.customplayertagout.enable", defaultValue = "false")
	public static boolean CUSTOMTAG_PLAYER_OUT_ENABLE;
	
	@Property(key = "gameserver.admin.announce.levels", defaultValue = "*")
	public static String ANNOUNCE_LEVEL_LIST;
    
    
    
}
