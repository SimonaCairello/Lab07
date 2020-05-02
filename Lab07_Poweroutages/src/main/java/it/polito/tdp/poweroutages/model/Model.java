package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	
	private List<PowerOutages> soluzione;
	private List<PowerOutages> powerOutages;
	private int maxYears;
	private int maxHours;
	private int maxPersone;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> getPowerOutagesList(Nerc nerc) {
		return podao.getPowerOutagesList(nerc);
	}
	
	public void ricorsiva(List<PowerOutages> parziale, int livello, List<PowerOutages> powerOutages) {
		if(parziale.size()>2) {
			if(calcolaNumeroCoinvolti(parziale) >= this.maxPersone && this.controlloNumOre(parziale) && this.controlloNumAnni(parziale)) {
				maxPersone = calcolaNumeroCoinvolti(parziale);
				this.soluzione.clear();
				this.soluzione.addAll(parziale);
			}
			
			if(!this.controlloNumOre(parziale) || !this.controlloNumAnni(parziale))
				return;
		}
		
		for(PowerOutages p : powerOutages) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				
				this.ricorsiva(parziale, livello+1, powerOutages);
				
				parziale.remove(p);
			}
		}
	}
	
	public List<PowerOutages> trovaSequenza(Nerc nerc, int maxYears, int maxHours) {
		this.soluzione = new ArrayList<PowerOutages>();
		this.powerOutages = new ArrayList<PowerOutages>(this.getPowerOutagesList(nerc));
		this.maxYears = maxYears;
		this.maxHours = maxHours*60;
		
			
		List<PowerOutages> parzialeVuoto = new ArrayList<PowerOutages>();
			
		ricorsiva(parzialeVuoto, 0, powerOutages); 
		return this.soluzione;
	}
	
	public int calcolaNumeroCoinvolti(List<PowerOutages> parziale) {
		int somma = 0;
		for(PowerOutages p : parziale)
			somma += p.getCustomersAffected();
		
		return somma;
	}
	
	public boolean controlloNumOre(List<PowerOutages> parziale) {
		int d = 0;
		
		for(int i=0; i<parziale.size(); i++) {
			d += (int) Duration.between(parziale.get(i).getDateEventBegan(), parziale.get(i).getDateEventFinished()).toMinutes();
			if(d>maxHours)
				return false;
		}
		return true;
	}
	
	public boolean controlloNumAnni(List<PowerOutages> parziale) {
		int annoRecente = 0;
		int annoVecchio;
		int differenza = 0;
		
		annoVecchio = parziale.get(0).getDateEventFinished().getYear();
		
		for(PowerOutages p : parziale) {
			if(p.getDateEventFinished().getYear()>=annoRecente)
				annoRecente = p.getDateEventFinished().getYear();
			
			if(p.getDateEventFinished().getYear()<=annoVecchio)
				annoVecchio = p.getDateEventFinished().getYear();			
		}
		differenza = annoRecente - annoVecchio;
		
		if(differenza<=maxYears)
			return true;
		return false;		
	}

}
