package com.comtrade.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PrivateMessage;

public class Broker {
	private static Broker instance;
	private Connection conn;

	private Broker() {
		loadDriver();
	}

	private void loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Broker getInstance() {
		if (instance == null) {
			instance = new Broker();
		}
		return instance;
	}

	public void startTransaction() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniviber", "root", "");
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void confirmTransaction() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cancelTransaction() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(GeneralDomain gd) {
		String upit = "INSERT INTO " + gd.getTableName() + " " + gd.getForInsert();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<GeneralDomain> getAll(GeneralDomain gd) {
		List<GeneralDomain> lista = new ArrayList<>();
		String upit = "SELECT * FROM "+gd.getForSelect();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(upit);
			lista = gd.editSelect(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	public void update(GeneralDomain gd) {
		String upit = "UPDATE "+gd.getTableName()+gd.getForUpdate(gd);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void delete(GeneralDomain gd) {
		String upit = "DELETE FROM "+gd.getTableName()+gd.getForDelete(gd);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<GeneralDomain> getAllForOne(GeneralDomain pm, GeneralDomain u) {
		List<GeneralDomain> list = new ArrayList<>();
		String upit = "SELECT * FROM "+pm.getTableName()+pm.getForSelectForSpecific(u);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(upit);
			list = pm.editSelect(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateToSeen(PrivateMessage pm) {
		String upit = "UPDATE "+pm.getTableName()+" SET messageStatus='"+pm.getMessageStatus()+"' WHERE userOneId="+pm.getUserOneId()+" AND userTwoId ="+pm.getUserTwoId()+" AND senderId="+pm.getSenderId()+" AND messageStatus='Delivered'";
		try {
			Statement st =conn.createStatement();
			st.executeUpdate(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
