package com.comtrade.so.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.comtrade.constant.Constant;
import com.comtrade.db.Broker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.Groups;
import com.comtrade.so.GeneralSystemOperation;

public class GetAllGroupsSO extends GeneralSystemOperation {

	@SuppressWarnings("unchecked")
	@Override
	public void executeConcreteOperation(Object obj) {
		HashMap<String, Object> hm = (HashMap<String, Object>) obj;
		Groups gr = (Groups) hm.get(Constant.TYPE_OF_OBJECT);
		List<GeneralDomain> listOfObject = new ArrayList<>();
		listOfObject = Broker.getInstance().getAll(gr);
		hm.put(Constant.LIST_OF_OBJECT, listOfObject );
	}

}
