package vaadin;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

public class PasswordResetWindow extends Window {

	public PasswordResetWindow(){
		final TextField username = new TextField("Type your username");
		final Button cancel = new Button("Close", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		final Button confirm = new Button("Reset password", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(!username.getValue().isEmpty()){
					close();
					Notification.show("Password changed", "Instructions was sent to your email",Notification.Type.WARNING_MESSAGE);
				}
			}
		});
		
		VerticalLayout contents = new VerticalLayout(username, new HorizontalLayout(cancel,confirm));
		this.setContent(contents);
		this.setModal(true);
		this.center();
	}
}
