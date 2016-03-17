package vaadin.services;

import java.util.ArrayList;
import java.util.List;

import vaadin.models.Contestant;

public class ContestantService {
	private static List<Contestant> listOfContestants= new ArrayList<Contestant>();
	private Contestant contestant;
	
	public long addContestant(Contestant contestant) {
		listOfContestants.add(contestant);
		return 1;
	}
	
	
	public boolean isBibInStartlist(Contestant contestant){
		for(Contestant c : getContestants()) {
			if (c.getContestantBib().equals(contestant.getContestantBib())) {
				return true;
			}
		} return false;
	}
	
	public Integer getIndexOfContestantToRemove(Contestant contestant){
		for(Contestant c : getContestants()) {
			if (c.getContestantBib().equals(contestant.getContestantBib())) {
				return listOfContestants.indexOf(c);
			}
		} return 0;
	}
	
	public Integer deleteContestant(Integer index) {	
		listOfContestants.remove((int)index);
		return index;
	}
	
	public List<Contestant> getContestants() {
		return listOfContestants;
	}
	
	
	public boolean isAlreadyInStartlist(Contestant contestant) {
		for(Contestant c : getContestants()) {
			if ((c.getContestantName().equals(contestant.getContestantName()) && 
				c.getContestantSurname().equals(contestant.getContestantSurname())) || 
				c.getContestantBib().equals(contestant.getContestantBib())	) {
				return true;
			}
		}
		return false;
	}
}
