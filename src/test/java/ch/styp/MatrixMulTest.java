package ch.styp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixMulTest {

    @Test
    void mulMatrixvsMatrixVector() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var mmul = new MatrixMul();
        var c_baseline = mmul.baseline(a, b, SIZE);
        var c_vector = mmul.simpleVectorAVX256(a, b, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
//            System.out.println(i);
            assertEquals(c_baseline[i], c_vector[i], 0.001f);
        }

    }

    @Test
    void mulMatrixvsMatrixBlockedVector() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 512;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var mmul = new MatrixMul();
        var c_baseline = mmul.baseline(a, b, SIZE);
        var c_vector = mmul.blockedVectorAVX256(a, b, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
//            System.out.println(i);
            assertEquals(c_baseline[i], c_vector[i], 0.001f);
        }

    }

    @Test
    void mulMatrixvsMatrixBlockedVectorUnrolled() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 512;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var mmul = new MatrixMul();
        var c_baseline = mmul.baseline(a, b, SIZE);
        var c_vector = mmul.blockedVectorUnrolledAVX512(a, b, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
//            System.out.println(i);
            assertEquals(c_baseline[i], c_vector[i], 0.001f);
        }

    }


}
