package ch.styp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixMulTest {

    @Test
    void mulMatrixvsMatrixVector(){
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 64;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var mmul = new MatrixMul();
        var c_baseline = mmul.baseline(a, b, SIZE);
        var c_vector = mmul.simpleVector(a, b, SIZE);

        for(var i=0; i<SIZE * SIZE; i++){
//            System.out.println(i);
            assertEquals(c_baseline[i], c_vector[i], 0.001f);
        }

    }

    @Test
    void mulMatrixvsMatrixBlockedVector(){
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 1024;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var mmul = new MatrixMul();
        var c_baseline = mmul.baseline(a, b, SIZE);
        var c_vector = mmul.blockedVector(a, b, SIZE);

        for(var i=0; i<SIZE * SIZE; i++){
//            System.out.println(i);
            assertEquals(c_baseline[i], c_vector[i], 0.001f);
        }

    }


}
