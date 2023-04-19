package javadatabase0;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 

import java.sql.*;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


class JavaDataBase0 implements ActionListener 
{   
    JFrame f ;
    String selectedId ;
    JMenuBar m ; 
    JMenu student, course  ;
    JMenuItem s1, s2 ,s3,s4 , c1,c2,c3,c4;
    JTable studentTable, courseTable ;
    JPanel  current ,p_insert ,p_update ,p_delete,p_view;       
    JLabel courseid , coursename , semester ; 
    JTextArea courseidtext , coursenametext, semestertext,newCourseName , newSemester , studentId, studentName   ;
    JButton insertButton , update, submitUpdate, submitDelete ;
    JComboBox<String> dropdown ; 
    String[] courseCols = {"courseID", "courseName","semester", "elective"};
    String[] studentCols = {"ID" ,"NAME" ,"SEM" ,"COURSES"} ;
    DefaultTableModel modelC, modelS ;
     
    JavaDataBase0() throws ClassNotFoundException{
        
   
    
    f =  new JFrame("Database") ;
    m =  new JMenuBar() ;
    
    current = new JPanel() ;
    p_update = new JPanel(); 
    p_insert = new JPanel(); 
    p_delete = new JPanel(); 
    
    update = new JButton("Update record"); update.addActionListener(this);
    submitUpdate =  new JButton("Submit Update"); submitUpdate.addActionListener(this) ;
    submitDelete = new JButton("Delete Record"); submitDelete.addActionListener(this) ;
    
    student  =  new JMenu("Student") ; 
    course   =  new JMenu("Course") ; 
    
    s1 =  new JMenuItem("Insert") ; s1.addActionListener(this);
    s2 =  new JMenuItem("Update") ; s2.addActionListener(this);
    s3 =  new JMenuItem("Delete") ; s3.addActionListener(this);
    s4 =  new JMenuItem("View")   ; s4.addActionListener(this);


    c1 =  new JMenuItem("Insert") ; c1.addActionListener(this);
    c2 =  new JMenuItem("Update") ; c2.addActionListener(this);
    c3 =  new JMenuItem("Delete") ; c3.addActionListener(this);
    c4 =  new JMenuItem("View")   ; c4.addActionListener(this);

    course.add(c1) ; student.add(s1);
    course.add(c2) ; student.add(s2);
    course.add(c3) ; student.add(s3);
    course.add(c4) ; student.add(s4);

    
    modelS = new DefaultTableModel();
    modelC=  new DefaultTableModel() ;

    studentTable = new JTable(modelS);
    courseTable = new JTable(modelC);




    studentTable.getModel().addTableModelListener(new TableModelListener() {


        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model1 = (TableModel) e.getSource();
            String columnName = model1.getColumnName(column);
            Object data = model1.getValueAt(row, column);
            try {
//
                    System.out.println("here");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    });

//        courseTable.getModel().addTableModelListener(new TableModelListener() {
//
//
//            public void tableChanged(TableModelEvent e) {
//                int row = e.getFirstRow();
//                int column = e.getColumn();
//                TableModel model1 = (TableModel) e.getSource();
//                System.out.print("g");
//                try {
////
//                    for(int i =0 ;i<model1.getColumnCount();i++)
//                    {
//                        System.out.println(model1.getValueAt(row,i));
//                    }
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//        });
    

    

    m.add(student)      ; 
    m.add(course)       ;
    
    f.setJMenuBar(m)    ; 
    
    f.add(current) ;
    
    f.setVisible(true);
    f.setSize(800,500);
    f.setDefaultCloseOperation(3);
   
    
    }
    
     public void setContentPanel(JPanel newContentPanel) {
        // Remove the old content panel
        f.getContentPane().remove(current);
        
        // Set the new content panel
        current = newContentPanel;
        f.getContentPane().add(current, BorderLayout.CENTER);

        // Refresh the frame
        f.revalidate();
        f.repaint();
    }

    JTextArea studentSem ;
    public void onInsertStudent(){
        p_insert     =     new JPanel();

        studentName  =     new JTextArea(1,15) ;
        studentId    =     new JTextArea(1 , 20);
        studentSem  =   new JTextArea(1,1);

        JButton insertS =  new JButton("Insert") ;


        p_insert.add(new JLabel("Student Id :")) ; p_insert.add(studentId);
        p_insert.add(new JLabel("Student Name : ")); p_insert.add(studentName) ;
        p_insert.add(new JLabel("Select Student Sem")); p_insert.add(studentSem);

        p_insert.add(insertS) ;

        setContentPanel(p_insert);

        insertS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JPanel insertSubmit =  new JPanel() ;


                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM course where semester =" + Integer.parseInt(studentSem.getText()));
                    CallableStatement insertCall = con.prepareCall("{call insert_student(?,?,?,?)}");

                   ArrayList<String> s =  new ArrayList<String>() ;

                    while (rs.next()) {
                        s.add(rs.getString("courseName")) ;
                    }

                    JCheckBox[] arr = new JCheckBox[s.size()];

                    for(int i =0 ;i<s.size() ;i++){

                        arr[i] =  new JCheckBox(s.get(i));
                        insertSubmit.add(arr[i]) ;
                    }

                    JButton ins ;
                    ins =  new JButton("Insert record") ;
                    insertSubmit.add(ins) ;

                    setContentPanel(insertSubmit);

                    ins.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String courses = "";
                            for(int i =0 ;i<s.size() ;i++){
                                if(arr[i].isSelected()){
                                        courses += arr[i].getText() +", ";
                                }
                            }
                            try{
                                insertCall.setString(1,studentId.getText());
                                insertCall.setString(2,studentName.getText());
                                insertCall.setString(3,courses);
                                insertCall.setInt(4,Integer.parseInt(studentSem.getText()));

                                int i = insertCall.executeUpdate() ;

                                if(i>=1) JOptionPane.showMessageDialog(f,studentName.getText()+" Added ");
                                setContentPanel(new JPanel());

                            }catch (Exception iii){
                                System.err.print(iii.toString());
                            }

                         }
                    });

                }
                catch (Exception ii){
                    System.err.print(ii.toString());
                }
            }
        });


    }

    public void updateStudent() {

        JPanel c = new JPanel() ;
        JButton updt =  new JButton("UPDATE ") ;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            CallableStatement deleteCall = con.prepareCall("{call delete_student(?)}");

            // Add data to dropdown
            dropdown = new JComboBox<String>();

            while (rs.next()) {
                dropdown.addItem(rs.getString("studentID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        c.add(dropdown) ;
        c.add(updt) ;
        setContentPanel(c);

        updt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                JPanel updatedStudent =  new JPanel( );
                JButton btn4 = new JButton("UPDATE") ;
                String id = dropdown.getSelectedItem().toString() ;


                Statement st = null;
                try {
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from student where studentID = '" + id + "' ;");
                    rs.next();
                    String selectedCourses = rs.getString("courses") ;
                    ResultSet rs1 = st.executeQuery("SELECT * FROM course where semester =" + rs.getString("semester"));

                    ArrayList<String> s =  new ArrayList<String>() ;

                    while (rs1.next()) {
                        s.add(rs1.getString("courseName")) ;
                    }

                    JCheckBox[] arr = new JCheckBox[s.size()];

                    for(int i =0 ;i<s.size() ;i++){
                        boolean x  = selectedCourses.contains(s.get(i));
                        arr[i] =  new JCheckBox(s.get(i),x);
                        updatedStudent.add(arr[i]) ;
                    }

                    updatedStudent.add(btn4) ;
                    btn4.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String courses = "";
                            for(int i =0 ;i<s.size() ;i++){
                                if(arr[i].isSelected()){
                                    courses += arr[i].getText() +", ";
                                }
                            }

                            Statement st = null;
                            try {
                                PreparedStatement ps = con.prepareStatement("UPDATE student set courses = ? where studentID = ?  ;");
                                ps.setString(1,courses);
                                ps.setString(2,id);

                                if(ps.executeUpdate() > 0 ) JOptionPane.showMessageDialog(f,"updated user"+ id);
        //UPDATE table_name
                                //SET column1 = value1, column2 = value2, ...
                                //WHERE condition;
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                    setContentPanel(updatedStudent);

            }
        });
    }
    public void delStudent(){

        try {
             JPanel c = new JPanel() ;
             JButton deletes =  new JButton("Delete") ;
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            CallableStatement deleteCall = con.prepareCall("{call delete_student(?)}");

            // Add data to dropdown
            dropdown = new JComboBox<String>();

            while (rs.next()) {
                dropdown.addItem(rs.getString("studentID"));
            }
             c.add(dropdown) ;
             c.add(deletes) ;
            setContentPanel(c);

             deletes.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     try {
                         deleteCall.setString(1, dropdown.getSelectedItem().toString());

                         int  i = deleteCall.executeUpdate()  ;

                         if(i>0) {
                             JOptionPane.showMessageDialog(f,"deleted "  + dropdown.getSelectedItem().toString(),"delete", JOptionPane.WARNING_MESSAGE);

                         }
                         setContentPanel(new JPanel());
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }
                 }
             });



        } catch (SQLException ee) {
            ee.printStackTrace();
        }
    }

    JCheckBox isElective;
     public void onInsert(){
    p_insert =  new JPanel(); 
    insertButton =  new JButton("Add record");  insertButton.addActionListener(this) ;
    courseidtext  =  new JTextArea(1,10);
    coursenametext = new JTextArea(1,10);
    semestertext  =  new JTextArea(1,4);

    isElective =  new JCheckBox("Elective ?") ;

    courseid = new JLabel("Course ID"); 
    coursename = new JLabel("Course Name") ;
    semester =  new JLabel("Semester"); 
      
    p_insert.add(courseid); p_insert.add(courseidtext);        p_insert.add(coursename);  p_insert.add(coursenametext);     p_insert.add(semester);  p_insert.add(semestertext); p_insert.add(isElective) ;  p_insert.add(insertButton);
    setContentPanel(p_insert);

     }
     
     public void updateDropDown(){
         try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM course");

            // Add data to dropdown
            dropdown = new JComboBox<String>(); 
            
            while (rs.next()) {
                dropdown.addItem(rs.getString("courseID"));
            }
      
            } catch (SQLException ee) {
            ee.printStackTrace();
        }   
     }
     
     public void onDelete(){
             p_delete = new JPanel(); 
            updateDropDown(); 
            p_delete.add(dropdown); 
            p_delete.add(submitDelete); 
            setContentPanel(p_delete);   
     
     }
     
     
    @Override
    public void actionPerformed(ActionEvent e){  
        if(e.getSource() == c1 ){
            onInsert(); 
        }   
        else if(e.getSource() == c2){
            p_update = new JPanel(); 
             updateDropDown(); 
             p_update.add(dropdown) ; p_update.add(update) ;
             setContentPanel(p_update); 
             
        
        }
        else if(e.getSource() == c3){
            onDelete();  
        }
        
        else if(e.getSource() == c4){
                showTable(); 
        }
         else if(e.getSource() == update){
            JPanel  updatePanel = new JPanel(); 
             selectedId = dropdown.getSelectedItem().toString() ;
            JLabel l1 = new JLabel("new course name"); 
            JLabel l2 =  new JLabel("new semester") ;
            
            
             newCourseName = new JTextArea(1,10) ; 
             newSemester = new JTextArea(1,4) ;
            
            updatePanel.add(l1); updatePanel.add(newCourseName); /*updatePanel.add(l2); updatePanel.add(newSemester)*/ ; updatePanel.add(submitUpdate);
            
            setContentPanel(updatePanel); 
        }
         
        else if(e.getSource() == insertButton){
            int sem = Integer.parseInt(semestertext.getText())  ; 
            System.out.print(sem) ;
            addRecord(courseidtext.getText(),coursenametext.getText(),sem,isElective.isSelected());
            setContentPanel(new JPanel()); 
            
        }
       
        else if(e.getSource() == submitUpdate){
              updateRecord(selectedId,newCourseName.getText(),/*,Integer.parseInt(newSemester.getText()),*/false );
              selectedId=""; 
              setContentPanel(new JPanel());
        }
        
        else if(e.getSource() == submitDelete){
            selectedId = dropdown.getSelectedItem().toString() ;
            try{
                        deleteStatement.setString(1, selectedId);
                        int i=deleteStatement.executeUpdate();  
              if(i >= 1 ){
                                
              JOptionPane.showMessageDialog(f, "record deleted : " + selectedId);
              selectedId=""; 
              setContentPanel(new JPanel());
            }

            }catch(Exception e1 ){
                            System.out.println(e);
            }
            
           setContentPanel(new JPanel()); 
        }
        else if(e.getSource() == s1){
          onInsertStudent();

        }
        else if (e.getSource() == s2){
            // update
            updateStudent() ;
        }
        else if (e.getSource() == s3){
            delStudent()  ;
        }
        else if (e.getSource() == s4){
            showTableStudent();
        }
        
    }

    public void showTableStudent(){

        modelS = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                if(col == 0 || col ==2 || col ==3 )
                    return false; //Renders column 0 uneditable.
                return true;
            }
        };

        modelS.setColumnIdentifiers(studentCols);

        p_view = new JPanel();
        p_view.setSize(800,400);

        try{
            PreparedStatement pst = con.prepareStatement("select * from student;");
            ResultSet rs = pst.executeQuery();
            String sid, sname, semester, courses;


            while (rs.next()) {
                sid = rs.getString("studentID");
                sname = rs.getString("studentName");
                courses = rs.getString("courses");
                semester = rs.getString("semester").toString();

                modelS.addRow(new Object[]{sid, sname, semester, courses});
            }
        }
        catch(Exception e2){
            System.out.println(e2);
        }

        studentTable.setModel(modelS);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(700);

//        studentTable.getModel().addTableModelListener(new TableModelListener() {
//
//
//            public void tableChanged(TableModelEvent e) {
//                int row = e.getFirstRow();
//                int column = e.getColumn();
//                TableModel model1 = (TableModel) e.getSource();
//                try {
//                    updateRecord(model1.getValueAt(row,0).toString(), model1.getValueAt(row,1).toString(),  Boolean.parseBoolean(model1.getValueAt(row,3).toString())) ;
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//        });

        p_view.add(new JScrollPane(studentTable));
        setContentPanel(p_view);
    }



    public void showTable(){
        
     modelC = new DefaultTableModel() {
	public boolean isCellEditable(int row, int col) {
		if(col == 0 || col ==2 || col ==3 )
			return false; //Renders column 0 uneditable.
		return true;
	}
};
     
        modelC.setColumnIdentifiers(courseCols);
    
    
        p_view = new JPanel(); 
        
        try{
            PreparedStatement pst = con.prepareStatement("select * from course ");
            ResultSet rs = pst.executeQuery();
            String cid, cname, semester, elective;
            
            while (rs.next()) {
                cid = rs.getString("courseID");
                cname = rs.getString("courseName");
                semester = rs.getString("semester");
                elective = rs.getString("elective").toString();
                modelC.addRow(new Object[]{cid, cname, semester, elective});
            }
        }
        catch(Exception e2){
             System.out.println(e2);
        }


        courseTable.setModel(modelC);

        courseTable.getModel().addTableModelListener(new TableModelListener() {


            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model1 = (TableModel) e.getSource();
                try {
                    updateRecord(model1.getValueAt(row,0).toString(), model1.getValueAt(row,1).toString(),  Boolean.parseBoolean(model1.getValueAt(row,3).toString())) ;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        p_view.add(new JScrollPane(courseTable));
        setContentPanel(p_view);
    }

   Connection con; 
   PreparedStatement insertStatement, updateStatement, deleteStatement ; 
  
      void initalizeDatabase() throws ClassNotFoundException{
       
        try
        {
                Class.forName("com.mysql.jdbc.Driver"); 
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Demo","root",""); 
                insertStatement = con.prepareStatement("insert into course values(?,?,?,?)");  
                updateStatement = con.prepareStatement("UPDATE course SET courseName=?, elective =? WHERE courseID=? ");
                deleteStatement = con.prepareStatement("DELETE FROM course WHERE courseID=?");
               
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         
    }
      
    public void updateRecord(String cid, String cname, Boolean elect){
         try
        {
                
                updateStatement.setString(3,cid);//1 specifies the first parameter in the query  
                updateStatement.setString(1,cname); 
//                updateStatement.setInt(2,sem) ;
                updateStatement.setBoolean(2, elect);

                int i = updateStatement.executeUpdate();
                if(i>=1)JOptionPane.showMessageDialog(f, "record Updated : " + cid);

        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
        
    }
    
    
    public void addRecord(String cid, String cname, int sem ,Boolean elect){
     try
        {
                
                insertStatement.setString(1,cid);//1 specifies the first parameter in the query  
                insertStatement.setString(2,cname); 
                insertStatement.setInt(3,sem) ;
                insertStatement.setBoolean(4, elect); 
                
                int i=insertStatement.executeUpdate();  
                
                if(i>=1)JOptionPane.showMessageDialog(f, "record Added : " + cid);
                
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         
    
    } 
    
     @Override
    protected void finalize() throws Throwable {
        con.close(); 
    }
    
    public static void main(String args[]) throws ClassNotFoundException
    {
            new JavaDataBase0().initalizeDatabase();
    }
    
}

