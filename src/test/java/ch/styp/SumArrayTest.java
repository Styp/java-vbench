package ch.styp;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;
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
    void capabilityTest(){
        VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
        System.out.print(SPECIES);
    }

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
