package com.comtrade.so.friends;

import com.comtrade.db.Broker;
import com.comtrade.domain.Friends;
import com.comtrade.so.GeneralSystemOperation;

public class DeleteFriedsSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		Friends fr = (Friends) obj;
		Broker.getInstance().delete(fr);
	}

}
