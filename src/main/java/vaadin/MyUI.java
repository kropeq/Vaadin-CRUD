package vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import vaadin.views.ContestantsView;
import vaadin.views.LoginView;
import vaadin.views.RegistrationView;

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
	
	private Button sessionTest;
	public Button logout;
	//public Button login;
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
        
        setContent(layout);
        
        final Navigator navigator = new Navigator(this, viewLayout);
        navigator.addView("Registration", RegistrationView.class);
        navigator.addView("Contestants", ContestantsView.class);
        navigator.addView("Login", LoginView.class);
        
        navigator.navigateTo("Registration");
        for(String s: new String[]{"Registration", "Contestants", "Login"})
        	topBar.addComponent(this.createNavigationButton(s, navigator, topBar));
        
        logout = new Button("Logout");
        logout.setVisible(false);
        topBar.addComponent(logout);
        
        sessionTest = new Button("Check session");
        topBar.addComponent(sessionTest);

        sessionTest.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String username_test = String.valueOf(getSession().getAttribute("username"));
				if(username_test != "null"){
					Notification.show("Your nick in this session is: "+username_test,Notification.Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Session is unrecognized.",Notification.Type.ERROR_MESSAGE);
				}
			}
		});
        //topBar.addComponent(this.createLoginForm());
    }

    private Button createNavigationButton(final String state, final Navigator navigator, final CssLayout topBar){
    	return new Button(state, new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String username_test = String.valueOf(getSession().getAttribute("username"));
				if(state=="Contestants"){
					if(username_test != "null"){
						navigator.navigateTo(state);
					} else {
						Notification.show("You aren't logged in! You can't go to this section.",Notification.Type.ERROR_MESSAGE);
					}
				} else if (state=="Login"){
					if(username_test != "null"){
					Notification.show("You are logged in already! You can do it only once.",Notification.Type.ERROR_MESSAGE);
					} else {
						navigator.navigateTo(state);
					}
				} else {
					navigator.navigateTo(state);
				}
			}
		});
    }
    

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
    public static class MyUIServlet extends VaadinServlet {
    }

}
