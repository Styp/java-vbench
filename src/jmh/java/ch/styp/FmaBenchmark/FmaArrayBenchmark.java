package ch.styp.FmaBenchmark;

import ch.styp.FmaArray;
import ch.styp.GeneratorHelpers;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
        "-XX:-TieredCompilation",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})

public class FmaArrayBenchmark {

    // All these numbers are 2^n-1 to avoid memory alignment!
    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
    private int LENGTH;
    private float[] a;
    private float[] b;
    private float result;
    private FmaArray algorithm;


    @Setup(Level.Iteration)
    public void init(){
        this.a = GeneratorHelpers.initFloatArray(LENGTH);
        this.b = GeneratorHelpers.initFloatArray(LENGTH);
        this.result = 0.0f;

        this.algorithm = new FmaArray();
    }

    @Benchmark
    public float arrayFmaScalarByHand(){
        return algorithm.scalarFMAbyHand(a, b, result);
    }

    @Benchmark
    public float arrayFmaScalarByMathLib(){
        return algorithm.scalarFMAbyMathLib(a, b, result);
    }

    @Benchmark
    public float arrayFmaVector(){
        return algorithm.vectorFMA(a, b, result);
    }

}
