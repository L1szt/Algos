import java.util.*;
class isBipartite {
    public boolean isBipartite(int g[][]) {
        int V = g.length;
        int color[] = new int[V];  // red -> 0, blue -> 1
        boolean visited[] = new boolean[V]; 
        for (int i =0; i < V; i++) {  // graph might be disconnected
            if (!visited[i]) if (!dfs(i, g, visited, color, 1)) return false;
        }
        return true;
    }
    public boolean dfs(int v, int g[][], boolean visited[], int color[], int cur) {
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
