package ch.styp.SumArrayBenchmark;

import ch.styp.Helpers.GeneratorHelpers;
import ch.styp.SumArrayAlgorithm;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-XX:-UseSuperWord",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})
public class SumArrayNoSuperWord {

    // All these numbers are 2^n-1 to avoid memory alignment!
    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
    private int LENGTH;
    private int[] a;
    private int[] b;
    private int[] result;
    private SumArrayAlgorithm algorithm;

    @Setup(Level.Iteration)
    public void init(){
        this.a = GeneratorHelpers.initIntArray(LENGTH);
        this.b = GeneratorHelpers.initIntArray(LENGTH);

        this.result = new int[LENGTH];
        this.algorithm = new SumArrayAlgorithm();
    }

    @Benchmark
    public int[] arraySumScalarNoOpt() {
        return algorithm.scalarComputation(a, b, result);
    }

}
