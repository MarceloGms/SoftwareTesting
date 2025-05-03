package com.qcs;

public class FR6_17 {
    int desired_temp;
    int error;
    int temps[];
    int fans[][];
    String name;

    public FR6_17(String name, int desired_temp, int error, int temps[], int fans[][]) {
        this.desired_temp = desired_temp;
        this.error = error;
        this.temps = temps;
        this.fans = fans;
        this.name = name;
    }

    public int[][] checkRange() {
        for (int i = 0; i < temps.length; i++) {
            int sum = 0;
            for (int j = 0; j < fans[i].length; j++) {
                sum += fans[i][j];
            }
            int fanAverage = sum / fans[i].length;
                
            if (temps[i] > desired_temp + error) {
                  int change = fanAverage == 0 ? 20 : 10;
                  for (int j = 0; j < fans[i].length; j++) {
                      int result = fans[i][j] + change * -1;
                      if (result > 100) {
                          result = 100;
                      } else if (result < 0) {
                          result = 0;
                      }
                      fans[i][j] = result;
                  }
        
            } else if (temps[i] < desired_temp - error) {               
                int change = fanAverage == 0 ? 20 : 10;
        
                for (int j = 0; j < fans[i].length; j++) {
                    int result = fans[i][j] + change;
                    if (result > 100) {
                        result = 100;
                    } else if (result < 0) {
                        result = 0;
                    }
                    fans[i][j] = result;
                }
        
            }
        }
        return fans;
    }

}
