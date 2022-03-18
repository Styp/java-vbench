package ch.styp;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;


public class SumArray {

    private static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    public static int[] scalarComputation(int[] a, int[] b) {
        var c = new int[a.length];

        for (var i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }

        return c;
    }

    public static int[] vectorComputation(int[] a, int[] b) {
        var c = new int[a.length];
        var upperBound = SPECIES.loopBound(a.length);

        var i = 0;
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc
            var va = IntVector.fromArray(SPECIES, a, i);
            var vb = IntVector.fromArray(SPECIES, b, i);
            var vc = va.add(vb);
            vc.intoArray(c, i);
        }

        for (; i < a.length; i++) { // Cleanup loop
            c[i] = a[i] + b[i];
        }

        return c;

    }



}
