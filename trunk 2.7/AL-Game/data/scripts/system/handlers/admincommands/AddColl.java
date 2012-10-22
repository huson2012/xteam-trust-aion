package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Alex
 */
public class AddColl extends ChatCommand {

    public AddColl() 
    {
        super("addcoll");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params == null || params.length < 2) {
            PacketSendUtility.sendMessage(player, "syntax //addcoll <имя> +|- <int>");
            return;
        }

        Player target = World.getInstance().findPlayer(Util.convertName(params[0]));
        
        if (target == null){
            PacketSendUtility.sendMessage(player, "Неправильное имя персонажа");
            return;
        }
        
        int money = player.getDonatmoney();

        int coll = 0;
        String paramValue = params[2];
            try 
            {
               coll = Integer.parseInt(paramValue);
            } catch (Exception e) {
                PacketSendUtility.sendMessage(player, "syntax //addcoll <имя> +|- <int>");
                return;
            }
            if (params[1].equals("+")) {
                target.setDonatmoney(money + coll);
				PacketSendUtility.sendMessage(player, "Вы выдали персонажу "+target.getName()+" "+coll+" coll");
				PacketSendUtility.sendMessage(target, "Администратор "+player.getName()+" выдал вам "+coll+" coll");
            }
            else if (params[1].equals("-")) {
                target.setDonatmoney(money - coll);
				PacketSendUtility.sendMessage(player, "Вы выдали персонажу "+target.getName()+" -"+coll+" coll");
				PacketSendUtility.sendMessage(target, "Администратор "+player.getName()+" выдал вам -"+coll+" coll");
            }
			PacketSendUtility.sendMessage(target, "Для просмотра баланса на вашем счету, используйте команду //coll");
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //addcoll <имя> +|- <int>");
    }
}
