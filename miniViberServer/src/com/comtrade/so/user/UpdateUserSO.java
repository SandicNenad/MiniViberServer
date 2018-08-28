package com.comtrade.so.user;

import com.comtrade.db.Broker;
import com.comtrade.domain.User;
import com.comtrade.so.GeneralSystemOperation;

public class UpdateUserSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		User u = (User) obj;
		Broker.getInstance().update(u);
	}

}
