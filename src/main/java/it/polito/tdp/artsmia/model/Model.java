package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	//Passo 1: creare il grafo e la lista nei vertici e inizializzarli
	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private List<ArtObject> allNodes;
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.allNodes = new ArrayList<>();
		this.dao = new ArtsmiaDAO();
		this.idMap = new HashMap<>();
	}
	//Lo metto in privato perchè è una funziona che viene chiamata
	//una volta per tutto il programma, devo inizializzare il dao prima
	private void loadNodes() {
		if (this.allNodes.isEmpty())
			this.allNodes= this.dao.listObjects();
		if (this.idMap.isEmpty()) {
			for (ArtObject aTemp : this.allNodes) {
				idMap.put(aTemp.getId(), aTemp);
			}
		}
	}
	
	//Passo 2: creare un metodo per costruire il grafo
	public void buildGrafo() {
		this.loadNodes();
		
		Graphs.addAllVertices(graph, allNodes);
		//Da fare solo se il database è piccolo 
//		for(ArtObject a1: allNodes) {
//			for (ArtObject a2 : allNodes) {
//				
//				int peso = this.dao.getWeight(a1.getId(), a2.getId());
//				Graphs.addEdgeWithVertices(this.graph, a1, a2, peso);
//				
//			}
//		}
		List<EdgeModel> allEdges = this.dao.getAllWeight(idMap);
		for (EdgeModel eTemp : allEdges) {
			Graphs.addEdgeWithVertices(this.graph, eTemp.getSource(), eTemp.getTarget(), eTemp.getPeso());

		}
		System.out.println("This graph contains "+ this.graph.vertexSet().size()+" nodes");
		System.out.println("This graph contains "+ this.graph.edgeSet().size()+" edges");
	}
	public boolean isBuild() {
		return (this.allNodes.size()>0);
	}
	
	public boolean isIdInGraph(Integer objId) {
		if (this.idMap.get(objId)!= null) {
			return true;
		}else {
			return false;
		}
	}
	
	public Integer calcolaConnessa (Integer objID) {
		DepthFirstIterator<ArtObject, DefaultWeightedEdge> iterator = 
				new DepthFirstIterator<>(this.graph, this.idMap.get(objID));
		//1 metodo
		List<ArtObject> compConness = new ArrayList<>();
		while( iterator.hasNext()) {
			compConness.add(iterator.next());
		}
		//2 metodo
		ConnectivityInspector<ArtObject, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
		Set<ArtObject> setConnesso =  inspector.connectedSetOf(this.idMap.get(objID));
	
		return setConnesso.size();
	}
	
	
}
