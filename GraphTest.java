import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
public class GraphTest {

    @Test
    public void testGraph()
    {
        Graph sureThisWorks = new Graph(false);
        assertThat(sureThisWorks, is(notNullValue()));
    }

    @Test
    public void testAddingSingleVertex()
    {
        Graph<Character> addSingleVertex = new Graph(false);
        addSingleVertex.addVertex('c');
        assertThat(addSingleVertex.getVertices().size(), is(equalTo(1)));
    }

    @Test
    public void testAddingSeveralVertices()
    {
        Graph<Character> addVertices = new Graph(false);
        char currentChar = 'a';
        int numberOfVerticesToAdd = 64;
        int currentVerticesCount = 0;
        for (int i = 0; i < 64; i++)
        {
            addVertices.addVertex(currentChar++);
            assertThat(currentVerticesCount, is(lessThan(addVertices.getVertices().size())));
            currentVerticesCount = addVertices.getVertices().size();
        }

    }

    @Test
    public void testAddingThenRemovingVertexUndirected() {
        Graph<Character> addAndRemove = new Graph(false);
        char charToAdd = 'a';
        int numVertices = 4;
        for (int i = 0; i < numVertices; i++) {
            addAndRemove.addVertex(charToAdd++);

        }

        for (int j = 0; j < numVertices; j++)
        {
            int currentSize = addAndRemove.getVertices().size();

            addAndRemove.removeVertex(--charToAdd);
            assertThat(currentSize - 1, is(equalTo(addAndRemove.getVertices().size())));
            assertFalse(addAndRemove.getVertices().contains(charToAdd));
        }
    }


    @Test
    public void testAddingThenRemovingVertexDirected() {
        Graph<Character> addAndRemove = new Graph(true);
        char charToAdd = 'a';
        int numVertices = 4;
        for (int i = 0; i < numVertices; i++) {
            addAndRemove.addVertex(charToAdd++);

        }

        for (int j = 0; j < numVertices; j++)
        {
            int currentSize = addAndRemove.getVertices().size();

            addAndRemove.removeVertex(--charToAdd);
            assertThat(currentSize - 1, is(equalTo(addAndRemove.getVertices().size())));
            assertFalse(addAndRemove.getVertices().contains(charToAdd));
        }
    }


    @Test
    public void testAddingThenRemovingConnexctedVerticesUndirected()
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
        for (int j = 0; j < numVertices/2; j++)
        {
            currentChar = initialChar;
            currentChar++;
            for (Character c : addRemoveConnected.getVertices()) {
                if (c != initialChar) {
                    addRemoveConnected.addEdge(initialChar, c, 1);

                    assertTrue(addRemoveConnected.edgeExists(initialChar, c));
                    assertTrue(addRemoveConnected.edgeExists(c, initialChar));
                }
            }
            initialChar++;
        }

        initialChar = 'a';
        for (int i = 0; i < numVertices/2; i++)
        {
            for (Character destination : addRemoveConnected.getVertices())
            {
                if (addRemoveConnected.edgeExists(initialChar, destination))
                {

                    assertTrue(addRemoveConnected.edgeExists(initialChar, destination));
                    assertThat(addRemoveConnected.getEdgeWeight(initialChar, destination),
                            is(equalTo(addRemoveConnected.getEdgeWeight(destination, initialChar))));

                    addRemoveConnected.removeEdge(initialChar, destination);
                    assertFalse(addRemoveConnected.edgeExists(initialChar, destination));
                    assertFalse(addRemoveConnected.edgeExists(destination, initialChar));

                }
            }

            initialChar++;

        }
    }

}
