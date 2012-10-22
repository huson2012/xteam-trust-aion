package admincommands;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.skillengine.model.Effect;
import com.light.gameserver.skillengine.model.SkillTemplate;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author ggadv2
 */
public class AddEffect extends ChatCommand {
	public AddEffect()
	{
		super("addeffect");
	}

	@Override
	public void execute (final Player admin, String... params)
	{

		if(params.length < 1)
		{
			PacketSendUtility.sendMessage(admin, "Syntax: //addeffect <skillid> <duration>\nThis command adds specified effect to the target for a specified duration (in seconds). Leaving duration null will use the default skill duration.");
			return;
		}

		VisibleObject target = admin.getTarget();
		Player effected = admin;
		int duration = 0;

		if(target instanceof Player)
			effected = (Player) target;

		SkillTemplate sTemplate = null;

		try
		{
			sTemplate = DataManager.SKILL_DATA.getSkillTemplate(Integer.parseInt(params[0]));
		}
		catch(Exception e)
		{
			PacketSendUtility.sendMessage(admin, "Wrong skill id!");
			return;
		}

		if(params.length == 2)
		{
			duration = Integer.parseInt(params[1]) * 1000;
		}
		else
		{
			duration = sTemplate.getDuration();
			if (duration < 1)
				duration = sTemplate.getEffects().getEffectsDuration();
		}

		Effect effect = new Effect(admin, effected, sTemplate, 1, duration);
		effected.getEffectController().addEffect(effect);
		effect.addAllEffectToSucess();
		effect.startEffect(true);
		PacketSendUtility.sendMessage(admin, "Effect " + sTemplate.getName() + " added to player " + effected.getName() + " for " + (duration / 1000) + " second(s).");
	}
}
