import java.util.concurrent.TimeUnit;

/**
 * Created by nacho on 4/30/17.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("*******************");
        System.out.println("*  Gantt Creator  *");
        System.out.println("*******************\n\n");

        //First parse input or retrieve demo data created on some method in this class

        //Second create all Resources; cpu, io, etc

        //Third create TaskManager, assign resources and pass all processes.
        TaskManager mng = new TaskManager();

        //Fourth, start clock iterations antil TaskManager signals halt
        Boolean finished = false;
        while (!finished) {
            try {TimeUnit.SECONDS.sleep(1);} catch (Exception e){System.out.println("Error on timed sleep");}

            System.out.println("Clock Cycle ++");
            finished = mng.updata();
        }


    }

}
