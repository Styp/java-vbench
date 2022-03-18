package ch.styp;

import java.util.Random;

public class GeneratorHelpers {

    public static float[] initFloatArray(int length){
        var floatArray = new float[length];

        Random rand = new Random();
        for(var i = 0; i<length; i++){
            floatArray[i] = rand.nextFloat();
        }
        return floatArray;
    }

    public static int[] initIntArray(int length){
        var intArray = new int[length];

        Random rand = new Random();
        for(var i = 0; i<length; i++){
            intArray[i] = rand.nextInt();
        }

        return intArray;
    }

    public static double[][] generateRandom2dImage(int sizeX, int sizeY){
        var image = new double[sizeX][sizeY];

        var rand = new Random();
        for(var y = 0; y < sizeY; y++){
            for(var x = 0; x < sizeX; x++){
                image[x][y] = rand.nextDouble();
            }
        }

        return image;
    }

}
