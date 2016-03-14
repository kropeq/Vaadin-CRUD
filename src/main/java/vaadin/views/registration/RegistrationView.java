package vaadin.views.registration;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class RegistrationView extends FormLayout implements View {
	
	private final TextField username = new TextField("Type your username: ");
	private final PasswordField passwd = new PasswordField("Type your password: ");
	private final PasswordField retyped = new PasswordField("Retype your password: ");
	private final Button register = new Button("Register me");
	
	public RegistrationView(){
		for(Component c: new Component[]{username,passwd,retyped,register})
		this.addComponent(c);
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
