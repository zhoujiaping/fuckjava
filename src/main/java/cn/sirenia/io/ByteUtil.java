package cn.sirenia.io;

/**
 * 参考netty的ByteBufUtil#isUtf8
 * */
public class ByteUtil {
    /**
     * 判断一个字节数组内容是否为utf8无bom编码。
     * */
    private static boolean isUtf8(byte[] buf, int index, int length) {
        //utf8 bom
        if(index==0 && length>=3 && buf[0]==-17 && buf[1]==-69 && buf[2]==-65){
            return false;
        }
        final int endIndex = index + length;
        while (index < endIndex) {
            byte b1 = buf[index++];
            byte b2, b3, b4;
            if ((b1 & 0x80) == 0) {
                // 1 byte
                continue;
            }
            if ((b1 & 0xE0) == 0xC0) {
                // 2 bytes
                //
                // Bit/Byte pattern
                // 110xxxxx    10xxxxxx
                // C2..DF      80..BF
                if (index >= endIndex) { // no enough bytes
                    return false;
                }
                b2 = buf[index++];
                if ((b2 & 0xC0) != 0x80) { // 2nd byte not starts with 10
                    return false;
                }
                if ((b1 & 0xFF) < 0xC2) { // out of lower bound
                    return false;
                }
            } else if ((b1 & 0xF0) == 0xE0) {
                // 3 bytes
                //
                // Bit/Byte pattern
                // 1110xxxx    10xxxxxx    10xxxxxx
                // E0          A0..BF      80..BF
                // E1..EC      80..BF      80..BF
                // ED          80..9F      80..BF
                // E1..EF      80..BF      80..BF
                if (index > endIndex - 2) { // no enough bytes
                    return false;
                }
                b2 = buf[index++];
                b3 = buf[index++];
                if ((b2 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80) { // 2nd or 3rd bytes not start with 10
                    return false;
                }
                if ((b1 & 0x0F) == 0x00 && (b2 & 0xFF) < 0xA0) { // out of lower bound
                    return false;
                }
                if ((b1 & 0x0F) == 0x0D && (b2 & 0xFF) > 0x9F) { // out of upper bound
                    return false;
                }
            } else if ((b1 & 0xF8) == 0xF0) {
                // 4 bytes
                //
                // Bit/Byte pattern
                // 11110xxx    10xxxxxx    10xxxxxx    10xxxxxx
                // F0          90..BF      80..BF      80..BF
                // F1..F3      80..BF      80..BF      80..BF
                // F4          80..8F      80..BF      80..BF
                if (index > endIndex - 3) { // no enough bytes
                    return false;
                }
                b2 = buf[index++];
                b3 = buf[index++];
                b4 = buf[index++];
                if ((b2 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80 || (b4 & 0xC0) != 0x80) {
                    // 2nd, 3rd or 4th bytes not start with 10
                    return false;
                }
                if ((b1 & 0xFF) > 0xF4 // b1 invalid
                        || (b1 & 0xFF) == 0xF0 && (b2 & 0xFF) < 0x90    // b2 out of lower bound
                        || (b1 & 0xFF) == 0xF4 && (b2 & 0xFF) > 0x8F) { // b2 out of upper bound
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
