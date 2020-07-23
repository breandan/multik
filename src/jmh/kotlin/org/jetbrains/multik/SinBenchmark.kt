package org.jetbrains.multik

import org.jetbrains.multik.api.JvmMath
import org.jetbrains.multik.api.d2array
import org.jetbrains.multik.api.mk
import org.jetbrains.multik.core.D2
import org.jetbrains.multik.core.D2Array
import org.jetbrains.multik.core.Ndarray
import org.jetbrains.multik.jni.NativeMath
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.sin
import kotlin.random.Random

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(value = 2, jvmArgsPrepend = ["-Djava.library.path=./build/libs"])
open class SinBenchmark {
    @Param("10", "100", "1000")
    var size: Int = 0
    private lateinit var arg: D2Array<Double>
    private lateinit var result: Ndarray<Double, D2>
    private lateinit var ran: Random

    @Setup
    fun generate() {
        System.load("/Users/pavel.gorgulov/Projects/main_project/multik/src/jni_multik/cmake-build-debug/libjni_multik.dylib")
        ran = Random(1)
        arg = mk.d2array(size, size) { ran.nextDouble() }
    }

    @TearDown
    fun assertResult() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                val expected = sin(arg[i, j])
                if (expected != result[i, j]) throw IllegalStateException("Sin calculation error: actual - sin(${arg[i, j]}) = ${result[i, j]}, expected - $expected")
            }
        }
    }

    @Benchmark
    fun sinJniBench(bh: Blackhole) {
        result = NativeMath.sin(arg)
        bh.consume(result)
    }

    @Benchmark
    fun sinJvmBench(bh: Blackhole) {
        result = JvmMath.sin(arg)
        bh.consume(result)
    }
}