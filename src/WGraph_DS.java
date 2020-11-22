package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class WGraph_DS implements weighted_graph {
	static int id_node = 0;

	private HashMap<Integer, node_info> nodes; // list of the weighted_graph nodes
	int mc = 0; // the number of edges of this graph
	int v = 0; // number of change that happen on this graph - only for test


	/*
	 * Default Constructor of weighted_graph class
	 */
	public WGraph_DS() {
		nodes = new HashMap<>();
	}

	/**
	 * Deep copy constructor of weighted_class
	 */
	public WGraph_DS(weighted_graph graph) {
		nodes = new HashMap<>();
		// Loop over the graph nodes
		if (graph == null) {
			return;
		}
		for (node_info node: graph.getV()) {
			node_info newNode = new Noode(node);
			addNode(newNode);
			for (node_info neighbor: ((Noode) node).getNi()) {
				int nodeKey = node.getKey();
				int neighborKey = neighbor.getKey();
				// Check if keys are not the same and if they are neighbors
				if (nodeKey != neighborKey && ((Noode) neighbor).hasNi(nodeKey)) {
					// Connect the nodes
					connect(nodeKey, neighborKey, ((Noode)node).getEdge(neighborKey));
				}
			}	
		}
		mc = graph.getMC();
	}

	/*
	 * we get the key return the node_info
	 */
	@Override
	public node_info getNode(int key) {
		return nodes.get(key);
	}
	
	/*
	 * return true if and only if there is an edge between the two node_data
	 * Check if node keys are not the same and if the nodes exist
	 * If the nodes are neighbours in two directions return true
	 * else return false
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		Noode nodeData1 = (Noode) nodes.get(node1);
		Noode nodeData2 = (Noode) nodes.get(node2);
		if (node1 != node2 && nodeData1 != null && nodeData2 != null) {
			if (nodeData1.hasNi(node2) && nodeData2.hasNi(node1)) {
				return true;
			}
		}	
		return false;
	}

	/*
	 * Check if node keys are not the same and if the nodes exist
	 * If the nodes are neighbours in two directions return the edge value
	 * else return -1
	 */
	@Override
	public double getEdge(int node1, int node2) {
		Noode nodeData1 = (Noode) nodes.get(node1);
		Noode nodeData2 = (Noode) nodes.get(node2);
		if(nodeData1 == nodeData2 && nodeData1 != null)
			return 0;
		if (node1 != node2 && nodeData1 != null && nodeData2 != null) {
			if (nodeData1.hasNi(node2) && nodeData2.hasNi(node1)) {
				return nodeData1.getEdge(node2);
			}
		}	
		return -1;
	}
	
	/*
	 *check if the node is already exists so there is nothing to do
	 *else we have to add it by the given key 
	 */
	@Override
	public void addNode(int key) {
		if(!this.nodes.containsKey(key)) {
			this.nodes.put(key, new Noode(key));
		}
	}
	
	/*
	 * this method we add a node by the node itself so first we check if 
	 * the node is already exists so there is nothing , else we
	 * add it and add it key
	 */
	public void addNode(node_info node) {
		if(!this.nodes.containsKey(node.getKey())) {
			this.nodes.put(node.getKey(), node);
		}
	}

	/*
	 * to connect 2 nodes and the edge's value so we check if the two nodes
	 * is the same so there is nothing to do;
	 * then if at least there is one of the nodes null also nothing to do;
	 * else we add node1 and node 2 to become ni in the two direction 
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if (node1 == node2)
			return;
		
		node_info n1 = getNode(node1);
		node_info n2 = getNode(node2);
		if((n1 != null) && (n2 != null)) {
			if(!((Noode) n1).hasNi(node2)) {
				((Noode) n1).addNi((Noode) n2 , w);
				((Noode) n2).addNi((Noode) n1 , w);
				v++;
				mc++;
			}
		}
	}

	/*
	 * return a copy for the nodes in a collection
	 */
	@Override
	public Collection<node_info> getV() {
		return this.nodes.values();
	}

	/*
	 * return a copy of the nodes in a collection that connected to
	 * the node id;(the neighbours )
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		Noode n = (Noode) this.nodes.get(node_id);
		return n.getNi();
	}

	/*
	 * we save the neighbours of the node in a list, and save a keys in 
	 * another list, and Loop over the neighbor keys and remove the edge
	 */
	@Override
	public node_info removeNode(int key) {
		Noode n= (Noode) getNode(key);
		if(n != null) {
			Collection<node_info> save =  n.getNi();
			List<Integer> keys = new ArrayList<>();
			for (node_info neighbor: save) {
				keys.add(neighbor.getKey());
			}
			for (int neighborKey: keys) {
				removeEdge(key, neighborKey);
			}

			nodes.remove(key);
			mc++;
		}
		return n;
	}

	/*
	 * to remove an edge between two nodes first we check if
	 *node keys are not the same and if the nodes exist at all
	 *if the two nodes are ni in the two ways then we have to remove it twice
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		Noode nodeData1 = (Noode) nodes.get(node1);
		Noode nodeData2 = (Noode) nodes.get(node2);
		if (node1 != node2 && nodeData1 != null && nodeData2 != null) {
			if (nodeData1.hasNi(node2) && nodeData2.hasNi(node1)) {
				v--;
				mc++;
			}
			if (nodeData1.hasNi(node2)) {
				nodeData1.removeNode(nodeData2);	
			}
			if (nodeData2.hasNi(node1)) {
				nodeData2.removeNode(nodeData1);
			}
		}
	}

	/*
	 * return the verices's size
	 */
	@Override
	public int nodeSize() {
		return this.nodes.size();
	}

	/*
	 * return the edge's number
	 */
	@Override
	public int edgeSize() {
		return v;
	}

	/*
	 * return the mode count of any change 
	 */
	@Override
	public int getMC() {
		return	mc;
	}

	public class Noode implements node_info{

		private int key = 0; // the id of the node
		private HashMap<Integer ,node_info> neighbors = new HashMap<>(); // list of the neighbourhood of this node
		private HashMap<Integer ,Double> wEdge = new HashMap<>(); 
		private String info;
		private double tag = 0;


		/*
		 * Default constructor for node_info class.
		 */
		public Noode() {
			key = id_node++;
		}

		/*
		 *  Constructor for node_info class
		 */
		public Noode(int key) {
			this.key = key;
		}

		/*
		 * Copy constructor for node_info class
		 */
		public Noode(node_info node) {
			key = node.getKey();
			info = node.getInfo();
			tag = node.getTag();
		}
		
		/*
		 * return the key for every node
		 * to remember ; every node have a unique key
		 */
		@Override
		public int getKey() {
			return key;
		}

		/*
		 * return the node's remark
		 */
		@Override
		public String getInfo() {
			return info;
		}
		
		/*
		 * Allows changing node's remark
		 */
		@Override
		public void setInfo(String s) {
			this.info = s;
		}
		
		/*
		 *node temporal data  
		 */
		@Override
		public double getTag() {
			return tag;
		}

		/*
		 * Allows changing node's data 
		 */
		@Override
		public void setTag(double t) {
			this.tag = t;	
		}
		
		/*
		 * returns all the Neighbour nodes of node_info with a collection
		 */
		public Collection<node_info> getNi(){
			return this.neighbors.values();
		}
		
		/*
		 *to add a neighbour between to nodes 
		 * in that we first test if the Noode is null or not! 
		 * if it not null so we add it to the neighbours then we add its Edge value;
		 */
		public void addNi(Noode t , double w) {
			if (t != null) {
				this.neighbors.put(t.getKey(), t);
				this.wEdge.put(t.getKey(), w);
			}	
		}

		/*
		 * This method removes the edge between this-node, first we remove the noode from his neighbors then
		 * we remove its Edge value
		 */
		public void removeNode(node_info node) {	
			this.neighbors.remove(node.getKey());
			this.wEdge.remove(node.getKey());
		}

		/*
		 * return true if and only if there is an edge between the key and this
		 * that mean if it has an neighbour
		 */
		public boolean hasNi(int key) {
			return neighbors.containsKey(key);
		}
		
		/*
		 * return the value value of the edge for this key.
		 */
		public double getEdge(int key) {
			return this.wEdge.get(key);
		}


	}

}
