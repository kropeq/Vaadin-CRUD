package vaadin.services;

import java.util.ArrayList;
import java.util.List;

import vaadin.models.Contestant;

public class ContestantService {
	private static List<Contestant> listOfContestants= new ArrayList<Contestant>();
	
	public long addContestant(Contestant contestant) {
		listOfContestants.add(contestant);
		return 1;
	}
	
	public long deleteContestant(Contestant contestant) {
		listOfContestants.remove(listOfContestants.indexOf(contestant));
		return 1;
	}
	
	public List<Contestant> getContestants() {
		return listOfContestants;
	}
	
	public boolean isAlreadyInStartlist(Contestant contestant) {
		for(Contestant c : getContestants()) {
			if (c.getContestantName().equals(contestant.getContestantName()) && 
				c.getContestantSurname().equals(contestant.getContestantSurname())) {
				return true;
			}
		}
		return false;
	}
}
