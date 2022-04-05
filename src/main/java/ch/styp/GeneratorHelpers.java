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

}
