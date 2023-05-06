package com.mycompany.ads_inoo ;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class ADS_inoo  {  
    
    static final int BUTTON_WIDTH = 1000 ;
    //number of Jlabels 
    int elements ;
    JFrame frame  ;
    JPanel upper ,lower; 
    JLabel addPosL , addElementL ,addNL  , queryLl, queryHl ,querySum; 
    JTextArea addPos ,addElement, addN, queryL , queryH ;
    
    JButton query, update ; 
    Double rows ;
    int LOCATION_TO_ADD; 
    int n ;
    
    int entered_elements ;
    
    Vector<Integer> v ;
    ADS_inoo(){ 
        queryL =  new JTextArea("0",1,5);
        queryH =  new JTextArea("0",1,5); 
        queryLl =  new JLabel("Enter Lower bound") ; 
        queryHl =  new JLabel("Enter Higher bound"); 
        
        queryLl.setBounds(80,105, 120,20);
        queryL.setBounds(190,100,60,30);  
        

        queryHl.setForeground(Color.WHITE); 
        queryLl.setForeground(Color.WHITE); 

        
        queryHl.setBounds(360, 105, 110, 20);
        queryH.setBounds(470,100,60,30); 
       
        querySum =  new JLabel("Query Sum :  "); 
        querySum.setBounds(100,80, 100, 40);
        
        frame = new JFrame("Segment Visualizer in Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         addPosL =  new JLabel("Add position to update") ;   
         addElementL =  new JLabel("Enter Element to add") ;  
         addNL  =  new JLabel("Enter Number of Elements") ;   
         
         
         
         addPos = new JTextArea("",1,4); 
         addElement  = new JTextArea("" ,1,4) ;
         addN = new JTextArea("",1,4) ;
        
         addPosL.setBounds(840,100,120,30); 
         addPosL.setForeground(Color.WHITE);
         
         addPos.setBounds(960,100,80,30);
         addElementL.setBounds(1100,100,120,30);
         addElement.setBounds(1215,100,60,30);
        addElementL.setForeground(Color.WHITE);
        addNL.setBounds(500,80, 150,40);
        addN.setBounds(650, 85,60,30) ;
        
        
        JButton generate =  new JButton("Generate") ;
        generate.setBounds(755, 75 ,100,50);
        
        
        upper =  new JPanel();
        lower =  new JPanel() ; 
        
       
        Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);
        
        
        
        upper.setPreferredSize(new Dimension(1500, 200));
        lower.setPreferredSize(new Dimension(1500, 600));
        
        Color  bc = new Color (102, 0, 204);
        
        upper.setBackground(bc);
        bc = new Color (102, 178, 255);
        lower.setBackground(bc) ;
        
     
        query  =  new JButton("query"); 
        update =  new JButton("update") ; 
        
        
        query.setBounds(750/2-100,40,150,50);
        update.setBounds(750+275,40,150,50);  
        
        upper.add(update) ;
        upper.add(query) ; 
        upper.add(queryLl); 
        upper.add(queryHl) ;
        upper.add(queryL);
        upper.add(queryH); 
        upper.add(addPosL);
        upper.add(addPos);
        
        upper.add(addElementL);
        upper.add(addElement);
        
        lower.add(querySum) ;
        lower.add(addNL);
        lower.add(addN); 
        lower.add(generate); 
        lower.setLayout(null);
        
        //600 
         JLabel[]l  = new JLabel[300]; 
         
     

        
        generate.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
         n = Integer.parseInt(addN.getText());
         entered_elements = n  ;
         rows = 0.0 ;
        
        while( Math.pow(2,rows)<n){
           rows++ ;
        }
        
        rows++ ;
        
        elements = (int)Math.pow(2,rows) ;
        
       int x   ; 
       int y  = 200 ;
       int h  = 40 ;
       int counter = 0 ; 
       
       for(double i = 1 ; i<=rows ; i++)
       {   
           int req_width = 1000-((int)Math.pow(2.0,i-1)*10) ;
           int w  = (int) ((int)req_width/Math.pow(2.0,i-1)) ; 
           
            
           x = 300 ;
           for(double j = 1 ; j<=Math.pow(2,i-1) ; j++)
           { 
              l[counter] =  new JLabel("0",SwingConstants.CENTER) ;
              
              l[counter].setBounds(x,y, w,h) ;
              l[counter].setBorder(blackline);
              l[counter].setForeground(Color.WHITE);
              l[counter].setBackground(Color.lightGray);
              l[counter].setOpaque(true);
              lower.add(l[counter]) ;
              counter++ ;
             
              x = x+w+10 ;
           }
          
          y+= 50 ;
       }
        
        elements = counter ;

        lower.revalidate();
        lower.repaint();
            }
        });
       
       
       
    update.addActionListener(new ActionListener() { 
     int current; 
     @Override 
     
       public void actionPerformed(ActionEvent e)
       { 
           
           v =   new Vector<>();
           current = 0 ;
           int n = Integer.parseInt(addPos.getText());
           
           if(n>entered_elements || n<1){
               JOptionPane.showMessageDialog(null, "position should be between  " + "1" + "and " +  entered_elements, "ERROR", JOptionPane.WARNING_MESSAGE);
               return ;
           }
           int low = 0 , high = (int)Math.pow(2,rows-1) ; 
           
           while(true){  
               //  HOME 
               v.add(current);
               System.out.println(current); 
               if(current*2 +1 >= elements) break; 
               
               
               if(n<=(low+high)/2) 
               {   
                   high = (low+high)/2  ; 
                   //go to left child 
                   current = 2*current + 1 ; 
               }
               else
               {
                      low = (low+high)/2 ;
                      current  = 2*current + 2; 
               }
               
           } 
        
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int c =0  ;
            @Override
            public void run() {
                if(c == v.size()){
                    l[v.get(v.size()-1)].setBackground(Color.GREEN);
                 int addValue = Integer.parseInt(addElement.getText());
                 l[v.get(v.size()-1)].setText(Integer.toString(addValue)); 
                 l[v.get(v.size()-1)].setForeground(Color.WHITE);
                 l[v.get(v.size()-1)].setBackground(Color.lightGray);
               
                 cancel();
                 return ;
                }
                else{ 
                l[v.get(c)].setBackground(Color.GREEN) ; 
                l[v.get(c)].setOpaque(true) ;
                c++;
                }
            }
            }, 0, 200);
        
        
        
         timer.schedule(new TimerTask() {
            int c = v.size()-2  ;
            @Override
            public void run() {
                if(c == -1){
                   cancel();
                   return ;
                }
                
                int left  = Integer.parseInt( l[v.get(c)*2+1].getText()) , right = Integer.parseInt( l[v.get(c)*2+2].getText()); 
                int sum = left+right; 
                l[v.get(c)].setText(Integer.toString(sum)) ; 
                l[v.get(c)].setForeground(Color.WHITE);
                l[v.get(c)].setBackground(Color.lightGray);
                l[v.get(c)].setOpaque(true) ;
                c-- ;
            }
            
            }, 2000, 200);
        }
       });
       
    
    query.addActionListener(new ActionListener(){
           
            int sum = 0 ;
            Stack<vec>s ; 
            
            @Override
            public void actionPerformed(ActionEvent e) { 
                sum =0 ;
                v = new Vector<>();
                v.clear(); 
                s =  new Stack<>() ; 
                 // si ss se
                s.add(new vec(0,1,(int)Math.pow(2,rows-1)));           
                

               int qs  = Integer.parseInt(queryL.getText()) ; 
               int qe  = Integer.parseInt(queryH.getText()) ; 
               
               if(qs<1 || qe > entered_elements){
                   JOptionPane.showMessageDialog(null, "query should be between " + "1" + " and " + entered_elements, "error", JOptionPane.WARNING_MESSAGE);
                   return  ;
                   
               }
               System.out.print(qs+ " " + qe) ; 
               
                while(!s.empty())
                {   
                    vec curr = s.pop() ;
                    int ss  = curr.ss ;
                    int se = curr.se ; 
                    int si = curr.si ; 
                    
                    
                    if(qe<ss || se<qs){
                        continue ;
                    }
                    
                    if(qs<=ss && se<=qe){
                        v.add(si);
                        sum+= Integer.parseInt(l[si].getText()) ;
                        continue ;
                    }
                    
                    
     
                 int mid = (ss + se) / 2;
                  // si ss se
                 if(2*si+1<elements) s.add(new vec(2*si+1 , ss,mid)) ;
                 if(2*si+2<elements)  s.add(new vec(2*si+2 , mid+1,se)) ;

                }
                



        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int c =0  ;
            @Override
            public void run() {
                if(c >= v.size()){
                     return ;
                }
                else{ 
                // Change the button's color to the next color in the array
                l[v.get(c)].setBackground(Color.RED) ; 
                l[v.get(c)].setOpaque(true) ;
                c++;
                }
            }
            }, 0, 100);
             
             
             querySum.setText("Query Sum : " +sum);
             
             
            timer.schedule(new TimerTask() {
            int c =0  ;
            @Override
            public void run() {
                if(c >= v.size()){
                     return ;
                }
                else{ 
                // Change the button's color to the next color in the array
                l[v.get(c)].setBackground(Color.lightGray) ; 
                l[v.get(c)].setOpaque(true) ;
                c++;
                }
            }
            }, 4000, 100);
             
             
             
            }
            
            
            
    });
       
        upper.setLayout(null); 
        
       
        frame.add(upper,BorderLayout.NORTH) ;
        frame.add(lower,BorderLayout.SOUTH );
        frame.setSize(1500,800); 
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        try {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            } else {
                UIManager.setLookAndFeel  ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
        }
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        // If Nimbus is not available, you can set to another look and feel.
    }
       new ADS_inoo();
    }

    
}


 class vec{
    int si  ;
    int se ;
    int ss ; 
    
    // si ss se
    vec(int x,int y,int z) {
        
        this.si = x ;
        this.ss = y ; 
        this.se = z; 
    }
}
