package ch.styp;

import ch.styp.Helpers.GeneratorHelpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleMatrixMulTest {

    @Test
    void simpleMatrixMultiplicationIJK() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultIJKBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultIJKBaseline = mmul.baselineIJK(a, b, resultIJKBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultIJKBaseline[i], 0.001f);
        }
    }

    @Test
    void simpleMatrixMultiplicationJIK() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultJIKBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultJIKBaseline = mmul.baselineJIK(a, b, resultJIKBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultJIKBaseline[i], 0.001f);
        }
    }

    @Test
    void simpleMatrixMultiplicationIKJ() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultIKJBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultIKJBaseline = mmul.baselineIKJ(a, b, resultIKJBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultIKJBaseline[i], 0.001f);
        }
    }

    @Test
    void simpleMatrixMultiplicationKIJ() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultKIJBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultKIJBaseline = mmul.baselineKIJ(a, b, resultKIJBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultKIJBaseline[i], 0.001f);
        }
    }

    @Test
    void simpleMatrixMultiplicationJKI() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultJKIBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultJKIBaseline = mmul.baselineJKI(a, b, resultJKIBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultJKIBaseline[i], 0.001f);
        }
    }

    @Test
    void simpleMatrixMultiplicationKJI() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultKJIBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultKJIBaseline = mmul.baselineKJI(a, b, resultKJIBaseline, SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultKJIBaseline[i], 0.001f);
        }
    }
}
