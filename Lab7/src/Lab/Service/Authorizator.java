package Lab.Service;

import Lab.Commands.Meta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Authorizator {
    private final static Logger logger = LogManager.getLogger();
    private static final File file = new File("users.cfg");
    private static boolean isCreated;

    private static class AuthorizatorHolder{
        static final Authorizator a = new Authorizator();
    }
    private Authorizator(){
        if(!FileTester.TestFileToExist(file.toPath())) {
            try {
                file.createNewFile();
            }
            catch (IOException e){
                logger.error("Не удаётся создать системный файл users");
                isCreated=false;
            }
        }
        isCreated= file.setWritable(true, true) &&
                file.setReadable(true, true);
        if(!isCreated)
            logger.error("Невозможно установить нужные права для системного файла users");
    }
    public static boolean create(){
        return AuthorizatorHolder.a.isCreated;
    }
    public static Authorizator getInstance(){
        return AuthorizatorHolder.a;
    }
    static synchronized boolean authorize(Meta meta) throws IOException, NoSuchAlgorithmException {
        if(!isCreated)
            throw new IOException("Нужный файл не существует");
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        String username = meta.getUsername();
        String pepper = "$&5?6%5.7#^Gf!)^";
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while(line!=null){
                String[] parameters = line.split(":");
                if(parameters.length==3&&parameters[0].equals(username)){
                    String salt = parameters[1];
                    String password = pepper.concat(meta.getPassword()).concat(salt);
                    byte[] bytes =  crypt.digest(password.getBytes());
                    crypt.reset();
                    if(Helper.getHexString(bytes).equals(parameters[2]))
                        return true;
                    return false;
                }
                line=reader.readLine();
            }
        }
        try(PrintWriter writer =new PrintWriter( new BufferedWriter(new FileWriter(file,true)))){
            Random random = new Random();
            byte MIN_VALUE=33;
            byte MAX_VALUE=126;
            int border = random.nextInt(11);
            int[] buffer = random.ints(MIN_VALUE, MAX_VALUE+1)
                    .limit(border).toArray();
            byte[] bytes = new byte[border];
            for (int i=0;i<border;i++){
                bytes[i] = (byte)buffer[i];
            }
            String salt = new String(bytes);
            bytes = pepper.concat(meta.getPassword()).concat(salt).
                    getBytes();
            bytes= crypt.digest(bytes);
            crypt.reset();
            String line = Helper.getHexString(bytes);
            line = meta.getUsername().concat(":").concat(salt).concat(":").concat(line);
            writer.println(line);
            return true;
        }
    }
}
