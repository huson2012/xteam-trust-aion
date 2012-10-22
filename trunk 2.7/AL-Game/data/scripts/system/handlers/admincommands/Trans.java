package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Alex
 */
public class Trans extends ChatCommand {

    public Trans() {
        super("trans");
    }

    enum RecipientType {

        ELYOS,
        ASMO,
        ALL,
        PLAYER;

        public boolean isAllowed(Race race) {
            switch (this) {
                case ELYOS:
                    return race == Race.ELYOS;
                case ASMO:
                    return race == Race.ASMODIANS;
                case ALL:
                    return race == Race.ELYOS || race == Race.ASMODIANS;
            }
            return false;
        }
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length < 4) {
            PacketSendUtility.sendMessage(admin, "Syntax: //trans <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
            return;
        }


        int id = 0;
        int idSkill = 0;
        int level = 0;

        RecipientType recipientType = null;

        if ("all".startsWith(params[4])) {
            recipientType = RecipientType.ALL;
        } else if ("ely".startsWith(params[4])) {
            recipientType = RecipientType.ELYOS;
        } else if ("asmo".startsWith(params[4])) {
            recipientType = RecipientType.ASMO;
        } else {
            PacketSendUtility.sendMessage(admin, "Syntax: //trans <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
            return;
        }

        try {
            idSkill = Integer.parseInt(params[1]);
            level = Integer.parseInt(params[2]);
            id = Integer.parseInt(params[3]);
        } catch (Exception e) {
            PacketSendUtility.sendMessage(admin, "Syntax: //trans <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
        }

        if (params[0].equals("add")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (recipientType.isAllowed(pp.getRace()) && pp.getWorldId() == id) {
                    pp.getController().useSkill(idSkill, level);
                }
            }
        } else if (params[0].equals("dispel")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (recipientType.isAllowed(pp.getRace()) && pp.getWorldId() == id) {
                    pp.getEffectController().removeEffect(idSkill);
                }
            }
        }
    }
}
