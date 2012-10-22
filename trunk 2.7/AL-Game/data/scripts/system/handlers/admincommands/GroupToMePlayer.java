package admincommands;

import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.services.abyss.AbyssPointsService;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Alex
 */
public class GroupToMePlayer extends ChatCommand {

    public GroupToMePlayer() {
        super("gtmp");// Я заебался её писать... а так норм:)
    }

    @Override
    public void execute(final Player admin, String... params) {
        if (params == null || params.length < 1) {
            onFail(admin, null);
            return;
        }

        final boolean colall = false;
        
        final int ap = 500000;
        
        final int apall = 100000;

        final Player groupToMove = World.getInstance().findPlayer(Util.convertName(params[0]));

        final String message = admin.getName() + " предлагает телепортировать вас и вашу группу к себе. Вы согласны телепортироватся вместе с группой?";

        final String messages = " Вы действительно желаете отправить запрос персонажу " + groupToMove.getName() + " на телепортацию всей группы? За телепорт всей группы с вас будет списано " + ap + " ап";

        if (groupToMove == null) {
            PacketSendUtility.sendMessage(admin, "Указанный игрок не онлайн.");
            return;
        }

        if (!groupToMove.isInGroup2()) {
            PacketSendUtility.sendMessage(admin, groupToMove.getName() + " не в группе.");
            return;
        }

        if (admin.getClientConnection().getAccount().getMembership() != 2) {
            PacketSendUtility.sendMessage(admin, "Эта команда доступна только VIP");
            return;
        }
        if (admin.getAbyssRank().getAp() < ap) {
            PacketSendUtility.sendMessage(admin, "У вас не достаточно ап, необходимое кол-во для телепорта " + ap + "");
            return;
        }
        RequestResponseHandler responseHandlers = new RequestResponseHandler(admin) {

            public void acceptRequest(Creature requester, Player responder) {
                RequestResponseHandler responseHandler = new RequestResponseHandler(groupToMove) {

                    public void acceptRequest(Creature requester, Player p) {
                        if(!colall){
                                   AbyssPointsService.addAp(admin, - ap);
                                    PacketSendUtility.sendMessage(admin, "За телепорт всей группы с вас было списано " + ap + " ап");
                        }
                        
                        for (final Player target : groupToMove.getPlayerGroup2().getMembers()) {
                            if (target != admin) {
                                TeleportService.teleportTo(target, admin.getWorldId(), admin.getInstanceId(),
                                        admin.getX(), admin.getY(), admin.getZ(), admin.getHeading(), 3000, true);
                                PacketSendUtility.sendMessage(target, "Вы были телепортированы к персонажу " + admin.getName() + ".");
                                PacketSendUtility.sendMessage(admin, "Персонаж " + groupToMove.getName() + " согласился на телепортацию");
                                PacketSendUtility.sendMessage(admin, "Персонаж " + target.getName() + " телепортируется к вам.");
                                if(colall){
                                    AbyssPointsService.addAp(admin, - apall);
                                    PacketSendUtility.sendMessage(admin, "За телепорт всей группы с вас было списано " + ap + " ап");
                                    PacketSendUtility.sendMessage(admin, "За телепорт персонажа "+target.getName()+" с вас было списано " + ap + " ап");
                                }
                                
                            }
                        }
                    }

                    public void denyRequest(Creature requester, Player pp) {
                        PacketSendUtility.sendMessage(pp, "Вы отказались от телепортации к персонажу " + admin.getName());
                        PacketSendUtility.sendMessage(admin, groupToMove.getName() + " отказался от телепортации");
                        return;
                    }
                };
                boolean requested = groupToMove.getResponseRequester().putRequest(902247, responseHandler);
                if (requested) {
                    PacketSendUtility.sendPacket(groupToMove, new SM_QUESTION_WINDOW(902247, 0, message));
                    PacketSendUtility.sendMessage(admin, "Запрос на телепорт успешно отправлен персонажу " + groupToMove.getName() + ". Ожидайте...");
                }
            }
            public void denyRequest(Creature requester, Player responder) {
                PacketSendUtility.sendMessage(admin, "Запрос отменен");
                return;
            }
        };
        boolean requested = admin.getResponseRequester().putRequest(902247, responseHandlers);
        if (requested) {
            PacketSendUtility.sendPacket(admin, new SM_QUESTION_WINDOW(902247, 0, messages));
            
        }
    }
    
    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //gtmp <лидер группы>");
    }
}
