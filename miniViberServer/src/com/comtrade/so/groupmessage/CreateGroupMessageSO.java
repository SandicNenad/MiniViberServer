package com.comtrade.so.groupmessage;

import com.comtrade.db.Broker;
import com.comtrade.domain.GroupMessages;
import com.comtrade.so.GeneralSystemOperation;

public class CreateGroupMessageSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		GroupMessages grMess = (GroupMessages) obj;
		Broker.getInstance().save(grMess);
	}

}
