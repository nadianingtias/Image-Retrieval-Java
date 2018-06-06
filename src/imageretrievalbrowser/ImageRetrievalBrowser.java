/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrievalbrowser;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 *
 * @author Nadian
 */
public class ImageRetrievalBrowser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        //--------------1. Images DB preparation
        //read data dari folder
        ArrayList<File> listFiles = new ArrayList<>(); //siapkan tempat untuk tampung semua file
        final File folder = new File("E:\\Users\\Nadian\\Documents\\NetBeansProjects\\ImageRetrievalBrowser\\src\\resources");
        
        //Baca resourcse 1 folder
        listFiles = listFilesForFolder(folder); ///mendapatkan semua file dari folder
        readListFiles(listFiles); //cek udah dapat file atau belum
        
        ArrayList<ImageSource> listImage = new ArrayList<>(); //siapkan tempat untuk list of Image (DATABASE)
        //tiap file yang ada di dalam folder dibaca, dibuatkan instance ImageSource, constructornya tipedata File 
        for(File eachFile : listFiles){
            ImageSource imageBaru = new ImageSource(eachFile);  // buat instance dari tiap file
            listImage.add(imageBaru);                   //tambahkan imageBaru ke dalam listImage
        }
        //preparation DB images SELESAI, trainingpoint terbentuk di ListImage
        //---------------------2. baca file testing
        //read data dari folder
        ArrayList<File> listFileTests = new ArrayList<>(); //siapkan tempat untuk tampung semua file
        final File folderTest = new File("E:\\Users\\Nadian\\Documents\\NetBeansProjects\\ImageRetrievalBrowser\\src\\tests");
        
        //Baca resourcse 1 folder testing
        listFileTests = listFilesForFolder(folderTest); ///mendapatkan semua file dari folder
        readListFiles(listFileTests); //cek udah dapat file atau belum
        File fileTest = listFileTests.get(0);
        ImageSource testingImage = new ImageSource(fileTest);
        
        //---------------------3. cari jarak dong
        double[] distances = new double[listImage.size()];
        distances = getDistanceBetween(testingImage,listImage);
        
        //---------------------4. tentukan K
        double[] dynamicDist = (distances);
        int k = 5;
        
        //---------------------5. do Sorting
        int counterK=0;
        while(counterK<k){
            //1. cari jarak min
            double minDistance = getMinValueOfDoubleArray(dynamicDist);
            //2. cari index dari angka min
            int indexOfMinDistance = getIndexOfArray(distances, minDistance);
            System.out.println(minDistance+" index: "+ indexOfMinDistance);
            //3. tampilkan gambar dengan index berisi min dari dynamicDist gambar min
            //tampilkan gambar index (indexOfMinDistance) ke label (counterK)
            //4. remove dist dengan index di atas
            dynamicDist[indexOfMinDistance] = 99999;
            counterK++;
        }
    }
        public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> mListFiles = new ArrayList<>();
        int counter=0;
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    mListFiles.add(fileEntry);
                    //System.out.println(fileEntry.getName());
                    //System.out.println("mList: " +mListFiles.get(counter).getName());
                    counter++;
                }
            }
        return mListFiles;
        }
        

    private static void readListFiles(ArrayList<File> mFiles) {
        for(File mfile : mFiles){
            System.out.println("FILE : "+mfile.getName());
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static double[] getDistanceBetween(ImageSource mTestingImage, ArrayList<ImageSource> mListImage) {
        int size = mListImage.size();
        double[] resultDist = new double[size];
        float[] testItem = mTestingImage.getPercentageHistogram();
        for(int i = 0 ; i<size ;i++){
            float[] trainItem = mListImage.get(i).getPercentageHistogram();
            resultDist[i] = getDistance(testItem,trainItem);
        }
        return resultDist;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static double getDistance(float[] mTestItem, float[] mTrainItem) {
        int size = mTestItem.length;
        double resultDistance;
        double tempDistance=0;
        for(int i=0; i<size ; i++){
            double diff = (double)(mTestItem[i]-mTrainItem[i]);
            tempDistance += Math.pow(diff,2);
        }
        resultDistance = (double) tempDistance/size;
        return resultDistance;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static double getMinValueOfDoubleArray(double[] mDistances) {
        //<double[]> t = Stream.of(mDistances);
        Stream<double[]> temp = Stream.of(mDistances);
        DoubleStream Stream = temp.flatMapToDouble(x -> Arrays.stream(x)); // Cant print Stream<int[]> directly, convert / flat it to IntStream 
        double mMinDistance = Stream.min().getAsDouble();
        return mMinDistance;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static int getIndexOfArray(double[] array, double value) {
        int var = -1 ;
        for(int i=0; i<array.length ; i++){
                if(array[i] == value){
                    var = i;
                    break;
                }
        }
        return var;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
