package ch.styp;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import static ch.styp.GeneratorHelpers.newFloatRowMajorMatrix;


//@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})
public class FloatMatrixMatrixMultiplication {

    //@Param({"64", "512", "1024"})
    @Param({"512"})
    int size;

//    @Param({"8", "16", "32", "64", "128", "256", "512"})
//    int blocksize;

    private float[] left;
    private float[] right;

    @Setup(Level.Iteration)
    public void init() {
        this.left = newFloatRowMajorMatrix(size * size);
        this.right = newFloatRowMajorMatrix(size * size);
    }

    @Benchmark
    public void mmBaseline(Blackhole bh) {
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.baseline(left, right, size));
    }

//    @Benchmark

//    public void mmBlocked(Blackhole bh) {
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blocked(left, right, size, blocksize));
//    }
//
//    @Benchmark
//    public void mmSimpleFma(Blackhole bh) {
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.simpleFMA(left, right, size));
//    }
//
    @Benchmark
    public void mmSimpleVector(Blackhole bh) {
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.simpleVector(left, right, size));
    }

    @Benchmark
    public void mmBlockedVector(Blackhole bh){
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.blockedVector(left, right, size));
    }
}
