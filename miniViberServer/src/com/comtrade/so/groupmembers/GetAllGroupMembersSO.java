package com.comtrade.so.groupmembers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.comtrade.constant.Constant;
import com.comtrade.db.Broker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.GroupMembers;
import com.comtrade.so.GeneralSystemOperation;

public class GetAllGroupMembersSO extends GeneralSystemOperation {

	@SuppressWarnings("unchecked")
	@Override
	public void executeConcreteOperation(Object obj) {
		HashMap<String, Object> hm = (HashMap<String, Object>) obj;
		GroupMembers grMem = (GroupMembers) hm.get(Constant.TYPE_OF_OBJECT);
		List<GeneralDomain> listOfObject = new ArrayList<>();
		listOfObject = Broker.getInstance().getAll(grMem);
		hm.put(Constant.LIST_OF_OBJECT, listOfObject);
	}

}
