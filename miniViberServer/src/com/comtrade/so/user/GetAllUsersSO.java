package com.comtrade.so.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.comtrade.constant.Constant;
import com.comtrade.db.Broker;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.so.GeneralSystemOperation;

public class GetAllUsersSO extends GeneralSystemOperation {

	@SuppressWarnings("unchecked")
	@Override
	public void executeConcreteOperation(Object obj) {
		HashMap<String, Object> hm = (HashMap<String, Object>) obj;
		User u = (User) hm.get(Constant.TYPE_OF_OBJECT);
		List<GeneralDomain> listaObj = new ArrayList<>(); 
		listaObj= Broker.getInstance().getAll(u);
		hm.put(Constant.LIST_OF_OBJECT, listaObj);
	}

}
