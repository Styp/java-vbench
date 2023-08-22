package ch.styp;

import ch.styp.Helpers.GeneratorHelpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FmaArrayTest {



    @Test
    void fmaArrayVectorVsScalarByHand(){
        // Prime Number, that doesn't make the registers align by accident!
        final int PRIME_NUMBER = 919;

        var a = GeneratorHelpers.initFloatArray(PRIME_NUMBER);
        var b = GeneratorHelpers.initFloatArray(PRIME_NUMBER);
        var result = 0.0f;

        var fmaArray = new FmaArray();

        var c_scalar = fmaArray.scalarFMAbyHand(a, b, result);
        var c_vector = fmaArray.vectorFMA(a, b, result);

        assertEquals(c_scalar, c_vector, 0.001f);
    }

    @Test
    void fmaArrayVectorVsScalarByLib(){
        // Prime Number, that doesn't make the registers align by accident!
        final int PRIME_NUMBER = 919;

        var a = GeneratorHelpers.initFloatArray(PRIME_NUMBER);
        var b = GeneratorHelpers.initFloatArray(PRIME_NUMBER);
        var result = 0.0f;

        var fmaArray = new FmaArray();

        var c_scalar = fmaArray.scalarFMAbyMathLib(a, b, result);
        var c_vector = fmaArray.vectorFMA(a, b, result);

        assertEquals(c_scalar, c_vector, 0.001f);
    }
}
