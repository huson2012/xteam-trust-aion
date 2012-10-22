package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.WeatherService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.WorldMapType;

/**
 * Admin command allowing to change weathers of the world.
 * 
 * @author Kwazar
 */
public class Pogoda extends ChatCommand {

	public Pogoda() {
		super("pogoda");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params.length == 0 || params.length > 2) {
			onFail(admin, null);
			return;
		}

		String regionName = null;
		int weatherType = -1;

		regionName = new String(params[0]);

		if (params.length == 2) {
			try {
				weatherType = Integer.parseInt(params[1]);
			}
			catch (NumberFormatException e) {
				PacketSendUtility.sendMessage(admin, "Установите значение погоды в локации от 0 до 8.");
				return;
			}
		}

		if (regionName.equals("reset")) {
			WeatherService.getInstance().resetWeather();
			return;
		}

		// Retrieving regionId by name
		WorldMapType region = null;
		for (WorldMapType worldMapType : WorldMapType.values()) {
			if (worldMapType.name().toLowerCase().equals(regionName.toLowerCase())) {
				region = worldMapType;
			}
		}

		if (region != null) {
			if (weatherType > -1 && weatherType < 9) {
				WeatherService.getInstance().changeRegionWeather(region.getId(), new Integer(weatherType));
			}
			else {
				PacketSendUtility.sendMessage(admin, "Вы должны указать значение от 0 до 8");
				return;
			}
		}
		else {
			PacketSendUtility.sendMessage(admin, "область " + regionName + " не найдена");
			return;
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player,
			"syntax //pogoda <Имя локации(poeta, ishalgen, и т.д.)> <значение(от 0 до 8)> а также //pogoda reset (Сбросить погоду)");
	}

}
