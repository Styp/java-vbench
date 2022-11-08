package ch.styp;

import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import static ch.styp.GeneratorHelpers.newFloatRowMajorMatrix;
import jdk.incubator.vector.FloatVector;



//@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})
public class FloatMatrixMatrixMultiplication {

    @Param({"32", "64", "128", "256", "512", "1024", "2048"})
//    @Param({"128"})
    int size;


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

    @Benchmark
    public void mmBlocked(Blackhole bh) {
        var matrixMul = new MatrixMul();
        int blocksize = 16;
        bh.consume(matrixMul.blocked(left, right, size, blocksize));
    }

    @Benchmark
    public void mmSimpleFma(Blackhole bh) {
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.simpleFMA(left, right, size));
    }

    public void mmSimpleVectorPrefered(Blackhole bh) {
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.simpleVectorPrefered(left, right, size));
    }

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

    @Benchmark
    public void mmBlockedVectorPrefered(Blackhole bh){
        var matrixMul = new MatrixMul();
        bh.consume(matrixMul.blockedVectorPrefered(left, right, size));
    }
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
//    public void mmBlockedVectorUnrolledAVX512(Blackhole bh){
//        var matrixMul = new MatrixMul();
//        bh.consume(matrixMul.blockedVectorUnrolledAVX512(left, right, size));
//    }
}
