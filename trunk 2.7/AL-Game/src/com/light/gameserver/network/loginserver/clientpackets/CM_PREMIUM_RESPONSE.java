package com.light.gameserver.network.loginserver.clientpackets;

import com.light.gameserver.model.ingameshop.InGameShopEn;
import com.light.gameserver.network.loginserver.LsClientPacket;

/**
 * @author KID
 */
public class CM_PREMIUM_RESPONSE extends LsClientPacket {
	private int requestId;
	private int result;
	private int points;

	public CM_PREMIUM_RESPONSE(int opCode) {
		super(opCode);
	}

	@Override
	protected void readImpl() {
		requestId = readD();
		result = readD();
		points = readD();
	}

	@Override
	protected void runImpl() {
		InGameShopEn.getInstance().finishRequest(requestId, result, points);
	}
}
