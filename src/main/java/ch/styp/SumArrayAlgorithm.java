package ch.styp;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;


public class SumArrayAlgorithm {

    private static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    public int[] scalarComputation(int[] a, int[] b, int[] result) {
        for (var i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }

        return result;
    }

    public int[] vectorComputation(int[] a, int[] b, int[] result, VectorSpecies<Integer> species) {
        var upperBound = species.loopBound(a.length);

        var i = 0;
        for (; i < upperBound; i += species.length()) {
            // FloatVector va, vb, vc
            var va = IntVector.fromArray(species, a, i);
            var vb = IntVector.fromArray(species, b, i);
            var vc = va.add(vb);
            vc.intoArray(result, i);
        }

        for (; i < a.length; i++) { // Cleanup loop
            result[i] = a[i] + b[i];
        }

        return result;
    }

    public int[] vectorComputation(int[] a, int[] b, int[] result) {
        return vectorComputation(a, b, result, SPECIES);
    }

}
