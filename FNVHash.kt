package com.huawei.kotlin


class FNVHash {
    companion object {

        private val FNV_64_INIT = -0x340d631b7bdddcdbL
        private val FNV_64_PRIME = 0x100000001b3L

        fun hash64(str: CharSequence): Long {
            var hash = FNV_64_INIT
            val len = str.length
            for (i in 0 until len) {
                val c = str[i]
                hash  = hash xor c.toLong()
                hash *= FNV_64_PRIME
            }
            return hash
        }

        fun hash64(str: String): Long {
            var hash = FNV_64_INIT
            val len = str.length
            for (i in 0 until len) {
                val c = str[i]
                hash = hash xor c.toLong()
                hash *= FNV_64_PRIME
            }
            return hash
        }
    }
}

fun main(args: Array<String>) {
    println(FNVHash.hash64("ttest"))
}
