//package ch.styp;
//
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.BenchmarkMode;
//import org.openjdk.jmh.annotations.Mode;
//import org.openjdk.jmh.annotations.OutputTimeUnit;
//import org.openjdk.jmh.annotations.Scope;
//import org.openjdk.jmh.annotations.State;
//import org.openjdk.jmh.infra.Blackhole;
//import java.util.concurrent.TimeUnit;
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@BenchmarkMode(Mode.AverageTime)
//public class BooleanPoolingJMH
//{
//    @State(Scope.Benchmark)
//    public static class MyBenchmarkState
//    {
//        final Boolean[] testArray = new Boolean[10_000_000];
//    }
//    @Benchmark
//    public void newlyCreatedBooleans(MyBenchmarkState state,
//                                     Blackhole blackhole)
//    {
//        for (int i = 0; i < state.testArray.length; i++)
//        {
//            state.testArray[i] = new Boolean(true);
//        }
//        blackhole.consume(state.testArray);
//    }
//    @Benchmark
//    public void populateWithPooledValue(MyBenchmarkState state,
//                                        Blackhole blackhole)
//    {
//        for (int i = 0; i < state.testArray.length; i++)
//        {
//            state.testArray[i] = Boolean.TRUE;
//        }
//        blackhole.consume(state.testArray);
//    }
//}