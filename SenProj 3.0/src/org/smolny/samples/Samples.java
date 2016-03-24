package org.smolny.samples;

import java.util.Arrays;

/**
 * Created by Asus on 24.03.2016.
 */
public class Samples {

    public static void main(String... args) {
        int[][] wolves = {
                {5,3},
                {6,4},
                {3,8},
                {1,4}
        };

        int[] rabbit = {5,4};

        double[] sp = new double[2];
        for(int[] w : wolves) {

            double[] vector = {rabbit[0] - w[0], rabbit[1] - w[1]};
            double length = Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);
            vector[0] = vector[0] / (length * length);
            vector[1] = vector[1] / (length * length);

            sp[0] += vector[0];
            sp[1] += vector[1];

        }

        System.out.println("Superposition: " + Arrays.toString(sp));
        long[] target = {Math.round(rabbit[0] + sp[0]), Math.round(rabbit[1] + sp[1])};
        System.out.println("Target: " + Arrays.toString(target));
    }

}
