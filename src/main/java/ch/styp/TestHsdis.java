package ch.styp;

import java.util.Random;

public class TestHsdis {

    public static void main(String... args) {
        TestHsdis testHsdis = new TestHsdis();
        var size = 2048;
        var left = initFloatArray(size);
        var right = initFloatArray(size);

        for(int i = 0; i <= 10_000_000; i++) {
            var result = testHsdis.addArrays(left, right);
        }

    }

    public static float[] initFloatArray(int length){
        var floatArray = new float[length];

        Random rand = new Random();
        for(var i = 0; i<length; i++){
            floatArray[i] = rand.nextFloat();
        }
        return floatArray;
    }

    private float[] addArrays(float[] left, float[] right) {
       float[] result = new float[left.length];
        for(int i=0; i < left.length; i++){
           result[i] = left[i] + right[i];
       }
        return result;
    }

}
