package com.comtrade.so.privatemessage;

import com.comtrade.db.Broker;
import com.comtrade.domain.PrivateMessage;
import com.comtrade.so.GeneralSystemOperation;

public class updatePrivateMessageToSeenSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		PrivateMessage pm = (PrivateMessage) obj;
		Broker.getInstance().updateToSeen(pm);
	}

}
