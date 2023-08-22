package ch.styp;

import ch.styp.Helpers.GeneratorHelpers;
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

        var resultBaseline = new float[SIZE * SIZE];
        var resultVector = new float[SIZE * SIZE];

        var mmul = new MatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultVector = mmul.simpleVectorAVX256(a, b, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultVector[i], 0.001f);
        }

    }

    @Test
    void mulMatrixvsMatrixBlocked() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;
        final int BLOCK_SIZE = 16;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultBlocked = new float[SIZE * SIZE];

        var mmul = new MatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultBlocked = mmul.blocked(a, b, resultBlocked, SIZE, BLOCK_SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultBlocked[i], 0.001f);
        }

    }

    @Test
    void mulMatrixvsMatrixSimpleFMA() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultFMA = new float[SIZE * SIZE];

        var mmul = new MatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultFMA = mmul.simpleFMA(a, b, resultFMA, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultFMA[i], 0.001f);
        }
    }

    @Test
    void mulMatrixvsMatrixVectorPrefered() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultVectorPrefered = new float[SIZE * SIZE];

        var mmul = new MatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultVectorPrefered = mmul.simpleVectorPrefered(a, b, resultVectorPrefered, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultVectorPrefered[i], 0.001f);
        }
    }

//
//    @Test
//    void mulMatrixvsMatrixBlockedVector() {
//        // Prime Number, that doesn't make the registers align by accident!
//        final int SIZE = 128;
//
//        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
//        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
//
//        var mmul = new MatrixMul();
//        var c_baseline = mmul.baseline(a, b, SIZE);
//        var c_vector = mmul.blockedVectorUnrolledAVX512_8(a, b, SIZE);
//
//        for (var i = 0; i < SIZE * SIZE; i++) {
////            System.out.println(i);
//            assertEquals(c_baseline[i], c_vector[i], 0.001f);
//        }
//
//    }
//
//    @Test
//    void mulMatrixvsMatrixBlockedVectorUnrolled() {
//        // Prime Number, that doesn't make the registers align by accident!
//        final int SIZE = 512;
//
//        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
//        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
//
//        var mmul = new MatrixMul();
//        var c_baseline = mmul.baseline(a, b, SIZE);
//        var c_vector = mmul.blockedVectorUnrolledAVX512_4(a, b, SIZE);
//
//        for (var i = 0; i < SIZE * SIZE; i++) {
////            System.out.println(i);
//            assertEquals(c_baseline[i], c_vector[i], 0.001f);
//        }
//
//    }
//

}
