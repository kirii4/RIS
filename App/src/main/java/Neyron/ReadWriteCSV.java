
package Neyron;

import java.io.*;
import java.util.ArrayList;


public class ReadWriteCSV {
    public static ArrayList readCSV(String filePath,String file, boolean labeled){
        ArrayList dataList = new ArrayList<>();
        BufferedReader csvReader;
        try {
            csvReader = new BufferedReader(new FileReader(filePath + file));
            try {
                String line;
                if (labeled)
                    csvReader.readLine();
                while ( (line = csvReader.readLine()) != null ) {
                    String[] rowStr = line.split(",");
                    
                    ArrayList<Double> row = new ArrayList<Double>();
                    for (int i =0; i < rowStr.length;i++){
                        row.add(Double.valueOf(rowStr[i]));
                    }
                    
                    dataList.add(row);
                } 
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace(); 
        }
        System.out.println("The file: " + file + " was read");
        return dataList;

    }
    public static void writeCSV( double[] data, String filePath,String fileName) throws IOException{
        FileWriter csvWriter = new FileWriter(filePath + fileName);
                
        for (int i = 0; i < data.length; i++) {
           csvWriter.append(data[i] + "\n");
        }
        
        csvWriter.flush();
        csvWriter.close();
    }
    
    public static void writeCSV(ArrayList dataList, String filePath, String fileName) throws IOException{
        FileWriter csvWriter = new FileWriter(filePath + fileName);
                
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<Double> row = (ArrayList<Double>)dataList.get(i);
            
           for (int j = 0; j< row.size(); j++){
               if (j <row.size() - 1)
                csvWriter.append(row.get(j) + ",");
               else
                   csvWriter.append(String.valueOf(row.get(j)));
           }
           csvWriter.append("\n");
        }
        
        csvWriter.flush();
        csvWriter.close();
    }

    public static void writeCSV(double[] data, String filePath, String fileName, String lable) throws IOException{
        FileWriter csvWriter = new FileWriter(filePath + fileName);
        csvWriter.append(lable + "\n");
        
        for (int i = 0; i < data.length; i++) {
           csvWriter.append(data[i] + "\n");
        }
        
        csvWriter.flush();
        csvWriter.close();
    }

    public static double[] pickAnItemList(ArrayList dataList, int index){
        double[] itemList = new double[dataList.size()];
        
        for(int i = 0; i < dataList.size();i++){
            ArrayList<Double> row = (ArrayList<Double>)dataList.get(i);
            itemList[i] = row.get(index);
        }
        
        return itemList;
    }
}
