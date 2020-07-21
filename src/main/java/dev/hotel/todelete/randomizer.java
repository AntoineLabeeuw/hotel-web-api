package dev.hotel.todelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randomizer {

	public static void main(String[] args) {
		List<String> l = new ArrayList<>();
		// Personnes déjà passées
		// l.add("Léo");
		// l.add("Robin");
		// l.add("Baptiste");
		// l.add("Antoine");
		l.add("Sana");
		l.add("Gaëlle");
		l.add("Khalil");
		l.add("Alexis");
		l.add("Vokan");
		l.add("Mohamed");
		l.add("Souleymane");
		l.add("Jérémy");
		Random rand = new Random();
		System.out.println(l.get(rand.nextInt(l.size())));

	}

}
