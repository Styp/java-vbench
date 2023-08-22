package ch.styp;

import ch.styp.Helpers.GeneratorHelpers;
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
        int[] result = new int[PRIME_NUMBER];

        var algorithm = new SumArrayAlgorithm();
        var c_scalar = algorithm.scalarComputation(a, b, result);
        var c_vector = algorithm.vectorComputation(a, b, result);

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


}
