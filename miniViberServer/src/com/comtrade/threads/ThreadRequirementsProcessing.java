package com.comtrade.threads;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.comtrade.constant.Constant;
import com.comtrade.controllerserver.ControllerServer;
import com.comtrade.domain.Friends;
import com.comtrade.domain.GroupMembers;
import com.comtrade.domain.GroupMessages;
import com.comtrade.domain.Groups;
import com.comtrade.domain.PrivateMessage;
import com.comtrade.domain.SendingEmail;
import com.comtrade.domain.SendingFile;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;

public class ThreadRequirementsProcessing extends Thread {

	private Socket socket;
	private String usernameActive;
	private Map<String, Object> hm = new HashMap<>();
	private int idUserThis;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		// ControllerServer.getInstance().addActiveUser(this);
		while (true) {
			try {
				ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
				try {
					TransferClass tc = (TransferClass) inSocket.readObject();
					processRequirementClient(tc);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
//				e.printStackTrace();
				// ControllerServer.getInstance().removeClientFromActiveList(this);
				ControllerServer.getInstance().removeThreadUserHM(idUserThis);
				ControllerServer.getInstance().currentlyActiveUserThreads();
				break;
			}
		}
	}

	@SuppressWarnings("unused")
	private void processRequirementClient(TransferClass tc) {

		switch (tc.getOperation()) {
		case Constant.ASK_FOR_GROUP_PICTURE:
			String groupPictureURL = (String) tc.getClientObject();
			File fAskGroupPicture = new File(Constant.GROUP_PICTURE_SERVER+groupPictureURL);
			try {
				byte[] pictureGroup = Files.readAllBytes(fAskGroupPicture.toPath());
				SendingFile sfAskForGroupPicture = new SendingFile(pictureGroup);
				sfAskForGroupPicture.setFileName(groupPictureURL);
				sendGroupPictureToOnlineUser(sfAskForGroupPicture);
			} catch (IOException e3) {
				e3.printStackTrace();
			}
			
			break;
		case Constant.COPY_GROUP_PICTURE_TO_ALL:
			SendingFile sfGroupPicture = (SendingFile) tc.getClientObject();
			File fNewGroupPicture = new File(Constant.GROUP_PICTURE_SERVER+sfGroupPicture.getFileName());
			try {
				Files.write(fNewGroupPicture.toPath(), sfGroupPicture.getPicture(), StandardOpenOption.CREATE);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			ControllerServer.getInstance().copyGroupPictureToOnlineUsers(sfGroupPicture);
			break;
		
		case Constant.ASK_FOR_FRIEND_PROFILE_PICTURE:
			String friendProfilePictureURL = (String) tc.getClientObject();
			File f1 = new File(Constant.PROFILE_PICTURE_SERVER + friendProfilePictureURL);
			byte[] picture;
			try {
				picture = Files.readAllBytes(f1.toPath());
				SendingFile sfAskForProfilePicture = new SendingFile(picture);
				sfAskForProfilePicture.setFileName(friendProfilePictureURL);
				sendPictureToOnlineFriend(sfAskForProfilePicture);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
			
		case Constant.SEND_MY_PROFILE_PICTURE_TO_NEW_FRIEND:
			SendingFile sfPicToSpecificFriend = (SendingFile) tc.getClientObject();
			ControllerServer.getInstance().copyProfilePictureToOnlineFriends(sfPicToSpecificFriend);
			break;
			
		case Constant.COPY_PROFILE_PICTURE_TO_SERVER:
			SendingFile sf = (SendingFile) tc.getClientObject();
			File f = new File(Constant.PROFILE_PICTURE_SERVER + sf.getFileName());
			for (User temp : ControllerServer.getInstance().getAllUsers()) {
				if (temp.getTypeOfUser().equals("admin")) {
					sf.getListOfRecievers().add(temp.getIdUser());
				}
			}
			ControllerServer.getInstance().copyProfilePictureToOnlineFriends(sf);
			try {
				Files.write(f.toPath(), sf.getPicture(), StandardOpenOption.CREATE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case Constant.SEND_PASSWORD_TO_EMAIL:
			String username = (String) tc.getClientObject();
			List<User> listOfUsers = (List<User>) ControllerServer.getInstance().getAllUsers();
			boolean isInList = false;
			String email = "";
			String pass = "";
			for (User temp : listOfUsers) {
				if (username.equalsIgnoreCase(temp.getUsername())) {
					isInList = true;
					email = temp.getEmail();
					pass = temp.getPassword();
					break;
				}
			}
			if (isInList) {
				String emailBody = "Your password for miniViber is " + pass;
				SendingEmail mail = new SendingEmail(email, "miniViber recovered password", emailBody);
				TransferClass tcPassReset = new TransferClass();
				tcPassReset.setServerMessage("Password sent to " + email);
				tcPassReset.setOperation(Constant.SEND_PASSWORD_SUCCESS);
				sendToClient(tcPassReset);
			} else {
				TransferClass tcPassReset = new TransferClass();
				tcPassReset.setServerMessage("Username don`t exist in our database");
				tcPassReset.setOperation(Constant.SEND_PASSWORD_FAILED);
				sendToClient(tcPassReset);
			}

			break;
			
		case Constant.CREATE_USER:
			User u = (User) tc.getClientObject();
			List<User> listaSvihUsera = ControllerServer.getInstance().getAllUsers();
			boolean hasUsername = false;
			boolean hasEmail = false;
			for (User uTemp : listaSvihUsera) {
				if (uTemp.getUsername().equalsIgnoreCase(u.getUsername())) {
					hasUsername = true;
					break;
				}
				if (uTemp.getEmail().equalsIgnoreCase(u.getEmail())) {
					hasEmail = true;
					break;
				}

			}
			if (!hasUsername && !hasEmail) {
				ControllerServer.getInstance().createUser(u);
				TransferClass tc2 = new TransferClass();
				tc2.setServerMessage("CLIENT CREATED");
				for (User tempUser : ControllerServer.getInstance().getAllUsers()) {
					if (u.getUsername().equals(tempUser.getUsername())) {
						idUserThis = tempUser.getIdUser();
						ControllerServer.getInstance().addActiveThreadUserHM(idUserThis, this);
						u = tempUser;
					}
				}
				usernameActive = u.getUsername();
				hm.put(Constant.ALL_USERS_LIST, ControllerServer.getInstance().getAllUsers());
				User currUser = null;
				hm.put(Constant.ALL_FRIEND_RELATION_LIST, ControllerServer.getInstance().getAllFriendRelation());
				hm.put(Constant.ALL_USERS_HM, ControllerServer.getInstance().getAllUsersHM());
				for (Entry<Integer, User> temp : ControllerServer.getInstance().getAllUsersHM().entrySet()) {
					if (usernameActive.equals(temp.getValue().getUsername())) {
						currUser = temp.getValue();
					}
				}
				hm.put(Constant.CURRENT_USER, currUser);
				hm.put(Constant.ALL_PRIVATE_MESSAGES, ControllerServer.getInstance().getAllPMforThisUser(currUser));
				hm.put(Constant.ALL_GROUPS_LIST, ControllerServer.getInstance().getAllGroups());
				hm.put(Constant.ALL_GROUPS_HM, ControllerServer.getInstance().getAllGroupsHM());
				hm.put(Constant.ALL_GROUPMEMBERS_LIST, ControllerServer.getInstance().getAllGroupMembers());
				hm.put(Constant.ALL_GROUPMESSAGES_LIST, ControllerServer.getInstance().getallGroupMessages());
				hm.put(Constant.ACTIVE_USERS, ControllerServer.getInstance().getListOfActiveUsers());

				tc2.setServerObject(hm);
				sendToClient(tc2);

			} else if (hasUsername) {
				TransferClass tc2 = new TransferClass();
				tc2.setServerMessage("USERNAME EXIST");
				tc2.setServerObject(u);
				sendToClient(tc2);
			} else if (hasEmail) {
				TransferClass tc2 = new TransferClass();
				tc2.setServerMessage("EMAIL EXIST");
				tc2.setServerObject(u);
				sendToClient(tc2);
			}
			break;
			
		case Constant.GET_ALL_USERS:
			TransferClass tc3 = new TransferClass();
			List<User> lista = ControllerServer.getInstance().getAllUsers();
			tc3.setServerObject(lista);
			sendToClient(tc3);
			break;

		case Constant.LOGIN:
			TransferClass tc4 = new TransferClass();
			User us = (User) tc.getClientObject();
			List<User> listaSvihUsera1 = ControllerServer.getInstance().getAllUsers();
			hm.put(Constant.ALL_USERS_LIST, listaSvihUsera1);
			hm.put(Constant.ALL_FRIEND_RELATION_LIST, ControllerServer.getInstance().getAllFriendRelation());
			hm.put(Constant.ALL_USERS_HM, ControllerServer.getInstance().getAllUsersHM());
			hm.put(Constant.ALL_GROUPS_LIST, ControllerServer.getInstance().getAllGroups());
			hm.put(Constant.ALL_GROUPS_HM, ControllerServer.getInstance().getAllGroupsHM());

			tc4.setOperation(Constant.LOGIN_FAILED);
			tc4.setServerMessage("WRONG USERNAME OR PASSWORD");
			for (User uTemp : listaSvihUsera1) {
				if (us.getUsername().equalsIgnoreCase(uTemp.getUsername())
						&& us.getPassword().equals(uTemp.getPassword())) {
					if (ControllerServer.getInstance().isThisUserActive(uTemp.getIdUser())) {
						tc4.setServerMessage("USERNAME CURRENTLY LOGGED AT ANOTHER COMPUTER");
						break;
					} else {
						usernameActive = uTemp.getUsername();
						idUserThis = uTemp.getIdUser();
						ControllerServer.getInstance().addActiveThreadUserHM(idUserThis, this);
						hm.put(Constant.CURRENT_USER, uTemp);
						hm.put(Constant.ALL_PRIVATE_MESSAGES,
								ControllerServer.getInstance().getAllPMforThisUser(uTemp));
						hm.put(Constant.ALL_GROUPMEMBERS_LIST, ControllerServer.getInstance().getAllGroupMembers());
						hm.put(Constant.ALL_GROUPMESSAGES_LIST, ControllerServer.getInstance().getallGroupMessages());
						hm.put(Constant.ACTIVE_USERS, ControllerServer.getInstance().getListOfActiveUsers());
						List<SendingFile> listOfNewPictures = new ArrayList<>();
						listOfNewPictures = ControllerServer.getInstance().getProfilePicturesHM()
								.get(uTemp.getIdUser());
						ControllerServer.getInstance().getProfilePicturesHM().remove(uTemp.getIdUser());
						hm.put(Constant.ALL_NEW_PICTURES_LIST, listOfNewPictures);
						List<SendingFile> listOfNewGroupPictures = new ArrayList<>();
						listOfNewGroupPictures = ControllerServer.getInstance().getGroupPicturesHM().get(uTemp.getIdUser());
						ControllerServer.getInstance().getGroupPicturesHM().remove(uTemp.getIdUser());
						hm.put(Constant.ALL_NEW_GROUP_PICTURES, listOfNewGroupPictures);
						tc4.setServerObject(hm);
						tc4.setOperation(Constant.LOGIN_SUCCESSFULL);
						tc4.setServerMessage("LOGIN SUCCESSFULL");
						break;
					}
				}
			}
			sendToClient(tc4);
			ControllerServer.getInstance().updateOnlineUsersAtClientSide();
			break;
			
		case Constant.UPDATE_USER_INFO:
			TransferClass tc5 = new TransferClass();
			User u1 = (User) tc.getClientObject();
			hm.put(Constant.CURRENT_USER, u1);
			ControllerServer.getInstance().updateUser(u1);
			hm.put(Constant.ALL_USERS_LIST, ControllerServer.getInstance().getAllUsers());
			hm.put(Constant.ALL_USERS_HM, ControllerServer.getInstance().getAllUsersHM());
			tc5.setServerObject(hm);
			tc5.setOperation(Constant.UPDATE_SUCCESSFULL);
			sendToClient(tc5);
			ControllerServer.getInstance().refreshAllActiveWithUpdate(idUserThis);
			ControllerServer.getInstance().updateOnlineUsersAtClientSide();
			break;

		case Constant.FRIEND_REQUEST_SENT:
			Friends fr = (Friends) tc.getClientObject();
			ControllerServer.getInstance().createFriendRelation(fr);
			ControllerServer.getInstance().sendFriendRelationIfHeIsOnline(fr);
			break;

		case Constant.FRIEND_REQUEST_ACCEPTED:
			Friends fr2 = (Friends) tc.getClientObject();
			ControllerServer.getInstance().updateFriends(fr2);
			ControllerServer.getInstance().sendFriendRelationIfHeIsOnline(fr2);
			break;

		case Constant.FRIEND_REQUEST_DECLINED:
			Friends fr3 = (Friends) tc.getClientObject();
			ControllerServer.getInstance().deleteFriends(fr3);
			ControllerServer.getInstance().sendFriendRelationIfHeIsOnline(fr3);
			break;

		case Constant.FRIEND_REQUEST_BLOCK_FRIEND:
			Friends fr4 = (Friends) tc.getClientObject();
			ControllerServer.getInstance().updateFriends(fr4);
			ControllerServer.getInstance().sendFriendRelationIfHeIsOnline(fr4);
			break;

		case Constant.FRIEND_REQUEST_BLOCK_NON_FRIEND:
			Friends fr5 = (Friends) tc.getClientObject();
			ControllerServer.getInstance().createFriendRelation(fr5);
			ControllerServer.getInstance().sendFriendRelationIfHeIsOnline(fr5);
			break;

		case Constant.MESSAGE_PENDING_OPERATION:
			PrivateMessage pm = (PrivateMessage) tc.getClientObject();
			ControllerServer.getInstance().createPrivateMessage(pm);
			ControllerServer.getInstance().sendMsgToFriendIfHeIsOnline(pm);
			break;

		case Constant.MESSAGE_DELIVERED_OPERATION:
			PrivateMessage pm1 = (PrivateMessage) tc.getClientObject();
			ControllerServer.getInstance().updatePrivateMessage(pm1);
			ControllerServer.getInstance().sendMsgStatusUpdateFriendIfHeIsOnline(pm1);

			break;

		case Constant.MESSAGE_SEEN_OPERATION:
			PrivateMessage pmSeenOp = (PrivateMessage) tc.getClientObject();
			ControllerServer.getInstance().updatePrivateMessageToSeen(pmSeenOp);
			ControllerServer.getInstance().sendMsgStatusUpdateFriendIfHeIsOnline(pmSeenOp);
			break;

		case Constant.GROUP_CREATE_OPERATION:
			Groups gr = (Groups) tc.getClientObject();
			boolean hasGroupNameInBase = false;
			for (Groups tmpGr : ControllerServer.getInstance().getAllGroups()) {
				if (tmpGr.getGroupName().equalsIgnoreCase(gr.getGroupName())) {
					hasGroupNameInBase = true;
					break;
				}
			}
			if (hasGroupNameInBase) {
				TransferClass tc2 = new TransferClass();
				tc2.setOperation(Constant.GROUP_CREATE_FAILED);
				tc2.setServerMessage("GROUP NAME ALREADY EXIST");
				tc2.setServerObject(gr);
				sendToClient(tc2);
			} else {
				ControllerServer.getInstance().createGroup(gr);
				TransferClass tc2 = new TransferClass();
				tc2.setServerObject(gr);
				tc2.setOperation(Constant.GROUP_CREATE_SUCCESS);
				tc2.setServerMessage("GROUP SUCCESFULLY CREATED");
				ControllerServer.getInstance().updateGroupsAtOnlineUsers(tc2);
				GroupMembers grMem = new GroupMembers(gr.getGroupCreatorId(), gr.getGroupName(), gr.getDateOfCreation(),
						gr.getTimeOfCreation(), "admin");
				ControllerServer.getInstance().createGroupMembers(grMem);
				TransferClass tc22 = new TransferClass();
				tc22.setServerObject(grMem);
				tc22.setOperation(Constant.GROUPMEMBERS_CREATED);
				ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tc22);
			}
			break;
			
		case Constant.GROUPMEMBERS_KICKED:
			GroupMembers grMem = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().deleteGroupMembers(grMem);
			TransferClass tc2 = new TransferClass();
			tc2.setOperation(Constant.GROUPMEMBERS_KICKED);
			tc2.setServerObject(grMem);
			ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tc2);
			break;
		case Constant.GROUPMEMBERS_ASKTOJOIN:
			GroupMembers grMem1 = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().createGroupMembers(grMem1);
			TransferClass tcAskToJoin = new TransferClass();
			tcAskToJoin.setOperation(Constant.GROUPMEMBERS_ASKTOJOIN);
			tcAskToJoin.setServerObject(grMem1);
			ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tcAskToJoin);
			break;
		case Constant.GROUPMEMBERS_CANCELREQUEST:
			GroupMembers grMemCcl = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().deleteGroupMembers(grMemCcl);
			TransferClass tcCancel = new TransferClass();
			tcCancel.setOperation(Constant.GROUPMEMBERS_CANCELREQUEST);
			tcCancel.setServerObject(grMemCcl);
			ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tcCancel);
			break;
		case Constant.GROUPMEMBERS_ACCEPTEDMEMBERSHIP:
			GroupMembers grMemAcc = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().updateGroupMembers(grMemAcc);
			TransferClass tcAcceptmember = new TransferClass();
			tcAcceptmember.setOperation(Constant.GROUPMEMBERS_ACCEPTEDMEMBERSHIP);
			tcAcceptmember.setServerObject(grMemAcc);
			ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tcAcceptmember);
			break;
		case Constant.GROUPMEMBERS_DECLINEDMEMBERSHIP:
			GroupMembers grMemDcl = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().deleteGroupMembers(grMemDcl);
			TransferClass tcDecl = new TransferClass();
			tcDecl.setOperation(Constant.GROUPMEMBERS_DECLINEDMEMBERSHIP);
			tcDecl.setServerObject(grMemDcl);
			ControllerServer.getInstance().updateGroupMembersAtOnlineUsers(tcDecl);
			break;
		case Constant.GROUPMEMBERS_CHANGED:
			GroupMembers groupMemberChaged = (GroupMembers) tc.getClientObject();
			ControllerServer.getInstance().updateGroupMembers(groupMemberChaged);
			break;
		case Constant.GROUPMESSAGE_CREATEOPERATION:
			GroupMessages grMess = (GroupMessages) tc.getClientObject();
			ControllerServer.getInstance().createGroupMessage(grMess);
			ControllerServer.getInstance().sendGroupMessageToOnlineMembers(grMess);
			break;

		default:
			break;
		}
	}

	private void sendToClient(TransferClass tc2) {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.writeObject(tc2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void refreshForm() {
		hm.put(Constant.ALL_USERS_LIST, ControllerServer.getInstance().getAllUsers());
		hm.put(Constant.ALL_USERS_HM, ControllerServer.getInstance().getAllUsersHM());
		TransferClass tc = new TransferClass();
		tc.setServerObject(hm);
		tc.setOperation(Constant.REFRESH_FORM);
		sendToClient(tc);
	}

	public void sendFriendRelationToOnlineUser(Friends fr) {
		TransferClass tc = new TransferClass();
		tc.setOperation(Constant.FRIEND_REQUEST_SENT);
		tc.setServerObject(fr);
		sendToClient(tc);
	}

	public void sentMsgToOnlineFriend(PrivateMessage pm) {
		TransferClass tc = new TransferClass();
		if (pm.getMessageStatus().equals(Constant.MESSAGE_PENDING)) {
			tc.setOperation(Constant.MESSAGE_PENDING_OPERATION);
		} else if (pm.getMessageStatus().equals(Constant.MESSAGE_DELIVERED)) {
			tc.setOperation(Constant.MESSAGE_DELIVERED_OPERATION);
		} else if (pm.getMessageStatus().equals(Constant.MESSAGE_SEEN)) {
			tc.setOperation(Constant.MESSAGE_SEEN_OPERATION);
		}
		tc.setServerObject(pm);
		sendToClient(tc);
	}

	public void updateGroupsAtOnlineUsers(TransferClass tc2) {
		sendToClient(tc2);
	}

	public void updateGroupMembersAtOnlineUsers(TransferClass tc2) {
		sendToClient(tc2);
	}

	public void sendGroupMessageToOnlineMembers(GroupMessages grMess) {
		TransferClass tc = new TransferClass();
		tc.setOperation(Constant.GROUPMESSAGE_NEWMESSAGE);
		tc.setServerObject(grMess);
		sendToClient(tc);
	}

	public void updateOnlineUsersAtClientSide(List<Integer> listOfActiveUsers) {
		TransferClass tc = new TransferClass();
		tc.setOperation(Constant.LIST_OF_ACTIVE_USERS_CHANGED);
		tc.setServerObject(listOfActiveUsers);
		sendToClient(tc);
	}

	public void sendPictureToOnlineFriend(SendingFile sf) {
		TransferClass tcPicture = new TransferClass();
		tcPicture.setOperation(Constant.UPDATED_PROFILE_PICTURE);
		tcPicture.setServerObject(sf);
		sendToClient(tcPicture);
	}

	public void sendGroupPictureToOnlineUser(SendingFile sfGroupPicture) {
		TransferClass tcGroupPicture = new TransferClass();
		tcGroupPicture.setOperation(Constant.UPDATED_GROUP_PICTURE);
		tcGroupPicture.setServerObject(sfGroupPicture);
		sendToClient(tcGroupPicture);
	}
}
