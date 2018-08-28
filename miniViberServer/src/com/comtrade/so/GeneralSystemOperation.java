package com.comtrade.so;

import com.comtrade.db.Broker;

public abstract class GeneralSystemOperation {
	public void executeSo(Object obj) {
		try {
			startTransaction();
			executeConcreteOperation(obj);
			confirmTransaction();
		} catch (Exception e) {
			cancelTransaction();
		} finally {
			closeConnection();
		}
	}

	public abstract void executeConcreteOperation(Object obj);

	private void startTransaction() {
		Broker.getInstance().startTransaction();
	}

	private void confirmTransaction() {
		Broker.getInstance().confirmTransaction();
	}

	private void cancelTransaction() {
		Broker.getInstance().cancelTransaction();
	}
	
	private void closeConnection() {
		Broker.getInstance().closeConnection();
	}
}
