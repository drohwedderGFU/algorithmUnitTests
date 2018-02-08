import org.junit.*;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DaniGraphTest {
    
    private Graph<String> _undirectedGraph;
    private Graph<String> _directedGraph;
    private final int TO_STORE = 5;
    private final int LEGAL_WEIGHT = 1, ILLEGAL_WEIGHT = -1;
    private final String ILLEGAL_VERTEX = "Doesn't", ANOTHER_ILLEGAL_VERTEX = "Exist";
    private final String[] VERTICES = {"A", "B", "C", "D", "E"};
    private List<String> manualList;
    private List<String> generatedList;
    
    
    
    @Before
    public void setup(){
        
        _undirectedGraph = new Graph<String>(false);
        _directedGraph = new Graph<String>(true);
    }
    
    
    @Test
    public void testTest(){
        HashMap<String, Integer> eh = new HashMap<String, Integer>();
        
        eh.put("C", 6);
        
        eh.put("A", 1);
        eh.put("A", 2);
        
        eh.put("B", 5);
        
        eh.put("AA", 10);
        
        Graph<String> a = new Graph<String>(false);
        a.addVertex("A");
        a.addVertex("B");
        a.addVertex("C");
        
        a.addEdge("A", "B", 1);
        a.addEdge("B", "C", 2);
        a.addEdge("C", "A", 3);
        
        System.out.println(a.toString());
        
        
        Set<String> vertices = eh.keySet();
        //String[] moreVertices = (String[])vertices.toArray();
        //List<String> verticesList = Arrays.asList(moreVertices);
        for (String thing : vertices){
            System.out.println(eh.get(thing));
        }
        
    }
    
    /**
     * This populates each graph with 5 vertices
     */
    @Test
    public void populateVertices(){
        try{
            for (int i = 0; i < TO_STORE; i++){
                _undirectedGraph.addVertex(VERTICES[i]);
                _directedGraph.addVertex(VERTICES[i]);
            }
        }
        catch(IllegalArgumentException e){ } //success
    }
    
    
    /**
     * This populates the graph with 5 vertices
     */
    @Test
    public void populateEdges(){
        
        populateVertices();
        
        for (int i = 0; i < TO_STORE - 1; i++){
            _undirectedGraph.addEdge(VERTICES[i], VERTICES[i+1], LEGAL_WEIGHT);
            _directedGraph.addEdge(VERTICES[i], VERTICES[i+1], LEGAL_WEIGHT);
        }
        
        _undirectedGraph.addEdge(VERTICES[TO_STORE-1], VERTICES[0], LEGAL_WEIGHT);
        _directedGraph.addEdge(VERTICES[TO_STORE-1], VERTICES[0], LEGAL_WEIGHT);
        
    }
    
    
    /**
     * Tests the getVertices method by using the addVertex and 
     * removeVertex methods.  
     * This method should operate the same for both a
     * directed graph and an undirected graph
     * @throws Exception
     */
    @Test
    public void testGetVertices() throws Exception{
        
        // add vertices and edges and verify all vertices previously 
        // added are there
        
        populateVertices();
        
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES));
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES));
        
        // remove certain vertices and verify that the vertices were
        // correctly removed
        for (int i = 0; i < TO_STORE; i=i+2){
            _undirectedGraph.removeVertex(VERTICES[i]);
            _directedGraph.removeVertex(VERTICES[i]);
        }
        
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
        
        // add and edge and verify that the vertices have not changed
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
        
        _directedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
        
        // remove the edge and verify that vertices have not changed
        _undirectedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
        
        _directedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[3]));
    }
    
    
    /**
     * Tests the getEdgeWeight method using the addEdge and
     * removeEdge methods.
     * @throws Exception
     */
    @Test
    public void testGetEgdeWeight() throws Exception{
        
        populateVertices();
        
        // verify that getting the weight of an edge that doesn't
        // exist is -1
        assertEquals(_undirectedGraph.getEdgeWeight(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX),-1);
        assertEquals(_directedGraph.getEdgeWeight(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX),-1);
        
        // add an edge and verify the correct weight is returned
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_undirectedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), LEGAL_WEIGHT);
        
        _directedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_directedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), LEGAL_WEIGHT);
        
        // update the edges weight and verify the correct weight is
        // still returned
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_undirectedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), LEGAL_WEIGHT);
        
        _directedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertEquals(_directedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), LEGAL_WEIGHT);
        
        // remove edge and verify that -1 is returned
        _undirectedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertEquals(_undirectedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), -1);
        
        _directedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertEquals(_directedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), -1);
        
        
    }
    
    
    /**
     * This method tests the edgeExists method
     * @throws Exception
     */
    @Test
    public void testEdgeExists() throws Exception{
        
        populateVertices();
        
        // verify that a non existent edge returns false
        assertFalse(_undirectedGraph.edgeExists(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX));
        assertFalse(_directedGraph.edgeExists(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX));
        
        // add an edge and verify that it returns true
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertTrue(_undirectedGraph.edgeExists(VERTICES[1], VERTICES[3]));
        
        _directedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertTrue(_directedGraph.edgeExists(VERTICES[1], VERTICES[3]));
        
        // remove edge and verify that it no longer exists
        _undirectedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertFalse(_undirectedGraph.edgeExists(VERTICES[1], VERTICES[3]));
        
        _directedGraph.removeEdge(VERTICES[1], VERTICES[3]);
        assertFalse(_directedGraph.edgeExists(VERTICES[1], VERTICES[3]));
        
        // add an edge back and verify that in the undirected graph, the 
        // opposite edge also exists
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertTrue(_undirectedGraph.edgeExists(VERTICES[3], VERTICES[1]));
        
        // add the edge again, updating the edge weight, remove the edge and 
        // verify that the edge no longer exists
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[3], 100);
        assertEquals(_undirectedGraph.getEdgeWeight(VERTICES[1], VERTICES[3]), 100);
        
        // add the edge to the directed graph and verify that the opposite 
        // edge does not exist
        _directedGraph.addEdge(VERTICES[1], VERTICES[3], LEGAL_WEIGHT);
        assertFalse(_directedGraph.edgeExists(VERTICES[3], VERTICES[1]));
        
    }
    
    
    /**
     * This tests the addVertex method
     * @throws Exception
     */
    @Test
    public void testAddVertex() throws Exception{
        
        // verify you can't add a null vertex
        try{
            _undirectedGraph.addVertex(null);
            fail("Should throw exception");
        }
        catch(IllegalArgumentException e){ } // success
        
        
        try{
            _directedGraph.addVertex(null);
            fail("Should throw exception");
        }
        catch(IllegalArgumentException e){ } // success
        
        // add a vertex and verify that its the only vertex in the set
        _undirectedGraph.addVertex(VERTICES[0]);
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[0]));
        
        _directedGraph.addVertex(VERTICES[0]);
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[0]));
        
        // add a new vertex, remove the first vertex and verify that the only
        // vertex in the set is the second vertex added
        _undirectedGraph.addVertex(VERTICES[1]);
        _undirectedGraph.removeVertex(VERTICES[0]);
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[1]));
        
        _directedGraph.addVertex(VERTICES[1]);
        _directedGraph.removeVertex(VERTICES[0]);
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[1]));
        
        // add a new edge and an edge between the two and verify that adding
        // an edge doesn't change the listed vertices
        _undirectedGraph.addVertex(VERTICES[2]);
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[2], LEGAL_WEIGHT);
        assertEquals(_undirectedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[2]));
        
        _directedGraph.addVertex(VERTICES[2]);
        _directedGraph.addEdge(VERTICES[1], VERTICES[2], LEGAL_WEIGHT);
        assertEquals(_directedGraph.getVertices(), Arrays.asList(VERTICES[1], VERTICES[2]));
    }
    
    
    /**
     * This tests the remove vertex method using the addVertex, addEdge and 
     * getVertices methods
     * @throws Exception
     */
    @Test
    public void testRemoveVertex() throws Exception{
        
        
        // try to delete a vertex that isn't there
        try{
            _undirectedGraph.removeVertex("Doesn't exist");
            fail("Should throw a NoSuchElementException");
        }
        catch(NoSuchElementException e){ } // success
        
        try{
            _directedGraph.removeVertex("Doesn't exist");
            fail("Should throw a NoSuchElementException");
        }
        catch(NoSuchElementException e){ } // success
        
        
        // add a few vertices, remove one and verify the correct vertices are there
        populateVertices();
        
        _undirectedGraph.removeVertex(VERTICES[2]);
        assertEquals(Arrays.asList(VERTICES[0], VERTICES[1], VERTICES[3], VERTICES[4]), _undirectedGraph.getVertices());
        
        _directedGraph.removeVertex(VERTICES[2]);
        assertEquals(Arrays.asList(VERTICES[0], VERTICES[1], VERTICES[3], VERTICES[4]), _directedGraph.getVertices());
        
        
        // add a few edges connected to one vertex in the directed graph, 
        // delete that vertex and verify that the all edge information 
        // related to that vertex is gone
        _directedGraph.addVertex(VERTICES[2]);
        
        // add 5 edges that connect to the vertices to be
        // removed and 5 random edges
        for (int i = 0; i<TO_STORE && i!=2; i++){
            _directedGraph.addEdge(VERTICES[2], VERTICES[i], LEGAL_WEIGHT);
            _directedGraph.addEdge(VERTICES[i], VERTICES[TO_STORE-1-i], LEGAL_WEIGHT);
        }
        
        _directedGraph.removeVertex(VERTICES[2]);
        
        for (int i = 0; i<TO_STORE && i!=2; i++){
            assertFalse(_directedGraph.edgeExists(VERTICES[2], VERTICES[i]));
            assertTrue(_directedGraph.edgeExists(VERTICES[i], VERTICES[TO_STORE-1-i]));
            
        }
        
        // add a few edges connected to one vertex, delete that vertex and 
        // verify that the all edge information related to that vertex is gone
        _undirectedGraph.addVertex(VERTICES[2]);
        
        // add 5 edges that connect to the vertices to be
        // removed and 5 random edges
        for (int i = 0; i<TO_STORE && i!=2; i++){
            _undirectedGraph.addEdge(VERTICES[2], VERTICES[i], LEGAL_WEIGHT);
            _undirectedGraph.addEdge(VERTICES[i], VERTICES[TO_STORE-1-i], LEGAL_WEIGHT);
        }
        
        _undirectedGraph.removeVertex(VERTICES[2]);
        
        for (int i = 0; i<TO_STORE && i!=2; i++){
            assertFalse(_undirectedGraph.edgeExists(VERTICES[2], VERTICES[i]));
            assertTrue(_undirectedGraph.edgeExists(VERTICES[i], VERTICES[TO_STORE-1-i]));
        }
    }
    
    
    /*
     * This tests the addEdge method
     */
    @Test
    public void testAddEdge() throws Exception{
        
        populateVertices();
        
        // try adding an edge with the same source and destination
        try{
            _undirectedGraph.addEdge(VERTICES[0], VERTICES[0], LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(IllegalArgumentException e){ } // success
        
        try{
            _directedGraph.addEdge(VERTICES[0], VERTICES[0], LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(IllegalArgumentException e){ } // success
        
        
        
        // try adding an edge with a source that doesn't exist
        try{
            _undirectedGraph.addEdge(VERTICES[0], ILLEGAL_VERTEX, LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        // try adding an edge with a source that doesn't exist
        try{
            _directedGraph.addEdge(VERTICES[0], ILLEGAL_VERTEX, LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        
        
        // try adding an edge with a destination that doesn't exist
        try{
            _undirectedGraph.addEdge(ILLEGAL_VERTEX, VERTICES[0], LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        try{
            _undirectedGraph.addEdge(ILLEGAL_VERTEX, VERTICES[0], LEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        
        
        // try adding an edge with a negative weight
        try{
            _undirectedGraph.addEdge(ILLEGAL_VERTEX, VERTICES[0], ILLEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        try{
            _undirectedGraph.addEdge(ILLEGAL_VERTEX, VERTICES[0], ILLEGAL_WEIGHT);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        // add a few edges to the undirected graph and verify that the edges
        // exists both ways
        _undirectedGraph.addEdge(VERTICES[0], VERTICES[1], LEGAL_WEIGHT);
        _undirectedGraph.addEdge(VERTICES[1], VERTICES[2], LEGAL_WEIGHT);
        _undirectedGraph.addEdge(VERTICES[2], VERTICES[3], LEGAL_WEIGHT);
        
        assertTrue(_undirectedGraph.edgeExists(VERTICES[1], VERTICES[0]));
        assertTrue(_undirectedGraph.edgeExists(VERTICES[2], VERTICES[1]));
        assertTrue(_undirectedGraph.edgeExists(VERTICES[3], VERTICES[2]));
        
        
        // add a few edges to the directed graph and verify that the edges
        // only exist going one way
        _directedGraph.addEdge(VERTICES[0], VERTICES[1], LEGAL_WEIGHT);
        _directedGraph.addEdge(VERTICES[1], VERTICES[2], LEGAL_WEIGHT);
        _directedGraph.addEdge(VERTICES[2], VERTICES[3], LEGAL_WEIGHT);
        
        assertFalse(_directedGraph.edgeExists(VERTICES[1], VERTICES[0]));
        assertFalse(_directedGraph.edgeExists(VERTICES[2], VERTICES[1]));
        assertFalse(_directedGraph.edgeExists(VERTICES[3], VERTICES[2]));
    }
    
    
    @Test
    public void testRemoveEdge() throws Exception{
        
        // try to remove and edge between two vertices
        // that don't exist
        try{
            _undirectedGraph.removeEdge(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        try{
            _directedGraph.removeEdge(ILLEGAL_VERTEX, ANOTHER_ILLEGAL_VERTEX);
            fail("Should throw exception");
        }
        catch(NoSuchElementException e){ } // success
        
        populateVertices();
        
        // try to remove an edge between two valid 
        // vertices that do not contain an edge
        try{
            _undirectedGraph.removeEdge(VERTICES[0], VERTICES[1]);
        }
        catch(NoSuchElementException e){ } // success
        
        try{
            _directedGraph.removeEdge(VERTICES[0], VERTICES[1]);
        }
        catch(NoSuchElementException e){ } // success
        
        // add edge, remove them, verify that they 
        // no longer exist in the graph but that the
        // vertices have not changed
        populateEdges();
        
        for (int i = 0; i < TO_STORE - 1; i++){
            _undirectedGraph.removeEdge(VERTICES[i], VERTICES[i+1]);
            _directedGraph.removeEdge(VERTICES[i], VERTICES[i+1]);
        }
        
        _undirectedGraph.removeEdge(VERTICES[TO_STORE-1], VERTICES[0]);
        _directedGraph.removeEdge(VERTICES[TO_STORE-1], VERTICES[0]);
        
        for (int i = 0; i < TO_STORE - 1; i++){
            assertFalse(_undirectedGraph.edgeExists(VERTICES[i], VERTICES[i+1]));
            assertFalse(_directedGraph.edgeExists(VERTICES[i], VERTICES[i+1]));
        }
        
        assertFalse(_undirectedGraph.edgeExists(VERTICES[TO_STORE-1], VERTICES[0]));
        assertFalse(_directedGraph.edgeExists(VERTICES[TO_STORE-1], VERTICES[0]));
        
        // 
        
    }

}