package vaadin.services;

import java.util.ArrayList;
import java.util.List;

import vaadin.models.UserSession;

public class UserSessionService {
	private static List<UserSession> listOfUsers= new ArrayList<UserSession>();
	
	public long addUser(UserSession usersession) {
		listOfUsers.add(usersession);
		return 1;
	}
	
	public long deleteUser(UserSession usersession) {
		listOfUsers.remove(listOfUsers.indexOf(usersession));
		return 1;
	}
	
	public List<UserSession> getUsers() {
		return listOfUsers;
	}
	
	public boolean isAlreadyInSession(UserSession usersession) {
		for(UserSession u : getUsers()) {
			if (u.getUsername().equals(usersession.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
}
