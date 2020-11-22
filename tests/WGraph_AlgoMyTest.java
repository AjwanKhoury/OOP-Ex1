package ex1.tests;

import org.junit.jupiter.api.Test;

import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoMyTest {
	
	@Test
    void get_Graph() {
		 weighted_graph g = test_graph1();
		 weighted_graph_algorithms ag = new WGraph_Algo();
		 ag.init(g);
		 assertEquals(g, ag.getGraph());
	}

    @Test
    void isConnected() {
    	 weighted_graph g1 = test_graph1();
		 weighted_graph_algorithms ag1 = new WGraph_Algo();
		 ag1.init(g1);
		 assertTrue(ag1.isConnected());
		 
		 weighted_graph g2 = test_graph2();
		 weighted_graph_algorithms ag2 = new WGraph_Algo();
		 ag2.init(g2);
		 assertFalse(ag2.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = test_graph1();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,2);
        assertEquals(d, 3);
        
        d = ag0.shortestPathDist(0,5);
        assertEquals(d, -1);
        
        d = ag0.shortestPathDist(0,4);
        assertEquals(d, 1);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = test_graph1();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,2);
        int[] checkKey = {0, 4, 3, 2};
        int i = 0;
        for(node_info n: sp) {
        	assertEquals(n.getKey(), checkKey[i]);
        	i++;
        }
    }
    
    @Test
    void save_load() {
        weighted_graph g0 = test_graph1();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "test_graph.obj";
        ag0.save(str);
        weighted_graph g1 = test_graph1();
        ag0.load(str);
        ag0.save("second.obj");
        assertEquals(g1,ag0.getGraph());
    }

    private weighted_graph test_graph1() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);

        g0.connect(0,1,1);
        g0.connect(1,2,3);
        g0.connect(2,3,1);
        g0.connect(3,4,1);
        g0.connect(4,0,1);

        return g0;
    }
    
    private weighted_graph test_graph2() {
        weighted_graph g0 = new WGraph_DS();
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);

        return g0;
    }
}