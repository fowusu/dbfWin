package dbfWin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DbfWin {
    
	DbfEntry viewRecord = new DbfEntry();
	DbfRec recordInto =new DbfRec();

	Frame F = new Frame();
	

	DbfWin() {
		
		F.setLayout(new GridLayout(2, 1));
		Button b1 = new Button("Date Entry");
		Button b2 = new Button("Date View");
		F.add(b1);
		F.add(b2);
		F.setSize(400, 400);
		F.setVisible(true);
		
		
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				viewRecord.F2.setVisible(true);
			}
		});
       b2.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			recordInto.F3.setVisible(true);
		}   	   
       });
		
	}		

	public class DbfEntry implements ActionListener{
		Frame F2 = new Frame();
		Button sendDb; 
		Label regno;
		Label name; 
		Label marks; 
		TextField screen = new TextField(20);
		TextField screen2 = new TextField(20);
		TextField screen3 = new TextField(20);
		Panel p2 = new Panel();
		ResultSet Rs;
		DbfEntry(){
			p2.setLayout(new GridLayout(4,4));
			sendDb = new Button("SAVE");
			//regno = new Label("REGNO:");
			name = new Label("NAME:");
			marks = new Label("MARKS:");
			
			//p2.add(regno);
			//p2.add(screen);
			p2.add(name);
			p2.add(screen2);
			p2.add(marks);
			p2.add(screen3); 
			p2.add(sendDb);
			
			
			
			sendDb.addActionListener(this);
			
			F2.add(p2, BorderLayout.CENTER);
			F2.setSize(400, 200);
			F2.setVisible(true);
			
		}
		public void actionPerformed(ActionEvent E) {
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");// Driver
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/qa","root","");//Ip adress/name of db/username,password
				
				Statement St = con.createStatement();
				
				Rs = St.executeQuery("SELECT MAX(regno)+1 as T From school");
				Rs.next(); 
				int newRegNo=Rs.getInt(1); 
				//St.executeUpdate("Insert into School values(" + newRegNo +")"); 
				
				St.executeUpdate("insert into school values("+ newRegNo+","+"'"+screen2.getText()+"'"+","+screen3.getText()+")"); 
				  
				
				

			}catch(Exception e) {
				System.out.print(e.toString());
			}

		}
	}
	
public class DbfRec{
	    Frame F3 = new Frame();
	    
		ResultSet Rs;
		Button b1;
		TextField T1, T2, T3, T4, T5;
		Label L1, L2, L3, L4, L5;

		DbfRec(){
			T1 = new TextField(10);
			T2 = new TextField(10);
			T3 = new TextField(10);
			b1 = new Button("NEXT");
			L1 = new Label("RegNo");
			L2 = new Label("NAME");
			L3 = new Label("MARKS");
			L4 = new Label("PERCENTAGE");
			L5 = new Label("RESULT");
			T4 = new TextField(10);
			T5 = new TextField(10);
			
			F3.setLayout(new GridLayout(6, 2));
			F3.add(L1);
			F3.add(T1);
			F3.add(L2);
			F3.add(T2);
			F3.add(L3);
			F3.add(T3);
			F3.add(L4);
			F3.add(T4);
			F3.add(L5);
			F3.add(T5);
			F3.add(new Label());
			F3.add(b1);
			F3.setSize(400, 400);
			F3.setVisible(true);

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/qa", "root", "");
				Statement S = con.createStatement();
				Rs = S.executeQuery("Select * From School");
				if (Rs.next()) {
					showRecord();
				}

			} catch (Exception e) {
				System.out.print(e.toString());
			}

			b1.addActionListener(

					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent X) {
							try {
							if (Rs.next()) {
								showRecord();
							}

						} catch (Exception e) {
							System.out.print(e.toString());
						}
						}
					});

		}
		
public void showRecord() {
			
			
			int RegNo, Marks; 
			Float per;
			String name,result; 
			try {
			RegNo = Rs.getInt(1);
			Marks = Rs.getInt(3);
			name = Rs.getString(2); 
			per = (float) Marks * 150/100;
			
			if (per >= 60) {
				result = "PASS";
			}else {
				result = "FAIL";
			}
			
			T1.setText(Integer.toString(RegNo));
			T2.setText(name);
			T3.setText(Integer.toString(Marks));
			T4.setText(Float.toString(per));
			T5.setText(result);
			
			}
			catch(Exception e){
				System.out.print(e.toString());
			}
		}
		
	}
	public static void main(String abc[]) {

		new DbfWin();
	
	}

}
