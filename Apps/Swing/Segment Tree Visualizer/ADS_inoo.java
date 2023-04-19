package com.mycompany.ads_inoo ;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.border.Border;

public class ADS_inoo  {  
    
    static final int BUTTON_WIDTH = 1000 ;
    //number of Jlabels 
    int elements ;
    JFrame frame  ;
    JPanel upper ,lower; 
    JLabel addPosL , addElementL ,addNL  , queryLl, queryHl ; 
    JTextArea addPos ,addElement, addN, queryL , queryH ;
    
    JButton query, update ; 
    Double rows ;
    int LOCATION_TO_ADD; 
    int n ;
    
    Vector<Integer> v ;
    ADS_inoo(){ 
        queryL =  new JTextArea("0",1,5);
        queryH =  new JTextArea("0",1,5); 
        queryLl =  new JLabel("Enter Lower bound") ; 
        queryHl =  new JLabel("Enter Higher bound"); 
        
        queryLl.setBounds(70,100, 120,20);
        queryL.setBounds(190,100,100,20);  
        
        queryLl.setOpaque(true) ;
        queryLl.setForeground(Color.WHITE); 
        queryLl.setBackground(Color.DARK_GRAY);
        queryHl.setBackground(Color.DARK_GRAY);

        queryHl.setOpaque(true) ; 
        queryHl.setForeground(Color.WHITE); 
        
        queryHl.setBounds(350, 100, 120, 20);
        queryH.setBounds(470,100,100,20); 
       
       
        
        
        frame = new JFrame("GridBagLayoutExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         addPosL =  new JLabel("Add position to update") ;   
         addElementL =  new JLabel("Enter Element to add") ;  
         addNL  =  new JLabel("Enter Number of Elements") ;   
         
         
         addPos = new JTextArea("",1,4); 
         addElement  = new JTextArea("" ,1,4) ;
         addN = new JTextArea("",1,4) ;
         
//         addPos.setBounds(elements, elements, BUTTON_WIDTH, elements);
        
        upper =  new JPanel();
        lower = new JPanel() ; 
        
       
        Border blackline = BorderFactory.createLineBorder(Color.BLUE);
        
        
        
        upper.setPreferredSize(new Dimension(1500, 200));
        lower.setPreferredSize(new Dimension(1500, 600));
      
        upper.setBackground(Color.DARK_GRAY);
        lower.setBackground(Color.WHITE) ;
        
     
        query  =  new JButton("query"); 
        update =  new JButton("update") ; 
        
        query.setOpaque(true);
        update.setOpaque(true); 
        
        query.setBounds(750/2-100,40,150,50);
        update.setBounds(750+275,40,150,50);  
        
        upper.add(update) ;
        upper.add(query) ; 
        upper.add(queryLl); 
        upper.add(queryHl) ;
        upper.add(queryL);
        upper.add(queryH); 
         
        
        lower.setLayout(null);
        
        //600 
        
         n = Integer.parseInt( JOptionPane.showInputDialog(frame,"Enter Number of Elements","INPUT ",JOptionPane.INFORMATION_MESSAGE));
        rows = 0.0 ;
        
        while( Math.pow(2,rows)<n){
           rows++ ;
        }
        
        rows++ ;
        
        elements = (int)Math.pow(2,rows) ;
        JLabel[]l  = new JLabel[300]; 
        
       
       
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
              l[counter].setForeground(Color.BLACK);
           
              lower.add(l[counter]) ;
              counter++ ;
             
              x = x+w+10 ;
           }
          
          y+= 50 ;
       }
        
       elements = counter ;

       
    update.addActionListener(new ActionListener() { 
     int current; 
     @Override 
     
       public void actionPerformed(ActionEvent e)
       {
           v =   new Vector<>();
           current = 0 ;
           int n = Integer.parseInt( JOptionPane.showInputDialog(frame,"Location to insert","INPUT ",JOptionPane.INFORMATION_MESSAGE));
           
           int low = 0 , high = (int)Math.pow(2,rows-1)  ; 
           
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
                 int addValue = Integer.parseInt( JOptionPane.showInputDialog(null,"Enter element","INPUT ",JOptionPane.INFORMATION_MESSAGE));
                 l[v.get(v.size()-1)].setText(Integer.toString(addValue)); 
                 l[v.get(v.size()-1)].setBackground(Color.WHITE);
                 cancel();
                }
                else{ 
                // Change the button's color to the next color in the array
                l[v.get(c)].setBackground(Color.GREEN) ; 
                l[v.get(c)].setOpaque(true) ;
                c++;
                }
            }
            }, 0, 100);
        
        
        
         timer.schedule(new TimerTask() {
            int c = v.size()-2  ;
            @Override
            public void run() {
                if(c == -1){
                   cancel();
                }
                
                int left  = Integer.parseInt( l[v.get(c)*2+1].getText()) , right = Integer.parseInt( l[v.get(c)*2+2].getText()); 
                int sum = left+right; 
                System.out.println(sum);
                l[v.get(c)].setText(Integer.toString(sum)) ; 
                l[v.get(c)].setBackground(Color.WHITE) ; 
                l[v.get(c)].setOpaque(true) ;
                c-- ;
            }
            
            }, 2000, 100);
        }
       });
       
    
    query.addActionListener(new ActionListener(){
           
            int l =2;  
            int r = 4;
            int sum = 0 ;
            
            Stack<vec>s ; 
            
             @Override
            public void actionPerformed(ActionEvent e) { 
                s =  new Stack<>() ; 
                s.add(new vec(0,0,n-1));              
               
                while(!s.empty())
                {   
                    vec curr = s.pop() ;
                    int start  = curr.ss ;
                    int end = curr.se ; 
                    int si = curr.si ; 
                    System.out.println(si + " " + start + " " +end + " " +l + " "+r);
                    if(r<start || end<l){
                        continue ;
                    }
                    if(l>=start && r<=end){
                        sum+=si ;
                        continue ;
                    }
                    
                 int mid = (start + end) / 2;
                 s.add(new vec(2*si+1 , start,mid)) ;
                 s.add(new vec(2*si+2 , mid+1,end)) ;

                }
                
                queryL.setText(Integer.toString(sum));
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
       new ADS_inoo();
    }

    
}


/*
int query(int node, int start, int end, int l, int r)
{
    if(r < start or end < l)
    {
        // range represented by a node is completely outside the given range
        return 0;
    }
    if(l <= start and end <= r)
    {
        // range represented by a node is completely inside the given range
        // ADD to stack and color 
        return tree[node];
    }
    // range represented by a node is partially inside and partially outside the given range
    int mid = (start + end) / 2;
    int p1 = query(2*node, start, mid, l, r);
    int p2 = query(2*node+1, mid+1, end, l, r);
    return (p1 + p2);
} 

*/

 class vec{
    int si  ;
    int se ;
    int ss ; 
    
    vec(int x,int y,int z) {
        
        this.si = x ;
        this.ss = y ; 
        this.se = z; 
    }
}