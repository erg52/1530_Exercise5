import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Pi {
    public static int threads, iterations, turns;
    public static  int[] hits, misses;
    // public static ThreadLocalRandom rand;
    public static void main(String[] args){
        threads = 0;
        iterations = 0;

        if(args.length != 2){
            System.out.println("Must be 2 arguments");
            System.exit(1);
        }
        try{
            threads = Integer.parseInt(args[0]);
            iterations = Integer.parseInt(args[1]);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        hits = new int[threads];
        misses = new int[threads];
        for(int i=0; i<threads; i++){
            hits[i] = 0;
            misses[i] = 0;
        }


        turns = iterations/threads;
        Thread[] threadArray = new Thread[threads];

        for(int i=0; i<threads; i++){
            final int j = i;
            threadArray[i] = new Thread(() -> {
                piCalc(j);
            });
        }

        try{
            for(int i=0; i<threads; i++){
                threadArray[i].start();
               
            }

            for(int i=0; i<threads; i++){
                threadArray[i].join();
            }

            int hitCount = 0;
            int missCount = 0;
            for(int i=0; i< threads; i++){
                hitCount += hits[i];
            }
            for(int i=0; i< threads; i++){
                missCount += misses[i];
            }
            float ratio = (float)hitCount/(float)iterations;
            System.out.println("Total: "+(missCount+hitCount));
            System.out.println("Inside: "+hitCount);
            System.out.println("Ratio: "+ratio);
            System.out.println("Pi: "+ratio*4);

            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void piCalc(int i){
            Random rand = new Random();
            double x,y;
            int tempHit = 0;
            int tempMiss = 0;
            for(int j=0; j<turns; j++){
                x = rand.nextDouble();
                y = rand.nextDouble();
                if((x*x + y*y) < 1){
                    tempHit++;
                }
                else{
                    tempMiss++;
                }
            }
            hits[i] = tempHit;
            misses[i] = tempMiss;
    }
}