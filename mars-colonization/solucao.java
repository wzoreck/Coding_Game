// Puzzle in: https://www.codingame.com/ide/puzzle/mars-colonization

import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    static Grafo g;
    static List<Aresta> arvoreMinima;

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int M = in.nextInt();
        int S = in.nextInt();
        g = new Grafo();

        System.err.println("--- Input ---");
        System.err.println(M + "\t" + S);
        for (int i = 0; i < M; i++) {
            int x = in.nextInt();
            System.err.print(x + "\t");
            int y = in.nextInt();
            System.err.println(y);
            g.addVertice(new Vertice(i, x, y));
        }
        System.err.println("-------------\n\n");
        
        // Ligaçao Arestas
        int j =1;
        for (int i = 0; i < M-1; i++) {
            while (j < M) {
                int xSoma = g.getVertice(i).getX() - g.getVertice(j).getX();
                int ySoma = g.getVertice(i).getY() - g.getVertice(j).getY();
                double xPow = Math.pow(xSoma, 2);
                double yPow = Math.pow(ySoma, 2);
                double peso = Math.sqrt(xPow + yPow);
                g.addAresta(new Aresta(g.getVertice(i), g.getVertice(j), peso));
                j++;
            }
            j = i+2;
        }
        
        System.err.println(g);

        arvoreMinima = prim(g.getVertice(1));
        System.err.println();
        System.err.println();
        System.err.println(arvoreMinima);

        List<Double> pesos = new ArrayList<>();

        for (Aresta a : arvoreMinima) {
            pesos.add(a.getPeso());
        }

        Collections.sort(pesos);
        System.err.println("Pesos: " + pesos);

        for (int i = 0; i < S-1; i++)
            pesos.remove(pesos.size()-1);
        System.err.println("Pesos (Removidos pontos satelite): " + pesos);

        System.out.printf("%.2f", pesos.get(pesos.size()-1));

    }

    // Prim
    public static List<Aresta> prim(Vertice vertice){
        List<Aresta> am = new ArrayList<Aresta>();
        List<Vertice> visitados = new ArrayList<Vertice>();
        
        visitados.add(vertice);
        
        while(visitados.size() < g.getNumberOfVertices()) {
            Aresta menorPeso = null;
            for(Vertice v: visitados) {
                for (Aresta a : v.getArestas()) {
                    if(a.getV1()!=v) {
                        if(!visitados.contains(a.getV1())) {
                            if(menorPeso == null) {
                                menorPeso = a;
                            }else {
                                if (a.getPeso() < menorPeso.getPeso()) {
                                    menorPeso = a;
                                }
                            }
                        }
                    }else {
                        if(!visitados.contains(a.getV2())) {
                            if(menorPeso == null) {
                                menorPeso = a;
                            }else {
                                if (a.getPeso() < menorPeso.getPeso()) {
                                    menorPeso = a;
                                }
                            }
                        }
                    }
                }
            }
            if(visitados.contains(menorPeso.getV1()))
                visitados.add(menorPeso.getV2());
            else
                visitados.add(menorPeso.getV1());
            am.add(menorPeso);
        }
        
        return am;
    }

    // Grafo
    public static class Vertice {

        private int id;
        private int x;
        private int y;
        private List<Aresta> arestas;
        
        public Vertice(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            arestas = new ArrayList<Aresta>();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }

        public void addAresta(Aresta aresta) {
            arestas.add(aresta);
        }

        public List<Aresta> getArestas() {
            return arestas;
        }

        public void setArestas(List<Aresta> arestas) {
            this.arestas = arestas;
        }
        
        @Override
        public String toString() {
            return "Vertice [id = " + id + " cordenadas X = " + x + " y = " + y + ", arestas=" + arestas + "]\n";
        }
    }

    public static class Aresta {

        private Vertice v1;
        private Vertice v2;
        private double peso;
            
        public Aresta(Vertice v1, Vertice v2, double peso) {
            super();
            this.v1 = v1;
            this.v2 = v2;
            this.peso = peso;
            v1.addAresta(this);
            v2.addAresta(this);
        }

        public Vertice getV1() {
            return v1;
        }

        public void setV1(Vertice v1) {
            this.v1 = v1;
        }

        public Vertice getV2() {
            return v2;
        }

        public void setV2(Vertice v2) {
            this.v2 = v2;
        }

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }
        
        @Override
        public String toString() {
            return "Aresta [v1=" + v1.getId() + ", v2=" + v2.getId() + ", peso=" + peso + "]";
        }   
    }


    public static class Grafo {
    
        private List<Aresta> arestas;
        private List<Vertice> vertices;
        
        public Grafo() {
            arestas = new ArrayList<Aresta>();
            vertices = new ArrayList<Vertice>();
        }

        public void addVertice(Vertice vertice) {
            vertices.add(vertice);
        }
        
        public int getNumberOfVertices() {
            return vertices.size();
        }
        
        public void addAresta(Aresta aresta) {
            arestas.add(aresta);
        }
        
        public Vertice getVertice(int pos) {
            return vertices.get(pos);
        }
        
        @Override
        public String toString() {
            return "Grafo [vertices=\n" + vertices + ", \narestas=" + arestas + "]";
        }
    }

}