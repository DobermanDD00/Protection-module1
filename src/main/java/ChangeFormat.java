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


    public static String byteToHexStr(byte[] bytes) {
        HexFormat hex = HexFormat.ofDelimiter(" ").withUpperCase();
        return "X'" + hex.formatHex(bytes) + "\'";

    }
    public static int strToInt(String str){
        long i;
        if (str.matches("\\d{1,10}")){
                try{
                    // именно здесь String преобразуется в int
                    i = Long.parseLong(str.trim());

                    // выведем на экран значение после конвертации
                    System.out.println("int i = " + i);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка в приведении строки в число");
                    throw new RuntimeException(e);
                }

                if (i > 0 && i <= 2147483647)
                    return (int) i;
                else
                    return -1;

            }
            else {
                return -1;
            }

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
