package com.opennxt.tools.impl.other

import com.opennxt.filesystem.Container
import com.opennxt.tools.Tool
import io.netty.buffer.Unpooled
import kotlin.system.exitProcess

class ModelDecoderTest : Tool(name = "model-decoder", help = "attempts to decode models") {
    override fun runTool() {
        val printValues = false

        val table = filesystem.getReferenceTable(47)!!
        table.archives.forEach { (k, _) ->
            if ((k % 500) == 0) println("decoding $k/${table.highestEntry()}")
            val data = Unpooled.wrappedBuffer(Container.decode(filesystem.read(47, k)!!).data)

            data.readerIndex(3) // TODO Header is un-read?
            val size = data.readUnsignedShortLE()
            val byte_0x5 = data.readUnsignedByte()
            val byte_0x6 = data.readUnsignedByte()
            val byte_0x7 = data.readUnsignedByte()
            if (printValues) println("size = $size")
            if (printValues) println("byte_0x5 = $byte_0x5")
            if (printValues) println("byte_0x6 = $byte_0x6")
            if (printValues) println("byte_0x7 = $byte_0x7")

            for (i in 0 until size) {

                val intmask = data.readIntLE()
                val type = data.readUnsignedByte()
                val noclue = data.readUnsignedShortLE()
                if (printValues) println("@$i")
                if (printValues) println(" int=$intmask, byte=$type short=$noclue")
                if (printValues) println(" entering FUN_003172f0")

                val read_size = data.readUnsignedShortLE()
                if (printValues) println("  read_size=$read_size") // <- int from above is used here

                val intmask_bit0 = (intmask and 0x1)
                val intmask_bit1 = (((intmask) shr 1) and 0x1)
                val intmask_bit4 = (((intmask) shr 4) and 0x1)

                if (printValues) println("  bit0=$intmask_bit0, bit1=$intmask_bit1, bit4=$intmask_bit4")
                if (printValues) println("")

                if (printValues) println("  bit0x0=${(intmask and 0x1) == 0} (read if false, from cache or idk if true)")
                if ((intmask and 0x1) == 0) {
                    if (printValues) println("Not sure how to handle intmask_bit0 == 0")
                } else {
                    val values = IntArray(read_size) { data.readUnsignedShortLE() }
                    if (printValues) println("   bit0x0=${values.contentToString()}")
                }
                if (printValues) println("")

                if (printValues) println("  bit0x1=$intmask_bit1")
                if (intmask_bit1 == 0) {
                    if (printValues) println("   TODO: It loads from cache or something? idk")
                } else {
                    val values = IntArray(read_size) { data.readUnsignedByte().toInt() }
                    if (printValues) println("   bit0x4=${values.contentToString()}")
                }

                if (printValues) println("")
                if (printValues) println("  bit0x4=${(intmask and 0x4) != 0}")
                if ((intmask and 0x4) != 0) {
                    val values = IntArray(read_size) { data.readUnsignedShortLE() }
                    if (printValues) println("   bit0x4=${values.contentToString()}")
                }

                val blubAmAFish = data.readUnsignedByte().toInt()
                if (printValues) println("\n  b2_0x0=$blubAmAFish")
                for (j in 0 until blubAmAFish) {
                    val blockSize = data.readUnsignedShortLE()
                    val values = IntArray(blockSize) { data.readUnsignedShortLE() }
                    if (printValues) println("   count=$blockSize")
                    if (printValues) println("   b2_0x0_$j=${values.contentToString()}")
                }

                val readsize2 = data.readUnsignedShortLE() // line 167
                if (printValues) println("\n  readsize2=$readsize2 (vertices?)")
                run { // from this shit
                    val a = ArrayList<Int>()
                    val b = ArrayList<Int>()
                    val c = ArrayList<Int>()
                    for (j in 0 until readsize2) {
                        a += data.readUnsignedShortLE()
                        b += data.readUnsignedShortLE()
                        c += data.readUnsignedShortLE()
                    }

                    if (printValues) println("   a=$a")
                    if (printValues) println("   b=$b")
                    if (printValues) println("   c=$c")

                    val idk = data.readBytes(readsize2 * 3).release()

                    val idk2 = data.readBytes(readsize2 * 4).release()

                    val idk3 = IntArray(readsize2 * 2) { data.readUnsignedShortLE() }
                    if (printValues) println("   idk3=${idk3.contentToString()}")

                    if ((intmask and 0x8) != 0) {
                        val values = IntArray(readsize2) { data.readUnsignedShortLE() }
                        if (printValues) println("   idk4=${values.contentToString()}")
                    }
                }
            }

            for (i in 0 until byte_0x5) {
                val someByte = data.readUnsignedByte()
                val someShort1 = data.readUnsignedShortLE()
                val someShort2 = data.readUnsignedShortLE()

                val someShort3 = data.readUnsignedShortLE()
                for (i in 0 until someShort3) {
                    val someInt1 = data.readIntLE()
                    val someInt2 = data.readIntLE()
                    val someInt3 = data.readIntLE()
                    val someInt4 = data.readIntLE()
                    val someInt5 = data.readIntLE()
                    val someShort6 = data.readShortLE()
                    val someByte7 = data.readUnsignedByte()
                    val someShort8 = data.readShortLE()
                    val someShort9 = data.readShortLE()
                    val someShort10 = data.readShortLE()
                    val someByte11 = data.readUnsignedByte()
                }
            }

            for (i in 0 until byte_0x6) {
                val someShort1 = data.readUnsignedShortLE()//2
                val someInt2 = data.readIntLE()//6
                val someInt3 = data.readIntLE()//10
                val someInt4 = data.readIntLE()//14
                val someShort5 = data.readUnsignedShortLE()//16
                val someInt6 = data.readIntLE()//20
                val someInt7 = data.readIntLE()//24
                val someInt8 = data.readIntLE()//28
                val someShort9 = data.readUnsignedShortLE()//32
                val someInt10 = data.readIntLE()//36
                val someInt11 = data.readIntLE()//40
                val someInt12 = data.readIntLE()//40
                val someShort13 = data.readUnsignedShortLE()//42
            }

            for (i in 0 until byte_0x7) {
                val someShort1 = data.readShortLE()
                val someInt2 =  data.readIntLE()
                val someInt3 =  data.readIntLE()
                val someInt4 =  data.readIntLE()
                val someShort5 = data.readShortLE()
            }

            if (data.isReadable) {
                println("STILL READABLE: ${data.readableBytes()} ON $k")
                if (k == 104781) {
                    println("  But it's \"that\" model")
                } else {
                    exitProcess(1)
                }
            }

            data.release()
        }
    }
}