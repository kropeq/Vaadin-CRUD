package vaadin.views;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

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
	public static String userInSession;
	
	ContestantService contestantservice;
	Contestant contestant;
	
	public ContestantsView(){
		super();
		
		contestantservice = new ContestantService();
		
		createContestant = new Button("Add");
		updateContestant = new Button("Update");
		deleteContestant = new Button("Delete");
		
		//Integer width = UI.getCurrent().getPage().getBrowserWindowWidth();
		//width = width/3;
		startListTable = new Table("Start list");
		startListTable.setWidth("400px");
		startListTable.addContainerProperty("Bib", Integer.class, null);
		startListTable.addContainerProperty("Name", String.class, null);
		startListTable.addContainerProperty("Surname", String.class, null);
		startListTable.addContainerProperty("Nation", String.class, null);
		startListTable.setColumnWidth("Bib", 100);
		startListTable.setColumnWidth("Name", 100);
		startListTable.setColumnWidth("Surname", 100);
		startListTable.setColumnWidth("Nation", 100);
		
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
		
		// ustawienie "wymagane" do kazdego z pol CRUD
		for(Field<?> f: new Field<?>[]{ this.contestantBib, this.contestantName, this.contestantSurname, this.contestantNation }){
			f.setRequired(true);
			f.setRequiredError("This field is required.");
		}
		
		this.contestantBib.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String bib = value == null ? "" : value.toString();
				if(!bib.matches("[0-9]{1,2}")) throw new InvalidValueException("BIB must contain only 1 lub 2 digits!");
			}
		});
		
		this.contestantName.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String name = value == null ? "" : value.toString();
				if(name.length() < 2) throw new InvalidValueException("Name must be at least 2 letters");
				if(!name.matches("[A-ZĄĆĘŁŃÓŚŻŹ]{1}[a-ząćęłńóśżź]+")) throw new InvalidValueException("Name must contain one capital letter and letters ");
			}
		});
		
		this.contestantSurname.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String surname = value == null ? "" : value.toString();
				if(surname.length() < 2) throw new InvalidValueException("Surname must be at least 2 letters");
				if(!surname.matches("[A-ZĄĆĘŁŃÓŚŻŹ]{1}[a-ząćęłńóśżź]+")) throw new InvalidValueException("Surname must contain one capital letter and letters ");
			}
		});
		
		this.contestantNation.addValidator(new Validator(){
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				String nation = value == null ? "" : value.toString();
				if(nation.length() < 5) throw new InvalidValueException("Nation must be at least 5 letters");
				if(!nation.matches("[A-Z]{1}[a-z]+")) throw new InvalidValueException("Nation must contain one capital letter and letters ");
			}
		});
		
		// Sortowanie tabeli po wlasciwosci "Bib" przy uruchamianiu widoku
		Object [] properties={"Bib"};
		boolean [] ordering={true};
		startListTable.sort(properties, ordering);
		
		// widok dla administratora
		if(userInSession.equals("admin")){
			// prawa czesc widoku
			startListTable.setEditable(true);
			
			//Object rowId = startListtable.getValue();
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
		
		// Naciśnięcie w Tabli na któryś wiersz spowoduje uzupełnieine danych w formularzu
		startListTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
		    @Override
		    public void itemClick(ItemClickEvent itemClickEvent) {
		    	Item item = startListTable.getItem(itemClickEvent.getItemId());
		        Object Bib = item.getItemProperty("Bib").getValue();
		        Object Name = item.getItemProperty("Name").getValue();
		        Object Surname = item.getItemProperty("Surname").getValue();
		        Object Nation = item.getItemProperty("Nation").getValue();
		        contestantName.setValue(Name.toString());
		        contestantSurname.setValue(Surname.toString());
		        contestantNation.setValue(Nation.toString());
		        contestantBib.setValue(Bib.toString());
		    }
		});
		
		createContestant.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(contestantBib.isValid() && contestantName.isValid() && contestantSurname.isValid() && contestantNation.isValid()){
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
						getUI().getNavigator().navigateTo("Contestants");
					} else {
						Notification.show("This contestant is already in the startlist!",Notification.Type.ERROR_MESSAGE);
					}
				} else Notification.show("One or more fields contains invalid values.",Notification.Type.ERROR_MESSAGE);
			}
		});
		
		deleteContestant.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(contestantBib.isValid()){
					Integer bib = Integer.parseInt(contestantBib.getValue());
					contestant = new Contestant(bib,"nic","nic","nic");
					
					if(contestantservice.isBibInStartlist(contestant)){	
						Integer index = contestantservice.getIndexOfContestantToRemove(contestant);
						contestantservice.deleteContestant(index);
						//Integer sprawdzam = contestantservice.deleteContestant(index);
						//Notification.show(" "+sprawdzam,Notification.Type.ERROR_MESSAGE);
						contestantName.clear();
						contestantSurname.clear();
						contestantBib.clear();
						contestantNation.clear();
						contestantName.focus();
						Notification.show("Contestants with bib "+bib+" was removed!",Notification.Type.WARNING_MESSAGE);
						getUI().getNavigator().navigateTo("Contestants");
					} else {
						Notification.show("This bib is not in the startlist!",Notification.Type.ERROR_MESSAGE);
					}
				} else Notification.show("BIB field contains invalid value.",Notification.Type.ERROR_MESSAGE);
			}
		});
		
		updateContestant.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(contestantBib.isValid() && contestantName.isValid() && contestantSurname.isValid() && contestantNation.isValid()){
					Integer bib = Integer.parseInt(contestantBib.getValue());
					contestant = new Contestant(bib,"nic","nic","nic");
					
					if(contestantservice.isBibInStartlist(contestant)){	
						Integer index = contestantservice.getIndexOfContestantToRemove(contestant);
						contestant = new Contestant(bib,contestantName.getValue(),contestantSurname.getValue(),contestantNation.getValue());
						contestantservice.updateContestant(contestant,index);
						//Integer sprawdzam = contestantservice.deleteContestant(index);
						//Notification.show(" "+sprawdzam,Notification.Type.ERROR_MESSAGE);
						contestantName.clear();
						contestantSurname.clear();
						contestantBib.clear();
						contestantNation.clear();
						contestantName.focus();
						Notification.show("Contestants with bib "+bib+" was updated!",Notification.Type.WARNING_MESSAGE);
						getUI().getNavigator().navigateTo("Contestants");
					} else {
						Notification.show("This bib is not in the startlist!",Notification.Type.ERROR_MESSAGE);
					}
				} else Notification.show("One or more fields contains invalid values.",Notification.Type.ERROR_MESSAGE);
			}
		});
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
