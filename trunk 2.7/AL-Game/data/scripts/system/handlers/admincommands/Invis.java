package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.state.CreatureVisualState;
import com.light.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.light.gameserver.skillengine.effect.AbnormalState;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Divinity
 */
public class Invis extends ChatCommand {

	public Invis() {
		super("invis");
	}
	@Override
	public void execute(Player player, String... params) {
	    if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) {
			PacketSendUtility.sendMessage(player, "Вы не можете использовать команду в тюрьме.");
			return;
        }
		if (player.getVisualState() < 3) {
			player.getEffectController().setAbnormal(AbnormalState.HIDE.getId());
			player.setVisualState(CreatureVisualState.HIDE3);
			PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
			PacketSendUtility.sendMessage(player, "You are invisible.");
		}
		else {
			player.getEffectController().unsetAbnormal(AbnormalState.HIDE.getId());
			player.unsetVisualState(CreatureVisualState.HIDE3);
			PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
			PacketSendUtility.sendMessage(player, "You are visible.");
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
