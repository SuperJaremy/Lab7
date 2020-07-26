package Lab.Commands;

import Lab.Service.FileWork;
import Lab.Service.Work;


import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

public class ExecuteScript extends Command {
    public ExecuteScript(){
        name="execute_script";
    }
    private boolean recursion(){
        Scanner input = new Scanner(System.in);
        System.out.println("Вы хотите зайти в рекурсию? [Y/N]");
        String line;
        while(true) {
            line = input.nextLine().trim().toUpperCase();
            if (line.equals("Y"))
                return true;
            if (line.equals("N"))
                return false;
            System.out.println("Введите Y или N");
        }
    }

    @Override
    public Meta validate(Work work) {
        Vector<Integer> scripts = work.getScripts();
        if(work.getElement()==null){
            System.out.println("У команды "+name+" должен быть аргумент");
        }
        else{
            boolean completed;
            if(!scripts.contains(Paths.get(work.getElement()).hashCode()))
                scripts.add(Paths.get(work.getElement()).hashCode());
            else{
                if(recursion())
                    scripts.add(Paths.get(work.getElement()).hashCode());
                else
                    throw new DontReportException();
            }
            Work innerWork = new FileWork(work.getElement(), scripts, work.getCommunicator());
            completed = innerWork.start();
            if(completed)
                work.setInProcess(innerWork.isInProcess());
            scripts.remove(scripts.size()-1);
            System.out.println(completed?"Скрипт выполнен"
                    :"Выполнение скрипта прервано");
        }
        throw new DontReportException();
    }
}
