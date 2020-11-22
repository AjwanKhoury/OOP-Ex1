package ex1.tests;

import org.junit.jupiter.api.Test;

import ex1.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;

import java.util.Collection;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSMyTest {

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(1);
        int s = g.nodeSize();
        assertEquals(2,s);
        
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        g.removeNode(5);
        s = g.nodeSize();
        assertEquals(3,s);

    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        int e_size =  g.edgeSize();
        assertEquals(0, e_size);
        
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        e_size =  g.edgeSize();
        assertEquals(0, e_size);
        
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        e_size =  g.edgeSize();
        assertEquals(2, e_size);
        
        g.removeEdge(0,1);
        g.removeEdge(0,2);
        g.removeEdge(0,3);
        e_size =  g.edgeSize();
        assertEquals(0, e_size);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }
    }
    
    @Test
    void getVWithKey() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(3,1,1);
        g.connect(3,2,1);
        Collection<node_info> v1 = g.getV(0);
        Collection<node_info> v2 = g.getV(3);
        assertEquals(v1.size(), v2.size());
    }

    @Test
    void hasEdge() {
    	  weighted_graph g = new WGraph_DS();
          g.addNode(0);
          g.addNode(1);
          g.addNode(2);
          assertFalse(g.hasEdge(0, 1));
          assertFalse(g.hasEdge(5, 4));
          
          g.connect(0,1,1);
          assertTrue(g.hasEdge(0, 1));
          assertFalse(g.hasEdge(0, 2));
          assertFalse(g.hasEdge(1, 2));
          
          g.removeEdge(0, 1);
          assertFalse(g.hasEdge(0, 1));

    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        assertFalse(g.hasEdge(0,1));
        assertFalse(g.hasEdge(0,2));
        assertFalse(g.hasEdge(1,2));
        
        g.connect(0,1,1);
        assertTrue(g.hasEdge(0, 1));
        assertFalse(g.hasEdge(0,2));
        assertFalse(g.hasEdge(1,2));
        
        g.connect(0, 2, 1);
        assertTrue(g.hasEdge(0,1));
        assertTrue(g.hasEdge(0,2));
        assertFalse(g.hasEdge(1,2));
        
        g.connect(1, 2, 1);
        assertTrue(g.hasEdge(0,1));
        assertTrue(g.hasEdge(0,2));
        assertTrue(g.hasEdge(1,2));
    }

    @Test
    void Add_RemoveNode() {
        weighted_graph g = new WGraph_DS();
        assertEquals(0, 0);
        g.addNode(0);
        assertEquals(1, 1);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        assertEquals(4, 4);
      
        g.removeNode(4);
        g.removeNode(0);
        assertEquals(3, 3);
        
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);
        assertEquals(0, 0);
    }
    
    @Test
    void getEdge() {
    	  weighted_graph g = new WGraph_DS();
          g.addNode(0);
          g.addNode(1);
          g.addNode(2);
          assertEquals(-1, g.getEdge(0, 1));
          
          g.connect(0,1,1);
          assertEquals(1, g.getEdge(0, 1));
          assertEquals(-1, g.getEdge(1, 2));
          assertEquals(0, g.getEdge(0, 0));
          
          g.removeEdge(0, 1);
          assertEquals(-1, g.getEdge(0, 1));

    }
    

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        assertFalse(g.hasEdge(0, 1));
        
        g.connect(0,1,1);
        assertTrue(g.hasEdge(0, 1));
        
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(0, 1));

    }
}


