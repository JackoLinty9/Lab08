package it.polito.tdp.extflightdelays.model;

public class Collegamento { 
	
	Airport partenza;
	Airport arrivo;
	double peso;
	
	public Collegamento(Airport partenza, Airport arrivo, double peso) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.peso = peso;
	}

	public Airport getPartenza() {
		return partenza;
	}

	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}

	public Airport getArrivo() {
		return arrivo;
	}

	public void setArrivo(Airport arrivo) {
		this.arrivo = arrivo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public String toString() {
		return partenza.getAirportName() + " - " + arrivo.getAirportName() + " : " + peso;
	}
}
