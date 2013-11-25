import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods

    private Collection<Vertex> myVertices;
    private Collection<Edge> myEdges;
    private Map<Vertex, ArrayList<Vertex>> adjacencyList;

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
    	//copy in
    	//copy the vertices as well (deeeeep copy)
    	myVertices = new ArrayList<Vertex>();
    	for(Vertex tempV: v) {
    		myVertices.add(new Vertex(tempV.getLabel(), tempV.known, tempV.distance, tempV.path));
    	}
    	myEdges = new ArrayList<Edge>();
    	for(Edge tempE: e) {
    		myEdges.add(new Edge(tempE.getSource(), tempE.getDestination(), tempE.getWeight()));
    	}
    	
    	adjacencyList = new HashMap<Vertex, ArrayList<Vertex>>();
    	
    	//add to adj list
    	for(Vertex tempV: myVertices) {
    		adjacencyList.put(tempV, new ArrayList<Vertex>());
    	}
    	for(Edge tempE: myEdges) {
    		if (tempE.getWeight() < 0) {
    			throw new IllegalArgumentException("Edge weights cannot be negative.");
    		}
    		adjacencyList.get(tempE.getSource()).add(tempE.getDestination());
    	}
    	

    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
    	Collection<Vertex> vOut = new ArrayList<Vertex>();
    	for(Vertex tempV: myVertices) {
    		vOut.add(new Vertex(tempV.getLabel(), tempV.known, tempV.distance, tempV.path));
    	}
    	return vOut;
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
    	Collection<Edge> eOut = new ArrayList<Edge>();
    	for(Edge tempE: myEdges) {
    		eOut.add(new Edge(tempE.getSource(), tempE.getDestination(), tempE.getWeight()));
    	}
    	return eOut;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
    	if (!myVertices.contains(v)) {
    		throw new IllegalArgumentException("Input vertex does not exist.");
    	}
    	
    	return adjacencyList.get(v);
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
    	if (!myVertices.contains(a) || !myVertices.contains(b)) {
    		throw new IllegalArgumentException("One or both of the input vertices is invalid.");
    	}
    	Edge e = findEdge(a, b);
    	if (e != null) {
    		return e.getWeight();
    	}
    	return -1;
    }

    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {
    	if (!myVertices.contains(a) || !myVertices.contains(b)) {
    		throw new IllegalArgumentException("One or both of the input vertices is invalid.");
    	}
    	
    	if (a.equals(b)) {
    		List<Vertex> l = new ArrayList<Vertex>();
    		l.add(a);
    		return new Path(l, 0);
    	}
    	
    	dijsktra(a);
    	
    	List<Vertex> vertList = new Stack<Vertex>();
    	Vertex temp = b;
    	
    	while(!temp.path.equals(a)) {
    		vertList.add(temp);
    		temp = temp.path;
    	}
    	vertList.add(a);
    	
    	return new Path(vertList, b.distance);
    }
    
    private void dijsktra(Vertex start) {
    	ArrayList<Vertex> vertList = new ArrayList<Vertex>();
    	for(Vertex v: myVertices) {
    		v.distance = Integer.MAX_VALUE;
    		v.known = false;
    		v.path = null;
    		vertList.add(v);
    	}
    	
    	start.distance = 0;
    	
    	while(!vertList.isEmpty()) {
    		Vertex v = smallestDist(vertList);
    		
    		vertList.remove(v);
    		v.known = true;
    		
    		for(Vertex w: adjacencyList.get(v)) {
    			if (!w.known) {
    				int costVW = edgeCost(v,w);
    				
    				if (v.distance + costVW < w.distance) {
    					w.distance = v.distance + costVW;
    					w.path = v;
    				}
    			}
    			
    		}
    	}
    }
    
    private Vertex smallestDist(ArrayList<Vertex> vertList) {
    	int min = Integer.MAX_VALUE;
		Vertex v = null;
    	for(Vertex tempV: vertList) {
			if (tempV.distance < min) {
				min = tempV.distance;
				v = tempV;
			}
		}
    	return v;
    }
    
    //returns the edge object from a to b if exists, otherwise returns null
    private Edge findEdge(Vertex a, Vertex b) {
    	for(Edge e: myEdges) {
    		if (e.getSource().equals(a) && e.getDestination().equals(b)) {
    			return e;
    		}
    	}
    	return null;
    }
    

}
