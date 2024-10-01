package server.Storage;

import server.Model.Player;

import java.util.ArrayList;
import java.util.List;

public class Storage
{
	private static List<Player> liste = new ArrayList<Player>();
	
	public static void add(Player p) {
		liste.add(p);	
	}

	public static void remove(Player p) {
		liste.remove(p);	
	}
	
	public static void clear() {
		liste.clear();
	}
	
	public static int size() {
		return liste.size();
	}

	public static List<Player> getListe() {
		return liste;
	}
}