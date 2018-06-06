/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Learning;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nadian
 */
public class BelajarImageRetrieval {
    public static void main(String[] args) {
        //assigning value to hashmap
        HashMap<Integer,String> hm=new HashMap<Integer,String>();  
        hm.put(100,"Amit");  
        hm.put(101,"Vijay");  
        hm.put(102,"Rahul");  
        hm.put(100,"bro");
        for(Map.Entry m:hm.entrySet()){  
         System.out.println(m.getKey()+" "+m.getValue());  
        } 
        
        HashMap<String,Integer> histogram=new HashMap<String, Integer>();
        int n=1;
        histogram.put("hitam",n+=1 );
        for(Map.Entry m:histogram.entrySet()){  
         System.out.println(m.getKey()+" "+m.getValue());  
        } 
        //--------prove pointer or new object
        HashMap<String,Integer> histogramCopy=new HashMap<String, Integer>();
        histogramCopy = new HashMap(histogram);
        histogram.put("putih", n+=1);
        for(Map.Entry m:histogramCopy.entrySet()){  
         System.out.println("copi: "+m.getKey()+" "+m.getValue());  
        } 
        for(Map.Entry m:histogram.entrySet()){  
         System.out.println(m.getKey()+" "+m.getValue());  
        } 
        
        //--------sum of array
        int [] arr = {1,2,3,4,15,6};
        int sum = Arrays.stream(arr).sum();
        System.out.println("jumlah: " +sum);
        
        //-------belajar hashmap double value
        // create our map
        Map<String, Wrapper> peopleByForename = new HashMap<String, Wrapper>();

        // populate it
        Wrapper people = new Wrapper();
        peopleByForename.put("kelas 1", new Wrapper(new Person("Nadia Ningtias"),
                                                new Person("Indiana Giants"))
                            );

        // read from it
        
        Wrapper kelas1 = peopleByForename.get("kelas 1");
        Person person1 = kelas1.getPerson1();
        Person newPerson = new Person("Madian dong");
        peopleByForename.get("kelas 1").setPerson1(newPerson);
        Person person2 = kelas1.getPerson2();
        
        
        System.out.println("1: "+person1.getName());
        System.out.println("2: "+person2.getName());
        System.out.println("person1 hash: " +peopleByForename.get("kelas 1").getPerson1().getName());
        
//        -----color coba
        Color color = new Color(25,255,255);
        getColorString(color);
        //--------getmin
        double[] tes = {23,34,53,24,6,74};
        double min;
        min = getMinValueOfDoubleArray(tes);
        System.out.println("minimum : "+min);
        
        //--------------
        int[] array = {0,6,7,7,5,3,6,7};
        Arrays.sort(array);
        System.out.printf("Modified arr[] : %s",
                          Arrays.toString(array));
        //------------
        System.out.println("");
        final Double[][] doubles = new Double[][]{{5.0, 1.0}, {1.0, 2.0}, {8.0, 6.0}};
        Arrays.sort(doubles, (a, b) -> Double.compare(a[0], b[0]));
        for(int i=0;i<doubles.length;i++){
            for(int j=0;j<doubles[0].length;j++){
                System.out.print("sorted? "+doubles[i][j]+" ");
            }
            System.out.println("");
        }
        //----------
        JFrame frame = new JFrame("A Simple GUI");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocation(430, 100);

        JPanel panel = new JPanel();

        frame.add(panel);

        JLabel lbl = new JLabel("Select one of the possible choices and click OK");
        lbl.setVisible(true);

        panel.add(lbl);

        String[] choices = { "CHOICE 10","CHOICE 2", "CHOICE 3","CHOICE 4","CHOICE 5","CHOICE 6"};

        final JComboBox<String> cb = new JComboBox<String>(choices);

        cb.setVisible(true);
        panel.add(cb);

        JButton btn = new JButton("OK");
        panel.add(btn);
        System.out.println("now cb : "+cb.getSelectedItem().toString());
    }
    
    
    //methood
    private static String getColorString(Color color)
    {
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0]*= 360;
        comp[1]*= 100;
        comp[2]*= 100;
        System.out.println(comp[0]+", "+comp[1]+", "+comp[2]);
        return  comp[0]+", "+comp[1]+", "+comp[2];
    }
    //-----------------
    private static double getMinValueOfDoubleArray(double[] mDistances) {
        //<double[]> t = Stream.of(mDistances);
        Stream<double[]> temp = Stream.of(mDistances);
        DoubleStream Stream = temp.flatMapToDouble(x -> Arrays.stream(x)); // Cant print Stream<int[]> directly, convert / flat it to IntStream 
        double mMinDistance = Stream.min().getAsDouble();
        return mMinDistance;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
            
    
}


class Wrapper {
    private Person person1;
    private Person person2;
    
    public Wrapper(Person person1, Person person2) {
       this.person1 = person1;
       this.person2 = person2;
    }

    public Wrapper() {
    }
    
    
    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }
    
    
    
}
