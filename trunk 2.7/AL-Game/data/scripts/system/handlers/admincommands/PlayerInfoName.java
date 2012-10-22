package admincommands;

import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Alex
 */
public class PlayerInfoName extends ChatCommand {

    public PlayerInfoName() {
        super("n");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length != 0) {
            PacketSendUtility.sendMessage(admin, "//n таргет ");
            return;
        }
          Player target = admin;

		if (admin.getTarget() instanceof Player) {
			target = (Player) admin.getTarget();
                             PacketSendUtility.sendMessage(admin, " Имя персонажа: " + target.getName() + "\n Псевдоним: "+target.getPlayerClass().getRusname()+"");    
                } else {
                    PacketSendUtility.sendMessage(admin, " Ваше имя: " + target.getName() + "\n Ваш Псевдоним: "+target.getPlayerClass().getRusname()+"");
                }
        }
}
