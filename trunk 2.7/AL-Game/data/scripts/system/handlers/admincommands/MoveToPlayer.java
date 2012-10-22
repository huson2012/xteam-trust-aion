package admincommands;

import com.light.gameserver.configs.administration.AdminConfig;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.model.gameobjects.player.TeleportTask;
import com.light.gameserver.skillengine.SkillEngine;
import com.light.gameserver.skillengine.model.Skill;
import com.light.gameserver.model.Race;

/**
 * Admin movetoplayer command.
 * 
 * @author Alex
 */
public class MoveToPlayer extends ChatCommand {

	public MoveToPlayer() {
		super("gotoplayer");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(admin, "syntax //gotoplayer Имя перса");
			return;
		}

		Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (player == null) {
			PacketSendUtility.sendMessage(admin, "Указанный игрок не онлайн.");
			return;
		}

		if (player == admin) 
		{
			PacketSendUtility.sendMessage(admin, "Нельзя использовать на себя.");
			return;
		}
		if (admin.isInPrison() && admin.getAccessLevel() == 0){PacketSendUtility.sendYellowMessageOnCenter(admin, " Нельзя использовать находясь в тюрьме.");return;}
                if (player.isInPrison()&& admin.getAccessLevel() == 0){PacketSendUtility.sendYellowMessageOnCenter(admin, " Нельзя использовать на персонажа в тюрьме.");return;}
                
		if(admin.getAccessLevel()!=0)
		{
		    TeleportService.teleportTo(admin, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),
			player.getZ(), player.getHeading(), 3000, true);
		    PacketSendUtility.sendYellowMessageOnCenter(admin, "Телепортация к персонажу " + player.getName() + ".");
		}
		if(player.getAccessLevel()==0 && admin.getCommonData().getRace() == Race.ASMODIANS && player.getCommonData().getRace() == Race.ASMODIANS && player.getWorldId() != 120010000 && player.getWorldId() != 110010000 && player.getWorldId() != AdminConfig.LOC_ID)
		{
		    setTeleportTask(admin, player.getWorldId(), player.getInstanceId(), (int) player.getX(), (int) player.getY(), (int) player.getZ(), (int) player.getHeading());
		    PacketSendUtility.sendYellowMessageOnCenter(admin, "Телепортация к персонажу " + player.getName() + ".");	
		}
		else if(player.getAccessLevel()==0 && admin.getCommonData().getRace() == Race.ELYOS && player.getCommonData().getRace() == Race.ELYOS && player.getWorldId() != 120010000 && player.getWorldId() != 110010000 && player.getWorldId() != AdminConfig.LOC_ID)
		{
		    setTeleportTask(admin, player.getWorldId(), player.getInstanceId(), (int) player.getX(), (int) player.getY(), (int) player.getZ(), (int) player.getHeading());
		    PacketSendUtility.sendYellowMessageOnCenter(admin, "Телепортация к игроку " + player.getName() + " .");	
		}
		else
		{
		   if (admin.getAccessLevel()==0)
		   {
		    PacketSendUtility.sendYellowMessageOnCenter(admin, "Невозможно телепортироваться к игроку вражеской рассы или к представителю Администрации.");
			return;
		   }
		   else
		   {
		    return;
		   }
		}
		    Skill skill = SkillEngine.getInstance().getSkill(admin, 1801, 1, admin);
              skill.useSkill();
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //gotoplayer <Имя игрока>");
	}
	void setTeleportTask(Player player, int worldId, int instanceId, int x, int y, int z, int h)
    {
        player.setTeleportTask(new TeleportTask(worldId, instanceId, x, y, z, h));
    }

}
