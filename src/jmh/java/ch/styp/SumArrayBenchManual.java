/*
package ch.styp;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;


@State(Scope.Benchmark)
@Fork(jvmArgsAppend = "--add-modules jdk.incubator.vector")
public class SumArrayBenchManual {

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
    public void arraySumVector128(Blackhole bh){
        bh.consume(SumArrayAlgorithm.vectorComputation(a, b, IntVector.SPECIES_128));
    }

    @Benchmark
    public void arraySumVector256(Blackhole bh){
        bh.consume(SumArrayAlgorithm.vectorComputation(a, b, IntVector.SPECIES_256));
    }

    @Benchmark
    public void arraySumVector512(Blackhole bh){
        bh.consume(SumArrayAlgorithm.vectorComputation(a, b, IntVector.SPECIES_512));
    }

    @Benchmark
    public void arraySumVectorAuto(Blackhole bh){
        bh.consume(SumArrayAlgorithm.vectorComputation(a, b, IntVector.SPECIES_PREFERRED));
    }

    @Benchmark
    public void arraySumScalar(Blackhole bh) {
        bh.consume(SumArrayAlgorithm.scalarComputation(a, b));
    }

}
*/
