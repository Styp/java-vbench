package ch.styp;


import java.util.random.RandomGenerator;

public class GeneratorHelpers {

    public static float[] initFloatArray(int length){
        var floatArray = new float[length];

        var rand = RandomGenerator.getDefault();
        for(var i = 0; i<length; i++){
            floatArray[i] = rand.nextFloat();
        }
        return floatArray;
    }

    public static int[] initIntArray(int length){
        var intArray = new int[length];

        var rand = RandomGenerator.getDefault();
        for(var i = 0; i<length; i++){
            intArray[i] = rand.nextInt();
        }

        return intArray;
    }

    public static float[] newFloatRowMajorMatrix(int size) {
        var rand = RandomGenerator.getDefault();
        float[] matrix = new float[size];
        for (int i = 0; i < matrix.length; ++i) {
            matrix[i] = rand.nextFloat();
        }
        return matrix;
    }

}
