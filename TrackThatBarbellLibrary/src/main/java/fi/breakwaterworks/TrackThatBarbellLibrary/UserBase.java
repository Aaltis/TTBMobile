package fi.breakwaterworks.TrackThatBarbellLibrary;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserBase {
	
	protected Long userId;
	protected String name;
	protected Boolean enabled;
	protected String email;
    protected Set<WorkLogBase> worklogs;
    protected Date lastPasswordReset;
	
	public UserBase(){		
	}
	
	public UserBase(String name,  String email) {
		super();
		this.name = name;
		this.email = email;
        this.enabled=true;
        this.worklogs=new HashSet<WorkLogBase>();
	}


    
   

}
