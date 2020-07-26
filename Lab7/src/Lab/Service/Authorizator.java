package Lab.Service;

import Lab.Commands.Meta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Authorizator {
    private final static Logger logger = LogManager.getLogger();
    private static final File file = new File("users.cfg");
    private static boolean isCreated;
    private final static Map<String,String> users  = new ConcurrentHashMap<>();
    private final static String pepper = "$&5?6%5.7#^Gf!)^";

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
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while(line!=null){
                String[] parameters = line.split(":");
                if(parameters.length==3) {
                    String user = parameters[0];
                    String password = String.join(":",parameters[1],parameters[2]);
                    users.put(user,password);
                    line=reader.readLine();
                }
                else {
                    logger.error("Неверный формат файла конфигурации\n");
                    isCreated=false;
                    throw new IOException();
                }
            }
        }
        catch (IOException e){
            logger.error("Ошибка при работе с файлом конфигруации");
        }
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
        if(users.containsKey(username)){
            String[] parameters = users.get(username).split(":");
            String salt = parameters[0];
            String password = pepper.concat(meta.getPassword()).concat(salt);
            byte[] bytes =  crypt.digest(password.getBytes());
            crypt.reset();
            return Helper.getHexString(bytes).equals(parameters[1]);
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
            String password = Helper.getHexString(bytes);
            String line = meta.getUsername().concat(":").concat(salt).concat(":").concat(password);
            writer.println(line);
            users.put(meta.getUsername(),salt.concat(":").concat(password));
            return true;
        }
    }
}
