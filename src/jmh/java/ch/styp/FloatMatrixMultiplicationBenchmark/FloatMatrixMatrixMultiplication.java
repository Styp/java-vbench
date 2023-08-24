package ch.styp.FloatMatrixMultiplicationBenchmark;

import ch.styp.MatrixMul;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import static ch.styp.Helpers.GeneratorHelpers.newFloatRowMajorMatrix;


//@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})
public class FloatMatrixMatrixMultiplication {

//    private static final int BLOCK_SIZE = 8;


    @Param({"4", "8", "16"})
    int BLOCK_SIZE;

//    @Param({"16", "32", "64", "128", "256", "512", "1024", "2048"})
    @Param({"16", "512", "2048"})
    int size;
    private float[] left;
    private float[] right;
    private float[] result;
    private MatrixMul algorithm;


    @Setup(Level.Iteration)
    public void init() {
        this.left = newFloatRowMajorMatrix(size * size);
        this.right = newFloatRowMajorMatrix(size * size);
        this.result = new float[size * size];

        this.algorithm = new MatrixMul();
    }

    @Benchmark
    public float[] mmBaseline() {
        return algorithm.baseline(left, right, result, size);
    }

    @Benchmark
    public float[] mmBaselineIKJ() {
        return algorithm.baselineIKJ(left, right, result, size);
    }

    @Benchmark
    public float[] mmBlocked() {
        return algorithm.blocked(left, right, result, size, BLOCK_SIZE);
    }

    @Benchmark
    public float[] mmBlockedIKJ() {
        return algorithm.blockedIKJ(left, right, result, size, BLOCK_SIZE);
    }
//
//    @Benchmark
//    public float[] mmSimpleFma() {
//        return algorithm.simpleFMA(left, right, result, size);
//    }
//
//    @Benchmark
//    public float[] mmBlockedFma() {
//        return algorithm.blockedFMA(left, right, result, size, BLOCK_SIZE);
//    }
//
    @Benchmark
    public float[] mmSimpleVectorPreferred() {
        return algorithm.simpleVectorPrefered(left, right, result, size);
    }
//
//    @Benchmark
//    public void mmSimpleVectorAVX256(Blackhole bh) {
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.simpleVectorAVX256(left, right, size));
//    }
//
//    @Benchmark
//    public void mmSimpleVectorAVX512(Blackhole bh) {
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.simpleVectorAVX512(left, right, size));
//    }
//
//    @Benchmark
//    public float[] mmBlockedVectorPrefered(){
//        return algorithm.blockedVectorPrefered(left, right, result, size);
//    }

//    @Benchmark
//    public void mmBlockedVectorAVX256(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorAVX256(left, right, size));
//    }
//
//    @Benchmark
//    public void mmBlockedVectorAVX512(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorAVX512(left, right, size));
//    }
//
//    @Benchmark
//    public void mmBlockedVectorUnrolledAVX256(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorUnrolledAVX256(left, right, size));
//    }
//
//    @Benchmark
//    public void mmBlockedVectorUnrolledAVX512_4(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorUnrolledAVX512_4(left, right, size));
//    }
//
//    @Benchmark
//    public void mmBlockedVectorUnrolledAVX512_8(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorUnrolledAVX512_8(left, right, size));
//    }
}
