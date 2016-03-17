package vaadin.views;

import java.util.List;

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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import vaadin.MyUI;
import vaadin.models.Contestant;
import vaadin.services.ContestantService;

public class ContestantsView extends CssLayout implements View {

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
	
	ContestantService contestantservice;
	Contestant contestant;
	
	public ContestantsView(){
		super();
		
		contestantservice = new ContestantService();
		
		createContestant = new Button("Dodaj");
		updateContestant = new Button("Aktualizuj");
		deleteContestant = new Button("Usuń");
		emptyLabel = new Label("");
		emptyLabel2 = new Label("");
		
		//Integer width = UI.getCurrent().getPage().getBrowserWindowWidth();
		//width = width/3;
		startListTable = new Table("Start list");
		startListTable.setWidth("450px");
		startListTable.addContainerProperty("Bib", Integer.class, null);
		startListTable.addContainerProperty("Name", String.class, null);
		startListTable.addContainerProperty("Surame", String.class, null);
		startListTable.addContainerProperty("Nation", String.class, null);
		
		Integer numberOfRows;
		//List contestantsInStartlist = contestantservice.getContestants();
		for(Contestant c : contestantservice.getContestants()) {
			numberOfRows = startListTable.size();
			startListTable.addItem(new Object[]{c.getContestantBib(),c.getContestantName(),c.getContestantSurname(),c.getContestantNation()}, numberOfRows+1);
		}
		
		contestantBib = new TextField("Start number");
		contestantName = new TextField("Name");
		contestantSurname = new TextField("Surname");
		contestantNation = new TextField("Nation");
		
		// widok dla administratora
		if(userInSession.equals("admin")){
			// prawa czesc widoku
			CRUDForm = new GridLayout(2,2);
			CRUDForm.addComponent(contestantName);
			CRUDForm.addComponent(contestantSurname);
			CRUDForm.addComponent(contestantBib);
			CRUDForm.addComponent(contestantNation);
			HorizontalLayout layoutButtons = new HorizontalLayout(createContestant,updateContestant,deleteContestant);
			layoutButtons.setSpacing(true);
			VerticalLayout CRUDLayout = new VerticalLayout(CRUDForm, layoutButtons);
			CRUDLayout.setMargin(true);
			// lewa czesc widoku
			VerticalLayout contents = new VerticalLayout();
			contents.addComponent(startListTable);
			contents.setMargin(true);
			
			HorizontalLayout layout = new HorizontalLayout(contents,CRUDLayout);
			layout.setSizeFull();
			this.addComponent(layout);
		// widok dla użytkownika
		} else {
			VerticalLayout contents = new VerticalLayout();
			contents.addComponent(startListTable);
			contents.setMargin(true);
			this.addComponent(contents);
		}
		
		createContestant.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Integer bib = Integer.parseInt(contestantBib.getValue());
				String name = contestantName.getValue();
				String surname = contestantSurname.getValue();
				String nation = contestantNation.getValue();
				contestant = new Contestant(bib,name,surname,nation);
				if(!contestantservice.isAlreadyInStartlist(contestant)){	
					//getSession().setAttribute("username", username.getValue());
					contestantservice.addContestant(contestant);
					Integer numberOfRows = startListTable.size();
					startListTable.addItem(new Object[]{bib,name,surname,nation}, numberOfRows+1);
					//String username_text = String.valueOf(getSession().getAttribute("username"));
					//getUI().getNavigator().navigateTo("Contestants");
					contestantName.clear();
					contestantSurname.clear();
					contestantBib.clear();
					contestantNation.clear();
					contestantName.focus();
					Notification.show("Added!",Notification.Type.WARNING_MESSAGE);
				} else {
					Notification.show("This contestant is already in the startlist!",Notification.Type.ERROR_MESSAGE);
				}
			}
		});
		
		deleteContestant.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Integer bib = Integer.parseInt(contestantBib.getValue());
				contestant = new Contestant(bib,"nic","nic","nic");
				
				if(contestantservice.isBibInStartlist(contestant)){	
					Integer index = contestantservice.getIndexOfContestantToRemove(contestant);
					contestantservice.deleteContestant(index);
					contestantName.clear();
					contestantSurname.clear();
					contestantBib.clear();
					contestantNation.clear();
					contestantName.focus();
					Notification.show("Contestants with bib "+(index+1)+" was removed!",Notification.Type.WARNING_MESSAGE);
				} else {
					Notification.show("This bib is not in the startlist!",Notification.Type.ERROR_MESSAGE);
				}
			}
		});
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
