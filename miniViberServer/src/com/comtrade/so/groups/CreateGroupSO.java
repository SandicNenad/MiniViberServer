package com.comtrade.so.groups;

import com.comtrade.db.Broker;
import com.comtrade.domain.Groups;
import com.comtrade.so.GeneralSystemOperation;

public class CreateGroupSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		Groups gr = (Groups) obj;
		Broker.getInstance().save(gr);
	}

}
