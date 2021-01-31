import java.util.*;
class isBipartite {
    public boolean isBipartite(int[][] graph) {
        int V = graph.length;
        int color[] = new int[V];  // 1 - red, 0 - blue
        boolean visited[] = new boolean[V];  // keep track of the already visited vertices
        for (int i =0; i < V; i++) {
            if (!visited[i]) if (!dfs(i, graph, visited, color, 1)) return false;
        }
        return true;
    }
    public boolean dfs(int v, int[][] g, boolean visited[], int color[], int cur) {
        if (visited[v]) {
            if (color[v] != cur) return false;
            else return true;
        }
        visited[v] = true;
        color[v] = cur;
        for (int next : g[v]) {
            if (!dfs(next, g, visited, color, (cur+1)%2)) return false;
        }
        return true;
    }
}
