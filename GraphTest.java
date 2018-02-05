import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
               assertThat(
                  addRemoveConnected.getEdgeWeight(initialChar, destination),
                  is(equalTo(addRemoveConnected
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


   @Test public void testRemovingVertexWithEdgesUndirected()
   {
      int EDGE_WEIGHT = 20;
      Graph<Integer> diGraph = new Graph<Integer>(false);

      int numberOfVertices = 8;

      for (int i = 0; i < numberOfVertices; i++)
      {
         diGraph.addVertex(i);
      }

      for (int j = 0; j < numberOfVertices; j++)
      {
         for (Integer destination : diGraph.getVertices())
         {
            if (j != destination && !diGraph.edgeExists(j, destination))
            {
               diGraph.addEdge(j, destination, EDGE_WEIGHT);
            }
         }
      }

      diGraph.removeVertex(0);
      assertFalse(diGraph.getVertices().contains(0));
      assertTrue(diGraph.getVertices().size() == numberOfVertices - 1);
      for (Integer source : diGraph.getVertices())
      {
         for (Integer destination : diGraph.getVertices())
         {
            if (source != destination)
            {
               assertTrue(diGraph.edgeExists(source, destination));
               assertTrue(
                  diGraph.getEdgeWeight(source, destination) == EDGE_WEIGHT);
            }
         }
      }

   }


   @Test public void testRemovingVertexWithEdgesDirected()
   {
      int EDGE_WEIGHT = 20;
      Graph<Integer> graph = new Graph<Integer>(true);

      int numberOfVertices = 8;

      for (int i = 0; i < numberOfVertices; i++)
      {
         graph.addVertex(i);
      }

      for (int j = 0; j < numberOfVertices; j++)
      {
         for (Integer destination : graph.getVertices())
         {
            if (j != destination && !graph.edgeExists(j, destination))
            {
               graph.addEdge(j, destination, EDGE_WEIGHT);
            }
         }
      }

      graph.removeVertex(0);
      assertFalse(graph.getVertices().contains(0));
      assertTrue(graph.getVertices().size() == numberOfVertices - 1);
      for (Integer source : graph.getVertices())
      {
         for (Integer destination : graph.getVertices())
         {
            if (source != destination)
            {
               assertTrue(graph.edgeExists(source, destination));
               assertTrue(
                  graph.getEdgeWeight(source, destination) == EDGE_WEIGHT);
            }
         }
      }

   }

   @Test
   public void testToStringContainsEdgeWeightsUndirected()
   {
      int initialEdgeWeight = 2;
      int maxEdgeWeight = 5;
      int numberOfVertices = 10;

      Graph<Integer> graph = new Graph<>(false);
      for (int i = 0; i < numberOfVertices; i++)
      {
         graph.addVertex(i);

      }

      int startingVertex = 0;
      graph.addEdge(startingVertex, startingVertex +1, initialEdgeWeight++);
      startingVertex++;
      graph.addEdge(startingVertex, startingVertex +1, initialEdgeWeight++);
      startingVertex++;
      graph.addEdge(startingVertex, startingVertex +1, initialEdgeWeight++);
      startingVertex++;
      graph.addEdge(startingVertex, startingVertex +1, initialEdgeWeight);
      for (int j = startingVertex; j >= 0; j--)
      {
         assertTrue(graph.toString().contains(Integer.toString(initialEdgeWeight--)));
      }
   }

   @Test
   public void testEdgeObjectCompareTo()
   {
      Graph.Edge edge0 = new Graph.Edge("source", "destination", 5);
      Graph.Edge edge1 = new Graph.Edge("source", "destination", 6);

      // Check that the compareTo method is properly implemented
      assertTrue(edge0.compareTo(edge1) < 0);

   }

   @Test
   public void testMinSpanning()
   {
      Graph<Integer> sourceMinSpan = new Graph<Integer>(false);

      int lowerCost = 4;

      int higherCost = 20;

      int numVertices = 100;

      for (int i = 0; i  < numVertices; i++)
      {
         sourceMinSpan.addVertex(i);
      }

      for (int j = 0; j < numVertices; j++)
      {
         sourceMinSpan.addEdge(j, (j +  1) % numVertices, lowerCost);
      }


      for (int mod = 2; mod < 20; mod++)
      {
         for (int j = 0; j < numVertices; j++)
         {
            sourceMinSpan.addEdge(j, (j + mod) % numVertices, higherCost);
         }
      }

      Graph<Integer> minSpanTree = sourceMinSpan.minimumSpanningTree();

      for (Graph.Edge<Integer> edge : minSpanTree.getEdges())
      {
         assertThat(edge.getWeight(), is(equalTo(lowerCost)));
      }

      assertThat(minSpanTree.getEdges().size(), is(equalTo(numVertices - 1)));


   }


   @Test
   public void testGetEdgesDirected()
   {
      Graph<Integer> checkDirectedEdges = new Graph<>(true);

      int numVertices = 8;
      for (int i = 0; i < numVertices; i++)
      {
         checkDirectedEdges.addVertex(i);
      }

      for (int j = 0; j < numVertices; j++)
      {
         checkDirectedEdges.addEdge(j, (j + 1) % numVertices,
            j+1);
         assertTrue(checkDirectedEdges.edgeExists(j, (j + 1) % numVertices));
      }

      List<Graph.Edge<Integer>> edgesDirected = checkDirectedEdges.getEdges();
      assertThat(edgesDirected.size(), is(equalTo(numVertices)));
      for (Graph.Edge<Integer> edge : edgesDirected)
      {
         assertTrue(checkDirectedEdges.edgeExists(edge.getSource(), edge.getDestination()));

         assertThat(checkDirectedEdges.getEdgeWeight(edge.getSource(),
            edge.getDestination()), is(equalTo(edge.getWeight())));

         checkDirectedEdges.removeEdge(edge.getSource(), edge.getDestination());
      }

      assertThat(checkDirectedEdges.getEdges().size(), is(equalTo(0)));




   }


   @Test
   public void testGetEdgesUndirected()
   {
      Graph<Integer> checkDirectedEdges = new Graph<>(false);

      int numVertices = 8;
      for (int i = 0; i < numVertices; i++)
      {
         checkDirectedEdges.addVertex(i);
      }

      for (int j = 0; j < numVertices; j++)
      {
         checkDirectedEdges.addEdge(j, (j + 1) % numVertices,
            j+1);
         assertTrue(checkDirectedEdges.edgeExists(j, (j + 1) % numVertices));
      }

      List<Graph.Edge<Integer>> edgesDirected = checkDirectedEdges.getEdges();
      assertThat(checkDirectedEdges.getEdges().size(), is(equalTo(numVertices)));

      for (Graph.Edge<Integer> edge : edgesDirected)
      {
         assertTrue(checkDirectedEdges.edgeExists(edge.getSource(), edge.getDestination()));

         assertThat(checkDirectedEdges.getEdgeWeight(edge.getSource(),
            edge.getDestination()), is(equalTo(edge.getWeight())));

         checkDirectedEdges.removeEdge(edge.getSource(), edge.getDestination());
      }

      assertThat(checkDirectedEdges.getEdges().size(), is(equalTo(0)));




   }


   @Test
   public void testGetEdgeDirected()
   {
      Graph<Integer> getAnEdge = new Graph<>(true);

      int numVertices = 16;

      for (int i = 0; i < numVertices; i++)
      {
         getAnEdge.addVertex(i);
      }

      for (int j = 0; j < numVertices; j++)
      {
         getAnEdge.addEdge(j, (j + 1) % numVertices, j + 1);
      }
      ArrayList<Graph.Edge<Integer>> edges = new ArrayList<>();
      for (int k = 0; k < numVertices; k++)
      {
         edges.add(getAnEdge.getEdge(k, (k + 1) % numVertices));
         assertThat(edges.get(k), is(notNullValue()));
      }

      assertTrue(getAnEdge.getEdge(1234, 4321) == null);

   }

   @Test public void testGetEdgeUndirected()
   {
      Graph<Integer> getAnEdge = new Graph<>(false);

      int numVertices = 16;

      for (int i = 0; i < numVertices; i++)
      {
         getAnEdge.addVertex(i);
      }

      for (int j = 0; j < numVertices; j++)
      {
         getAnEdge.addEdge(j, (j + 1) % numVertices, j + 1);
      }
      ArrayList<Graph.Edge<Integer>> edges = new ArrayList<>();
      for (int k = 0; k < numVertices; k++)
      {
         edges.add(getAnEdge.getEdge(k, (k + 1) % numVertices));
         assertThat(edges.get(k), is(notNullValue()));
      }

      assertTrue(getAnEdge.getEdge(1234, 4321) == null);

      for (int l = numVertices - 1; l > 0; l--)
      {
         assertThat(getAnEdge.getEdge(l, l - 1), is(notNullValue()));
         assertThat(getAnEdge.getEdgeWeight(l, l - 1),
            is(equalTo(edges.get(l - 1).getWeight())));
      }

   }

}
