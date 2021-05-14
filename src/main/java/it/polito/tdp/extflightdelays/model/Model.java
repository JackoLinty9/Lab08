package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private SimpleWeightedGraph<Airport, DefaultWeightedEdge>grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer,Airport>idMap; //per i vertici è conveniente creare mappa
	                                   //corrispondenza tra chiavi e oggetti
	
	public Model() {
		dao=new ExtFlightDelaysDAO();
		idMap = new HashMap<Integer,Airport>();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int distanza) {
		
		//aggiungo vertici 
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values()); // non ci sono filtri sui vertici
		
		//aggiungo archi -> approcio 3 (dato l'elevato numero di vertici)
		for(Collegamento c:dao.getCollegamenti(idMap, distanza)) {
			
			DefaultWeightedEdge e=this.grafo.getEdge(c.getPartenza(), c.getArrivo());
			if(e==null) { //non c'è ancora arco tra A1 e A2
				Graphs.addEdgeWithVertices(grafo, c.getPartenza(), c.getArrivo(),distanza);
			}else { //c'è già arco tra A1 e A2
				double peso = grafo.getEdgeWeight(e); //estraggo peso corrente
				double newPeso = (peso + c.getPeso())/2; //aggiorno peso
				grafo.setEdgeWeight(e, newPeso); //inserisco nuovo peso
			}
			
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Collegamento> getCollegamento(){
		//uso la classe Rotta per salvare gli archi del grafo con il relativo peso
		List<Collegamento> coll = new ArrayList<Collegamento>();
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			coll.add(new Collegamento(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e)));
		}
		return coll;
	}
	
	

}
