package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Alex
 */
public class LordAll extends ChatCommand {

    public LordAll() {
        super("lordall");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length < 1) {
            PacketSendUtility.sendMessage(admin, "Syntax: //lordall <loc | all>");
            return;
        }
        for (Player pp : World.getInstance().getAllPlayers()) {
            if (params[0].equals("loc")) {
                if (pp.getCommonData().getRace() == Race.ELYOS && pp.getWorldId() == admin.getWorldId()) {
                    pp.getController().useSkill(11889, 5);
                } else if (pp.getCommonData().getRace() == Race.ASMODIANS && pp.getWorldId() == admin.getWorldId()) {
                    pp.getController().useSkill(11890, 5);
                }
            } else if (params[0].equals("all")) {
                if (pp.getCommonData().getRace() == Race.ELYOS) {
                    pp.getController().useSkill(11889, 5);
                } else if (pp.getCommonData().getRace() == Race.ASMODIANS) {
                    pp.getController().useSkill(11890, 5);
                }
            }

        }
    }
}