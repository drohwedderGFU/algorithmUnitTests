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

}
