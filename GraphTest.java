import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class GraphTest
{
    public final int DEFAULT_WEIGHT = 2;


    @Test public void testGraph()
    {
        Graph sureThisWorks = new Graph(false);
        assertThat(sureThisWorks, is(notNullValue()));
    }


    @Test public void testAddingSingleVertex()
    {
        Graph<Character> addSingleVertex = new Graph(false);
        addSingleVertex.addVertex('c');
        assertThat(addSingleVertex.getVertices().size(), is(equalTo(1)));
    }


    @Test public void testAddingSeveralVertices()
    {
        Graph<Character> addVertices = new Graph(false);
        char currentChar = 'a';
        int numberOfVerticesToAdd = 64;
        int currentVerticesCount = 0;
        for (int i = 0; i < 64; i++)
        {
            addVertices.addVertex(currentChar++);
            assertThat(currentVerticesCount,
               is(lessThan(addVertices.getVertices().size())));
            currentVerticesCount = addVertices.getVertices().size();
        }

    }


    @Test public void testAddingThenRemovingVertexUndirected()
    {
        Graph<Character> addAndRemove = new Graph(false);
        char charToAdd = 'a';
        int numVertices = 4;
        for (int i = 0; i < numVertices; i++)
        {
            addAndRemove.addVertex(charToAdd++);

        }

        for (int j = 0; j < numVertices; j++)
        {
            int currentSize = addAndRemove.getVertices().size();

            addAndRemove.removeVertex(--charToAdd);
            assertThat(currentSize - 1,
               is(equalTo(addAndRemove.getVertices().size())));
            assertFalse(addAndRemove.getVertices().contains(charToAdd));
        }
    }


    @Test public void testAddingThenRemovingVertexDirected()
    {
        Graph<Character> addAndRemove = new Graph(true);
        char charToAdd = 'a';
        int numVertices = 4;
        for (int i = 0; i < numVertices; i++)
        {
            addAndRemove.addVertex(charToAdd++);

        }

        for (int j = 0; j < numVertices; j++)
        {
            int currentSize = addAndRemove.getVertices().size();

            addAndRemove.removeVertex(--charToAdd);
            assertThat(currentSize - 1,
               is(equalTo(addAndRemove.getVertices().size())));
            assertFalse(addAndRemove.getVertices().contains(charToAdd));
        }
    }


    @Test public void testAddingThenRemovingConnexctedVerticesUndirected()
    {
        Graph<Character> addRemoveConnected = new Graph(false);
        char charToAdd = 'a';
        int numVertices = 16;
        for (int i = 0; i < numVertices; i++)
        {
            addRemoveConnected.addVertex(charToAdd++);
        }
        char initialChar = 'a';
        char currentChar = initialChar;
        for (int j = 0; j < numVertices / 2; j++)
        {
            currentChar = initialChar;
            currentChar++;
            for (Character c : addRemoveConnected.getVertices())
            {
                if (c != initialChar)
                {
                    addRemoveConnected.addEdge(initialChar, c, DEFAULT_WEIGHT);

                    assertTrue(addRemoveConnected.edgeExists(initialChar, c));
                    assertTrue(addRemoveConnected.edgeExists(c, initialChar));
                }
            }
            initialChar++;
        }

        initialChar = 'a';
        for (int i = 0; i < numVertices / 2; i++)
        {
            for (Character destination : addRemoveConnected.getVertices())
            {
                if (addRemoveConnected.edgeExists(initialChar, destination))
                {

                    assertTrue(
                       addRemoveConnected.edgeExists(initialChar, destination));
                    assertThat(addRemoveConnected
                       .getEdgeWeight(initialChar, destination), is(equalTo(
                       addRemoveConnected
                          .getEdgeWeight(destination, initialChar))));

                    addRemoveConnected.removeEdge(initialChar, destination);
                    assertFalse(
                       addRemoveConnected.edgeExists(initialChar, destination));
                    assertFalse(
                       addRemoveConnected.edgeExists(destination, initialChar));

                }
            }

            initialChar++;

        }
    }


    @Test public void testRemovingEdgesOnDirectedGraph()
    {
        Graph<Integer> diGraph = new Graph(true);
        int FIRST_VERTEX = 0;
        int NUM_VERTICES = 16;

        // create all vertices
        for (int i = FIRST_VERTEX; i < NUM_VERTICES + FIRST_VERTEX; i++)
        {
            diGraph.addVertex(i);
        }

        // connect entire graph
        for (int j = FIRST_VERTEX; j < NUM_VERTICES + FIRST_VERTEX; j++)
        {
            for (int k = FIRST_VERTEX; k < NUM_VERTICES + FIRST_VERTEX; k++)
            {
                if (k != j)
                {
                    diGraph.addEdge(j, k, DEFAULT_WEIGHT);
                    assertTrue(diGraph.edgeExists(j, k));
                }
            }
        }

        // disconnect first vertex

        for (Integer vertex : diGraph.getVertices())
        {
            if (vertex != FIRST_VERTEX)
            {
                diGraph.removeEdge(FIRST_VERTEX, vertex);
                // ensure outgoing edge is removed
                assertFalse(diGraph.edgeExists(FIRST_VERTEX, vertex));
                // ensure incoming edge is retained
                assertTrue(diGraph.edgeExists(vertex, FIRST_VERTEX));
                assertThat(diGraph.getEdgeWeight(FIRST_VERTEX, vertex),
                   is(equalTo(-1)));
                assertThat(diGraph.getEdgeWeight(vertex, FIRST_VERTEX),
                   is(equalTo(DEFAULT_WEIGHT)));
            }
        }

    }

}
