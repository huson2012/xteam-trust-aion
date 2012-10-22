package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;

/**
 * @author dragoon112
 */
public class CM_REMOVE_ALTERED_STATE extends AionClientPacket {

	private int skillid;

	/**
	 * @param opcode
	 */
	public CM_REMOVE_ALTERED_STATE(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	/*
	 * (non-Javadoc)
	 * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
	 */
	@Override
	protected void readImpl() {
		skillid = readH();

	}

	/*
	 * (non-Javadoc)
	 * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
	 */
	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		player.getEffectController().removeEffect(skillid);
	}

}
