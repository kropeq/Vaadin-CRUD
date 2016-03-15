package vaadin.services;

import vaadin.services.sql.SqlContainerService;

public class ServiceProvider {

	public ContainerService getContainerService(){
		try {
			return new SqlContainerService();
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private final static ServiceProvider INSTANCE = new ServiceProvider();
	
	public static final ServiceProvider getInstance(){
		return INSTANCE;
	}
}
