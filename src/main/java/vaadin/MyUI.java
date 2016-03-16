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
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	getSession().setAttribute("username", null);
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
        
        navigator.navigateTo("Contestants");
        for(String s: new String[]{"Registration", "Contestants", "Login"})
        	topBar.addComponent(this.createNavigationButton(s, navigator, topBar));
        
        //topBar.addComponent(this.createLoginForm());
    }

    private Button createNavigationButton(final String state, final Navigator navigator, final CssLayout topBar){
    	return new Button(state, new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(state);
			}
		});
    }
    

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
    public static class MyUIServlet extends VaadinServlet {
    }
}
