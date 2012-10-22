package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.SkillLearnService;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author ATracer
 */
public class GiveMissingSkills extends ChatCommand {

	public GiveMissingSkills() {
		super("gms");
	}

	@Override
	public void execute(Player player, String... params) {
		SkillLearnService.addMissingSkills(player);
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
