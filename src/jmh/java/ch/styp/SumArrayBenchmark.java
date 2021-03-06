//package ch.styp;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.infra.Blackhole;
//
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//
//@State(Scope.Benchmark)
//@Fork(jvmArgsPrepend = {"--add-modules=jdk.incubator.vector",
//        "-XX:-TieredCompilation",
//        "-XX:+UseVectorCmov",
//        "-XX:+UseCMoveUnconditionally",
//        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"})
//
//public class SumArrayBenchmark {
//
//    // All these numbers are 2^n-1 to avoid memory alignment!
//    @Param({"15", "255", "4095", "65535", "1048575", "16777215", "268435455"})
//
////    @Param({"53", "541", "5381"})
//    private int LENGTH;
//    private int[] a;
//    private int[] b;
//
//
//    @Setup(Level.Iteration)
//    public void init() {
//        this.a = GeneratorHelpers.initIntArray(LENGTH);
//        this.b = GeneratorHelpers.initIntArray(LENGTH);
//    }
//
//    @Benchmark
//    public void arraySumScalar(Blackhole bh) {
//        bh.consume(SumArray.scalarComputation(a, b));
//    }
//
//    @Benchmark
//    public void arraySumVector(Blackhole bh) {
//        bh.consume(SumArray.vectorComputation(a, b));
//    }
//
////    @Benchmark
////    public void arrayFmaScalar(Blackhole bh){
////        bh.consume(SumArray.scalarFMA(a, b));
////    }
////
////    @Benchmark
////    public void arrayFmaVector(Blackhole bh){
////        bh.consume(SumArray.vectorFMA(a, b));
////    }
//
//}