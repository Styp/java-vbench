package ch.styp.SumArrayBenchmark;

import ch.styp.Helpers.GeneratorHelpers;
import ch.styp.SumArrayAlgorithm;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})

public class SumArrayBenchmark {

    // All these numbers are 2^n-1 to avoid memory alignment!
    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
    private int LENGTH;
    private int[] a;
    private int[] b;
    private int[] result;
    private SumArrayAlgorithm algorithm;

    @Setup(Level.Iteration)
    public void init() {
        this.a = GeneratorHelpers.initIntArray(LENGTH);
        this.b = GeneratorHelpers.initIntArray(LENGTH);

        this.result = new int[LENGTH];
        this.algorithm = new SumArrayAlgorithm();
    }

    @Benchmark
    public int[] arraySumScalar() {
        return algorithm.scalarComputation(a, b, result);
    }

    @Benchmark
    public int[] arraySumVector(Blackhole bh) {
        return algorithm.vectorComputation(a, b, result);
    }


//    @Benchmark
//    public void arrayFmaScalar(Blackhole bh){
//        bh.consume(SumArrayAlgorithm.scalarFMA(a, b));
//    }
//
//    @Benchmark
//    public void arrayFmaVector(Blackhole bh){
//        bh.consume(SumArrayAlgorithm.vectorFMA(a, b));
//    }

}