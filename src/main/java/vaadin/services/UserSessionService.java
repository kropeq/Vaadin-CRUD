package vaadin.services;

import java.util.ArrayList;
import java.util.List;

import vaadin.models.Contestant;
import vaadin.models.UserSession;

public class UserSessionService {
	private static List<UserSession> listOfUsersSession= new ArrayList<UserSession>();
	
	public long addUser(UserSession usersession) {
		listOfUsersSession.add(usersession);
		return 1;
	}
	
	public long deleteUser(Integer index) {
		listOfUsersSession.remove((int)index);
		return 1;
	}
	
	public List<UserSession> getUsers() {
		return listOfUsersSession;
	}
	
	public Integer getIndexOfUserSessionToRemove(UserSession usersession){
		for(UserSession u : getUsers()) {
			if (u.getUsername().equals(usersession.getUsername())) {
				return listOfUsersSession.indexOf(u);
			}
		} return 0;
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
