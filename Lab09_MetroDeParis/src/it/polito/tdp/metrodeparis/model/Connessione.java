package it.polito.tdp.metrodeparis.model;

public class Connessione {
	private int idConn;
	private Linea linea;
	private Fermata stazP;
	private Fermata stazA;
	public Connessione(int idConn, Linea linea, Fermata stazP, Fermata stazA) {
		super();
		this.idConn = idConn;
		this.linea = linea;
		this.stazP = stazP;
		this.stazA = stazA;
	}
	public int getIdConn() {
		return idConn;
	}
	public void setIdConn(int idConn) {
		this.idConn = idConn;
	}
	public Linea getLinea() {
		return linea;
	}
	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	public Fermata getStazP() {
		return stazP;
	}
	public void setStazP(Fermata stazP) {
		this.stazP = stazP;
	}
	public Fermata getStazA() {
		return stazA;
	}
	public void setStazA(Fermata stazA) {
		this.stazA = stazA;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idConn;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (idConn != other.idConn)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "stazP=" + stazP + " -  stazA=" + stazA +"(Linea: "+this.linea+")";
	}
	
	

}
