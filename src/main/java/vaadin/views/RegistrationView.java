package vaadin.views;

import com.vaadin.data.Validator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import vaadin.models.User;
import vaadin.services.UserService;

public class RegistrationView extends FormLayout implements View, Button.ClickListener {
	
	private TextField username = new TextField("Type your username: ");
	private PasswordField passwd = new PasswordField("Type your password: ");
	private PasswordField retyped = new PasswordField("Retype your password: ");
	private Button register = new Button("Register me",this);
	
	private User user;
	private UserService userservice;
	
	public RegistrationView(){
		super();
		
		userservice = new UserService();
		this.setSizeFull();
		this.addComponent(this.username);
		this.addComponent(this.passwd);
		this.addComponent(this.retyped);
		this.addComponent(this.register);
		this.setMargin(true);
		
		// ustawienie "wymagane" do kazdego z pol rejestracji
		for(Field<?> f: new Field<?>[]{ this.username, this.passwd, this.retyped }){
			f.setRequired(true);
			f.setRequiredError("This field is required.");
		}
		
		// validator na podstawie tutorialu
		// sprawdza czy username jest co najmniej dlugosci 5 liter i czy zawiera tylko litery
		this.username.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String user = value == null ? "" : value.toString();
				if(user.length() < 5) throw new InvalidValueException("Username must be at least 6 letters");
				if(!user.matches("[a-zA-Z]*")) throw new InvalidValueException("Username must contain only letters");
			}
		});
		
		// validator na podstawie tutorialu
		// sprawdza czy haslo to ciag liter oraz przynajmniej jednej cyfry
		this.passwd.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String pass = value == null ? "" : value.toString();
				if(pass.length() < 6) throw new InvalidValueException("Password must be at least 6 letters");
				if(!pass.matches(".*[0-9].*")) throw new InvalidValueException("Password must contain at least 1 digit");
				if(!pass.matches(".*[a-zA-Z].*")) throw new InvalidValueException("Password must contain at least 1 letter");
			}
		});
		
		// validator na podstawie tutorialu
		// sprawdza czy haslo i powtorzone haslo sie zgadzaja
		this.retyped.addValidator(new Validator(){
			@Override
			public void validate(Object value) throws InvalidValueException {
				String retyped = value == null ? "" : value.toString();
				if(!retyped.equals(passwd.getValue())) throw new InvalidValueException("password and retyped password doesn't match");
			}
		});
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// ustawienie kursora w polu username
		username.focus();
	}


	@Override
	public void buttonClick(ClickEvent event) {
	
		// stworzenie nowego Usera na podstawie wpisanych danych
		user = new User(this.username.getValue(),this.passwd.getValue());
		
		// sprawdzenie walidacji loginu i hasla
		if(username.isValid() && passwd.isValid() && retyped.isValid()){
			// sprawdzenie czy istnieje juz taki login i haslo
			if(!userservice.isAlreadyRegistered(user)){
				// jesli nie to tworzymy nowy
				userservice.addUser(user);
				Notification.show("Account "+username.getValue()+" has been created.");
				username.setValue("");
				passwd.setValue("");
				retyped.setValue("");
				//String username_text = String.valueOf(getSession().getAttribute("username"));
				if(getSession().getAttribute("username") == null){
					getUI().getNavigator().navigateTo("Login");
				} else {
					getUI().getNavigator().navigateTo("Contestants");
				}
				// jesli tak to wyswietlamy komunikat, ze jest zajety
			} else {
				Notification.show(username.getValue()+" account already exist! Choose another account name. ",Notification.Type.ERROR_MESSAGE);
				username.clear();
			}
		} else Notification.show("One or more fields contains invalid values.",Notification.Type.ERROR_MESSAGE);
		
	}

}
