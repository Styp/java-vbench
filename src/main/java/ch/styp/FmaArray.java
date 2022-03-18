package ch.styp;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class FmaArray {

    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    // FMA: Fused Multiply Add: c = c + (a * b)
    public static float scalarFMA(float[] a, float[] b){
        var c = 0.0f;

        for(var i=0; i < a.length; i++){
            c = Math.fma(a[i], b[i], c);
        }
        return c;
    }

    public static float vectorFMA(float[] a, float[] b){
        var upperBound = SPECIES.loopBound(a.length);
        var sum = FloatVector.zero(SPECIES);

        var i = 0;
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            sum = va.fma(vb, sum);
        }
        var c = sum.reduceLanes(VectorOperators.ADD);

        for (; i < a.length; i++) { // Cleanup loop
            c += a[i] * b[i];
        }
        return c;
    }

}
