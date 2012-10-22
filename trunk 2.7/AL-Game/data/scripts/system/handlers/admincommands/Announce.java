package admincommands;

import java.util.Iterator;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Ben, Ritsu Smart Matching Enabled //announce anon This will work. as well as //announce a This will work.
 *         Both will match the "a" or "anon" to the "anonymous" flag.
 */
public class Announce extends ChatCommand
{

	public Announce() 
	{
		super("announce");
	}

	@Override
	public void execute(Player player, String... params) 
	{
		String message;

		if (params == null || params.length < 1)
		{
		 sendInfo(player);
		 return;
		}
	try
	{
		if (("admin").startsWith(params[0].toLowerCase()))
		{
			message = "Администрация: ";
		}
		else if (("n").startsWith(params[0].toLowerCase())) 
		{
			message = player.getName() + ": ";
		}
		else 
		{
			PacketSendUtility.sendMessage(player, "Syntax: //announce <n> <сообщение>");
			return;
		}

		// Add with space
		for (int i = 1; i < params.length - 1; i++)
			message += params[i] + " ";

		// Add the last without the end space
		message += params[params.length - 1];

		Iterator<Player> iter = World.getInstance().getPlayersIterator();

		while (iter.hasNext()) 
		{
			PacketSendUtility.sendBrightYellowMessageOnCenter(iter.next(), message);
		}
	}
	catch (Exception e)
	{
		sendInfo(player);
		return;
	}
	}

	public void sendInfo(Player player)
	{
	 PacketSendUtility.sendMessage(player, "Syntax: //announce <n> <сообщение>");
	}
	
	@Override
	public void onFail(Player player, String message)
	{
		sendInfo(player);
		return;
	}
}
