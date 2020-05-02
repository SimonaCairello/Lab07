package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		Nerc nerc = new Nerc(3, "HI");
		//System.out.println(model.getNercList());
		
		// HECO, MAAC, HI, MAAP, ECAR, NPCC; max year<=5, maxHours<=300
		System.out.println(model.trovaSequenza(nerc, 3, 150));

	}

}
