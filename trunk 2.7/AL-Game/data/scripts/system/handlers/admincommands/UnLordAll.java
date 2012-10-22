package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Alex
 */
public class UnLordAll extends ChatCommand {

    public UnLordAll() {
        super("unlordall");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length != 0) {
            PacketSendUtility.sendMessage(admin, "Syntax: //unlordall");
            return;
        }
        for (Player pp : World.getInstance().getAllPlayers()) {
            if (pp.getCommonData().getRace() == Race.ELYOS) {
                pp.getEffectController().removeEffect(18831);
            } else if (pp.getCommonData().getRace() == Race.ASMODIANS) {
                pp.getEffectController().removeEffect(11890);
            }
        }
    }
}