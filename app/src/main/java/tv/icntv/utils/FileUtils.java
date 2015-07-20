package tv.icntv.utils;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {


    public FileUtils (){
    }

    private static FileChannel getOutChannel(String currentFile ){
        try {


            return new RandomAccessFile(currentFile,"rw").getChannel();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    };

    public static void write(String message,String file){
        FileChannel channel=getOutChannel(file);
        try {
            channel.position(channel.size());
            channel.write(ByteBuffer.wrap(message.getBytes("utf-8")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            channel.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static String getString(InputStream message) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(message));
        StringBuilder buffer = new StringBuilder();
        while (true) {
            String line = null;

            line = reader.readLine();

            if (line == null) {
                break;
            }
            buffer.append(line+"\r\n");
        }
        message.close();
        reader.close();
        return buffer.toString();
    }
}
