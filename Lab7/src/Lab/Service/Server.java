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
    private final static Logger logger = LogManager.getLogger(Server.class);
    static Answer IOAnswer = new Answer("Ошибка совместимости",
            false,true);
    static Answer ClassNotFoundAnswer = new Answer("Отправлен неверный класс",
            false,true);
    public boolean start() {
        try {
            Work work = new Work();
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
                            Meta meta = communicator.toMeta();
                            answer = work.execute(meta);
                            if (answer.isSuccess())
                                logger.info("Выполнена команда " + meta.getName());
                            else
                                logger.warn("Команда " + meta.getName() + " не смогла быть выполнене");
                        } catch (IOException e) {
                            answer = IOAnswer;
                            logger.warn("С клиента " + communicator.getSender()
                                    + " пришло неверное сообщение");
                        } catch (ClassNotFoundException e) {
                            answer = ClassNotFoundAnswer;
                            logger.warn("С клиента " + communicator.getSender()
                                    + " пришло неверное сообщение");
                        }
                        try {
                            communicator.sendAnswer(answer);
                            logger.info("Отправлено сообщение на адрес " + communicator.getSender());
                        } catch (IOException e) {
                            logger.error("Не удалось отправить ответ");
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