package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Link extends DefaultWeightedEdge{

	//setto una linea come arco pesato
	
	private Linea linea;
	
	public Link() { }
	
	public Link(Linea linea) {
		this.linea = linea;
	}
	
	public void setLinea(Linea linea) {
		this.linea = linea;
	}
}
