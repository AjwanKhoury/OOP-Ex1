package ex1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ex1.WGraph_DS.Noode;


public class WGraph_Algo implements weighted_graph_algorithms  {

	private weighted_graph g;

	/**
	 * Default constructor.
	 */
	public WGraph_Algo() {
		g = null;
	}

	/*
	 *return the weighted graph g
	 */
	@Override
	public void init(weighted_graph g) {
		this.g = g;
	}

	/*
	 * return the underlying graph
	 */
	@Override
	public weighted_graph getGraph() {
		return this.g;
	}

	/*
	 * Deep copy of the weighted graph
	 */
	@Override
	public weighted_graph copy() {
		return new WGraph_DS(getGraph());
	}

	/*
	 * we check if the graph null or the size of the edge is small than 2
	 * so we return true! then we loop over all the edges and tag them 0
	 * then we build a list of node_info and tag the first 1 ; and start loop
	 * over to add the neighbour and change there tag! in the last if there's one node that the tag of it
	 * Different than 2 so return false! 
	 */
	@Override
	public boolean isConnected() {
		if(g == null || g.getV().size() < 2)
			return true;

		for (node_info n: g.getV()) {
			n.setTag(0);
		}

		node_info n = g.getV().iterator().next();
		List<node_info> s= new ArrayList<node_info>();
		s.add(n);
		n.setTag(1);
		while(!s.isEmpty()) {
			n = s.get(0);
			Collection<node_info> help  = ((Noode)n).getNi();
			for(node_info t : help) {
				if(t.getTag() == 0) {
					s.add(t);
					t.setTag(1);
				}		
			}
			n.setTag(2);
			s.remove(n);
		}

		for(node_info t : g.getV())
			if(t.getTag() != 2)
				return false;

		return true;
	}

	/*
	 * we build a queue of node_info and add n1 {iterator} to it then tag it 0;
	 * and start loop over its neighbours and change the tag of every node by the tag for the node+
	 * the tag of it's edge !
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(g == null || g.nodeSize() < 2)
			return -1;

		node_info n1 = g.getNode(src);
		node_info n2 = g.getNode(dest);
		if (g.getNode(src) == null || g.getNode(dest) == null) 
			return -1;

		if (src == dest) 
			return 0;

		int max = Integer.MAX_VALUE;
		for (node_info n: g.getV()) 
			n.setTag(max);


		Queue<node_info> q = new LinkedList<>();
		q.add(n1);
		n1.setTag(0);

		while(!q.isEmpty()) {
			node_info n = q.poll();
			for(node_info t : ((Noode) n).getNi()) {
				double w = n.getTag() + ((Noode) n).getEdge(t.getKey());
				if(t.getTag() > w) {
					if(!q.equals(t))
						q.add(t);
					t.setTag(w);
				}		
			}
		}
		return n2.getTag();
	}

	/*
	 * its the same as shortestPathDist but here we save the check if the dist is 
	 * shorteset// longer! we take the shorteset one!
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		List<node_info> ans = new ArrayList<node_info>();
		HashMap<Integer, node_info> pre = new HashMap<>();

		if(g == null || g.getV().size() < 2)
			return null;

		node_info n1 = g.getNode(src);
		node_info n2 = g.getNode(dest);
		if (g.getNode(src) == null || g.getNode(dest) == null) 
			return null;

		if (src == dest) {
			ans.add(n1);
			return ans;
		}

		int max = Integer.MAX_VALUE;
		for (node_info n: g.getV()) 
			n.setTag(max);


		Queue<node_info> q = new LinkedList<>();
		q.add(n1);
		n1.setTag(0);
		pre.put(n1.getKey(), null);

		while(!q.isEmpty()) {
			node_info n = q.poll();
			for(node_info t : ((Noode) n).getNi()) {
				double w = n.getTag() + ((Noode) n).getEdge(t.getKey());
				if(t.getTag() > w) {
					if(!q.equals(t)) 
						q.add(t);
					pre.put(t.getKey(), n);
					t.setTag(w);
				}		
			}
		}

		if(n2.getTag() == max)
			return null;

		node_info n = n2;
		while(n != null) {
			ans.add(0, n);
			n = pre.get(n.getKey());
		}

		return ans;
	}


	public boolean save(String file) {
		try {
			FileWriter fw = new FileWriter(file, false);  
			PrintWriter outs = new PrintWriter(fw);
			outs.println(g.nodeSize());
			outs.println(g.edgeSize());
			outs.println(g.getMC());

			for(node_info n : g.getV()) {
				outs.print(n.getKey() + "," + n.getTag() + "," + n.getInfo());
				for(node_info t : ((Noode)n).getNi()) {
					outs.print("," + t.getKey() + "," + ((Noode)n).getEdge(t.getKey()));
				}
				outs.println();
			}
			outs.close(); 
			fw.close();

		} catch (Exception e) {
			System.out.println("cann't write!!");
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		try {
			FileReader fr = new FileReader(file); 
			BufferedReader br = new BufferedReader(fr);

			String str;
			g = new WGraph_DS();

			str = br.readLine();
			int nodenum = Integer.parseInt(str);
			str = br.readLine();
			int edgenum = Integer.parseInt(str);
			str = br.readLine();
			int mc = Integer.parseInt(str);
			str = br.readLine();

			while( str != null) {
				String[] helper = str.split(",");
				int key = Integer.parseInt(helper[0]);
				g.addNode(key);
				g.getNode(key).setTag(Double.parseDouble(helper[1]));
				g.getNode(key).setInfo(helper[2]);

				for(int i = 3 ; i < helper.length-1 ; i += 2) {
					g.connect(key, Integer.parseInt(helper[i]), Double.parseDouble(helper[i+1]));
				}		
				str = br.readLine();

				br.close();     
				fr.close(); 

			}
		}
		catch(IOException ex) {  
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
			return false;
		}
		return true ;
	}
}



