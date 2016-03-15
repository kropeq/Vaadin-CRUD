package vaadin.views.registration;

import java.sql.SQLException;

import com.vaadin.data.Container;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import vaadin.services.ServiceProvider;

public class RegistrationView extends FormLayout implements View, Button.ClickListener {
	
	private TextField username = new TextField("Type your username: ");
	private PasswordField passwd = new PasswordField("Type your password: ");
	private PasswordField retyped = new PasswordField("Retype your password: ");
	
	private FieldGroup fieldGroup = new FieldGroup();
	
	private Container container;
	
	private Button register = new Button("Register me",this);
	
	public RegistrationView(){
		super();
		this.addComponent(this.username);
		this.addComponent(this.passwd);
		this.addComponent(this.retyped);
		this.addComponent(this.register);
		
		for(Field<?> f: new Field<?>[]{ this.username, this.passwd, this.retyped }){
			f.setRequired(true);
			f.setRequiredError("This field is required.");
		}
		
		this.username.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String user = value == null ? "" : value.toString();
				if(user.length() < 5) throw new InvalidValueException("Username must be at least 6 letters");
				if(!user.matches("[a-zA-Z]*")) throw new InvalidValueException("Username must contain only letters");
			}
		});
		
		this.passwd.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String pass = value == null ? "" : value.toString();
				if(pass.length() < 6) throw new InvalidValueException("Password must be at least 6 letters");
				if(!pass.matches(".*[0-9].*")) throw new InvalidValueException("Password must contain at least 1 digit");
				if(!pass.matches(".*[a-zA-Z].*")) throw new InvalidValueException("Password must contain at least 1 letter");
			}
		});
		
		this.retyped.addValidator(new Validator(){
			@Override
			public void validate(Object value) throws InvalidValueException {
				String retyped = value == null ? "" : value.toString();
				if(!retyped.equals(passwd.getValue())) throw new InvalidValueException("password and retyped password doesn't match");
			}
			
		});
		
		this.fieldGroup.bind(this.username, "username");
		this.fieldGroup.bind(this.passwd, "passwd");
		//this.fieldGroup.bind(this.retyped, "retyped");
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.container = ServiceProvider.getInstance().getContainerService().getUsersContainer();
		Object newUserId = this.container.addItem();
		this.fieldGroup.setItemDataSource(this.container.getItem(newUserId));
	}


	@Override
	public void buttonClick(ClickEvent event) {
		if(this.fieldGroup.isValid()){
			try{
				this.fieldGroup.commit();
				//((SQLContainer)container).commit();
				this.register.setEnabled(false);
				Notification.show("Your account has been created.");
			} catch(CommitException e){
				e.printStackTrace();
				Notification.show("The data could not be saved.","Error message: "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
			//} catch (UnsupportedOperationException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//} catch (SQLException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		} else Notification.show("One or more fields contains invalid values.");
		
	}

}
