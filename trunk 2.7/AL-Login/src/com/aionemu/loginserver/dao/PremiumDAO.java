package com.aionemu.loginserver.dao;

import com.aionemu.commons.database.dao.DAO;

/**
 * @author KID
 */
public abstract class PremiumDAO implements DAO {

	public abstract int getPoints(int accountId);

	public abstract boolean updatePoints(int accountId, int points, int required);
	
	@Override
	public final String getClassName() {
		return PremiumDAO.class.getName();
	}
}
