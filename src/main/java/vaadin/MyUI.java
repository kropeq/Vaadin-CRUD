package vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

import vaadin.views.contestants.ContestantsView;
import vaadin.views.registration.RegistrationView;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
//@SuppressWarnings("serial")
public class MyUI extends UI {
	
   

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final CssLayout layout = new CssLayout();
        final CssLayout topBar = new CssLayout();
        final CssLayout viewLayout = new CssLayout();
        
        topBar.setHeight("90px");
        topBar.setWidth("100%");
        viewLayout.setSizeFull();
        
        layout.addComponent(topBar);
        layout.addComponent(viewLayout);
        
        /*final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);*/
        setContent(layout);
        
        final Navigator navigator = new Navigator(this, viewLayout);
        navigator.addView("Registration", RegistrationView.class);
        navigator.addView("Contestants", ContestantsView.class);
        
        navigator.navigateTo("Contestants");
        for(String s: new String[]{"Registration", "Contestants"})
        	topBar.addComponent(this.createNavigationButton(s, navigator, topBar));
        
        topBar.addComponent(this.createLoginForm());
    }

    private Button createNavigationButton(final String state, final Navigator navigator, final CssLayout topBar){
    	return new Button(state, new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(state);
			}
		});
    }
    
    private Component createLoginForm(){
    	GridLayout loginForm = new GridLayout(3,3);
    	TextField username = new TextField("username");
    	Label info = new Label("Don't have account yet?");
    	PasswordField password = new PasswordField("password");
    	
    	username.addStyleName("item");
    	password.addStyleName("item");
    	username.addStyleName("centered");
    	password.addStyleName("centered");
    	
    	
    	
    	Button login = new Button("Log in");
    	Button forgot = new Button("forgot password?");
    	Button register = new Button("Registration");
    	
    	Label loginInfo = new Label("This username is already taken");
    	
    	login.setSizeFull();
    	
    	login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Logging in is not yet supported.",Notification.Type.ERROR_MESSAGE);
				
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
    	loginForm.addComponent(info);
    	
    	// 2nd line of Grid
    	loginForm.addComponent(login);
    	loginForm.addComponent(forgot);
    	loginForm.addComponent(register);
    	
    	// 3rd line of Grid
    	//loginForm.addComponent(loginInfo);
    	
    	return loginForm;
    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
    public static class MyUIServlet extends VaadinServlet {
    }
}
