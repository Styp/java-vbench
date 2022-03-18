package ch.styp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SumArrayTest {

    @Test
    void sumArrayVectorVsScalar(){
        // Prime Number, that doesn't make the registers align by accident!
        final int PRIME_NUMBER = 919;

        var a = GeneratorHelpers.initIntArray(PRIME_NUMBER);
        var b = GeneratorHelpers.initIntArray(PRIME_NUMBER);

        var c_scalar = SumArray.scalarComputation(a, b);
        var c_vector = SumArray.vectorComputation(a, b);

        assertTrue(c_scalar.length == PRIME_NUMBER);
        assertTrue(c_scalar.length == c_vector.length);

        for(var i=0; i<PRIME_NUMBER; i++){
            assertEquals(c_scalar[i], c_vector[i]);
        }

    }

    @Test
    void fmaArrayVectorVsScalar(){
        // Prime Number, that doesn't make the registers align by accident!
        final int PRIME_NUMBER = 919;

        var a = GeneratorHelpers.initFloatArray(PRIME_NUMBER);
        var b = GeneratorHelpers.initFloatArray(PRIME_NUMBER);

        var c_scalar = FmaArray.scalarFMA(a, b);
        var c_vector = FmaArray.vectorFMA(a, b);

        assertEquals(c_scalar, c_vector, 0.0001f);

    }

}
