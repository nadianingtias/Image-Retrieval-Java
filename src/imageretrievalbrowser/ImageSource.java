/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrievalbrowser;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Nadian
 */
public class ImageSource {
    private String name;
    private File imgFile;
    private BufferedImage image;
    private int height, width;
    private int[] simpleHistogram = new int[13];
    private float[] percentageHistogram = new float[13];
//    ArrayList<Color> histogram;
    private HashMap<Integer,ColorIndex> HistogramIndex = new HashMap<Integer, ColorIndex>();  
    private HashMap<Integer,Integer> HistogramIndexInt = new HashMap<Integer, Integer>();
    
    

    
    float[][] nilaiHistogram;

    public ImageSource(File imgFile) {
        this.imgFile = imgFile;
        
        try {
            this.name = imgFile.getName();
            this.image = ImageIO.read(imgFile);
            this.height = this.image.getHeight();
            this.width = this.image.getWidth();
            System.out.println("name gambar: "+this.name+", height: "+ this.height + ", width: "+ this.width);
//            System.out.println("height: "+height);
//            System.out.println("width: "+width);
//            this.HistogramIndex = new HashMap(initHistogramIndex(this.image));
//              this.HistogramIndexInt = new HashMap(initHistogramIndexInt(this.image));
            this.simpleHistogram = initHistogramSimple(this.image);
        } catch (Exception ex) {
            if(Integer.parseInt(ex.getMessage())!=0)
            System.out.println("Exception : "+ ex.getMessage());
//            Logger.getLogger(BandImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0; i< this.HistogramIndex.size() ; i++){
            ColorIndex ci = HistogramIndex.get(i);
            System.out.println("histogramIndex get "+i+" "+ci);
            System.out.println("data "+i+" : "+ ci.getColorName()+" jumlah:  "+ ci.getColorCount());
        }
        for(int i=0 ; i< this.HistogramIndexInt.size(); i++){
            System.out.println("cek his int"+i+" "+HistogramIndexInt.get(i));
        }
        
        this.percentageHistogram = getPercentHist(this.simpleHistogram);
        //ini yang dipakai
        for(int i=0 ; i< this.simpleHistogram.length; i++){
//            System.out.println(i+" simple: "+ this.simpleHistogram[i]);
            System.out.println(i+" persentase: " + this.percentageHistogram[i]);
        }
    }

    public float[] getPercentageHistogram() {
        return percentageHistogram;
    }

    public void setPercentageHistogram(float[] percentageHistogram) {
        this.percentageHistogram = percentageHistogram;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public HashMap<Integer,ColorIndex> getHistogramIndex() {
        return HistogramIndex;
    }

    public void setHistogramIndex(HashMap<Integer, ColorIndex> HistogramIndex) {
        this.HistogramIndex = HistogramIndex;
    }
    public ImageSource() {
    }

    public File getImgFile() {
        return imgFile;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public BufferedImage getBuffImg() {
        return image;
    }

    public void setBuffImg(BufferedImage buffImg) {
        this.image = buffImg;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float[][] getNilaiHistogram() {
        return nilaiHistogram;
    }

    public void setNilaiHistogram(float[][] nilaiHistogram) {
        this.nilaiHistogram = nilaiHistogram;
    }

    private void initNilaiHistogram() {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private HashMap<Integer,ColorIndex> initHistogramIndex(BufferedImage mBuffImage) {
        HashMap<Integer, ColorIndex> mHistogramIndex = new HashMap<Integer, ColorIndex>(); 
        int p = this.height;
        int l = this.width;
        int[] counter = {0,0,0,0,0,
                         0,0,0,0,0,
                         0,0,0};
        for(int x=0; x<p ; x++){
            for(int y=0 ; y<l ; y++){
                float[] comp = new float[3];
                Color mColor = new Color(mBuffImage.getRGB(y, x));
                Color.RGBtoHSB(mColor.getRed(), mColor.getGreen(), mColor.getBlue(), comp);
                comp[0]*= 360;
                comp[1]*= 100;
                comp[2]*= 100;
//                System.out.println(comp[1]);
                ColorIndex newIndex = new ColorIndex();
                if(comp[2]<10){                         //hitam
                    newIndex = new ColorIndex("black",counter[0]+=1);
                    mHistogramIndex.put(0,newIndex);
                    
                }else if(comp[1]<10 && comp[2]>90){     //putih
                    newIndex = new ColorIndex("white",counter[1]+=1);
                    mHistogramIndex.put(1,newIndex);
                    
                }else if(comp[0]<=20 || comp[0]>345){   //merah
                    newIndex = new ColorIndex("red", counter[2]+=1);
//                    System.out.println(counter[2]);
                    mHistogramIndex.put(2,newIndex);
                }else if(comp[0]<=40){                  //jingga
                    newIndex = new ColorIndex("orange", counter[3]+=1);
                    mHistogramIndex.put(3,newIndex);
                }else if(comp[0]<=70){                  //kuning
                    newIndex = new ColorIndex("yellow", counter[4]+=1);
                    mHistogramIndex.put(4,newIndex);
                }else if(comp[0]<=100){                  //hijau kekuningan
                    newIndex = new ColorIndex("greenyellow", counter[5]+=1);
                    mHistogramIndex.put(5,newIndex);
                }else if(comp[0]<=140){                   //hijau
                    newIndex = new ColorIndex("green", counter[6]+=1);
                    mHistogramIndex.put(6,newIndex);
                }else if(comp[0]<=170){                      //teal
                    newIndex = new ColorIndex("teal", counter[7]+=1);
                    mHistogramIndex.put(7,newIndex);
                }else if(comp[0]<=200){                     //cyan
                    newIndex = new ColorIndex("cyan", counter[8]+=1);
                    mHistogramIndex.put(8,newIndex);
                }else if(comp[0]<=265){                      //blue
                    newIndex = new ColorIndex("blue", counter[9]+=1);
                    mHistogramIndex.put(9,newIndex);
                }else if(comp[0]<=285){                     //nila
                    newIndex = new ColorIndex("indigo", counter[10]+=1);
                    mHistogramIndex.put(10,newIndex);
                }else if(comp[0]<=315){                     //ungu
                    newIndex = new ColorIndex("purple", counter[11]+=1);
                    mHistogramIndex.put(11,newIndex);
                }else if(comp[0]<=170){                     //magenta
                    newIndex = new ColorIndex("magenta", counter[12]+=1);
                    mHistogramIndex.put(12,newIndex);
                }
            }
        }
        int [] arr = {1,2,3,4};
        int sum = Arrays.stream(counter).sum();
        
        return mHistogramIndex;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private HashMap<Integer,Integer> initHistogramIndexInt(BufferedImage mBuffImage) {
        HashMap<Integer, Integer> mHistogramIndex = new HashMap<Integer, Integer>(); 
        int p = this.height;
        int l = this.width;
        int[] counter = {0,0,0,0,0,
                        0,0,0,0,0,
                         0,0,0};
        for(int x=0; x<p ; x++){
            for(int y=0 ; y<l ; y++){
                float[] comp = {0,0,0};
                Color mColor = new Color(mBuffImage.getRGB(y, x));
                Color.RGBtoHSB(mColor.getRed(), mColor.getGreen(), mColor.getBlue(), comp);
                comp[0]*= 360;
                comp[1]*= 100;
                comp[2]*= 100;
//                System.out.println(comp[1]);
                ColorIndex newIndex = new ColorIndex();
                if(comp[2]<10){                         //hitam
                    newIndex = new ColorIndex("black",counter[0]+=1);
                    mHistogramIndex.put(0,counter[0]+=1);
                    
                }else if(comp[1]<10 && comp[2]>90){     //putih
                    newIndex = new ColorIndex("white",counter[1]+=1);
                    mHistogramIndex.put(1,counter[1]+=1);
                    
                }else if(comp[0]<=20 || comp[0]>345){   //merah
                    newIndex = new ColorIndex("red", counter[2]+=1);
//                    System.out.println(counter[2]);
                    mHistogramIndex.put(2,counter[2]+=1);
                }else if(comp[0]<=40){                  //jingga
                    newIndex = new ColorIndex("orange", counter[3]+=1);
                    mHistogramIndex.put(3,counter[3]+=1);
                }else if(comp[0]<=70){                  //kuning
                    newIndex = new ColorIndex("yellow", counter[4]+=1);
                    mHistogramIndex.put(4,counter[4]+=1);
                }else if(comp[0]<=100){                  //hijau kekuningan
                    newIndex = new ColorIndex("greenyellow", counter[5]+=1);
                    mHistogramIndex.put(5,counter[5]+=1);
                }else if(comp[0]<=140){                   //hijau
                    newIndex = new ColorIndex("green", counter[6]+=1);
                    mHistogramIndex.put(6,counter[6]+=1);
                }else if(comp[0]<=170){                      //teal
                    newIndex = new ColorIndex("teal", counter[7]+=1);
                    mHistogramIndex.put(7,counter[7]+=1);
                }else if(comp[0]<=200){                     //cyan
                    newIndex = new ColorIndex("cyan", counter[8]+=1);
                    mHistogramIndex.put(8,counter[8]+=1);
                }else if(comp[0]<=265){                      //blue
                    newIndex = new ColorIndex("blue", counter[9]+=1);
                    mHistogramIndex.put(9,counter[9]+=1);
                }else if(comp[0]<=285){                     //nila
                    newIndex = new ColorIndex("indigo", counter[10]+=1);
                    mHistogramIndex.put(10,counter[10]+=1);
                }else if(comp[0]<=315){                     //ungu
                    newIndex = new ColorIndex("purple", counter[11]+=1);
                    mHistogramIndex.put(11,counter[11]+=1);
                }else if(comp[0]<=170){                     //magenta
                    newIndex = new ColorIndex("magenta", counter[12]+=1);
                    mHistogramIndex.put(12,counter[12]+=1);
                }
            }
        }
        int [] arr = {1,2,3,4};
        int sum = Arrays.stream(counter).sum();
        
        return mHistogramIndex;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int[] initHistogramSimple(BufferedImage mBuffImage) {
        int[] mHistogramIndex = new int[13];
        int p = this.height;
        int l = this.width;
        int[] counter = {0,0,0,0,0,
                        0,0,0,0,0,
                         0,0,0};
        for(int x=0; x<p ; x++){
            for(int y=0 ; y<l ; y++){
                float[] comp = {0,0,0};
                Color mColor = new Color(mBuffImage.getRGB(y, x));
                Color.RGBtoHSB(mColor.getRed(), mColor.getGreen(), mColor.getBlue(), comp);
                comp[0]*= 360;
                comp[1]*= 100;
                comp[2]*= 100;
                if(comp[2]<10){                         //hitam
                    //newIndex = new ColorIndex("black",counter[0]+=1);
                    mHistogramIndex[0] = counter[0]+=1;
                    
                }else if(comp[1]<10 && comp[2]>90){     //putih
//                    newIndex = new ColorIndex("white",counter[1]+=1);
                    mHistogramIndex[1] = counter[1]+=1;
                    
                }else if(comp[0]<=20 || comp[0]>345){   //merah
//                    newIndex = new ColorIndex("red", counter[2]+=1;
//                    System.out.println(counter[2]);
                    mHistogramIndex[2] = counter[2]+=1;
                }else if(comp[0]<=40){                  //jingga
//                    newIndex = new ColorIndex("orange", counter[3]+=1);
                    mHistogramIndex[3] = (counter[3]+=1);
                }else if(comp[0]<=70){                  //kuning
//                    newIndex = new ColorIndex("yellow", counter[4]+=1);
                    mHistogramIndex[4] = (counter[4]+=1);
                }else if(comp[0]<=100){                  //hijau kekuningan
//                    newIndex = new ColorIndex("greenyellow", counter[5]+=1);
                    mHistogramIndex[5] = (counter[5]+=1);
                }else if(comp[0]<=140){                   //hijau
//                    newIndex = new ColorIndex("green", counter[6]+=1);
                    mHistogramIndex[6] = (counter[6]+=1);
                }else if(comp[0]<=170){                      //teal
//                    newIndex = new ColorIndex("teal", counter[7]+=1);
                    mHistogramIndex[7] = (counter[7]+=1);
                }else if(comp[0]<=200){                     //cyan
//                    newIndex = new ColorIndex("cyan", counter[8]+=1);
                    mHistogramIndex[8] = (counter[8]+=1);
                }else if(comp[0]<=265){                      //blue
//                    newIndex = new ColorIndex("blue", counter[9]+=1);
                    mHistogramIndex[9] = (counter[9]+=1);
                }else if(comp[0]<=285){                     //nila
//                    newIndex = new ColorIndex("indigo", counter[10]+=1);
                    mHistogramIndex[10] = (counter[10]+=1);
                }else if(comp[0]<=315){                     //ungu
//                    newIndex = new ColorIndex("purple", counter[11]+=1);
                    mHistogramIndex[11] = (counter[11]+=1);
                }else if(comp[0]<=170){                     //magenta
//                    newIndex = new ColorIndex("magenta", counter[12]+=1);
                    mHistogramIndex[12] = (counter[12]+=1);
                }
//                mHistogramIndex[1] =0;
            }
        }
        mHistogramIndex[1] =0;
        mHistogramIndex[0] =0;
        return mHistogramIndex;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private float[] getPercentHist(int[] mSimpleHistogram) {
        int size = mSimpleHistogram.length;
        float[] percentage = new float[size];
        int sum = Arrays.stream(mSimpleHistogram).sum();
        double persum = (double)1/sum;
        System.out.println("persum: "+persum);
        System.out.println("sum: "+sum);
        for(int i=0; i<size ; i++){
            System.out.println("mSimple: "+mSimpleHistogram[i]);
            percentage[i]= ((float)mSimpleHistogram[i]/sum*100);
            System.out.println(percentage[i]);
        }
        return percentage;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

class ColorIndex {
    private String colorName;
    private int colorCount;
    
    public ColorIndex(String mColorName, int colorCount) {
       this.colorName = mColorName;
       this.colorCount = colorCount;
    }

    public ColorIndex() {
    }
    
    
    public String getColorName() {
        return this.colorName;
    }
    public int getColorCount() {
        return this.colorCount;
    }
}

//note:
/**
 * histogram minuman ?
 * 
 * 
 * HSB =hue
 * hi = B<10
 * pu = H terserah S<10 dan B>90 
 * 0me = 0 - 20 atau 346 - 360 = 35
 * 30ji = 21 - 30 - 40 = 20 OK
 * 60ku = 41 - 70 = 30 OK
 * himuda = 71 - 100 = 30 OK
 * 120hi = 101 - 120 - 140 = 40 OK
 * teal = 141 - 170 = 30 OK
 * cyan = 171 - 200 = 30 OK
 * 240blue = 201 - 220 - 240 - 265 = 65 OK
 * 266ni = 266 - 285 = 20 OK
 * 300u = 286-300-315 = 30 OK
 * magenta = 216 - 345 = 30 OK
 * 
 *  * oranye - mangga
 * ijo matcha 
 * ijo alpukan
 * pink sirup
 * hitam ketan
 * ijo kacang ijo
 * pink tua buah naga
 * coklat eskepal
 * oranye blewah
 * ijo es cincau
 */