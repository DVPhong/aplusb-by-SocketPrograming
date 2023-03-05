import java.net.Socket;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;
public class aplusb {
  static int a = 0;
  static int b = 0;

  public static void main(String[] args) throws Exception {
    //Create socket for client conneting to server
    Socket clientSocket = new Socket("112.137.129.129", 27001);
    Scanner scanner = new Scanner(System.in);
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();
    while (true) {
      String input = scanner.nextLine();
      //String inType;
      byte[] packet;
      if (input.equals("t0")) {
        packet = createPackage0();
        //Gửi gói tin tới server

        outputStream.write(packet);
        outputStream.flush();

        // Nhận phản hồi

        byte[] responsePacket = new byte[16];
        inputStream.read(responsePacket);
        ByteBuffer responseBuffer = ByteBuffer.wrap(responsePacket);
        responseBuffer.order(ByteOrder.LITTLE_ENDIAN);
        a = responseBuffer.getInt(8);
        b = responseBuffer.getInt(12);
        System.out.println(a+" "+ b);
      } else if (input.equals("t2")) {
        packet = createPackage2();
        outputStream.write(packet);
        outputStream.flush();

        ByteBuffer buffer4 = ByteBuffer.allocate(50);
        buffer4.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(buffer4.array());
        inputStream.read(buffer4.array());
        System.out.println(buffer4.array());
      }
      else break;
    }

    clientSocket.close();
  }

  static byte[] createPackage0() {
    // Khai báo trường type, data và len
    int type0 = 0;
    String data0 = "21020375";
    int len = data0.length();

    //Tạo mảng byte lưu trữ toàn bộ gói tin
    byte[] packet0 = new byte[16];

    //Lớp byteBuffer để đóng gói
    ByteBuffer buffer = ByteBuffer.wrap(packet0);
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    buffer.putInt(type0);
    buffer.putInt(len);
    buffer.put(data0.getBytes());

    return packet0;
  }

  static byte[] createPackage2() {
    int type2 = 2;
    int data2 = a + b;
    int len2 = 4;

    //Tạo mảng byte lưu trữ toàn bộ gói tin
    byte[] packet2 = new byte[12];

    //Lớp byteBuffer để đóng gói
    ByteBuffer buffer2 = ByteBuffer.wrap(packet2);
    buffer2.order(ByteOrder.LITTLE_ENDIAN);
    buffer2.putInt(type2);
    buffer2.putInt(len2);
    buffer2.putInt(data2);

    return packet2;
  }


}
