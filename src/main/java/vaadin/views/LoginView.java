package vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
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

public class LoginView extends FormLayout implements View, Button.ClickListener {
	
	private User user;
	private UserService userservice;
	private TextField username;
	private GridLayout loginForm;
	private PasswordField password;
	private Button login;
	private Button forgot;
	private Button test;
	
	public LoginView(){
		super();
		
		userservice = new UserService();

		loginForm = new GridLayout(2,2);
    	username = new TextField("username");
    	password = new PasswordField("password");
    	
    	username.addStyleName("item");
    	password.addStyleName("item");
    	username.addStyleName("centered");
    	password.addStyleName("centered");
    	
    	login = new Button("Log in");
    	forgot = new Button("forgot password?");
    	test = new Button("Sprawdz sesje");
    	
    	login.setSizeFull();
    	
    	login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				user = new User(username.getValue(),password.getValue());
				String username_test = String.valueOf(getSession().getAttribute("username"));
				if(userservice.isAlreadyRegistered(user)){
					Notification.show("Zalogowano!",Notification.Type.HUMANIZED_MESSAGE);
					getSession().setAttribute("username", username.getValue());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getUI().getNavigator().navigateTo("Contestants");
				} else {
					Notification.show("Nie ma takiego uzytkownika!",Notification.Type.ERROR_MESSAGE);
				}

			}
		});
    	
    	test.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String username_test = String.valueOf(getSession().getAttribute("username"));
				if(username_test != "null"){
					Notification.show("Sesja jest zapisana! Nick to: "+username_test,Notification.Type.ERROR_MESSAGE);
				} else {
					Notification.show("Nie ma zapisanej sesji",Notification.Type.ERROR_MESSAGE);
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
    	loginForm.addComponent(test);
    	
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
