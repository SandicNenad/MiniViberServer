package com.comtrade.so.privatemessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.comtrade.constant.Constant;
import com.comtrade.db.Broker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PrivateMessage;
import com.comtrade.domain.User;
import com.comtrade.so.GeneralSystemOperation;

public class getAllPrivateMessagesForThisUserSO extends GeneralSystemOperation {

	@SuppressWarnings("unchecked")
	@Override
	public void executeConcreteOperation(Object obj) {
		HashMap<String, Object> hm = (HashMap<String, Object>) obj;
		PrivateMessage pm = (PrivateMessage) hm.get(Constant.TYPE_OF_OBJECT);
		List<GeneralDomain> listOfObj = new ArrayList<>();
		User u = (User) hm.get(Constant.VALUE_FOR_SELECT);
		listOfObj = Broker.getInstance().getAllForOne(pm, u);
		hm.put(Constant.LIST_OF_OBJECT, listOfObj);
	}

}
