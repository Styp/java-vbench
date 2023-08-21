package ch.styp;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class FmaArray {

    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    // FMA: Fused Multiply Add: c = c + (a * b)

    public float scalarFMAbyHand(float[] a, float[] b, float result){
        for(var i = 0; i < a.length; i++){
            result = result + (a[i] * b[i]);
        }
        return result;
    }

    public float scalarFMAbyMathLib(float[] a, float[] b, float result){
        for(var i=0; i < a.length; i++){
            result = Math.fma(a[i], b[i], result);
        }
        return result;
    }

    public float vectorFMA(float[] a, float[] b, float result){
        var upperBound = SPECIES.loopBound(a.length);
        var sum = FloatVector.zero(SPECIES);

        var i = 0;
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            sum = va.fma(vb, sum);
        }
        result = sum.reduceLanes(VectorOperators.ADD);

        for (; i < a.length; i++) { // Cleanup loop
            result += a[i] * b[i];
        }
        return result;
    }

}
