package ch.styp;

import java.util.Arrays;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;


public class MatrixMul {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    public float[] baseline(float[] a, float[] b, int n){
        float[] c = new float[n * n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float sum = 0.0f;
                for (int k = 0; k < n; k++) {
                    sum += a[i * n + k] * b[k * n + j];
                }
                c[i * n + j] = sum;
            }
        }
        return c;
    }

    public float[] blocked(float[] a, float[] b, int n, int blocksize) {
        float[] c = new float[n * n];

        int BLOCK_SIZE = blocksize;
        for (int kk = 0; kk < n; kk += BLOCK_SIZE) {
            for (int jj = 0; jj < n; jj += BLOCK_SIZE) {
                for (int i = 0; i < n; i++) {
                    for (int j = jj; j < jj + BLOCK_SIZE; ++j) {
                        float sum = c[i * n + j];
                        for (int k = kk; k < kk + BLOCK_SIZE; ++k) {
                            sum += a[i * n + k] * b[k * n + j];
                        }
                        c[i * n + j] = sum;
                    }
                }
            }
        }
        return c;
    }

    public float[] simpleFMA(float[] a, float[] b, int n) {
        float[] c = new float[n * n];

        int in = 0;
        for (int i = 0; i < n; ++i) {
            int kn = 0;
            for (int k = 0; k < n; ++k) {
                float aik = a[in + k];
                for (int j = 0; j < n; ++j) {
                    c[in + j] = Math.fma(aik,  b[kn + j], c[in + j]);
                }
                kn += n;
            }
            in += n;
        }
        return c;
    }

    public float[] simpleVector(float[] a, float[] b, int n) {
        final int upperBound = SPECIES.loopBound(n);
        float[] c = new float[n * n];

        int in = 0;
        for (int i = 0; i < n; i++) {
            int kn = 0;
            for (int k = 0; k < n; k++) {
                float aik = a[in + k];
                FloatVector vaik = FloatVector.broadcast(SPECIES, aik);

                for (int j=0; j < upperBound; j += SPECIES.length()) {
                    // FloatVector va, vb, vc
                    var vb = FloatVector.fromArray(SPECIES, b, kn + j);
                    var vc = FloatVector.fromArray(SPECIES, c, in + j);
                    vc = vaik.fma(vb, vc);
                    vc.intoArray(c, in + j);
                }
                kn += n;
            }
            in += n;
        }
        return c;
    }

    public float[] blockedVector(float[] a, float b[], int n) {
        float[] c = new float[n * n];
        int blockWidth = n >= 256 ? 512 : 256;
        int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j+=SPECIES.length()) {
                        var sum = FloatVector.fromArray(SPECIES, c, i * n + j);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; k++) {
                            var multiplier = FloatVector.broadcast(SPECIES, a[i*n + k]);
                            sum = multiplier.fma(FloatVector.fromArray(SPECIES, b, k*n + j), sum);
                        }
                        sum.intoArray(c, i*n + j);
                    }
                }
            }
        }
        return c;
    }

}