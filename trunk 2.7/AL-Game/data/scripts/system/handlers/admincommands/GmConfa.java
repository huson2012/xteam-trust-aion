package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.audit.GMService;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;

/**
 * admin gmc command.
 *
 * @author Alex
 */
public class GmConfa extends ChatCommand {

    public GmConfa() {
        super("gmc");
    }

    @Override
    public void execute(final Player player, String... params) {
        Collection<Player> Gms = GMService.getInstance().getGMs();
        String message = StringUtils.join(params, " ");
             message = "[GM Confa ] " + player.getName() + " : " + message;
        for (Player gm : Gms) {       
                PacketSendUtility.sendWhiteMessageOnCenter(gm, message);
				}
    }
}
