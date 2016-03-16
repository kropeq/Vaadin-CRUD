package vaadin.services;

import vaadin.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
	
	private static List<User> listOfUsers= new ArrayList<User>();
	
	public long addUser(User user) {
		listOfUsers.add(user);
		return 1;
	}
	
	public long deleteUser(User user) {
		listOfUsers.remove(listOfUsers.indexOf(user));
		return 1;
	}
	
	public List<User> getUsers() {
		return listOfUsers;
	}
	
	public boolean isAlreadyRegistered(User user) {
		for(User u : getUsers()) {
			if (u.getUsername().equals(user.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
}
