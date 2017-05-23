package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private Map<Integer, Linea> linee;
	private Map<Integer, Fermata> fermate;
	private List<Connessione> connessioni;
	
	private MetroDAO dao ;
	private WeightedMultigraph<Fermata, Link> grafo = null;
	
	private List<Link> shortestPathEdgeList = null;
	private double shortestPathTempoTot = -1;
	
	public Model() {
		this.dao = new MetroDAO();
	}
	
	public List<Fermata> getStazioni() {
		if(fermate == null){
			fermate = dao.getAllFermate();
		}
		
		return new ArrayList<Fermata>(fermate.values());
	}
	
	public void creaGrafo() {
		fermate = dao.getAllFermate();
		linee = dao.getAllLinee();
		connessioni = dao.getAllConnessioni(fermate, linee);
		
		//inizializzo il grafo
		grafo = new WeightedMultigraph<Fermata, Link>(Link.class);
		
		//aggiungo tutte le fermate come vertici
		Graphs.addAllVertices(grafo, fermate.values());
		
		//per ogni connessione calcolo il tempo e associo la linea a un arco 
		// il peso dell'arco è dato dal tempo di percorrimento, quindi per ogni connessione calcolo il tempo
		
		for(Connessione c: connessioni) {
			double velocita = c.getLinea().getVelocita();
			double distanza = LatLngTool.distance(c.getStazP().getCoords(), c.getStazA().getCoords(), LengthUnit.KILOMETER);
			double tempo = (distanza/velocita) * 60 * 60;
			
			Link link = grafo.addEdge(c.getStazP(), c.getStazA());
			
			if(link!=null) {
				grafo.setEdgeWeight(link, tempo);
				link.setLinea(c.getLinea());
			}
		}
		
		System.out.println("Grafo creato: " + grafo.vertexSet().size() + " nodi, " + grafo.edgeSet().size() + " archi");
		
	}
	
	public void calcolaPercorso(Fermata partenza, Fermata arrivo) {
		DijkstraShortestPath<Fermata, Link> diwhaat = new DijkstraShortestPath<Fermata, Link>(grafo, partenza, arrivo);
		
		shortestPathEdgeList = diwhaat.getPathEdgeList();
		shortestPathTempoTot = diwhaat.getPathLength();
		
		if(shortestPathEdgeList == null) 
			throw new RuntimeException("Impossibile creare un percorso");
		
		if(shortestPathEdgeList.size()-1 > 0) {
			shortestPathTempoTot += (shortestPathEdgeList.size()-1)*30;
		}
}
	
	public String getPercorsoEdgeList() {
		if(shortestPathEdgeList==null)
			throw new RuntimeException("Non e' stato creato alcun percorso.");
		
		StringBuilder risultato = new StringBuilder();
		risultato.append("Percorso: [");
		
		for (Link link : shortestPathEdgeList) {
			risultato.append(grafo.getEdgeTarget(link).getNome());
			risultato.append(", ");
		}
		risultato.setLength(risultato.length() - 2);
		risultato.append("]");

		return risultato.toString();
	}
	
	public double getPercorsoTempoTotale() {
		if (shortestPathEdgeList == null)
			throw new RuntimeException("Non è stato creato alcun percorso.");

		return shortestPathTempoTot;
	}
	
	

}
