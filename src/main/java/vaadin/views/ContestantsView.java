package vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContestantsView extends CssLayout implements View {

	private TextArea instructions = new TextArea();
	private Table startListTable = new Table();
	private GridLayout CRUDForm;
	private Button createContestant;
	private Button updateContestant;
	private Button deleteContestant;
	private TextField contestantBib;
	private TextField contestantName;
	private TextField contestantSurname;
	private TextField contestantNation;
	private Label emptyLabel;
	private Label emptyLabel2;
	public static String userInSession;
	
	public ContestantsView(){
		super();
		
		createContestant = new Button("Dodaj");
		updateContestant = new Button("Aktualizuj");
		deleteContestant = new Button("Usuń");
		emptyLabel = new Label("");
		emptyLabel2 = new Label("");
		
		instructions = new TextArea("Tu będzie lista");
		instructions.setValue("Tutaj będzie lista zawodnikow \n  zaś po prawej stronie będą pola do edycji dla admina");
		
		contestantBib = new TextField("Start number");
		contestantName = new TextField("Name");
		contestantSurname = new TextField("Surname");
		contestantNation = new TextField("Nation");
		
		// widok dla administratora
		if(userInSession.equals("admin")){
			CRUDForm = new GridLayout(3,4);
			CRUDForm.addComponent(contestantName);
			CRUDForm.addComponent(contestantSurname);
			CRUDForm.addComponent(emptyLabel);
			CRUDForm.addComponent(contestantBib);
			CRUDForm.addComponent(contestantNation);
			CRUDForm.addComponent(emptyLabel2);
			HorizontalLayout layoutButtons = new HorizontalLayout(createContestant,updateContestant,deleteContestant);
			layoutButtons.setSpacing(true);
			VerticalLayout CRUDLayout = new VerticalLayout(CRUDForm, layoutButtons);
			CRUDLayout.setWidth("50%");
			CRUDLayout.setMargin(true);
			VerticalLayout contents = new VerticalLayout();
			contents.setWidth("50%");
			contents.addComponent(instructions);
			contents.setMargin(true);
			
			HorizontalLayout layout = new HorizontalLayout(contents,CRUDLayout);
			layout.setSizeFull();
			this.addComponent(layout);
		// widok dla użytkownika
		} else {
			VerticalLayout contents = new VerticalLayout();
			contents.setWidth("50%");
			contents.addComponent(instructions);
			contents.setMargin(true);
			this.addComponent(contents);
		}
		
		
		
		
		//} else {
		//	CRUDForm.addComponent(updateContestant);
		////}
		
		//this.addComponent(this.instructions);
		//this.instructions.addStyleName("instructions");
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
