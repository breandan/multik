/*
 * Copyright 2020-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package org.jetbrains.kotlinx.multik.ndarray.data

/**
 * View for storing data in a [NDArray] and working them in a uniform style.
 *
 * @property data one of the primitive arrays.
 */
public interface ImmutableMemoryView<T : Number> : Iterable<T> {
    public val data: Any

    /**
     * Returns the value at [index].
     *
     * Note: Indexing takes place according to the initial data, so if you did any manipulations with the ndarray
     * (ex. reshape), then `get` from the ndarray with the same index will return another value.
     */
    public operator fun get(index: Int): T

    /**
     * [data] iterator
     */
    public override fun iterator(): Iterator<T>

    /**
     * Returns a new instance with a copied primitive array.
     */
    public fun copyOf(): ImmutableMemoryView<T>

    /**
     * Returns [ByteArray] if it is [MemoryViewByteArray].
     */
    public fun getByteArray(): ByteArray

    /**
     * Returns [ShortArray] if it is [MemoryViewShortArray].
     */
    public fun getShortArray(): ShortArray

    /**
     * Returns [IntArray] if it is [MemoryViewIntArray].
     */
    public fun getIntArray(): IntArray

    /**
     * Returns [LongArray] if it is [MemoryViewLongArray].
     */
    public fun getLongArray(): LongArray

    /**
     * Returns [FloatArray] if it is [MemoryViewFloatArray].
     */
    public fun getFloatArray(): FloatArray

    /**
     * Returns [DoubleArray] if it is [MemoryViewDoubleArray].
     */
    public fun getDoubleArray(): DoubleArray
}

/**
 * Extends [ImmutableMemoryView].
 *
 * @property size number of elements in [data].
 * @property indices indices of [data].
 * @property lastIndex last index in [data].
 */
public sealed class MemoryView<T : Number> : ImmutableMemoryView<T> {
    public abstract var size: Int

    public abstract var indices: IntRange

    public abstract var lastIndex: Int

    /**
     * Replaces the element at the given [index] with the specified [value].
     *
     * Note: Indexing takes place according to the initial data.
     */
    public abstract operator fun set(index: Int, value: T): Unit

    public abstract override fun copyOf(): MemoryView<T>

    public override fun getByteArray(): ByteArray = throw UnsupportedOperationException()

    public override fun getShortArray(): ShortArray = throw UnsupportedOperationException()

    public override fun getIntArray(): IntArray = throw UnsupportedOperationException()

    public override fun getLongArray(): LongArray = throw UnsupportedOperationException()

    public override fun getFloatArray(): FloatArray = throw UnsupportedOperationException()

    public override fun getDoubleArray(): DoubleArray = throw UnsupportedOperationException()
}

/**
 * View for [ByteArray].
 */
public class MemoryViewByteArray(override val data: ByteArray) : MemoryView<Byte>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Byte = data[index]

    override fun set(index: Int, value: Byte): Unit {
        data[index] = value
    }

    override fun getByteArray(): ByteArray = data

    override fun iterator(): Iterator<Byte> = data.iterator()

    override fun copyOf(): MemoryView<Byte> = MemoryViewByteArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewByteArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * View for [ShortArray].
 */
public class MemoryViewShortArray(override val data: ShortArray) : MemoryView<Short>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Short = data[index]

    override fun set(index: Int, value: Short): Unit {
        data[index] = value
    }

    override fun getShortArray(): ShortArray = data

    override fun iterator(): Iterator<Short> = data.iterator()

    override fun copyOf(): MemoryView<Short> = MemoryViewShortArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewShortArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * View for [IntArray].
 */
public class MemoryViewIntArray(override val data: IntArray) : MemoryView<Int>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Int = data[index]

    override fun set(index: Int, value: Int): Unit {
        data[index] = value
    }

    override fun getIntArray(): IntArray = data

    override fun iterator(): Iterator<Int> = data.iterator()

    override fun copyOf(): MemoryView<Int> = MemoryViewIntArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewIntArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * View for [LongArray].
 */
public class MemoryViewLongArray(override val data: LongArray) : MemoryView<Long>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Long = data[index]

    override fun set(index: Int, value: Long): Unit {
        data[index] = value
    }

    override fun getLongArray(): LongArray = data

    override fun iterator(): Iterator<Long> = data.iterator()

    override fun copyOf(): MemoryView<Long> = MemoryViewLongArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewLongArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * View for [FloatArray].
 */
public class MemoryViewFloatArray(override val data: FloatArray) : MemoryView<Float>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Float = data[index]

    override fun set(index: Int, value: Float): Unit {
        data[index] = value
    }

    override fun getFloatArray(): FloatArray = data

    override fun iterator(): Iterator<Float> = data.iterator()

    override fun copyOf(): MemoryView<Float> = MemoryViewFloatArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewFloatArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * View for [DoubleArray].
 */
public class MemoryViewDoubleArray(override val data: DoubleArray) : MemoryView<Double>() {
    override var size: Int = data.size

    override var indices: IntRange = data.indices

    override var lastIndex: Int = data.lastIndex

    override fun get(index: Int): Double = data[index]

    override fun set(index: Int, value: Double): Unit {
        data[index] = value
    }

    override fun getDoubleArray(): DoubleArray = data

    override fun iterator(): Iterator<Double> = data.iterator()

    override fun copyOf(): MemoryView<Double> = MemoryViewDoubleArray(data.copyOf())

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            other !is MemoryViewDoubleArray -> false
            size != other.size -> false
            else -> (0 until size).all { this.data[it] == other.data[it] }
        }
    }

    override fun hashCode(): Int =
        (0 until size).fold(1) { acc, r ->
            31 * acc + data[r].hashCode()
        }
}

/**
 * Creates a [MemoryView] based [size] and [dataType].
 */
public fun <T : Number> initMemoryView(size: Int, dataType: DataType): MemoryView<T> {
    val t = when (dataType.nativeCode) {
        1 -> MemoryViewByteArray(ByteArray(size))
        2 -> MemoryViewShortArray(ShortArray(size))
        3 -> MemoryViewIntArray(IntArray(size))
        4 -> MemoryViewLongArray(LongArray(size))
        5 -> MemoryViewFloatArray(FloatArray(size))
        6 -> MemoryViewDoubleArray(DoubleArray(size))
        else -> throw Exception("Unknown datatype: ${dataType.name}")
    }
    @Suppress("UNCHECKED_CAST")
    return t as MemoryView<T>
}

/**
 * Create a [MemoryView] based [size] and [dataType], where each elements will be initialized according
 * to the given [init] function.
 */
@Suppress("UNCHECKED_CAST")
public fun <T : Number> initMemoryView(size: Int, dataType: DataType, init: (Int) -> T): MemoryView<T> {
    val t = when (dataType.nativeCode) {
        1 -> MemoryViewByteArray(ByteArray(size, init as (Int) -> Byte))
        2 -> MemoryViewShortArray(ShortArray(size, init as (Int) -> Short))
        3 -> MemoryViewIntArray(IntArray(size, init as (Int) -> Int))
        4 -> MemoryViewLongArray(LongArray(size, init as (Int) -> Long))
        5 -> MemoryViewFloatArray(FloatArray(size, init as (Int) -> Float))
        6 -> MemoryViewDoubleArray(DoubleArray(size, init as (Int) -> Double))
        else -> throw Exception("Unknown datatype: ${dataType.name}")
    }
    return t as MemoryView<T>
}

@Suppress("UNCHECKED_CAST")
public fun <T : Number> List<T>.toViewPrimitiveArray(dataType: DataType): MemoryView<T> {
    val t = when (dataType.nativeCode) {
        1 -> MemoryViewByteArray((this as List<Byte>).toByteArray())
        2 -> MemoryViewShortArray((this as List<Short>).toShortArray())
        3 -> MemoryViewIntArray((this as List<Int>).toIntArray())
        4 -> MemoryViewLongArray((this as List<Long>).toLongArray())
        5 -> MemoryViewFloatArray((this as List<Float>).toFloatArray())
        6 -> MemoryViewDoubleArray((this as List<Double>).toDoubleArray())
        else -> throw Exception("Unknown datatype: ${dataType.name}")
    }
    return t as MemoryView<T>
}
