package ch.styp;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(jvmArgsAppend = "--add-modules jdk.incubator.vector")
public class FmaArrayBenchmark {

    // All these numbers are 2^n-1 to avoid memory alignment!
    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
    private int LENGTH;
    private float[] a;
    private float[] b;


    @Setup(Level.Iteration)
    public void init(){
        this.a = GeneratorHelpers.initFloatArray(LENGTH);
        this.b = GeneratorHelpers.initFloatArray(LENGTH);
    }


    @Benchmark
    public void arrayFmaScalar(Blackhole bh){
        bh.consume(FmaArray.scalarFMA(a, b));
    }

    @Benchmark
    public void arrayFmaVector(Blackhole bh){
        bh.consume(FmaArray.vectorFMA(a, b));
    }

}
