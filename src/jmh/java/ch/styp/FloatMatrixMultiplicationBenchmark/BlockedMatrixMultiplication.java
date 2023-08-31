package ch.styp.FloatMatrixMultiplicationBenchmark;

import ch.styp.BlockedMatrixMul;
import ch.styp.SimpleMatrixMul;
import org.openjdk.jmh.annotations.*;

import static ch.styp.Helpers.GeneratorHelpers.newFloatRowMajorMatrix;

@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"-XX:-TieredCompilation"})
public class BlockedMatrixMultiplication {

    @Param({"8", "16"})
    int block_size;
    @Param({"16", "32", "64", "128", "256"})
    int size;
    private float[] left;
    private float[] right;
    private float[] result;
    private BlockedMatrixMul algorithm;


    @Setup(Level.Iteration)
    public void init() {
        this.left = newFloatRowMajorMatrix(size * size);
        this.right = newFloatRowMajorMatrix(size * size);
        this.result = new float[size * size];

        this.algorithm = new BlockedMatrixMul();
    }

    @Benchmark
    public float[] blockedMatrixMultiplicationIJK() {
        return algorithm.blockedIJK(left, right, result, size, block_size);
    }

    @Benchmark
    public float[] blockedMatrixMultiplicationIKJ() {
        return algorithm.blockedIKJ(left, right, result, size, block_size);
    }

}
