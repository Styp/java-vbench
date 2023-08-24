package ch.styp;

import java.util.Arrays;

import jdk.incubator.vector.FloatVector;

import jdk.incubator.vector.VectorSpecies;


public class MatrixMul {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    public float[] baseline(float[] a, float[] b, float[] result, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float sum = 0.0f;
                for (int k = 0; k < n; k++) {
                    sum += a[i * n + k] * b[k * n + j];
                }
                result[i * n + j] = sum;
            }
        }
        return result;
    }

    public float[] baselineIKJ(float[] a, float[] b, float[] result, int n) {
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                for (int j = 0; j < n; j++) {
                    result[i * n + j] += aik * b[k * n + j];
                }
            }
        }
        return result;
    }

    public float[] blocked(float[] a, float[] b, float[] result, int n, final int blocksize) {
        for (int kk = 0; kk < n; kk += blocksize) {
            for (int jj = 0; jj < n; jj += blocksize) {
                for (int i = 0; i < n; i++) {
                    for (int j = jj; j < jj + blocksize; ++j) {
                        float sum = result[i * n + j];
                        for (int k = kk; k < kk + blocksize; ++k) {
                            sum += a[i * n + k] * b[k * n + j];
                        }
                        result[i * n + j] = sum;
                    }
                }
            }
        }
        return result;
    }

    public float[] blockedIKJ(float[] a, float[] b, float[] result, int n, final int blocksize) {
        for (int kk = 0; kk < n; kk += blocksize) {
            for (int jj = 0; jj < n; jj += blocksize) {
                for (int i = 0; i < n; i++) {
                    for (int k = kk; k < kk + blocksize; ++k) {
                        float aik = a[i * n + k];
                            for (int j = jj; j < jj + blocksize; ++j) {
                                result[i * n + j] += aik * b[k * n + j];
                        }
                    }
                }
            }
        }
        return result;
    }

    public float[] simpleFMA(float[] a, float[] b, float[] result, int n) {
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                for (int j = 0; j < n; j++) {
                    result[i * n + j] = Math.fma(aik, b[k * n + j], result[i * n + j]);
                }
            }
        }
        return result;
    }

    public float[] blockedFMA(float[] a, float[] b, float[] result, int n, final int blocksize) {
            for (int kk = 0; kk < n; kk += blocksize) {
                for (int jj = 0; jj < n; jj += blocksize) {
                    for (int i = 0; i < n; i++) {
                        for (int k = kk; k < kk + blocksize; k++) {
                            float aik = a[i * n + k];
                            for (int j = jj; j < jj + blocksize; j++) {
                                result[i * n + j] = Math.fma(aik, b[k * n + j], result[i * n + j]);
                            }
                        }
                }
            }
        }
        return result;
    }

    public float[] simpleVectorPrefered(float[] a, float[] b, float[] result, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
        final int upperBound = SPECIES.loopBound(n);

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                FloatVector vaik = FloatVector.broadcast(SPECIES, aik);
                for (int j = 0; j < upperBound; j += SPECIES.length()) {
                    FloatVector vb = FloatVector.fromArray(SPECIES, b, k * n + j);
                    FloatVector vc = FloatVector.fromArray(SPECIES, result, i * n + j);
                    vc = vaik.fma(vb, vc);
                    vc.intoArray(result, i * n + j);
                }
            }
        }
        return result;
    }

    public float[] simpleVectorAVX256(float[] a, float[] b, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_256;

        final int upperBound = SPECIES.loopBound(n);
        float[] c = new float[n * n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                FloatVector vaik = FloatVector.broadcast(SPECIES, aik);
                for (int j = 0; j < upperBound; j += SPECIES.length()) {
                    FloatVector vb = FloatVector.fromArray(SPECIES, b, k * n + j);
                    FloatVector vc = FloatVector.fromArray(SPECIES, c, i * n + j);
                    vc = vaik.fma(vb, vc);
                    vc.intoArray(c, i * n + j);
                }
            }
        }
        return c;
    }

    public float[] simpleVectorAVX512(float[] a, float[] b, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_512;

        final int upperBound = SPECIES.loopBound(n);
        float[] c = new float[n * n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                FloatVector vaik = FloatVector.broadcast(SPECIES, aik);
                for (int j = 0; j < upperBound; j += SPECIES.length()) {
                    FloatVector vb = FloatVector.fromArray(SPECIES, b, k * n + j);
                    FloatVector vc = FloatVector.fromArray(SPECIES, c, i * n + j);
                    vc = vaik.fma(vb, vc);
                    vc.intoArray(c, i * n + j);
                }
            }
        }
        return c;
    }

    public float[] blockedVectorPrefered(float[] a, float[] b, float[] result, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += SPECIES.length()) {
                        FloatVector sum = FloatVector.fromArray(SPECIES, result, i * n + j);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; k++) {
                            FloatVector multiplier = FloatVector.broadcast(SPECIES, a[i * n + k]);
                            sum = multiplier.fma(FloatVector.fromArray(SPECIES, b, k * n + j), sum);
                        }
                        sum.intoArray(result, i * n + j);
                    }
                }
            }
        }
        return result;
    }

    public float[] blockedVectorAVX256(float[] a, float[] b, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_256;

        float[] c = new float[n * n];
        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += SPECIES.length()) {
                        FloatVector sum = FloatVector.fromArray(SPECIES, c, i * n + j);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; k++) {
                            FloatVector multiplier = FloatVector.broadcast(SPECIES, a[i * n + k]);
                            sum = multiplier.fma(FloatVector.fromArray(SPECIES, b, k * n + j), sum);
                        }
                        sum.intoArray(c, i * n + j);
                    }
                }
            }
        }
        return c;
    }

    public float[] blockedVectorAVX512(float[] a, float[] b, int n) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_512;

        float[] c = new float[n * n];
        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += SPECIES.length()) {
                        FloatVector sum = FloatVector.fromArray(SPECIES, c, i * n + j);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; k++) {
                            FloatVector multiplier = FloatVector.broadcast(SPECIES, a[i * n + k]);
                            sum = multiplier.fma(FloatVector.fromArray(SPECIES, b, k * n + j), sum);
                        }
                        sum.intoArray(c, i * n + j);
                    }
                }
            }
        }
        return c;
    }

    public float[] blockedVectorUnrolledAVX512_4(float[] a, float[] b, int n) {
        VectorSpecies<Float> species512 = FloatVector.SPECIES_512;

        float[] c = new float[n * n];
        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += 64) {
                        FloatVector sum1 = FloatVector.fromArray(species512, c, i * n + j);
                        FloatVector sum2 = FloatVector.fromArray(species512, c, i * n + j + 16);
                        FloatVector sum3 = FloatVector.fromArray(species512, c, i * n + j + 32);
                        FloatVector sum4 = FloatVector.fromArray(species512, c, i * n + j + 48);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; ++k) {
                            FloatVector multiplier = FloatVector.broadcast(species512, a[i * n + k]);
                            sum1 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j), sum1);
                            sum2 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 16), sum2);
                            sum3 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 32), sum3);
                            sum4 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 48), sum4);
                        }
                        sum1.intoArray(c, i * n + j);
                        sum2.intoArray(c, i * n + j + 16);
                        sum3.intoArray(c, i * n + j + 32);
                        sum4.intoArray(c, i * n + j + 48);
                    }
                }
            }
        }
        return c;
    }

    public float[] blockedVectorUnrolledAVX512_8(float[] a, float[] b, int n) {
        VectorSpecies<Float> species512 = FloatVector.SPECIES_512;

        float[] c = new float[n * n];
        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += 128) {
                        FloatVector sum1 = FloatVector.fromArray(species512, c, i * n + j);
                        FloatVector sum2 = FloatVector.fromArray(species512, c, i * n + j + 16);
                        FloatVector sum3 = FloatVector.fromArray(species512, c, i * n + j + 32);
                        FloatVector sum4 = FloatVector.fromArray(species512, c, i * n + j + 48);
                        FloatVector sum5 = FloatVector.fromArray(species512, c, i * n + j + 64);
                        FloatVector sum6 = FloatVector.fromArray(species512, c, i * n + j + 80);
                        FloatVector sum7 = FloatVector.fromArray(species512, c, i * n + j + 96);
                        FloatVector sum8 = FloatVector.fromArray(species512, c, i * n + j + 112);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; ++k) {
                            FloatVector multiplier = FloatVector.broadcast(species512, a[i * n + k]);
                            sum1 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j), sum1);
                            sum2 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 16), sum2);
                            sum3 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 32), sum3);
                            sum4 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 48), sum4);
                            sum5 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 64), sum5);
                            sum6 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 80), sum6);
                            sum7 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 96), sum7);
                            sum8 = multiplier.fma(FloatVector.fromArray(species512, b, k * n + j + 112), sum8);
                        }
                        sum1.intoArray(c, i * n + j);
                        sum2.intoArray(c, i * n + j + 16);
                        sum3.intoArray(c, i * n + j + 32);
                        sum4.intoArray(c, i * n + j + 48);
                        sum5.intoArray(c, i * n + j + 64);
                        sum6.intoArray(c, i * n + j + 80);
                        sum7.intoArray(c, i * n + j + 96);
                        sum8.intoArray(c, i * n + j + 112);
                    }
                }
            }
        }
        return c;
    }

    public float[] blockedVectorUnrolledAVX256(float[] a, float[] b, int n) {
        VectorSpecies<Float> species256 = FloatVector.SPECIES_256;

        float[] c = new float[n * n];
        final int blockWidth = n >= 256 ? 512 : 256;
        final int blockHeight = n >= 512 ? 8 : n >= 256 ? 16 : 32;

        for (int rowOffset = 0; rowOffset < n; rowOffset += blockHeight) {
            for (int columnOffset = 0; columnOffset < n; columnOffset += blockWidth) {
                for (int i = 0; i < n; i++) {
                    for (int j = columnOffset; j < columnOffset + blockWidth && j < n; j += 64) {
                        FloatVector sum1 = FloatVector.fromArray(species256, c, i * n + j);
                        FloatVector sum2 = FloatVector.fromArray(species256, c, i * n + j + 8);
                        FloatVector sum3 = FloatVector.fromArray(species256, c, i * n + j + 16);
                        FloatVector sum4 = FloatVector.fromArray(species256, c, i * n + j + 24);
                        FloatVector sum5 = FloatVector.fromArray(species256, c, i * n + j + 32);
                        FloatVector sum6 = FloatVector.fromArray(species256, c, i * n + j + 40);
                        FloatVector sum7 = FloatVector.fromArray(species256, c, i * n + j + 48);
                        FloatVector sum8 = FloatVector.fromArray(species256, c, i * n + j + 56);
                        for (int k = rowOffset; k < rowOffset + blockHeight && k < n; ++k) {
                            FloatVector multiplier = FloatVector.broadcast(species256, a[i * n + k]);
                            sum1 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j), sum1);
                            sum2 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 8), sum2);
                            sum3 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 16), sum3);
                            sum4 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 24), sum4);
                            sum5 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 32), sum5);
                            sum6 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 40), sum6);
                            sum7 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 48), sum7);
                            sum8 = multiplier.fma(FloatVector.fromArray(species256, b, k * n + j + 56), sum8);
                        }
                        sum1.intoArray(c, i * n + j);
                        sum2.intoArray(c, i * n + j + 8);
                        sum3.intoArray(c, i * n + j + 16);
                        sum4.intoArray(c, i * n + j + 24);
                        sum5.intoArray(c, i * n + j + 32);
                        sum6.intoArray(c, i * n + j + 40);
                        sum7.intoArray(c, i * n + j + 48);
                        sum8.intoArray(c, i * n + j + 56);
                    }
                }
            }
        }
        return c;
    }
}
