package vaadin.views.contestants;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;

public class ContestantsView extends CssLayout implements View {

	private final TextField username = new TextField("Type your username: ");
	
	public ContestantsView(){
		//for(Component c: new Component[]{username,passwd,retyped})
		//this.addComponent(c);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
