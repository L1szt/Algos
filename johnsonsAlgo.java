import java.util.*;
import java.io.*;

public class johnsonsAlgo {
	public static void main(String args[]) throws FileNotFoundException {
		Scanner in = new Scanner(new File("Test"));
		int V = in.nextInt();
		int E = in.nextInt();
		System.out.println("|V| = " + V + ", " + "|E| = " + E);
		List<List<Pair>> edges = new ArrayList<>();
		for (int i = 0; i <= V; i++)
			edges.add(new ArrayList<>());
		for (int i = 0; i < E; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			int w = in.nextInt();
			edges.get(from).add(new Pair(to, w));
		}
		johnson(edges, V);
	}
	
	public static List<int[]> johnson(List<List<Pair>> edges, int V) {
		for (int i = 0; i <= V; i++) {  // create an additional vertex which that has directed edges (w = 0) to all vertices in the graph
			edges.get(0).add(new Pair(i, 0));
		}
		int h[] = bellmanFord(0, edges);  // compute distance for reweightening
		if (h == null) {
			System.out.println("Negative cycle found")
			return null;
		}
		List<List<Pair>> newEdges = new ArrayList<>();  // will store the newly reweighted edges
		for (int i =0; i <= V; i++) {
			newEdges.add(new ArrayList<>());
		}
		reweight(edges, newEdges, h);  // reweight all edges
		List<int[]> result = new ArrayList<>();
		result.add(new int[] {});
		for (int i = 1; i <= V; i++) {
			result.add(dijkstra(i, newEdges));
		}	
		// restoring correct length of shortest paths: for all edges (i, j) we do w_new((i, j)) = w((i, j)) - (h[i] - h[j]) = w((i, j)) + h[j] - h[i]
		for (int i = 1; i <= V; i++) {
			int[] cur = result.get(i);
			for (int j = 0; j < cur.length; j++) {
				cur[j] += h[j] - h[i];
			}
		}
		return result;
	}
	
	// reweight all edge weights
	public static void reweight(List<List<Pair>> edges, List<List<Pair>> newEdges, int h[]) {
		int V = edges.size();
		for (int i =1; i < V; i++) {
			List<Pair> cur = edges.get(i);
			for (int j = 0; j< cur.size(); j++) {
				int formerWeight = cur.get(j).weight;
				int newWeight = formerWeight + h[j] - h[cur.get(j).to]; 
				newEdges.get(i).add(new Pair(cur.get(j).to, newWeight));
			}
		}
	}

	// Bellman-Ford algorithm 
	public static int[] bellmanFord(int start, List<List<Pair>> edges) {
		List<Triple> neu = new ArrayList<>();
		int V = edges.size();
		for (int i =0; i < V; i++) {
			for (Pair p : edges.get(i)) {
				neu.add(new Triple(i, p.to, p.weight));
			}
		}
		int dist[] = new int[V];
		for (int i = 0; i < V; i++) {
			if (i == start) dist[i] = 0;
			else dist[i] = Integer.MAX_VALUE;
		}
		for (int i =0; i < V-1; i++) {
			for (Triple edge : neu) {
				int from = edge.from, to = edge.to, w = edge.weight;
				if(dist[from] != Integer.MAX_VALUE && dist[from] + w < dist[to]) dist[to] = dist[from] + w;
			}
		}
		for (Triple edge : neu) {
			if (dist[edge.to] > dist[edge.from] + edge.weight) {
				return null;
			}
		}
		return dist;
	}

	// Dijkstra's algorithm
	public static int[] dijkstra(int start, List<List<Pair>> edges) {
		PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> a.weight - b.weight);
		int dist[] = new int[(edges.size())];
		for (int i = 0; i <= edges.size()-1; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		dist[0] = dist[start] = 0;
		boolean closed[] = new boolean[edges.size()];
		q.add(new Pair(start, 0));
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			if (closed[cur.to])
				continue;
			closed[cur.to] = true;
			for (Pair next : edges.get(cur.to)) {
				if (!closed[next.to] && dist[cur.to] != Integer.MAX_VALUE && dist[next.to] > dist[cur.to] + next.weight) {
					dist[next.to] = dist[cur.to] + next.weight;
					q.add(new Pair(next.to, dist[next.to]));
				}
			}
		}
		return dist;
	}
}

class Pair {
	int to, weight;
	public Pair(int a, int b) {
		to = a;
		weight = b;
	}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) return false;
		Pair g = (Pair)o;
		return g.to == this.to && g.weight == this.weight;
	}
	@Override
	public String toString() {
		return "(" + this.to + ", " + this.weight + ")";
	}
}

class Triple{
	int from, to, weight;
	public Triple(int a, int b, int c) {
		from = a;
		to = b;
		weight = c;
	}
}
