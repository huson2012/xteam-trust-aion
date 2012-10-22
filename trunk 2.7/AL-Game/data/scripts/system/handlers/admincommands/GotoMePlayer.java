package admincommands;

import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.model.gameobjects.player.TeleportTask;
import com.light.gameserver.skillengine.SkillEngine;
import com.light.gameserver.skillengine.model.Skill;

/**
 * player movetome command.
 * 
 * @author Alex
 */
public class GotoMePlayer extends ChatCommand 
{

	public GotoMePlayer() 
	{
		super("gotomeplayer");
	}

	@Override
	public void execute(final Player player, String... params) 
	{
		if (params == null || params.length < 1)
		{
			PacketSendUtility.sendMessage(player, "syntax //gotomeplayer <имя персонажа>");
			return;
		}

		if (!player.isGM() && player.getClientConnection().getAccount().getMembership() != 2)
		{
		  PacketSendUtility.sendMessage(player, "Вам необходимо иметь уровень доступа VIP для использования этой команды");
			return;
		}
		if(player.getWorldId() == AdminConfig.LOC_ID) {
		    PacketSendUtility.sendMessage(player, "Нельзя телепортировать игроков находясь в этой локации");
			return;
		}
		final Player playerToMove;
        playerToMove = World.getInstance().findPlayer(Util.convertName(params[0]));
		
               if (player.isInPrison() && player.getAccessLevel() == 0){PacketSendUtility.sendYellowMessageOnCenter(player, " Нельзя использовать находясь в тюрьме.");return;}
                if (playerToMove.isInPrison()&& player.getAccessLevel() == 0){PacketSendUtility.sendYellowMessageOnCenter(player, " Нельзя использовать на персонажа в тюрьме.");return;}
		if (playerToMove == null) 
		{
			PacketSendUtility.sendMessage(player, " Указанный игрок не онлайн.");
			return;
		}

		if (playerToMove == player) 
		{
			PacketSendUtility.sendMessage(player, "Нельзя использовать на себя.");
			return;
		}
		if (playerToMove.getRace() != player.getRace() || playerToMove.isGM() && !player.isGM())
		{
		    PacketSendUtility.sendMessage(player, "Нельзя использовать на игроков противоположной расы или представителей администрации.");
			return;
		}
		
		                RequestResponseHandler responseHandler = new RequestResponseHandler(playerToMove)
						{
	                     public void acceptRequest(Creature requester, Player playerToMove)
	                     {
	                      setTeleportTask(playerToMove, player.getWorldId(), player.getInstanceId(), (int) player.getX(), (int) player.getY(), (int) player.getZ(), (int) player.getHeading());
		                  PacketSendUtility.sendMessage(player, "Персонаж " + playerToMove.getName() + " телепортируется к вам.");
		                  PacketSendUtility.sendMessage(playerToMove, "Вы согласились на телепортацию к персонажу " + player.getName() + ".");
						  PacketSendUtility.sendYellowMessageOnCenter(playerToMove, "Вы телепортируетесь к персонажу " + player.getName() + ".");
						  
						  Skill skill = SkillEngine.getInstance().getSkill(playerToMove, 1801, 1, player);
                          skill.useSkill();
                         }
						 public void denyRequest(Creature requester, Player playerToMove)
						 {
							PacketSendUtility.sendMessage(player, "Персонаж " + playerToMove.getName() + " отказался от телепортации.");
							PacketSendUtility.sendMessage(playerToMove, "Вы отказались от телепортации");					
						 }
	                    };
						boolean requested = playerToMove.getResponseRequester().putRequest(902247, responseHandler);
	                    try 
					    {
	                                               if(requested)
                                                       { 
													     String reason = Util.convertName(params[1]);
			                                               for(int itr = 3; itr < params.length; itr++)
			                                                reason += " "+params[itr];
															
														     String message = "Персонаж: "+ player.getName() + ", желает телепортировать вас к себе.Причина: "+ reason +". Вы желаете телепортироваться?";
                                                             PacketSendUtility.sendPacket(playerToMove, new SM_QUESTION_WINDOW(902247, 0, message));
															 return;
                                                       }
					    }
					     catch (Exception e) 
					    {
			              String message = "Персонаж: "+ player.getName() + ", желает телепортировать вас к себе.Вы желаете телепортироваться?";
                          PacketSendUtility.sendPacket(playerToMove, new SM_QUESTION_WINDOW(902247, 0, message));
		                }
	}

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax //gotomeplayer <characterName>");
	}
	void setTeleportTask(Player player, int worldId, int instanceId, int x, int y, int z, int h)
    {
        player.setTeleportTask(new TeleportTask(worldId, instanceId, x, y, z, h));
    }
}
