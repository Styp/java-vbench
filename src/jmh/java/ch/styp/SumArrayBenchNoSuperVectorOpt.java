package ch.styp;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import jdk.incubator.vector.IntVector;


@State(Scope.Benchmark)
@Fork(jvmArgsAppend = "--add-modules jdk.incubator.vector, -XX:+UseSuperWord")
public class SumArrayBenchNoSuperVectorOpt {

    // All these numbers are 2^n-1 to avoid memory alignment!
    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
    private int LENGTH;
    private int[] a;
    private int[] b;


    @Setup(Level.Iteration)
    public void init(){
        this.a = GeneratorHelpers.initIntArray(LENGTH);
        this.b = GeneratorHelpers.initIntArray(LENGTH);
    }

    @Benchmark
    public void arraySumScalarNoOpt(Blackhole bh) {
        bh.consume(SumArray.scalarComputation(a, b));
    }

}
