package Lab.Service;

import Lab.Commands.DontReportException;
import Lab.Commands.Meta;
import Lab.Communication.Communicator;
import Lab.Objects.MusicBand;
import Lab.Objects.Validation.FileValidation.FileMusicBandValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

public class FileWork extends Work {
    private int newCurrentLine;
    public FileWork(String path, Vector<Integer> Scripts, Communicator communicator){
        this.communicator=communicator;
        this.Scripts=Scripts;
        inProcess=true;
        pathOfScript= Paths.get(path);
        currentLine=0;
    }

    private boolean readFile(){
        if(FileTester.TestFileToRead(pathOfScript)){
            try(BufferedReader reader = new BufferedReader(
                    new FileReader(pathOfScript.toFile()))){
                Contents = new Vector<>(0);
                String line =reader.readLine();
                while(line!=null) {
                    Contents.add(line.trim());
                    line = reader.readLine();
                }
                Contents.add("");
            }
            catch (IOException e){
                System.out.println("Не удалось октрыть файл");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean start() {
        if(readFile()){
            while(currentLine<Contents.size()-1&&inProcess){
                element=null;
                newCurrentLine=0;
                Meta meta;
                try{
                    meta=validateLine(Contents.get(currentLine));
                    inProcess=!executeCommand(meta);
                    currentLine+=newCurrentLine;
                    currentLine++;
                }
                catch(DontReportException e){
                    currentLine++;
                }
                catch (NullPointerException e){
                    Scanner input  = new Scanner(System.in);
                    System.out.print("Ошибка в скрипте "
                            + pathOfScript + " в команде ");
                    System.out.print(Contents.get(currentLine)+" ");
                    System.out.println("В строке "+(currentLine+1));
                    System.out.println("Вы можете исправить ошибку." +
                            " Исполнение скрипта продолжится с той же " +
                            "строки в которой находилась ошибочная команда");
                    SkipBox sb =new SkipBox();
                    if(sb.requestSkip(input))
                        currentLine++;
                    else
                        if(!readFile())
                            return false;
                }
                catch (IOException e){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MusicBand validateMusicBand() {
        if(currentLine+10>Contents.size()){
            System.out.println("У объекта недостаточно параметров");
            throw new NullPointerException();
        }
        FileMusicBandValidator fmbv = new FileMusicBandValidator();
        MusicBand mb = fmbv.Validate(Contents.get(currentLine+1),Contents.get(currentLine+2),
                Contents.get(currentLine+3),Contents.get(currentLine+4),Contents.get(currentLine+5),
                Contents.get(currentLine+6),Contents.get(currentLine+7),Contents.get(currentLine+8),
                Contents.get(currentLine+9));
        newCurrentLine=fmbv.getLines();
        return mb;
    }
}
