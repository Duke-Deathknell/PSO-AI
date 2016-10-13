package ai.assign3;

/**Michael Alsbergas, 5104112
 *
 * This program uses PSO to solve several different problems.
 */

import java.util.Scanner;

public class AIAssign3 {

    double inertia1;
    double cog;
    double social;
    double explore;
    int pop; 
    int fun;
    int city;
    boolean globe;
    
    //Here the user inputs the parameters to be used by the PSO.
    public AIAssign3(){
        
        Scanner console = new Scanner(System.in);
        
        System.out.print("Input population size ");
        pop = console.nextInt();
        
        System.out.print("Input initial inertia ");
        inertia1 = console.nextDouble();
        
        System.out.print("Input cognitive influence ");
        cog = console.nextDouble();
        
        System.out.print("Input social influence ");
        social = console.nextDouble();
        
        System.out.print("Input explorative influence: ");
        explore = console.nextDouble();
        
        System.out.print("Global? (y/n): ");
        if (console.next().matches("y")){
            globe = true;
        }
        else {
            globe = false;
        }
        
        System.out.println("Function:1   Hartmann:2   Ackley:3   Schaffer:4   Sphere:5   TSP:6");
        System.out.print("Type here: ");
        fun = console.nextInt();
              
        if (fun == 1){
            Function();
        }
        else if (fun == 2){
            Hartmann();
        }
        else if (fun == 6){
            System.out.print("Enter number of cities: ");
            city = console.nextInt();
            TSP();
        }
        else {
            OtherFunctions();
        }       
        
    }
    
    //This solves the 2D function; y = 3 - x^2
    private void Function(){
        double[] xVal = new double[pop];
        double[] yVal = new double[pop];
        double[] inertia = new double [pop];
        
        for (int i=0 ; i<pop ; i++){
            inertia[i] = inertia1;
            
            xVal[i] = 10.0 - 20.0 * Math.random(); 
            
            yVal[i] = 3.0 - xVal[i] * xVal[i]; 
        }
        for (int pass = 0 ; pass < 100 ; pass++){
            
            for (int j = 0 ; j < pop ; j++){
             
                int global = 0;

                for (int k=0 ; k<pop ; k++){
                    if (yVal[k] < yVal[global]){
                        if (globe = true || xVal[k] < xVal[j]+10 && xVal[k] > xVal[j]-10){
                            global = k;
                        }
                    }
                } 
            
                inertia[j] = inertia[j] - cog * inertia[j] - social * (xVal[j] - xVal[global]) 
                        + explore * (inertia1 - 2*inertia1*Math.random());
                
                xVal[j] = xVal[j] + inertia[j];
                
                yVal[j] = 3.0 - xVal[j] * xVal[j];
            }
         }
        
        int best = 0;
        double average = 0;
        
        for (int k=0 ; k<pop ; k++){
            if (yVal[k] < yVal[best]){
                best = k;
            }
            average = average + yVal[k];
        }
        average = average / pop;
        
        System.out.println("Best: (" + xVal[best] + " , " + yVal[best] + ")");
        System.out.println("Average y value: " + average);
    }
    
    //This solves the three 3D problems
    private void OtherFunctions(){
        double[] xVal = new double[pop];
        double[] yVal = new double[pop];
        double[] zVal = new double[pop];
        double[] inertiaX = new double[pop];
        double[] inertiaY = new double[pop];
        
        for (int i=0 ; i<pop ; i++){
            inertiaX[i] = inertia1;
            inertiaY[i] = inertia1;
            
            xVal[i] = 5.0 - 10.0 * Math.random(); 
            yVal[i] = 5.0 - 10.0 * Math.random();
            
            if (fun == 3){
                //Ackley
                double A = Math.exp(-0.2 * Math.sqrt(0.5 * (xVal[i]*xVal[i] + yVal[i]*yVal[i])));
                double B = 0.5 * (Math.cos(2.0 * Math.PI * xVal[i]) + Math.cos(2.0 * Math.PI * yVal[i]));

                zVal[i] = -20 * (A) - Math.exp(B) + 20 + Math.E;
            }
            else if (fun == 4){
                //Schaffer 
                double A = Math.sin(xVal[i]*xVal[i] - yVal[i]*yVal[i]);
                double B = 1.0 + 0.1 * (xVal[i]*xVal[i] + yVal[i]*yVal[i]);
                
                zVal[i] = 0.5 + (A*A - 0.5)/(B*B);
            }
            else if (fun == 5){
                //Sphere Function
                zVal[i] = xVal[i]*xVal[i] + yVal[i]*yVal[i];
            }
        }
        
        for (int pass = 0 ; pass < 100 ; pass++){

                int global = 0;
                for (int k=0 ; k<pop ; k++){
                    if (zVal[k] < zVal[global]){
                        global = k;
                    }
                }
                
            for (int j = 0 ; j < pop ; j++){
                
                inertiaX[j] = inertiaX[j] - cog * inertiaX[j] - social * (xVal[j] - xVal[global]) 
                        + explore * (inertia1 - 2*inertia1*Math.random());
                
                xVal[j] = xVal[j] + inertiaX[j];
                
                inertiaY[j] = inertiaY[j] - cog * inertiaY[j] - social * (yVal[j] - yVal[global]) 
                        + explore * (inertia1 - 2*inertia1*Math.random());
                
                yVal[j] = yVal[j] + inertiaY[j];
                
                
                if (fun == 3){
                    //Ackley
                    double A = Math.exp(-0.2 * Math.sqrt(0.5 * (xVal[j]*xVal[j] + yVal[j]*yVal[j])));
                    double B = 0.5 * (Math.cos(2.0 * Math.PI * xVal[j]) + Math.cos(2.0 * Math.PI * yVal[j]));

                    zVal[j] = -20 * (A) - Math.exp(B) + 20 + Math.E;
                }
                else if (fun == 4){
                    //Schaffer 
                    double A = Math.sin(xVal[j]*xVal[j] - yVal[j]*yVal[j]);
                    double B = 1.0 + 0.1 * (xVal[j]*xVal[j] + yVal[j]*yVal[j]);

                    zVal[j] = 0.5 + (A*A - 0.5)/(B*B);
                }
                else if (fun == 5){
                    //Sphere Function
                    zVal[j] = xVal[j]*xVal[j] + yVal[j]*yVal[j];
                }
            }
         }
        
        int best = 0;
        double average = 0;
        
        for (int k=0 ; k<pop ; k++){
            if (zVal[k] < zVal[best]){
                best = k;
            }
            average = average + zVal[k];
        }
        average = average / pop;
        
        System.out.println("Best: (" + xVal[best] + " , " + yVal[best] + " , " + zVal[best] + ")");
        System.out.println("Average z value: " + average);
    }
    
    //Solves the 6D Hartmann problem
    private void Hartmann(){
        double[][] xVal = new double[pop][6];
        double[] zVal = new double[pop];
        double[][] inertiaX = new double[pop][6];
        
        double[] alpha = new double[4];
        alpha[0] = 1.0;
        alpha[1] = 1.2;
        alpha[2] = 3.0;
        alpha[3] = 3.2;
        
        for (int i=0 ; i<pop ; i++){
            for (int j=0 ; j<6 ; j++){

                inertiaX[i][j] = inertia1;

                xVal[i][j] = 5.0 - 10.0 * Math.random(); 
            }

                zVal[i] = 0.0;
                for (int N=0 ; N<4 ; N++){
                    zVal[i] = zVal[i] - alpha[N] * Math.exp(HartSum(N, xVal[i])); 
                }
        }
        
        for (int pass = 0 ; pass < 100 ; pass++){
            
            for (int j = 0 ; j < pop ; j++){
                
                int global = 0;
                for (int k=0 ; k<pop ; k++){
                    if (zVal[k] < zVal[global]){
                        if (globe == true || zVal[k] > zVal[j]-2){
                            global = k;
                        }
                    }
                }
                
                for (int k = 0 ; k < 6 ; k++){
                    inertiaX[j][k] = inertiaX[j][k] - cog * inertiaX[j][k] - social * (xVal[j][k] - xVal[global][k]) 
                            + explore * (inertia1 - 2*inertia1*Math.random());
                    
                    xVal[j][k] = xVal[j][k] + inertiaX[j][k];
                }
                
                zVal[j] = 0.0;
                for (int N=0 ; N<4 ; N++){
                    zVal[j] = zVal[j] - alpha[N] * Math.exp(HartSum(N, xVal[j])); 
                }
            }
         }
        
        int best = 0;
        double average = 0;
        String line = "";
        
        for (int k=0 ; k<pop ; k++){
            if (zVal[k] < zVal[best]){
                best = k;
            }
            average = average + zVal[k];
        }
        average = average / pop;
        
        for (int n = 0 ; n < 6 ; n++){
            line = line + xVal[best][n] + " , ";
        }
        
        System.out.println("Best: (" + line + zVal[best] + ")");
        System.out.println("Average z value: " + average);
    }
    
    //This helps calculate part of the Hartmann Function
    private double HartSum(int N, double[] x){
        double[][] P = new double[4][6];
        
        P[0][0] = 0.1312;
        P[0][1] = 0.1696;
        P[0][2] = 0.5569;
        P[0][3] = 0.0124;
        P[0][4] = 0.8283;
        P[0][5] = 0.5886;
        P[1][0] = 0.2329;
        P[1][1] = 0.4135;
        P[1][2] = 0.8307;
        P[1][3] = 0.3736;
        P[1][4] = 0.1004;
        P[1][5] = 0.9991;
        P[2][0] = 0.2348;
        P[2][1] = 0.1451;
        P[2][2] = 0.3522;
        P[2][3] = 0.2883;
        P[2][4] = 0.3047;
        P[2][5] = 0.6650;
        P[3][0] = 0.4047;
        P[3][1] = 0.8828;
        P[3][2] = 0.8732;
        P[3][3] = 0.5743;
        P[3][4] = 0.1091;
        P[3][5] = 0.0381;
        
        double[][] A = new double[4][6];
        
        A[0][0] = 10;
        A[0][1] = 3;
        A[0][2] = 17;
        A[0][3] = 3.5;
        A[0][4] = 1.7;
        A[0][5] = 8;
        A[1][0] = 0.05;
        A[1][1] = 10;
        A[1][2] = 17;
        A[1][3] = 0.1;
        A[1][4] = 8;
        A[1][5] = 14;
        A[2][0] = 3;
        A[2][1] = 3.5;
        A[2][2] = 1.7;
        A[2][3] = 10;
        A[2][4] = 17;
        A[2][5] = 8;
        A[3][0] = 17;
        A[3][1] = 8;
        A[3][2] = 0.05;
        A[3][3] = 10;
        A[3][4] = 0.1;
        A[3][5] = 14;
        
        double total = 0;
        
        for (int i =0 ; i<6 ; i++){
            total = total - A[N][i] * (x[i] - P[N][i])*(x[i] - P[N][i]);
        }
        
        return total;
    }   
    
    //Here is an attempt at a PSO to solve a TSP problem
    private void TSP(){
                
        double[][] xVal = new double[pop][city];
        double[] zVal = new double[pop];
        double[][] inertiaX = new double[pop][city];
        int[][] map =  new int[city][city];
        
        for (int out = 0 ; out<city ; out++){
            for (int in = out ; in< city ; in++){
                map[out][in] = (int)(city * Math.random());
                map[in][out] = map[out][in];
            }
        }
        
        for (int i=0 ; i<pop ; i++){
            for (int j=0 ; j<city ; j++){

                inertiaX[i][j] = inertia1;

                xVal[i][j] = city - 2*city * Math.random(); 
            }

                zVal[i] = Path(map, xVal[i]);
                
        }
        
        for (int pass = 0 ; pass < 100 ; pass++){

            for (int j = 0 ; j < pop ; j++){
                
                int global = 0;
                for (int k=0 ; k<pop ; k++){
                    if (zVal[k] < zVal[global]){
                        if (globe == true || zVal[k] > zVal[j]-2){
                            global = k;
                        }
                    }
                }
                
                for (int k = 0 ; k < city ; k++){
                    inertiaX[j][k] = inertiaX[j][k] - cog * inertiaX[j][k] - social * (xVal[j][k] - xVal[global][k]) 
                            + explore * (inertia1 - 2*inertia1*Math.random());
                    
                    xVal[j][k] = xVal[j][k] + inertiaX[j][k];
                }
                
                zVal[j] = Path(map, xVal[j]);
                
            }
         }
        
        int best = 0;
        double average = 0;
        String line = "";
        
        for (int k=0 ; k<pop ; k++){
            if (zVal[k] < zVal[best]){
                best = k;
            }
            average = average + zVal[k];
        }
        average = average / pop;
        
        int[] max = new int[city];
        int high = 0;
        
        for (int x=0 ; x<city ; x++){
            max[x] = city + 1;
        }
        
        for (int i = 0 ; i < city ; i++){
            for (int j = 0 ; j < city ; j++){
                if (xVal[best][j] >= xVal[best][high]){
                    for (int k = 0 ; k < city ; k++){
                        if (max[k] == j ){
                            k = city;
                        }
                        else if(k == city-1){
                            high = j;
                        }
                    }
                }
            }
            max[i] = high;
            line = line + high + ", ";
            high = 0;
        }
        
        System.out.println("Best: (" + line + " Value: " + zVal[best] + ")");
        System.out.println("Average z value: " + average);
        
        System.out.println("Map:");
        for (int out = 0 ; out<city ; out++){
            line = "";
            for (int in = 0 ; in< city ; in++){
                line = line + "[" + map[out][in] + "]";
            }
            System.out.println(line);
        }
        
    }
    
    //Here the path length of a particle is calculated
    private double Path(int[][] map, double[] X){
        int[] max = new int[city];
        int high = 0;
        double path = 0;
        
        for (int x=0 ; x<city ; x++){
            max[x] = city + 1;
        }
                
        for (int i = 0 ; i < city ; i++){
            for (int j = 0 ; j < city ; j++){
                if (X[j] >= X[high]){
                    for (int k = 0 ; k < city ; k++){
                        if (max[k] == j ){
                            k = city;
                        }
                        else if(k == city-1){
                            high = j;
                        }
                    }
                }
            }
            max[i] = high;
            path = path + map[i][high];
            high = 0;
        }
        return path;
    }
    
    
    public static void main(String[] args) {
        AIAssign3 a = new AIAssign3();
    }
}
