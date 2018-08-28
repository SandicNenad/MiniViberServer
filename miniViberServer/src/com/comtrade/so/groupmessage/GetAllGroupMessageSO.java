package com.comtrade.so.groupmessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.comtrade.constant.Constant;
import com.comtrade.db.Broker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.GroupMessages;
import com.comtrade.so.GeneralSystemOperation;

public class GetAllGroupMessageSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> hm = (HashMap<String, Object>) obj;
		GroupMessages grMess = (GroupMessages) hm.get(Constant.TYPE_OF_OBJECT);
		List<GeneralDomain> listOfObject = new ArrayList<>();
		listOfObject = Broker.getInstance().getAll(grMess);
		hm.put(Constant.LIST_OF_OBJECT, listOfObject);

	}

}
