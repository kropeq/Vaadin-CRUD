package vaadin.models;

public class UserSession {
	
	String username;
	
	public UserSession(String username) {
        this.username = username;
    }
	
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
