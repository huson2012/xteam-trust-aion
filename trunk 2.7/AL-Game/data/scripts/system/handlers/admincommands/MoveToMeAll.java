package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.utils.Util;

/**
 * @author Alex
 */
public class MoveToMeAll extends ChatCommand {

    public MoveToMeAll() {
        super("raceme");
    }

    @Override
    public void execute(final Player admin, final String... params) {
        if (params == null || params.length < 1) {
            sendInfo(admin);
            return;
        }

        if (params[0].equals("all")) {
            for (final Player p : World.getInstance().getAllPlayers()) {
                if (!p.equals(admin)) {
                    RequestResponseHandler responseHandler = new RequestResponseHandler(p) {

                        public void acceptRequest(Creature requester, Player p) {
                            TeleportService.teleportTo(p, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(), admin.getZ(), admin.getHeading(), 3000, true);
                            PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                            PacketSendUtility.sendMessage(admin, "Персонаж: " + p.getName() + " согласился на телепортацию.");
                            PacketSendUtility.sendMessage(p, "Вы были телепортированы к представителю администрации: " + admin.getName() + ".");
                            return;
                        }

                        public void denyRequest(Creature requester, Player p) {
                            PacketSendUtility.sendMessage(admin, "Персонаж " + p.getName() + " отказался от телепортации.");
                            PacketSendUtility.sendMessage(p, "Вы отказались от телепортации");

                        }
                    };
                    boolean requested = p.getResponseRequester().putRequest(902247, responseHandler);
                    try {
                        if (requested) {
                            String reason = Util.convertName(params[1]);
                            for (int itr = 3; itr < params.length; itr++) {
                                reason += " " + params[itr];
                            }

                            String message = "Администратор " + admin.getName() + ", желает телепортировать всю Атрею.Причина: " + reason + ". Вы желаете телепортироваться?";
                            PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                            return;
                        }
                    } catch (Exception e) {
                        String message = "Администратор " + admin.getName() + ", желает телепортировать всю Атрею.Вы желаете телепортироваться?";
                        PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                    }
                }
            }
        }

        if (params[0].equals("elyos")) {
            for (final Player p : World.getInstance().getAllPlayers()) {
                if (!p.equals(admin)) {
                    if (p.getRace() == Race.ELYOS) {
                        RequestResponseHandler responseHandler = new RequestResponseHandler(p) {

                            public void acceptRequest(Creature requester, Player p) {
                                TeleportService.teleportTo(p, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(), admin.getZ(), admin.getHeading(), 3000, true);
                                PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                                PacketSendUtility.sendMessage(admin, "Персонаж " + p.getName() + " согласился на телепортацию.");
                                PacketSendUtility.sendMessage(p, "Вы были телепортированы к представителю администрации: " + admin.getName() + ".");
                                return;
                            }

                            public void denyRequest(Creature requester, Player p) {
                                PacketSendUtility.sendMessage(admin, "Персонаж " + p.getName() + " отказался от телепортации.");
                                PacketSendUtility.sendMessage(p, "Вы отказались от телепортации");

                            }
                        };
                        boolean requested = p.getResponseRequester().putRequest(902247, responseHandler);
                        try {
                            if (requested) {
                                String reason = Util.convertName(params[1]);
                                for (int itr = 3; itr < params.length; itr++) {
                                    reason += " " + params[itr];
                                }

                                String message = "Администратор: " + admin.getName() + ", желает телепортировать всю Элисею.Причина: " + reason + ". Вы желаете телепортироваться?";
                                PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                                return;
                            }
                        } catch (Exception e) {
                            String message = "Администратор: " + admin.getName() + ", желает телепортировать всю Элисею.Вы желаете телепортироваться?";
                            PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                        }
                    }
                }
            }
        }

        if (params[0].equals("asmos")) {
            for (final Player p : World.getInstance().getAllPlayers()) {
                if (!p.equals(admin)) {
                    if (p.getRace() == Race.ASMODIANS) {
                        RequestResponseHandler responseHandler = new RequestResponseHandler(p) {

                            public void acceptRequest(Creature requester, Player p) {
                                TeleportService.teleportTo(p, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(),
                                        admin.getZ(), admin.getHeading(), 3000, true);
                                PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                                PacketSendUtility.sendMessage(admin, "пгрок " + p.getName() + " согласился на телепортацию.");
                                PacketSendUtility.sendMessage(p, "Вы были телепортированы к представителю администрации " + admin.getName() + ".");
                                return;
                            }

                            public void denyRequest(Creature requester, Player p) {
                                PacketSendUtility.sendMessage(admin, "Персонаж " + p.getName() + " отказался от телепортации.");
                                PacketSendUtility.sendMessage(p, "Вы отказались от телепортации");

                            }
                        };
                        boolean requested = p.getResponseRequester().putRequest(902247, responseHandler);
                        try {
                            if (requested) {
                                String reason = Util.convertName(params[1]);
                                for (int itr = 3; itr < params.length; itr++) {
                                    reason += " " + params[itr];
                                }

                                String message = "Администратор " + admin.getName() + ", желает телепортировать всю Асмодею.Причина: " + reason + ". Вы желаете телепортироваться?";
                                PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                                return;
                            }
                        } catch (Exception e) {
                            String message = "Администратор " + admin.getName() + ", желает телепортировать всю Асмодею.Вы желаете телепортироваться?";
                            PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 0, message));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFail(Player player, String message) {
        sendInfo(player);
    }

    private void sendInfo(Player player) {
        PacketSendUtility.sendMessage(player, "syntax //raceme < all | elyos | asmos > <причина>");
    }
}
