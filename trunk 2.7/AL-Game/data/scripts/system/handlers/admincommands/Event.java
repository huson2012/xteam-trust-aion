package admincommands;


import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import java.util.Iterator;

/**
 * @author Ben, Ritsu Smart Matching Enabled //announce anon This will work. as well as //announce a This will work.
 *         Both will match the "a" or "anon" to the "anonymous" flag.
 */
public class Event extends ChatCommand
{
    

	public Event() 
	{
		super("event");
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
		if (("n").startsWith(params[0].toLowerCase())) 
		{
			message = "Event Loc: ";
		}
		else 
		{
			PacketSendUtility.sendMessage(player, "Syntax: //event <n> <сообщение>");
			return;
		}
		// Add with space
		for (int i = 1; i < params.length - 1; i++)
			message += params[i] + " ";
			
		// Add the last without the end space
		message += params[params.length - 1];

		Iterator<Player> iter = World.getInstance().getPlayersIterator();

                Player target = null;
                
		while (iter.hasNext()) 
		{
                    target = iter.next();
                    if(target.getWorldId() == player.getWorldId())
                    {
			             PacketSendUtility.sendBrightYellowMessageOnCenter(target, message);
                    }
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
	 PacketSendUtility.sendMessage(player, "Syntax: //event <n> <сообщение>");
	}
	
	@Override
	public void onFail(Player player, String message)
	{
		sendInfo(player);
		return;
	}
}
