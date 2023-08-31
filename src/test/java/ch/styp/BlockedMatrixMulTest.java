package ch.styp;

import ch.styp.Helpers.GeneratorHelpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockedMatrixMulTest {

    public static final int BLOCK_SIZE = 16;

    @Test
    void blockedMatrixMulIJKTest() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultIJKBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        var bMmul = new BlockedMatrixMul();

        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultIJKBaseline = bMmul.blockedIJK(a, b, resultIJKBaseline, SIZE, BLOCK_SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultIJKBaseline[i], 0.001f);
        }
    }

    @Test
    void blockedMatrixMulIKJTest() {
        // Prime Number, that doesn't make the registers align by accident!
        final int SIZE = 256;

        var a = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);
        var b = GeneratorHelpers.newFloatRowMajorMatrix(SIZE * SIZE);

        var resultBaseline = new float[SIZE * SIZE];
        var resultIKJBaseline = new float[SIZE * SIZE];

        var mmul = new SimpleMatrixMul();
        var bMmul = new BlockedMatrixMul();

        resultBaseline = mmul.baseline(a, b, resultBaseline, SIZE);
        resultIKJBaseline = bMmul.blockedIKJ(a, b, resultIKJBaseline, SIZE, BLOCK_SIZE);

        for (var i = 0; i < SIZE * SIZE; i++) {
            assertEquals(resultBaseline[i], resultIKJBaseline[i], 0.001f);
        }
    }

}
