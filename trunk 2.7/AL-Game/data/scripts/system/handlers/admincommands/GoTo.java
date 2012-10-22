package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.HTMLService;
import com.light.gameserver.services.instance.InstanceService;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.world.WorldMap;
import com.light.gameserver.world.WorldMapInstance;
import com.light.gameserver.world.WorldMapType;
import com.light.gameserver.model.gameobjects.player.TeleportTask;
import com.light.gameserver.skillengine.SkillEngine;
import com.light.gameserver.skillengine.model.Skill;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.PlayerClass;
import com.light.gameserver.services.ClassChangeService;

public class GoTo extends ChatCommand
{

	public GoTo() {
		super("goto");
	}

	@Override
	public void execute(Player player, String... params) 
	{
		if (params == null || params.length < 1) 
		{
			PacketSendUtility.sendMessage(player, "syntax //goto <location>");
			return;
		}
		
		StringBuilder sbDestination = new StringBuilder();
		for(String p : params)
			sbDestination.append(p + " ");
		
		String destination = sbDestination.toString().trim();
				
		if (player.getAccessLevel() > 0) //Админ
		{
		    if (destination.equalsIgnoreCase("pvp"))
		    {
		          if(player.getCommonData().getRace() == Race.ELYOS)
		          {
			         goTo(player, 600010000, 478, 365, 327);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP зоны Элийцев.");
		          }
		          else if(player.getCommonData().getRace() == Race.ASMODIANS)
		          {
			         goTo(player, 600010000, 549, 1137, 332);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP зоны Асмодиан.");
			      }
		    }
            else if (destination.equalsIgnoreCase("pvp1"))
		    {
		          if(player.getCommonData().getRace() == Race.ELYOS)
		          {
			         goTo(player, 600010000, 754, 494, 323);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP I зоны Элийцев.");
		          }
		          else if(player.getCommonData().getRace() == Race.ASMODIANS)
		          {
			         goTo(player, 600010000, 762, 1038, 324);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP I зоны Асмодиан.");
			      }
		    }
			else if (destination.equalsIgnoreCase("pvp2"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 600010000, 1304, 665, 350);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP II зоны Элийцев.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 600010000, 1304, 870, 350);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP II зоны Асмодиан.");
                  }
			}
		    else if (destination.equalsIgnoreCase("home"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 110010000, 1791, 2174, 527);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в столицу Элийцев.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 120010000, 1275, 1278, 208);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в столицу Асмодиан.");
                  }
			}
			else if (destination.equalsIgnoreCase("test1"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 300020000, 104, 66, 25);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone I.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 300020000, 104, 66, 25);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone I.");
                  }
			}
			else if (destination.equalsIgnoreCase("test2"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 900020000, 144, 136, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone II.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 900020000, 144, 136, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone II.");
                  }
			}
			else if (destination.equalsIgnoreCase("test3"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 900030000, 228, 171, 49);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone III.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 900030000, 228, 171, 49);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone III.");
                  }
			}
			else if (destination.equalsIgnoreCase("test4"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 900100000, 196, 187, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone VI.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 900100000, 196, 187, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone VI.");
                  }
			}
			else if (destination.equalsIgnoreCase("shop"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     goTo(player, 210050000, 1327, 364, 589);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Магазин.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     goTo(player, 220070000, 1912, 2883, 561);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Магазин.");
                  }
			}	
			else if (destination.equalsIgnoreCase("fan") && player.isGM())
			{
			      if(player.getCommonData().getPlayerClass() == PlayerClass.ASSASSIN)
                  {
                     goTo(player, 300090000, 196, 290, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Убийц.");
                  }
                  else if(player.getCommonData().getPlayerClass() == PlayerClass.CLERIC)
                  {
                     goTo(player, 300090000, 192, 219, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Целителей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.GLADIATOR)
                  {
                     goTo(player, 300090000, 191, 149, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Гладиаторов.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.TEMPLAR)
                  {
                     goTo(player, 300090000, 263, 118, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Стражей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.RANGER)
                  {
                     goTo(player, 300090000, 332, 147, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Лучников.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.SORCERER)
                  {
                     goTo(player, 300090000, 361, 217, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Волшебников.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.SPIRIT_MASTER)
                  {
                     goTo(player, 300090000, 333, 285, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Заклинателей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.CHANTER)
                  {
                     goTo(player, 300090000, 263, 342, 105);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Чародеев.");
                  }
			}
		/**
		 * Elysea
		 */
		// Sanctum
		else if (destination.equalsIgnoreCase("Sanctum"))
			goTo(player, WorldMapType.SANCTUM.getId(), 1322, 1511, 568);
		// Kaisinel
		else if (destination.equalsIgnoreCase("Kaisinel"))
			goTo(player, WorldMapType.KAISINEL.getId(), 2155, 1567, 1205);
		// Poeta
		else if (destination.equalsIgnoreCase("Poeta"))
			goTo(player, WorldMapType.POETA.getId(), 806, 1242, 119);
		else if (destination.equalsIgnoreCase("Melponeh"))
			goTo(player, WorldMapType.POETA.getId(), 426, 1740, 119);
		// Verteron
		else if (destination.equalsIgnoreCase("Verteron"))
			goTo(player, WorldMapType.VERTERON.getId(), 1643, 1500, 119);
		else if (destination.equalsIgnoreCase("Cantas") || destination.equalsIgnoreCase("Cantas Coast"))
			goTo(player, WorldMapType.VERTERON.getId(), 2384, 788, 102);
		else if (destination.equalsIgnoreCase("Ardus") || destination.equalsIgnoreCase("Ardus Shrine"))
			goTo(player, WorldMapType.VERTERON.getId(), 2333, 1817, 193);
		else if (destination.equalsIgnoreCase("Pilgrims") || destination.equalsIgnoreCase("Pilgrims Respite"))
			goTo(player, WorldMapType.VERTERON.getId(), 2063, 2412, 274);
		else if (destination.equalsIgnoreCase("Tolbas") || destination.equalsIgnoreCase("Tolbas Village"))
			goTo(player, WorldMapType.VERTERON.getId(), 1291, 2206, 142);
		// Eltnen
		else if (destination.equalsIgnoreCase("Eltnen"))
			goTo(player, WorldMapType.ELTNEN.getId(), 343, 2724, 264);
		else if (destination.equalsIgnoreCase("Golden") || destination.equalsIgnoreCase("Golden Bough Garrison"))
			goTo(player, WorldMapType.ELTNEN.getId(), 688, 431, 332);
		else if (destination.equalsIgnoreCase("Eltnen Observatory"))
			goTo(player, WorldMapType.ELTNEN.getId(), 1779, 883, 422);
		else if (destination.equalsIgnoreCase("Novan"))
			goTo(player, WorldMapType.ELTNEN.getId(), 947, 2215, 252);
		else if (destination.equalsIgnoreCase("Agairon"))
			goTo(player, WorldMapType.ELTNEN.getId(), 1921, 2045, 361);
		else if (destination.equalsIgnoreCase("Kuriullu"))
			goTo(player, WorldMapType.ELTNEN.getId(), 2411, 2724, 361);
		// Theobomos
		else if (destination.equalsIgnoreCase("Theobomos"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 1398, 1557, 31);
		else if (destination.equalsIgnoreCase("Jamanok") || destination.equalsIgnoreCase("Jamanok Inn"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 458, 1257, 127);
		else if (destination.equalsIgnoreCase("Meniherk"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 1396, 1560, 31);
		else if (destination.equalsIgnoreCase("obsvillage"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 2234, 2284, 50);
		else if (destination.equalsIgnoreCase("Josnack"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 901, 2774, 62);
		else if (destination.equalsIgnoreCase("Anangke"))
			goTo(player, WorldMapType.THEOMOBOS.getId(), 2681, 847, 138);
		// Heiron
		else if (destination.equalsIgnoreCase("Heiron"))
			goTo(player, WorldMapType.HEIRON.getId(), 2540, 343, 411);
		else if (destination.equalsIgnoreCase("Heiron Observatory"))
			goTo(player, WorldMapType.HEIRON.getId(), 1423, 1334, 175);
		else if (destination.equalsIgnoreCase("Senemonea"))
			goTo(player, WorldMapType.HEIRON.getId(), 971, 686, 135);
		else if (destination.equalsIgnoreCase("Jeiaparan"))
			goTo(player, WorldMapType.HEIRON.getId(), 1635, 2693, 115);
		else if (destination.equalsIgnoreCase("Changarnerk"))
			goTo(player, WorldMapType.HEIRON.getId(), 916, 2256, 157);
		else if (destination.equalsIgnoreCase("Kishar"))
			goTo(player, WorldMapType.HEIRON.getId(), 1999, 1391, 118);
		else if (destination.equalsIgnoreCase("Arbolu"))
			goTo(player, WorldMapType.HEIRON.getId(), 170, 1662, 120);
		
		/**
		 * Asmodae
		 */
		// Pandaemonium
		else if  (destination.equalsIgnoreCase("Pandaemonium"))
			goTo(player, WorldMapType.PANDAEMONIUM.getId(), 1679, 1400, 195);
		// Marchutran
		else if (destination.equalsIgnoreCase("Marchutan"))
			goTo(player, WorldMapType.MARCHUTAN.getId(), 1557, 1429, 266);
		// Ishalgen
		else if (destination.equalsIgnoreCase("Ishalgen"))
			goTo(player, WorldMapType.ISHALGEN.getId(), 529, 2449, 281);
		else if (destination.equalsIgnoreCase("Anturoon"))
			goTo(player, WorldMapType.ISHALGEN.getId(), 940, 1707, 259);
		// Altgard
		else if (destination.equalsIgnoreCase("Altgard"))
			goTo(player, WorldMapType.ALTGARD.getId(), 1748, 1807, 254);
		else if (destination.equalsIgnoreCase("Basfelt"))
			goTo(player, WorldMapType.ALTGARD.getId(), 1903, 696, 260);
		else if (destination.equalsIgnoreCase("Trader"))
			goTo(player, WorldMapType.ALTGARD.getId(), 2680, 1024, 311);
		else if (destination.equalsIgnoreCase("Impetusiom"))
			goTo(player, WorldMapType.ALTGARD.getId(), 2643, 1658, 324);
		else if (destination.equalsIgnoreCase("Altgard Observatory"))
			goTo(player, WorldMapType.ALTGARD.getId(), 1468, 2560, 299);
		// Morheim
		else if (destination.equalsIgnoreCase("Morheim"))
			goTo(player, WorldMapType.MORHEIM.getId(), 308, 2274, 449);
		else if (destination.equalsIgnoreCase("Desert"))
			goTo(player, WorldMapType.MORHEIM.getId(), 634, 900, 360);
		else if (destination.equalsIgnoreCase("Slag"))
			goTo(player, WorldMapType.MORHEIM.getId(), 1772, 1662, 197);
		else if (destination.equalsIgnoreCase("Kellan"))
			goTo(player, WorldMapType.MORHEIM.getId(), 1070, 2486, 239);
		else if (destination.equalsIgnoreCase("Alsig"))
			goTo(player, WorldMapType.MORHEIM.getId(), 2387, 1742, 102);
		else if (destination.equalsIgnoreCase("Morheim Observatory"))
			goTo(player, WorldMapType.MORHEIM.getId(), 2794, 1122, 171);
		else if (destination.equalsIgnoreCase("Halabana"))
			goTo(player, WorldMapType.MORHEIM.getId(), 2346, 2219, 127);
		// Brusthonin
		else if (destination.equalsIgnoreCase("Brusthonin"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 2917, 2421, 15);
		else if (destination.equalsIgnoreCase("Baltasar"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 1413, 2013, 51);
		else if (destination.equalsIgnoreCase("Bollu"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 840, 2016, 307);
		else if (destination.equalsIgnoreCase("Edge"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 1523, 374, 231);
		else if (destination.equalsIgnoreCase("Bubu"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 526, 848, 76);
		else if (destination.equalsIgnoreCase("Settlers"))
			goTo(player, WorldMapType.BRUSTHONIN.getId(), 2917, 2417, 15);
		// Beluslan
		else if (destination.equalsIgnoreCase("Beluslan"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 398, 400, 222);
		else if (destination.equalsIgnoreCase("Besfer"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 533, 1866, 262);
		else if (destination.equalsIgnoreCase("Kidorun"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 1243, 819, 260);
		else if (destination.equalsIgnoreCase("Red Mane"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 2358, 1241, 470);
		else if (destination.equalsIgnoreCase("Kistenian"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 1942, 513, 412);
		else if (destination.equalsIgnoreCase("Hoarfrost"))
			goTo(player, WorldMapType.BELUSLAN.getId(), 2431, 2063, 579);
		
		/**
		 * Balaurea
		 */
		// Inggison
		else if (destination.equalsIgnoreCase("Inggison"))
			goTo(player, WorldMapType.INGGISON.getId(), 1335, 276, 590);
		else if (destination.equalsIgnoreCase("Ufob"))
			goTo(player, WorldMapType.INGGISON.getId(), 382, 951, 460);
		else if (destination.equalsIgnoreCase("Soteria"))
			goTo(player, WorldMapType.INGGISON.getId(), 2713, 1477, 382);
		else if (destination.equalsIgnoreCase("Hanarkand"))
			goTo(player, WorldMapType.INGGISON.getId(), 1892, 1748, 327);
		// Gelkmaros
		else if (destination.equalsIgnoreCase("Gelkmaros"))
			goTo(player, WorldMapType.GELKMAROS.getId(), 1763, 2911, 554);
		else if (destination.equalsIgnoreCase("Subterranea"))
			goTo(player, WorldMapType.GELKMAROS.getId(), 2503, 2147, 464);
		else if (destination.equalsIgnoreCase("Rhonnam"))
			goTo(player, WorldMapType.GELKMAROS.getId(), 845, 1737, 354);
		// Silentera
		else if (destination.equalsIgnoreCase("Silentera"))
			goTo(player, 600010000, 583, 767, 300);
		
		/**
		 * Abyss
		 */
		else if (destination.equalsIgnoreCase("Reshanta"))
			goTo(player, WorldMapType.RESHANTA.getId(), 951, 936, 1667);
		else if (destination.equalsIgnoreCase("Abyss 1"))
			goTo(player, WorldMapType.RESHANTA.getId(), 2867, 1034, 1528);
		else if (destination.equalsIgnoreCase("Abyss 2"))
			goTo(player, WorldMapType.RESHANTA.getId(), 1078, 2839, 1636);
		else if (destination.equalsIgnoreCase("Abyss 3"))
			goTo(player, WorldMapType.RESHANTA.getId(), 1596, 2952, 2943);
		else if (destination.equalsIgnoreCase("Abyss 4"))
			goTo(player, WorldMapType.RESHANTA.getId(), 2054, 660, 2843);
		else if (destination.equalsIgnoreCase("Eye of Reshanta") ||destination.equalsIgnoreCase("Eye"))
			goTo(player, WorldMapType.RESHANTA.getId(), 1979, 2114, 2291);
		else if (destination.equalsIgnoreCase("Divine Fortress") || destination.equalsIgnoreCase("Divine"))
			goTo(player, WorldMapType.RESHANTA.getId(), 2130, 1925, 2322);

		/**
		 * Instances
		 */
		else if (destination.equalsIgnoreCase("Haramel"))
			goTo(player, 300200000, 176, 21, 144);
		else if (destination.equalsIgnoreCase("Nochsana") || destination.equalsIgnoreCase("NTC"))
			goTo(player, 300030000, 513, 668, 331);
		else if (destination.equalsIgnoreCase("event") || destination.equalsIgnoreCase("Sky Temple of Arcanis"))
			goTo(player, 320050000, 177, 229, 536);
		else if (destination.equalsIgnoreCase("Fire Temple") ||destination.equalsIgnoreCase("FT"))
			goTo(player, 320100000, 144, 312, 123);
		else if (destination.equalsIgnoreCase("Kromede") || destination.equalsIgnoreCase("Kromede Trial"))
			goTo(player, 300230000, 248, 244, 189);
		// Steel Rake
		else if (destination.equalsIgnoreCase("Steel Rake") || destination.equalsIgnoreCase("SR"))
			goTo(player, 300100000, 237, 506, 948);
		else if (destination.equalsIgnoreCase("Steel Rake Lower") || destination.equalsIgnoreCase("SR Low"))
			goTo(player, 300100000, 283, 453, 903);
		else if (destination.equalsIgnoreCase("Steel Rake Middle") || destination.equalsIgnoreCase("SR Mid"))
			goTo(player, 300100000, 283, 453, 953);
		else if (destination.equalsIgnoreCase("Indratu") || destination.equalsIgnoreCase("Indratu Fortress"))
			goTo(player, 310090000, 562, 335, 1015);
		else if (destination.equalsIgnoreCase("Azoturan") || destination.equalsIgnoreCase("Azoturan Fortress"))
			goTo(player, 310100000, 458, 428, 1039);
		else if (destination.equalsIgnoreCase("Bio Lab") || destination.equalsIgnoreCase("Aetherogenetics Lab"))
			goTo(player, 310050000, 225, 244, 133);
		else if (destination.equalsIgnoreCase("Adma") || destination.equalsIgnoreCase("Adma Stronghold"))
			goTo(player, 320130000, 450, 200, 168);
		else if (destination.equalsIgnoreCase("Alquimia") || destination.equalsIgnoreCase("Alquimia Research Center"))
			goTo(player, 320110000, 603, 527, 200);
		else if (destination.equalsIgnoreCase("Draupnir") || destination.equalsIgnoreCase("Draupnir Cave"))
			goTo(player, 320080000, 491, 373, 622);
		else if (destination.equalsIgnoreCase("Theobomos Lab") || destination.equalsIgnoreCase("Theobomos Research Lab"))
			goTo(player, 310110000, 477, 201, 170);
		else if (destination.equalsIgnoreCase("Dark Poeta") || destination.equalsIgnoreCase("DP"))
			goTo(player, 300040000, 1214, 412, 140);
		// Lower Abyss
		else if (destination.equalsIgnoreCase("Sulfur") || destination.equalsIgnoreCase("Sulfur Tree Nest"))
			goTo(player, 300060000, 462, 345, 163);
		else if (destination.equalsIgnoreCase("Right Wing") || destination.equalsIgnoreCase("Right Wing Chamber"))
			goTo(player, 300090000, 263, 386, 103);
		else if (destination.equalsIgnoreCase("Left Wing") || destination.equalsIgnoreCase("Left Wing Chamber"))
			goTo(player, 300080000, 672, 606, 321);
		// Upper Abyss
		else if (destination.equalsIgnoreCase("Asteria Chamber"))
			goTo(player, 300050000, 469, 568, 202);
		else if (destination.equalsIgnoreCase("Miren Chamber"))
			goTo(player, 300130000, 527, 120, 176);
		else if (destination.equalsIgnoreCase("Kysis Chamber"))
			goTo(player, 300120000, 528, 121, 176);
		else if (destination.equalsIgnoreCase("Krotan Chamber"))
			goTo(player, 300140000, 528, 109, 176);
		else if (destination.equalsIgnoreCase("Roah Chamber"))
			goTo(player, 300070000, 504, 396, 94);
		// Divine
		else if (destination.equalsIgnoreCase("Abyssal Splinter") || destination.equalsIgnoreCase("Core"))
			goTo(player, 300220000, 704, 153, 453);
		else if (destination.equalsIgnoreCase("Dredgion"))
			goTo(player, 300110000, 414, 193, 431);
		else if (destination.equalsIgnoreCase("Chantra") || destination.equalsIgnoreCase("Chantra Dredgion"))
			goTo(player, 300210000, 414, 193, 431);
		else if (destination.equalsIgnoreCase("Taloc") || destination.equalsIgnoreCase("Taloc's Hollow"))
			goTo(player, 300190000, 200, 214, 1099);
		// Udas
		else if (destination.equalsIgnoreCase("Udas") || destination.equalsIgnoreCase("Udas Temple"))
			goTo(player, 300150000, 637, 657, 134);
		else if (destination.equalsIgnoreCase("Udas Lower") || destination.equalsIgnoreCase("Udas Lower Temple"))
			goTo(player, 300160000, 1146, 277, 116);
		else if (destination.equalsIgnoreCase("Beshmundir") || destination.equalsIgnoreCase("BT") || destination.equalsIgnoreCase("Beshmundir Temple"))
			goTo(player, 300170000, 1477, 237, 243);
		// Padmaraska Cave
		else if (destination.equalsIgnoreCase("Padmaraska Cave"))
			goTo(player, 320150000, 385, 506, 66);
		
		/**
		 * Quest Instance Maps
		 */
		// TODO : Changer id maps
		else if (destination.equalsIgnoreCase("Karamatis 0"))
			goTo(player, 310010000, 221, 250, 206);
		else if (destination.equalsIgnoreCase("Karamatis 1"))
			goTo(player, 310020000, 312, 274, 206);
		else if (destination.equalsIgnoreCase("Karamatis 2"))
			goTo(player, 310120000, 221, 250, 206);
		else if (destination.equalsIgnoreCase("Aerdina"))
			goTo(player, 310030000, 275, 168, 205);
		else if (destination.equalsIgnoreCase("Gerania"))
			goTo(player, 310040000, 275, 168, 205);
		// Stigma quest
		else if (destination.equalsIgnoreCase("Sliver") || destination.equalsIgnoreCase("Sliver of Darkness"))
			goTo(player, 310070000, 247, 249, 1392);
		else if (destination.equalsIgnoreCase("Space") || destination.equalsIgnoreCase("Space of Destiny"))
			goTo(player, 320070000, 246, 246, 125);
		else if (destination.equalsIgnoreCase("Ataxiar 1"))
			goTo(player, 320010000, 221, 250, 206);
		else if (destination.equalsIgnoreCase("Ataxiar 2"))
			goTo(player, 320020000, 221, 250, 206);
		else if (destination.equalsIgnoreCase("Bregirun"))
			goTo(player, 320030000, 275, 168, 205);
		else if (destination.equalsIgnoreCase("Nidalber"))
			goTo(player, 320040000, 275, 168, 205);
		
		/**
		 * Arenas
		 */
		else if (destination.equalsIgnoreCase("Sanctum Arena"))
			goTo(player, 310080000, 275, 242, 159);
		else if (destination.equalsIgnoreCase("Triniel Arena"))
			goTo(player, 320090000, 275, 239, 159);
		// Empyrean Crucible
		else if (destination.equalsIgnoreCase("Crucible 1-0"))
			goTo(player, 300300000, 380, 350, 95);
		else if (destination.equalsIgnoreCase("Crucible 1-1"))
			goTo(player, 300300000, 346, 350, 96);
		else if (destination.equalsIgnoreCase("Crucible 5-0"))
			goTo(player, 300300000, 1265, 821, 359);
		else if (destination.equalsIgnoreCase("Crucible 5-1"))
			goTo(player, 300300000, 1256, 797, 359);
		else if (destination.equalsIgnoreCase("Crucible 6-0"))
			goTo(player, 300300000, 1596, 150, 129);
		else if (destination.equalsIgnoreCase("Crucible 6-1"))
			goTo(player, 300300000, 1628, 155, 126);
		else if (destination.equalsIgnoreCase("Crucible 7-0"))
			goTo(player, 300300000, 1813, 797, 470);
		else if (destination.equalsIgnoreCase("Crucible 7-1"))
			goTo(player, 300300000, 1785, 797, 470);
		else if (destination.equalsIgnoreCase("Crucible 8-0"))
			goTo(player, 300300000, 1776, 1728, 304);
		else if (destination.equalsIgnoreCase("Crucible 8-1"))
			goTo(player, 300300000, 1776, 1760, 304);
		else if (destination.equalsIgnoreCase("Crucible 9-0"))
			goTo(player, 300300000, 1357, 1748, 320);
		else if (destination.equalsIgnoreCase("Crucible 9-1"))
			goTo(player, 300300000, 1334, 1741, 316);
		else if (destination.equalsIgnoreCase("Crucible 10-0"))
			goTo(player, 300300000, 1750, 1255, 395);
		else if (destination.equalsIgnoreCase("Crucible 10-1"))
			goTo(player, 300300000, 1761, 1280, 395);
		// Arena Of Chaos
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 1"))
			goTo(player, 300350000, 1332, 1078, 340);
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 2"))
			goTo(player, 300350000, 599, 1854, 227);
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 3"))
			goTo(player, 300350000, 663, 265, 512);
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 4"))
			goTo(player, 300350000, 1840, 1730, 302);
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 5"))
			goTo(player, 300350000, 1932, 1228, 270);
		else if (destination.equalsIgnoreCase("Arena Of Chaos - 6"))
			goTo(player, 300350000, 1949, 946, 224);

		/**
		 * Miscellaneous
		 */
		// Prison
		else if (destination.equalsIgnoreCase("Prison LF") || destination.equalsIgnoreCase("Prison Elyos"))
			goTo(player, 510010000, 256, 256, 49);
		else if (destination.equalsIgnoreCase("Prison DF") || destination.equalsIgnoreCase("Prison Asmos"))
			goTo(player, 520010000, 256, 256, 49);
		// Test
		else if (destination.equalsIgnoreCase("test1"))
			goTo(player, 300020000, 104, 66, 25);
		else if (destination.equalsIgnoreCase("test2"))
			goTo(player, 900020000, 144, 136, 20);
		else if (destination.equalsIgnoreCase("test3"))
			goTo(player, 900030000, 228, 171, 49);
		else if (destination.equalsIgnoreCase("test4"))
			goTo(player, 900100000, 196, 187, 20);
		// Unknown
		else if (destination.equalsIgnoreCase("IDAbPro"))
			goTo(player, 300010000, 270, 200, 206);
		// GamezNetwork GM zone
		else if (destination.equalsIgnoreCase("gm"))
			goTo(player, 120020000, 1442, 1133, 302);
		
		/**
		 * 2.5 Maps
		 */
		else if (destination.equalsIgnoreCase("Kaisinel Academy"))
			goTo(player, 110070000, 459, 251, 128);
		else if (destination.equalsIgnoreCase("Marchutan Priory"))
			goTo(player, 120080000, 577, 250, 94);
		else if (destination.equalsIgnoreCase("Esoterrace"))
			goTo(player, 300250000, 333, 437, 326);
		else
		{
		    PacketSendUtility.sendYellowMessageOnCenter(player, "Такой локации не существует.");
			return;
		}
		}
		else if (player.getAccessLevel() == 0) //Игрок
		{
		    if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) //Если в тюрьме
			{
			    PacketSendUtility.sendYellowMessageOnCenter(player, "Нельзя телепортироваться из тюрьмы.");
				return;
			}
			if (player.getController().isInCombat())
            {
                PacketSendUtility.sendMessage(player, "Вы не можете использовать команду находясь в бою!");
                return;
            }
			else if (destination.equalsIgnoreCase("pvp"))
		    {
		          if(player.getCommonData().getRace() == Race.ELYOS)
		          {
			         setTeleportTask(player, 600010000, 478, 365, 327);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP зоны Элийцев.");
		          }
		          else if(player.getCommonData().getRace() == Race.ASMODIANS)
		          {
			         setTeleportTask(player, 600010000, 549, 1137, 332);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP зоны Асмодиан.");
			      }
		    }
		    else if (destination.equalsIgnoreCase("pvp1"))
		    {
		          if(player.getCommonData().getRace() == Race.ELYOS)
		          {
			         setTeleportTask(player, 600010000, 754, 494, 323);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP I зоны Элийцев.");
		          }
		          else if(player.getCommonData().getRace() == Race.ASMODIANS)
		          {
			         setTeleportTask(player, 600010000, 762, 1038, 324);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP I зоны Асмодиан.");
			      }
		    }
			else if (destination.equalsIgnoreCase("pvp2"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 600010000, 1304, 665, 350);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP II зоны Элийцев.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 600010000, 1304, 870, 350);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в форт PvP II зоны Асмодиан.");
                  }
			}
		    else if (destination.equalsIgnoreCase("home"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 110010000, 1791, 2174, 527);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в столицу Элийцев.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 120010000, 1275, 1278, 208);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в столицу Асмодиан.");
                  }
			}
			else if (destination.equalsIgnoreCase("test1"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 300020000, 104, 66, 25);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone I.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 300020000, 104, 66, 25);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone I.");
                  }
			}
			else if (destination.equalsIgnoreCase("test2"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 900020000, 144, 136, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone II.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 900020000, 144, 136, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone II.");
                  }
			}
			else if (destination.equalsIgnoreCase("test3"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 900030000, 228, 171, 49);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone III.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 900030000, 228, 171, 49);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone III.");
                  }
			}
			else if (destination.equalsIgnoreCase("test4"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 900100000, 196, 187, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone VI.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 900100000, 196, 187, 20);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Test zone VI.");
                  }
			}
			else if (destination.equalsIgnoreCase("shop"))
			{
			      if(player.getCommonData().getRace() == Race.ELYOS)
                  {
                     setTeleportTask(player, 210050000, 1327, 364, 589);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Магазин.");
                  }
                  else if(player.getCommonData().getRace() == Race.ASMODIANS)
                  {
                     setTeleportTask(player, 220070000, 1912, 2883, 561);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в Магазин.");
                  }
			}
			else if (destination.equalsIgnoreCase("fan"))
			{
			      if(player.getCommonData().getPlayerClass() == PlayerClass.ASSASSIN)
                  {
                     setTeleportTask(player, 300090000, 196, 290, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Убийц.");
                  }
                  else if(player.getCommonData().getPlayerClass() == PlayerClass.CLERIC)
                  {
                     setTeleportTask(player, 300090000, 192, 219, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Целителей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.GLADIATOR)
                  {
                     setTeleportTask(player, 300090000, 191, 149, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Гладиаторов.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.TEMPLAR)
                  {
                     setTeleportTask(player, 300090000, 263, 118, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Стражей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.RANGER)
                  {
                     setTeleportTask(player, 300090000, 332, 147, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Лучников.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.SORCERER)
                  {
                     setTeleportTask(player, 300090000, 361, 217, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Волшебников.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.SPIRIT_MASTER)
                  {
                     setTeleportTask(player, 300090000, 333, 285, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Заклинателей.");
                  }
				  else if(player.getCommonData().getPlayerClass() == PlayerClass.CHANTER)
                  {
                     setTeleportTask(player, 300090000, 263, 342, 105);
                     PacketSendUtility.sendYellowMessageOnCenter(player, "Вы переноситесь в FAN зону, точка Чародеев.");
                  }
			}
			else
			{
			 PacketSendUtility.sendYellowMessageOnCenter(player, "Такой локации не существует, или у вас не достаточно прав для телепорта в эту локацию.");
			 return;
			}
			Skill skill = SkillEngine.getInstance().getSkill(player, 1801, 1, player);
            skill.useSkill();
		}
	}
	
	void setTeleportTask(Player player, int worldId, int x, int y, int z)
        {
            player.setTeleportTask(new TeleportTask(worldId, 1, x, y, z, 1));
        }
        
        void setTeleportTask(Player player, int instanceId, int worldId, int x, int y, int z)
        {
            player.setTeleportTask(new TeleportTask(worldId, instanceId, x, y, z, 1));
        }
	
	public static void goTo(final Player player, int worldId, float x, float y, float z) {
		WorldMap destinationMap = World.getInstance().getWorldMap(worldId);
		if (destinationMap.isInstanceType())
			TeleportService.teleportTo(player, worldId, getInstanceId(worldId, player), x, y, z, 3000 ,true);
		else
			TeleportService.teleportTo(player, worldId, x, y, z, 3000, true);
	}
	
	private static int getInstanceId(int worldId, Player player) {
		if (player.getWorldId() == worldId)	{
			WorldMapInstance registeredInstance = InstanceService.getRegisteredInstance(worldId, player.getObjectId());
			if (registeredInstance != null)
				return registeredInstance.getInstanceId();
		}
		WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(worldId);
		InstanceService.registerPlayerWithInstance(newInstance, player);
		return newInstance.getInstanceId();
	}
	
	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Syntax : //goto <location>");
	}
}