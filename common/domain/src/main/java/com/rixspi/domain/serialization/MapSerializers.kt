package com.rixspi.domain.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.internal.NamedValueEncoder

/**
 * This demo shows another approach to serialization:
 * Instead of writing fields and their values separately in two steps, as ElementValueOutput does,
 * in NamedValueOutput they got merged into one call.
 *
 * NamedValue is a subclass of TaggedValue, which allows you to associate any custom tag with object's field,
 * see TaggedDemo.kt.
 *
 * Here, the tag is field's name. Functionality of these classes is similar to kotlinx.serialization.Mapper
 * Note that null values are not supported here.
 *
 * // save
 * val out = MapOutput()
 * out.encode(serializer, obj)
 * // load
 * val inp = MapInput(out.map)
 * val other = inp.decode(serializer)
 *
 * return Result(obj, other, "${out.map.size} items ${out.map}")
 */
@InternalSerializationApi
class MapOutput(val map: MutableMap<String, Any> = mutableMapOf()) : NamedValueEncoder() {
    override fun beginCollection(
        descriptor: SerialDescriptor,
        collectionSize: Int
    ): CompositeEncoder {
        encodeTaggedInt(nested("size"), collectionSize)
        return this
    }

    override fun encodeTaggedValue(tag: String, value: Any) {
        map[tag] = value
    }
}


@InternalSerializationApi
class MapInput(val map: Map<String, Any>) : NamedValueDecoder() {
    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return decodeTaggedInt(nested("size"))
    }

    override fun decodeTaggedValue(tag: String): Any {
        return map.getValue(tag)
    }

    @ExperimentalSerializationApi
    override fun decodeSequentially(): Boolean = true
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        TODO("Supports only decodeSequentially for now")
    }
}