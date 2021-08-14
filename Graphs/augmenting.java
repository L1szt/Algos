import java.util.*;
import java.io.*;

class Edge {
	int u, v;
	public Edge(int u, int v) {
		this.u=u;
		this.v=v;
	}
	@Override
	public boolean equals(Object e) {
		return ((Edge)e).u==this.u&&((Edge)e).v==this.v;
	}
}

public class augmenting {
	public static void main(String args[]) {
		// We consider a given bipartite graph G=(A+B, E) and a matching M and R:=E\M
		ArrayList<Integer> A=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
		ArrayList<Integer> B=new ArrayList<>(Arrays.asList(5, 6, 7, 8));
		ArrayList<Edge> M=new ArrayList<>(Arrays.asList(new Edge(1, 5), new Edge(2, 6), new Edge(3, 8),
													 new Edge(5, 1), new Edge(6, 2), new Edge(8, 3)));
		ArrayList<Edge> R=new ArrayList<>(Arrays.asList(new Edge(1, 6), new Edge(2, 7), new Edge(3, 6), new Edge(4, 8),
													 new Edge(6, 1), new Edge(7, 2), new Edge(6, 3), new Edge(8, 4)));
		ArrayList<Edge> sol=augment(A, B, M, R);
		if(sol!=null) {
			for(int i=0;i<sol.size()-1;i++) {
				Edge e=sol.get(i);
				System.out.print("("+e.u+", "+e.v+") -> ");
			}
			Edge e=sol.get(sol.size()-1);
			System.out.print("("+e.u+", "+e.v+")");
		}
		System.out.println();
	}
	public static ArrayList<Edge> augment(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Edge> M, ArrayList<Edge> R) {
		int n=A.size()+B.size();
		ArrayList<ArrayList<Integer>> L=new ArrayList<>();
		ArrayList<Integer> visited=new ArrayList<>();
		L.add(new ArrayList<Integer>());
		// initialization of L_0
		for(int v:A) {
			int flag=0;
			for(Edge e:M) flag=e.u==v?1:flag;
			if(flag==0) {
				L.get(0).add(v);
				visited.add(v);
			}
		}
		System.out.println(L.get(0).toString());
		for(int i=1;i<=n;i++) {
			// odd
			L.add(new ArrayList<>());
			if(i%2==1) {
				for(int a:L.get(i-1)) {
					for(int b:B) {
						if(visited.contains(b)) continue;
						if(R.contains(new Edge(a, b))) L.get(i).add(b);
					}
				}
			} else {
				for(int b:L.get(i-1)) {
					for(int a:A) {
						if(visited.contains(a)) continue;
						if(M.contains(new Edge(b, a))) L.get(i).add(a);
					}
				}
			}
			System.out.println(L.get(i).toString());
			for(int l:L.get(i)) {
				visited.add(l);
				int flag=0;
				for(Edge e:M) if(e.u==l) flag=1;
				if(flag==0) {
					System.out.println("YES");
					ArrayList<Edge> sol=new ArrayList<>();
					int cur=l;
					for(int j=i;j>=1;j--) {
						if(j%2==1) {
							for(int a:L.get(j-1)) {
								if(R.contains(new Edge(a, cur))) {
									sol.add(0, new Edge(a, cur));
									cur=a;
									break;
								}
							}
						} else {
							for(int b:L.get(j-1)) {
								if(M.contains(new Edge(b, cur))) {
									sol.add(0, new Edge(b, cur));
									cur=b;
									break;
								}
							}
						}
					}
					return sol;
				}
			}
		}
		System.out.println("NO");
		return null;
	}
}
