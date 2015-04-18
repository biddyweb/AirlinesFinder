package thebeginnerapp.fr.airlinesfinder.Utils;

/**
 * File created by Kevin on 08/02/2015 for AirlinesFinder
 */
public class Airplane {
	private int id;
	private String name;
	private int categorie;
	private int airRange;
	private float consumption;
	private int speed;
	private int type;
	private int research;
	private int cargo;
	private int classic;
	private String link;
	private int seatMax;
	private int freight;

	public Airplane(int id, String name, int categorie, int airRange, float consumption, int speed, int type, int research, int cargo, int classic, String link, int seatMax, int freight)
	{
		this.id = id;
		this.name = name;
		this.categorie = categorie;
		this.airRange = airRange;
		this.consumption = consumption;
		this.speed = speed;
		this.type = type;
		this.research = research;
		this.cargo = cargo;
		this.classic = classic;
		this.link = link;
		this.seatMax = seatMax;
		this.freight = freight;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getCategorie()
	{
		return categorie;
	}

	public int getAirRange()
	{
		return airRange;
	}

	public float getConsumption()
	{
		return consumption;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getType()
	{
		return type;
	}

	public int getResearch()
	{
		return research;
	}

	public int getCargo()
	{
		return cargo;
	}

	public int getClassic()
	{
		return classic;
	}

	public String getLink()
	{
		return link;
	}

	public int getSeatMax()
	{
		return seatMax;
	}

	public int getFreight()
	{
		return freight;
	}

	@Override
	public String toString()
	{
		return  name + " (Catégorie "+ categorie + ")";
	}
}
