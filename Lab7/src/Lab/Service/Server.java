package Lab.Service;

import Lab.Commands.Meta;
import Lab.Communication.Communicator;
import Lab.Communication.ExitException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;


public class Server {
    Communicator communicator = new Communicator();
    Answer answer;
    Meta meta;
    private final static Logger logger = LogManager.getLogger(Server.class);
    static Answer IOAnswer = new Answer("Ошибка совместимости",
            false,true);
    static Answer ClassNotFoundAnswer = new Answer("Отправлен неверный класс",
            false,true);
    private static final Answer authorized = new Answer("Вы авторизованы",
            true,false);
    private static final Answer wrong = new Answer("Неверный пароль",false,false);
    public boolean start() {
        try {
            if(!Authorizator.create())
                return false;
            try(Database db = new Database()){
                if(!db.uploadCollection())
                    return false;
            }
            logger.info("Коллекция загружена");
            try {
                communicator.open();
            } catch (IOException e) {
                logger.error("Не удалось открыть соединение");
                return false;
            }
            logger.info("Сервер начал работу");
            while (true) {
                try {
                    if (communicator.receiveMessage()) {
                        try {
                            meta = communicator.getMeta();
                            boolean isAuthorized;
                            isAuthorized=Authorizator.authorize(meta);
                            if(isAuthorized&&meta.getName()!=null) {
                            }
                            else if(isAuthorized) {
                                answer = authorized;
                                logger.info("Пользователь "+meta.getUsername()+
                                        " авторизован");
                            }
                            else {
                                answer = wrong;
                                logger.info("Пользователь "+meta.getUsername()+
                                        " не авторизован");
                            }
                            if (answer.isSuccess()&&meta.getName()!=null)
                                logger.info("Выполнена команда " + meta.getName());
                            else if(meta.getName()!=null)
                                logger.warn("Команда " + meta.getName() + " не смогла быть выполнена");
                        } catch (IOException e) {
                            answer = IOAnswer;
                            logger.warn("С клиента пришло неверное сообщение");
                        } catch (ClassNotFoundException e) {
                            answer = ClassNotFoundAnswer;
                            logger.warn("С клиента пришло неверное сообщение");
                        }
                        try{
                            communicator.sendAnswer(meta.getUsername(), answer);
                            logger.info("Отправлено сообщение пользователю " + meta.getUsername());
                        }
                        catch (IOException e){
                            logger.error("Не удалось отправить сообщение");
                            return false;
                        }
                    }
                }
                catch (ExitException e){
                    logger.info("Инициализирован выход с сервера");
                    return true;
                }
            }
        }
        catch (Exception e) {
            logger.error("Несовместимое с жизнью исключение",e);
            try {
                if(communicator.isOpened())
                    communicator.close();
            } catch (IOException err) {
                logger.error("Что-то ужасное случилось",err);
            }
            return false;
        }
    }
}