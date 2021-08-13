package fi.breakwaterworks.TrackThatBarbellLibrary;

public class Privilege {


	protected Long id;
	protected String name;
	public Privilege(){
	}
	public Privilege(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}