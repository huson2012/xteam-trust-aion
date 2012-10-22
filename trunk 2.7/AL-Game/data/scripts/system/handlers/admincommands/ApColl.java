package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.abyss.AbyssPointsService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Alex
 * 
*/
public class ApColl extends ChatCommand {

    public ApColl() {
        super("obmen");
    }

    @Override
    public void execute(Player admin, String... params) {
        int cc = 100;
        int ap = 120000;
        int coll = admin.getDonatmoney();
        
        if (params == null || params.length < 1){
            PacketSendUtility.sendMessage(admin, "Syntax: //abmen yes\nОбмен " + cc + " coll на " + ap + " ап\nВаш баланс: "+coll+"\nЕсли согласны введите //obmen 'yes' ");
        }
            
        PacketSendUtility.sendMessage(admin, "Обмен " + cc + " coll на " + ap + " ап\nВаш баланс: "+coll+"\nЕсли согласны введите //obmen 'yes' ");
        if (params[0].equals("yes")) {
            if (coll < cc) {
                PacketSendUtility.sendMessage(admin, "Ваш текущий баланс: " + admin.getDonatmoney() + " Coll. Необходимо " + cc + "");
            } else {
                admin.setDonatmoney(coll - cc);
                AbyssPointsService.addAp(admin, ap);
                PacketSendUtility.sendMessage(admin, "Вы успешно обменяли " + cc + " coll на " + ap + " ап.Ваш текущий баланс: " + admin.getDonatmoney() + " Coll.");
                return;
            }
        }
    }
}
