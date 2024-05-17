package Neyron;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatasetPreProcessing {

    public static void preprocessing (String filePath, String fileName, String dateFormat) throws IOException{

        ArrayList dataset = timeStampFixing_mi_meteo_dataset(filePath, 
                                    fileName, 
                                    dateFormat);

        ArrayList sortedList = sortByMultipleFields(dataset);

        
        System.out.println("Dataset Preprocessing is done—"
                            + "The datasets are preprocessed, sorted and writen in new CSV files");
    }
    

    public static ArrayList timeStampFixing_mi_meteo_dataset(String filePath, 
                                                        String fileName, 
                                                        String dateFormat) throws IOException{
        ArrayList dataList = new ArrayList<>();
        BufferedReader csvReader;
        try {
            csvReader = new BufferedReader(new FileReader(filePath + fileName));
            try {
                String line;
                while ( (line = csvReader.readLine()) != null ) {
                    String[] items = line.split(",");

                    ArrayList<Double> newRow = new ArrayList<Double>();
                    
                    double year =0, month =0, day =0, hour = 0, targetParameter = 0;
                    
                    String[] dateSplitted = items[1].split("/");
                    
                    if(dateFormat == "MM/DD/YYYY HH:MM"){//Temperature

                        month = Double.valueOf(dateSplitted[0]);
                        day = Double.valueOf(dateSplitted[1]);
                    
                        year = Double.valueOf(dateSplitted[2].substring(0, 4));
                        
                        String[] st = dateSplitted[2].split(" ");
                        String[] st1 = st[1].split(":");
                        hour = Double.valueOf(st1[0]);
                    }else if (dateFormat == "YYYY/MM/DD HH:MM"){// Humidity

                        year = Double.valueOf(dateSplitted[0]);
                        month = Double.valueOf(dateSplitted[1]);
                        
                        day = Double.valueOf(dateSplitted[2].substring(0, 2).trim());
                        
                        //remove day from the text and split into two parts (hour and minute).
                        String tmpSplitter = dateSplitted[2].substring(2, dateSplitted[2].length());
                        String tmp[] = tmpSplitter.split(":");
                        hour = Double.valueOf(tmp[0].trim());
                        hour = hour;
                    }
                    
                    //[2]  
                    targetParameter = Double.valueOf(items[2]);
                    
                    newRow.add(year);
                    newRow.add(month);
                    newRow.add(day);
                    newRow.add(hour);
                    newRow.add(targetParameter);
                    
                    dataList.add(newRow);
                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace(); 
        }
        
    return dataList;
    
    }

    public static ArrayList sortByMultipleFields (ArrayList dataList){
        ArrayList dataListWithKey = new ArrayList();
        // Add a key field to each row
        // Key must be made by rows: 1, 2, and 3
        for (int i=0; i < dataList.size(); i++){
            ArrayList<Double> row = (ArrayList<Double>)dataList.get(i);
            
            String keyStr;
            keyStr = String.valueOf(row.get(1).intValue()); // month
            if (row.get(2).intValue() < 10) // day
                keyStr += "0" + String.valueOf(row.get(2).intValue());
            else
                keyStr += String.valueOf(row.get(2).intValue());
            
            if(row.get(3).intValue() < 10) // hour
                keyStr += "0" + String.valueOf(row.get(3).intValue());
            else
                keyStr += String.valueOf(row.get(3).intValue());
            
            double key = Double.valueOf(keyStr);
            row.add(0, key);
            dataListWithKey.add(row);
        }

        for (int i = 0; i < dataListWithKey.size(); i++){
            
            for (int j = 1; j < (dataListWithKey.size()- i) ; j ++){
                ArrayList<Double> rowJ = (ArrayList<Double>)dataListWithKey.get(j);
                double keyJ = rowJ.get(0);
                
                ArrayList<Double> rowJMinusOne = (ArrayList<Double>)dataListWithKey.get(j - 1);
                double keyJMinusOne = rowJMinusOne.get(0);
                
                if (keyJMinusOne > keyJ){
                    ArrayList<Double> rowTmp = rowJMinusOne;
                    dataListWithKey.set(j - 1, rowJ);
                    dataListWithKey.set(j, rowTmp);
                }
            }
        }
        
        return dataListWithKey;
    }
    
}
