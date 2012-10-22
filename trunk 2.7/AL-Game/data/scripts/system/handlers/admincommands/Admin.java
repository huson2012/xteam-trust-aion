package admincommands;

import com.light.gameserver.cache.HTMLCache;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.HTMLService;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.services.AnnounService;

/**
 * @author Phantom, ATracer
 */
public class Admin extends ChatCommand {

	public Admin() {
		super("admin");
	}

	@Override
	public void execute(Player player, String... params)
	{
		HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("commands.xhtml"));
	}

	@Override
	public void onFail(Player player, String message) 
	{
		// TODO Auto-generated method stub
	}
}
