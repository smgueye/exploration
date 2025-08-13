package io.mgueye.graph_theory.bfs;

import java.io.*;
import java.util.*;

public class CpTaskA {

  BufferedReader in;
  StringTokenizer tokenizer;
  PrintWriter out;

  private void solve() throws IOException {
    int T = nextInt();
    while (T-- > 0) {
      int numberCities = nextInt() + 1;
      int numberRoads = nextInt();
      int numberAffectations = nextInt();
      Map<Integer, List<Integer>> g = new HashMap<>();

      for (int i = 1; i <= numberRoads; i++) {
        int a = nextInt();
        int b = nextInt();
        g.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        g.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
      }

      ArrayList<ArrayList<Integer>> affectations = new ArrayList<>(numberAffectations);
      for (int i = 1; i <= numberAffectations; i++)
        affectations.add(new ArrayList<>(List.of(nextInt(), nextInt())));

      out.println(checkPlacements(g, affectations));
    }
  }

  private String checkPlacements(Map<Integer, List<Integer>> g,
                              ArrayList<ArrayList<Integer>> affectations) {
    LinkedList<Integer> q = new LinkedList<>();
    HashSet<Integer> s = new HashSet<>();
    boolean[] used = new boolean[g.size()+1];
    out.println(g);
    for (ArrayList<Integer> affectation : affectations) {
      int src = affectation.get(0);
      int range = affectation.get(1);
      q.add(src);
      used[src] = true;

      while (!q.isEmpty()) {
        Integer u = q.pop();
        s.add(u);
        if (range == 0)
          break;
        for (Integer v : g.get(u)) {
          if (used[v]) continue;
          used[v] = true;
          q.add(v);
          s.add(v);
        }
        range--;
        if (range <= 0) {
          q.clear();
          break;
        }
      }
    }
    out.println(Arrays.toString(used));
    out.println(g.size());
    out.println(s.size());
    return s.size() == g.size() ? "Yes" : "No";
  }

  public static void main(String[] args) {
    new CpTaskA().run();
  }

  public void run() {
    try {
      in = new BufferedReader(new InputStreamReader(System.in));
      out = new PrintWriter(System.out);
      tokenizer = null;
      solve();
      in.close();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  int nextInt() throws IOException {
    return Integer.parseInt(nextToken());
  }

  long nextLong() throws IOException {
    return Long.parseLong(nextToken());
  }

  double nextDouble() throws IOException {
    return Double.parseDouble(nextToken());
  }

  String nextToken() throws IOException {
    while (tokenizer == null || !tokenizer.hasMoreTokens())
      tokenizer = new StringTokenizer(in.readLine());
    return tokenizer.nextToken();
  }

  public boolean ready() throws IOException {
    return in.ready() || tokenizer.hasMoreTokens();
  }
}
