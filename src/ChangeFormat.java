import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HexFormat;

public class ChangeFormat {
    public static byte[] intToBytes(int i){
        byte[] res = new byte[4];
        ByteBuffer byteBuffer = ByteBuffer.wrap(res);
        byteBuffer.putInt(i);
        return res;

    }
    public static int bytesToInt(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getInt();
    }
    public static byte[] stringToBytes(String str){

        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Исключение: строка не преобразована в массив байтов");
            throw new RuntimeException(e);
        }
    }
    public static String bytesToString(byte[] bytes){

        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Исключение: массив байтов не преобразован в строку");
            throw new RuntimeException(e);
        }
    }

    public static String byteToHexStr(byte[] bytes) {
        HexFormat hex = HexFormat.ofDelimiter(" ").withUpperCase();
        return "X'" + hex.formatHex(bytes) + "\'";

    }

    public static void main(String[] args) {
//        int i = 1451343333;
//        System.out.println(i);
//        byte[] bytes = intToBytes(i);
//        for (byte b: bytes)
//            System.out.println(b);
//
//        System.out.println(bytesToInt(bytes));


//        String str = "ldfsarklsgjd";
//        System.out.println(str);
//        System.out.println(Arrays.toString(stringToBytes(str)));
//        System.out.println(bytesToString(stringToBytes(str)));


    }

}
