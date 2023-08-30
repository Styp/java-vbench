package ch.styp.FloatMatrixMultiplicationBenchmark;

import ch.styp.SimpleMatrixMul;
import org.openjdk.jmh.annotations.*;

import static ch.styp.Helpers.GeneratorHelpers.newFloatRowMajorMatrix;


@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"-XX:-TieredCompilation"})
public class SimpleMatrixMultiplication {

    @Param({"16", "32", "64", "128", "256", "512", "1024", "2048"})
    int size;
    private float[] left;
    private float[] right;
    private float[] result;
    private SimpleMatrixMul algorithm;


    @Setup(Level.Iteration)
    public void init() {
        this.left = newFloatRowMajorMatrix(size * size);
        this.right = newFloatRowMajorMatrix(size * size);
        this.result = new float[size * size];

        this.algorithm = new SimpleMatrixMul();
    }

    @Benchmark
    public float[] matrixMultiplicationIJK() {
        return algorithm.baselineIJK(left, right, result, size);
    }

    @Benchmark
    public float[] matrixMultiplicationJIK() {
        return algorithm.baselineJIK(left, right, result, size);
    }

    @Benchmark
    public float[] matrixMultiplicationIKJ() {
        return algorithm.baselineIKJ(left, right, result, size);
    }

    @Benchmark
    public float[] matrixMultiplicationKIJ() {
        return algorithm.baselineKIJ(left, right, result, size);
    }

    @Benchmark
    public float[] matrixMultiplicationJKI() {
        return algorithm.baselineJKI(left, right, result, size);
    }

    @Benchmark
    public float[] matrixMultiplicationKJI() {
        return algorithm.baselineKJI(left, right, result, size);
    }

}
