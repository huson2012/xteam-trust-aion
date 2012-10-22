package admincommands;

import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.state.CreatureVisualState;
import com.light.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.light.gameserver.skillengine.effect.AbnormalState;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
/**
 * @author Alex
 */
public class Unvis extends ChatCommand {

	public Unvis() {
		super("unvis");
	}
	@Override
	public void execute(Player player, String... params) {
	    if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) {
			PacketSendUtility.sendMessage(player, "Вы не можете использовать команду в тюрьме.");
			return;
        }
            VisibleObject visibleobject = player.getTarget();

		if (visibleobject == null || !(visibleobject instanceof Player)) {
			PacketSendUtility.sendMessage(player, "Wrong select target.");
			return;
		}

		Player target = (Player) visibleobject;
		if (target.getVisualState() < 3) {
			target.getEffectController().setAbnormal(AbnormalState.HIDE.getId());
			target.setVisualState(CreatureVisualState.HIDE3);
			PacketSendUtility.broadcastPacket(target, new SM_PLAYER_STATE(target), true);
			PacketSendUtility.sendMessage(target, "You are invisible.");
		}
		else {
			target.getEffectController().unsetAbnormal(AbnormalState.HIDE.getId());
			target.unsetVisualState(CreatureVisualState.HIDE3);
			PacketSendUtility.broadcastPacket(target, new SM_PLAYER_STATE(target), true);
			PacketSendUtility.sendMessage(target, "You are visible.");
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
