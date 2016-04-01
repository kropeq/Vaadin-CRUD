package vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Runo;

import vaadin.PasswordResetWindow;
import vaadin.models.User;
import vaadin.services.UserService;
import vaadin.MyUI;
import vaadin.views.ContestantsView;
import vaadin.models.UserSession;
import vaadin.services.UserSessionService;

public class LoginView extends FormLayout implements View, Button.ClickListener {
	
	private User user;
	private UserService userservice;
	private TextField username;
	private GridLayout loginForm;
	private PasswordField password;
	private Button login;
	private Button forgot;
	private UserSession usersession;
	private UserSessionService usersessionservice;
	
	public LoginView(){
		super();

		userservice = new UserService();
		usersessionservice = new UserSessionService();

		loginForm = new GridLayout(2,2);
    	username = new TextField("username");
    	password = new PasswordField("password");
    	
    	username.addStyleName("item");
    	password.addStyleName("item");
    	username.addStyleName("centered");
    	password.addStyleName("centered");
    	
    	login = new Button("Login");
    	forgot = new Button("Forgot password?");
    	
    	login.setSizeFull();
    	login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				user = new User(username.getValue(),password.getValue());
				
				if(userservice.isAlreadyRegistered(user)){	
					if(userservice.checkPassword(user)){
						if(!usersessionservice.isAlreadyInSession(new UserSession(username.getValue()))){
							getSession().setAttribute("username", username.getValue());
							usersession = new UserSession(username.getValue());
							usersessionservice.addUser(usersession);
							Notification.show("You are logged in as "+getSession().getAttribute("username")+"!",Notification.Type.HUMANIZED_MESSAGE);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(getSession().getAttribute("username") == null){
								MyUI.login.setVisible(true);
								MyUI.logout.setVisible(false);
							} else {
								MyUI.login.setVisible(false);
								MyUI.logout.setVisible(true);
							}
							String username_text = String.valueOf(getSession().getAttribute("username"));
							ContestantsView.userInSession = username_text;
							
							getUI().getNavigator().navigateTo("Contestants");
						} else {
							Notification.show("This user is already in session! You can't log in.",Notification.Type.ERROR_MESSAGE);
						}
					
					} else {
						Notification.show("Wrong password!",Notification.Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("There is no account named in this way!",Notification.Type.ERROR_MESSAGE);
				}
			}
		});
    	

    	forgot.addStyleName(Runo.BUTTON_LINK);	
    	forgot.addClickListener(new Button.ClickListener() {
		
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new PasswordResetWindow());
			}
		});
    	
    	// 1st line of Grid
    	loginForm.addComponent(username);
    	loginForm.addComponent(password);
    	
    	// 2nd line of Grid
    	loginForm.addComponent(login);
    	loginForm.addComponent(forgot);
    	loginForm.setMargin(true);
    	
    	this.addComponent(loginForm);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		username.focus();
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
}
