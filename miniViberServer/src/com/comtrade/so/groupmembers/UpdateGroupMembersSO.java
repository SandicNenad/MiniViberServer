package com.comtrade.so.groupmembers;

import com.comtrade.db.Broker;
import com.comtrade.domain.GroupMembers;
import com.comtrade.so.GeneralSystemOperation;

public class UpdateGroupMembersSO extends GeneralSystemOperation {

	@Override
	public void executeConcreteOperation(Object obj) {
		GroupMembers grMem = (GroupMembers) obj;
		Broker.getInstance().update(grMem);
	}

}
