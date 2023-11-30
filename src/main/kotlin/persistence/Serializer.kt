package persistence

import java.lang.Exception

interface Serializer {
    @Throws(Exception::class)
    fun write(obj:Any?)

    @Throws(Exception::class)
    fun read(): Any?

}