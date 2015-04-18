package thebeginnerapp.fr.airlinesfinder.Utils;

/**
 * File created by Kevin on 09/02/2015 for AirlinesFinder
 */
public class Country {
	private int id;
	private String name;
	private String bigramme;
	private int continent;

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getBigramme()
	{
		return bigramme;
	}

	public int getContinent()
	{
		return continent;
	}

	public Country(int id, String name, String bigramme, int continent)
	{
		this.id = id;
		this.name = name;
		this.bigramme = bigramme;
		this.continent = continent;
	}
}
