package ch.styp;

import java.util.Random;

public class TestMain {

    public static void main(String... args) {
        TestMain testMain = new TestMain();
        int[] a = testMain.initIntArray(8192);
        int[] b = testMain.initIntArray(8192);
        long timeNow = System.currentTimeMillis();
        for(int i = 0; i <= 100000; i++) {
            testMain.addArray(a, b);
        }
        System.out.print(System.currentTimeMillis() - timeNow);
    }

    public int[] initIntArray(int length){
        var intArray = new int[length];

        Random rand = new Random();
        for(var i = 0; i<length; i++){
            intArray[i] = rand.nextInt();
        }

        return intArray;
    }

    private int add(int a, int b) {
        int c = a + b;
        return c;
    }

    private int[] addArray(int[] a, int[] b){
        int[] c = new int[a.length];
        for(int i=0; i<a.length; i++){
            c[i] = a[i] + b[i];
        }
        return c;
    }
}

// -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xlog:class+load=info -XX:+LogCompilation TestMain