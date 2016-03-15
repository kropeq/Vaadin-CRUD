package vaadin.services;

import com.vaadin.data.Container;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public interface ContainerService {

	public SQLContainer getUsersContainer();
	
	public SQLContainer getContestantsContainer();
	
	
}
