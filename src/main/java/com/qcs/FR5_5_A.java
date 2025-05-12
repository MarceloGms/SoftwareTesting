package com.qcs;

import java.util.Queue;
import java.util.LinkedList;

public class FR5_5_A {

    static int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static Queue<int[]> queue;

    public static void seeNeighbors(float[][] image, int i, int j, int width, int height, float threshold) {
	/* Dummy function */
    }

    public static int detectObjects(float[][] image, int width, int height, float threshold, boolean[][] visited) {
        int objects = 0;
        // visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //System.out.println("i: " + i + " j: " + j);
                //System.out.println("image[i][j]: " + image[i][j]);
                if (image[i][j] > threshold && !visited[i][j]) {
                    //Encontrou um objeto
                    objects++;
                    //Explorar para ver se há vizinhos
                    seeNeighbors(image, i, j, width, height, threshold);
                }
            }
        }
        return objects;
    }
}
