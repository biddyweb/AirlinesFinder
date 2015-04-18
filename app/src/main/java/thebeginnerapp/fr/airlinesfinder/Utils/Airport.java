package thebeginnerapp.fr.airlinesfinder.Utils;

/**
 * File created by Kevin on 09/02/2015 for AirlinesFinder
 */
public class Airport {
	private int id;
	private int idCountry;
	private String name;
	private String city;
	private String trigramme;
	private double latRad;
	private int latSign;
	private double longRad;
	private int longSign;
	private int categorie;

	public void setLatRad(double latRad)
	{
		this.latRad = latRad;
	}

	public void setLongRad(double longRad)
	{
		this.longRad = longRad;
	}

	@Override
	public String toString()
	{
		return trigramme +  " " +  name + city ;

	}

	public int getId()
	{
		return id;
	}

	public int getIdCountry()
	{
		return idCountry;
	}

	public String getName()
	{
		return name;
	}

	public String getCity()
	{
		return city;
	}

	public String getTrigramme()
	{
		return trigramme;
	}

	public double getLatRad()
	{
		return latRad;
	}

	public int getLatSign()
	{
		return latSign;
	}

	public double getLongRad()
	{
		return longRad;
	}

	public int getLongSign()
	{
		return longSign;
	}

	public int getCategorie()
	{
		return categorie;
	}

	public Airport(int id, int idCountry, String name, String city, String trigramme, double latRad, int latSign, double longRad, int longSign, int categorie)
	{

		this.id = id;
		this.idCountry = idCountry;
		this.name = name;
		this.city = city;
		this.trigramme = trigramme;
		this.latRad = latRad;
		this.latSign = latSign;
		this.longRad = longRad;
		this.longSign = longSign;
		this.categorie = categorie;
	}
}
