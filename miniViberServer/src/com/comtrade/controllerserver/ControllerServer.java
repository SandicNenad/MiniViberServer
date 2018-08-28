package com.comtrade.controllerserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;

import com.comtrade.constant.Constant;
import com.comtrade.domain.Friends;
import com.comtrade.domain.GroupMembers;
import com.comtrade.domain.GroupMessages;
import com.comtrade.domain.Groups;
import com.comtrade.domain.PrivateMessage;
import com.comtrade.domain.SendingFile;
import com.comtrade.domain.User;
import com.comtrade.so.GeneralSystemOperation;
import com.comtrade.so.friends.CreateFriendRelationSO;
import com.comtrade.so.friends.DeleteFriedsSO;
import com.comtrade.so.friends.UpdateFriendsSO;
import com.comtrade.so.groupmembers.CreateGroupMemebersSO;
import com.comtrade.so.groupmembers.DeleteGroupMembersSO;
import com.comtrade.so.groupmembers.GetAllGroupMembersSO;
import com.comtrade.so.groupmembers.UpdateGroupMembersSO;
import com.comtrade.so.groupmessage.CreateGroupMessageSO;
import com.comtrade.so.groupmessage.GetAllGroupMessageSO;
import com.comtrade.so.friends.GetAllFriendRelationSO;
import com.comtrade.so.groups.CreateGroupSO;
import com.comtrade.so.groups.GetAllGroupsSO;
import com.comtrade.so.privatemessage.CreatePrivateMessageSO;
import com.comtrade.so.privatemessage.getAllPrivateMessagesForThisUserSO;
import com.comtrade.so.privatemessage.updatePrivateMessageSO;
import com.comtrade.so.privatemessage.updatePrivateMessageToSeenSO;
import com.comtrade.so.user.CreateUserSO;
import com.comtrade.so.user.GetAllUsersSO;
import com.comtrade.so.user.UpdateUserSO;
import com.comtrade.threads.ThreadRequirementsProcessing;
import com.comtrade.transfer.TransferClass;

public class ControllerServer {
	private static ControllerServer instance;
	private List<ThreadRequirementsProcessing> activeUserThreadsList = new ArrayList<>();
	private Map<Integer, ThreadRequirementsProcessing> hmActiveUserThreads = new HashMap<>();
	private List<Integer> listOfActiveUsers = new ArrayList<>();
	private Map<Integer, List<SendingFile>> profilePicturesHM = new HashMap<>();
	private Map<Integer, List<SendingFile>> groupPicturesHM = new HashMap<>();
	private JTextArea taServer;
	
	private ControllerServer() {
		readProfilePictureHMfromTxt();
		readGroupPictureHMfromTxt();
	}

	public static ControllerServer getInstance() {
		if (instance == null) {
			instance = new ControllerServer();
		}
		return instance;
	}
	
	public void setTextArea(JTextArea taServer) {
		this.taServer = taServer;
	}
	
	
	
	public List<Integer> getListOfActiveUsers() {
		return listOfActiveUsers;
	}

	public void setListOfActiveUsers(List<Integer> listOfActiveUsers) {
		this.listOfActiveUsers = listOfActiveUsers;
	}

	public void createUser(User u) {
		GeneralSystemOperation gso = new CreateUserSO();
		gso.executeSo(u);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		List<User> listaUsera = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new User());
		GeneralSystemOperation gso = new GetAllUsersSO();
		gso.executeSo(hm);
		listaUsera = (List<User>) hm.get(Constant.LIST_OF_OBJECT);
		return listaUsera;
	}

	public Map<Integer, User> getAllUsersHM() {
		Map<Integer, User> hm = new HashMap<>();
		for (User temp : getAllUsers()) {
			hm.put(temp.getIdUser(), temp);
		}
		return hm;
	}

	public void updateUser(User u1) {
		GeneralSystemOperation gso = new UpdateUserSO();
		gso.executeSo(u1);
	}

	public void addActiveUser(ThreadRequirementsProcessing trp) {
		activeUserThreadsList.add(trp);
	}

	public void refreshAllUsers(ThreadRequirementsProcessing trp) {
		for (ThreadRequirementsProcessing tempTrp : activeUserThreadsList) {
			if (!tempTrp.equals(trp)) {
				tempTrp.refreshForm();
			}
		}
	}

	public void removeClientFromActiveList(ThreadRequirementsProcessing threadRequirementsProcessing) {
		activeUserThreadsList.remove(threadRequirementsProcessing);
	}

	public void addActiveThreadUserHM(int idUser, ThreadRequirementsProcessing threadRequirementsProcessing) {
		System.out.println("povezao se user sa idem " + idUser);
		User tempUser = getAllUsersHM().get(idUser);
		String time = new SimpleDateFormat("dd MMMM, YYYY HH:mm:ss").format(new Date());
		taServer.append("["+time+"] - "+tempUser.getFirstName()+" "+tempUser.getLastName()+" ("+tempUser.getUsername()+") join miniViber");
		taServer.append("\n");
		
		hmActiveUserThreads.put(idUser, threadRequirementsProcessing);
		currentlyActiveUserThreads();
		listOfActiveUsers.add(idUser);
		
	}

	public void removeThreadUserHM(int idUser) {
		User tempUser = getAllUsersHM().get(idUser);
		String time = new SimpleDateFormat("dd MMMM, YYYY HH:mm:ss").format(new Date());
		taServer.append("["+time+"] - "+tempUser.getFirstName()+" "+tempUser.getLastName()+" ("+tempUser.getUsername()+") left miniVIBER");
		taServer.append("\n");
		
		hmActiveUserThreads.remove(idUser);
		List<Integer> tempList = new ArrayList<>();
		for (Entry<Integer, ThreadRequirementsProcessing> temp : hmActiveUserThreads.entrySet()) {
			tempList.add(temp.getKey());
		}
		listOfActiveUsers = tempList;
		updateOnlineUsersAtClientSide();
	}

	public void updateOnlineUsersAtClientSide() {
		for (Entry<Integer, ThreadRequirementsProcessing> temp : hmActiveUserThreads.entrySet()) {
			temp.getValue().updateOnlineUsersAtClientSide(listOfActiveUsers);
		}
	}

	public void currentlyActiveUserThreads() {
		System.out.println("------------Aktivno------" + hmActiveUserThreads.size() + "korisnika");
		for (Entry<Integer, ThreadRequirementsProcessing> temp : hmActiveUserThreads.entrySet()) {
			System.out.println("Aktivan user sa id-em " + temp.getKey());
		}
		System.out.println("------------------------------------");
	}

	public void createFriendRelation(Friends fr) {
		GeneralSystemOperation gso = new CreateFriendRelationSO();
		gso.executeSo(fr);
	}

	@SuppressWarnings("unchecked")
	public List<Friends> getAllFriendRelation() {
		List<Friends> listOfFriendRelations = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new Friends());
		GeneralSystemOperation gso = new GetAllFriendRelationSO();
		gso.executeSo(hm);
		listOfFriendRelations = (List<Friends>) hm.get(Constant.LIST_OF_OBJECT);
		return listOfFriendRelations;
	}

	public void sendFriendRelationIfHeIsOnline(Friends fr) {
		if (fr.getActionUserId() == fr.getUserOneId()) {
			if (hmActiveUserThreads.containsKey(fr.getUserTwoId())) {
				hmActiveUserThreads.get(fr.getUserTwoId()).sendFriendRelationToOnlineUser(fr);
			}
		} else {
			if (hmActiveUserThreads.containsKey(fr.getUserTwoId())) {
				hmActiveUserThreads.get(fr.getUserOneId()).sendFriendRelationToOnlineUser(fr);
			}
		}
	}

	public void updateFriends(Friends fr2) {
		GeneralSystemOperation gso = new UpdateFriendsSO();
		gso.executeSo(fr2);
	}

	public void refreshAllActiveWithUpdate(int idUserThis) {
		for (Entry<Integer, ThreadRequirementsProcessing> tempMap : hmActiveUserThreads.entrySet()) {
			if (tempMap.getKey() != idUserThis) {
				tempMap.getValue().refreshForm();
			}
		}
	}

	public void deleteFriends(Friends fr3) {
		GeneralSystemOperation gso = new DeleteFriedsSO();
		gso.executeSo(fr3);
	}

	public void sendMsgToFriendIfHeIsOnline(PrivateMessage pm) {
		if (pm.getSenderId() == pm.getUserOneId()) {
			if (hmActiveUserThreads.containsKey(pm.getUserTwoId())) {
				hmActiveUserThreads.get(pm.getUserTwoId()).sentMsgToOnlineFriend(pm);
			}
		} else {
			if (hmActiveUserThreads.containsKey(pm.getUserOneId())) {
				hmActiveUserThreads.get(pm.getUserOneId()).sentMsgToOnlineFriend(pm);
			}
		}
	}

	public void createPrivateMessage(PrivateMessage pm) {
		GeneralSystemOperation gso = new CreatePrivateMessageSO();
		gso.executeSo(pm);
	}

	@SuppressWarnings("unchecked")
	public List<PrivateMessage> getAllPMforThisUser(User uTemp) {
		List<PrivateMessage> listOfPrivateMessages = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new PrivateMessage());
		hm.put(Constant.VALUE_FOR_SELECT, uTemp);
		GeneralSystemOperation gso = new getAllPrivateMessagesForThisUserSO();
		gso.executeSo(hm);
		listOfPrivateMessages = (List<PrivateMessage>) hm.get(Constant.LIST_OF_OBJECT);
		return listOfPrivateMessages;
	}

	public void updatePrivateMessage(PrivateMessage pm) {
		GeneralSystemOperation gso = new updatePrivateMessageSO();
		gso.executeSo(pm);
	}

	public void sendMsgStatusUpdateFriendIfHeIsOnline(PrivateMessage pm1) {
		if (hmActiveUserThreads.containsKey(pm1.getSenderId())) {
			hmActiveUserThreads.get(pm1.getSenderId()).sentMsgToOnlineFriend(pm1);
		}
	}

	public void updatePrivateMessageToSeen(PrivateMessage pmSeenOp) {
		GeneralSystemOperation gso = new updatePrivateMessageToSeenSO();
		gso.executeSo(pmSeenOp);
	}

	public void createGroup(Groups gr) {
		GeneralSystemOperation gso = new CreateGroupSO();
		gso.executeSo(gr);
	}

	@SuppressWarnings("unchecked")
	public List<Groups> getAllGroups() {
		List<Groups> listOfGroups = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new Groups());
		GeneralSystemOperation gso = new GetAllGroupsSO();
		gso.executeSo(hm);
		listOfGroups = (List<Groups>) hm.get(Constant.LIST_OF_OBJECT);
		return listOfGroups;
	}

	public void createGroupMembers(GroupMembers grMem) {
		GeneralSystemOperation gso = new CreateGroupMemebersSO();
		gso.executeSo(grMem);
	}

	@SuppressWarnings("unchecked")
	public List<GroupMembers> getAllGroupMembers() {
		List<GroupMembers> listOfGroupMembers = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new GroupMembers());
		GeneralSystemOperation gso = new GetAllGroupMembersSO();
		gso.executeSo(hm);
		listOfGroupMembers = (List<GroupMembers>) hm.get(Constant.LIST_OF_OBJECT);
		return listOfGroupMembers;
	}

	public void deleteGroupMembers(GroupMembers grMem) {
		GeneralSystemOperation gso = new DeleteGroupMembersSO();
		gso.executeSo(grMem);
	}

	public void updateGroupsAtOnlineUsers(TransferClass tc2) {
		for (Entry<Integer, ThreadRequirementsProcessing> temp : hmActiveUserThreads.entrySet()) {
			temp.getValue().updateGroupsAtOnlineUsers(tc2);
		}
	}

	public void updateGroupMembersAtOnlineUsers(TransferClass tc2) {
		for (Entry<Integer, ThreadRequirementsProcessing> temp : hmActiveUserThreads.entrySet()) {
			temp.getValue().updateGroupMembersAtOnlineUsers(tc2);
		}
	}

	public void updateGroupMembers(GroupMembers grMemAcc) {
		GeneralSystemOperation gso = new UpdateGroupMembersSO();
		gso.executeSo(grMemAcc);
	}

	public void createGroupMessage(GroupMessages grMess) {
		GeneralSystemOperation gso = new CreateGroupMessageSO();
		gso.executeSo(grMess);
	}

	@SuppressWarnings("unchecked")
	public List<GroupMessages> getallGroupMessages() {
		List<GroupMessages> listOfGroupMessages = new ArrayList<>();
		Map<String, Object> hm = new HashMap<>();
		hm.put(Constant.TYPE_OF_OBJECT, new GroupMessages());
		GeneralSystemOperation gso = new GetAllGroupMessageSO();
		gso.executeSo(hm);
		listOfGroupMessages = (List<GroupMessages>) hm.get(Constant.LIST_OF_OBJECT);
		return listOfGroupMessages;
	}

	public void sendGroupMessageToOnlineMembers(GroupMessages grMess) {
		for (GroupMembers temp : getAllGroupMembers()) {
			if (temp.getIdUser() != grMess.getSenderId() && temp.getGroupName().equals(grMess.getGroupName())) {
				if (hmActiveUserThreads.containsKey(temp.getIdUser())) {
					hmActiveUserThreads.get(temp.getIdUser()).sendGroupMessageToOnlineMembers(grMess);
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public void copyProfilePictureToOnlineFriends(SendingFile sf) {
		for (Integer temp : sf.getListOfRecievers()) {
			if (hmActiveUserThreads.containsKey(temp)) {
				System.out.println("OVde smo videli da je aktivan " + temp);
				hmActiveUserThreads.get(temp).sendPictureToOnlineFriend(sf);
			} else {
				if (profilePicturesHM.containsKey(temp)) {
					System.out.println("Nije aktivan i ima ga u HM " + temp);
					profilePicturesHM.get(temp).add(sf);
				} else {
					System.out.println("Nije aktivan i nema ga u HM " + temp);
					List<SendingFile> list = new ArrayList<>();
					list.add(sf);
					profilePicturesHM.put(temp, list);
				}
				try {
					FileOutputStream fos = new FileOutputStream(Constant.PROFILE_PICTURES_HM);
					try {
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(profilePicturesHM);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void readProfilePictureHMfromTxt() {
		try {
			File f = new File(Constant.PROFILE_PICTURES_HM);
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(Constant.PROFILE_PICTURES_HM);
				try {
					ObjectInputStream ois = new ObjectInputStream(fis);
					try {
						profilePicturesHM = (Map<Integer, List<SendingFile>>) ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void readGroupPictureHMfromTxt() {
		File f = new File(Constant.GROUP_PICTURES_HM);
		if (f.exists()) {
			try {
				FileInputStream fis = new FileInputStream(Constant.GROUP_PICTURES_HM);
				try {
					ObjectInputStream ois = new ObjectInputStream(fis);
					try {
						groupPicturesHM = (Map<Integer, List<SendingFile>>) ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<Integer, List<SendingFile>> getProfilePicturesHM() {
		return profilePicturesHM;
	}

	public Map<Integer, List<SendingFile>> getGroupPicturesHM() {
		return groupPicturesHM;
	}

	public boolean isThisUserActive(int idUser) {
		if (hmActiveUserThreads.containsKey(idUser)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("resource")
	public void copyGroupPictureToOnlineUsers(SendingFile sfGroupPicture) {
		for (User tempUser : getAllUsers()) {
			if (hmActiveUserThreads.containsKey(tempUser.getIdUser())) {
				hmActiveUserThreads.get(tempUser.getIdUser()).sendGroupPictureToOnlineUser(sfGroupPicture);
			} else {
				if (groupPicturesHM.containsKey(tempUser.getIdUser())) {
					groupPicturesHM.get(tempUser.getIdUser()).add(sfGroupPicture);
				} else {
					List<SendingFile> list = new ArrayList<>();
					list.add(sfGroupPicture);
					groupPicturesHM.put(tempUser.getIdUser(), list);
				}
				try {
					FileOutputStream fos = new FileOutputStream(Constant.GROUP_PICTURES_HM);
					try {
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(groupPicturesHM);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Map<String, Groups> getAllGroupsHM() {
		Map<String, Groups> hm = new HashMap<>();
		for (Groups temp:getAllGroups()) {
			hm.put(temp.getGroupName(),temp);
		}
		return hm;
	}

}
