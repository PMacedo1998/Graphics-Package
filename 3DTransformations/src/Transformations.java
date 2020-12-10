


import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.List;
import java.util.Scanner;
import java.math.*;
import javax.swing.*;
import java.math.BigDecimal;

public class Transformations extends JPanel{
	public Transformations(){
		
	}
	public Transformations(double[][] perspectiveLines){
        BufferedImage bi = new BufferedImage(800,800, BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon(bi);
        add(new JLabel(icon));
        
        
        for(int i = 0;i<perspectiveLines.length;i++){
        	this.bresAlg(bi, (int)perspectiveLines[i][0],(int)perspectiveLines[i][1], (int)perspectiveLines[i][2], (int)perspectiveLines[i][3]);
        }
        
	}
	Scanner keyboard = new Scanner(System.in);
	
	public void inputLines(double [][] dataLines, int num, String fileName){//loads data into the array
		int lines = num;
		int cols = 6;
		try{
			File file = new File(fileName);
			Scanner myReader = new Scanner(file.getAbsoluteFile());
			for(int y = 0;y<lines;y++){
				for(int x = 0;x<cols;x++){
					dataLines[y][x]= myReader.nextInt();						
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	public int numLines(String file){//counts number of lines in the file
		
		int lines = 0;
		String line;
		try{
		File fileCount = new File(file);
		Scanner myReader2 = new Scanner(fileCount.getAbsoluteFile());
		while(myReader2.hasNextLine()==true){
			lines = lines+1;
			myReader2.nextLine();
		}
		myReader2.close();
		}
		catch(Exception ex) {
            ex.printStackTrace();			
		}
		return lines;
	}
	
	public void outputLines(double[][] dataLines, int line, int col){
		int lines = line;
		int cols = col;
		for(int y = 0;y<lines;y++){
			for(int x = 0;x<cols;x++){
				System.out.print(dataLines[y][x]+",");				
			}
			System.out.println();
		}
		
	}
	
	public double[][] basicTranslate(double Tx, double Ty, double Tz){
		
		double [][] basicTranslate = { {1,0,0,0},{0,1,0,0},{0,0,1,0},{Tx,Ty,Tz,1} };
		return basicTranslate;
				
	}
	
	public double[][] basicScale(double Sx, double Sy, double Sz){
		
		double[][] basicScale = { {Sx,0,0,0},{0,Sy,0,0},{0,0,Sz,0},{0,0,0,1} };
		return basicScale;
		
	}
	
	public double[][] basicRotateX(double Rval){
		double rad = Math.toRadians(Rval);
		double[][] basicRotate = { {1,0,0,0},{0,Math.cos(rad),Math.sin(rad),0}, {0,-Math.sin(rad),Math.cos(rad),0}, {0,0,0,1} };
		return basicRotate;
		
	}
	//fix y and z
	public double[][] basicRotateY(double Rval){
		double rad = Math.toRadians(Rval);
		double[][] basicRotate = { {Math.cos(rad),0,-Math.sin(rad),0}, {0,1,0,0}, {Math.sin(rad),0, Math.cos(rad),0}, {0,0,0,1} };
		return basicRotate;
		
	}
	public double[][] basicRotateZ(double Rval){
		double rad = Math.toRadians(Rval);
		double[][] basicRotate = { {Math.cos(rad),Math.sin(rad),0,0}, {-Math.sin(rad),Math.cos(rad),0,0}, {0,0,1,0}, {0,0,0,1} };
		return basicRotate;
		
	}
	
	
	

	public void applyTransformation(double[][] transformationMatrix, double[][]dataLines, int numLines){
		 double[] multiply ={0,0,0,1};
		 double[] multFinal = new double[4];
		 double sum;
		 for (int a = 0; a<numLines;a++){
			 multiply[0]= dataLines [a][0];
			 multiply[1]= dataLines [a][1];
			 multiply[2]= dataLines[a][2];
			 
				for (int e=0;e<4;e++){
					sum = 0;
					for(int f = 0;f<4;f++){
					sum = sum + transformationMatrix[f][e]*multiply[f];
					}
					multFinal[e]= sum;
				}
				dataLines[a][0]= multFinal[0];
				dataLines[a][1]= multFinal[1];
				dataLines[a][2]=multFinal[2];
		 }
		 for (int b = 0; b<numLines;b++){
			 multiply[0]= dataLines[b][3];
			 multiply[1]= dataLines[b][4];
			 multiply[2]=dataLines[b][5];
				for (int e=0;e<4;e++){
					sum = 0;
					for(int f = 0;f<4;f++){
					sum = sum + transformationMatrix[f][e]*multiply[f];
					}
					multFinal[e]= (int)sum;
				}
				dataLines[b][3]= multFinal[0];
				dataLines[b][4]= multFinal[1];
				dataLines[b][5]= multFinal[2];
		 }
				
	}
	
	   public void bresAlg(BufferedImage bi, int x0, int y0, int x1, int y1){

           Color color = (y0 % 2 == 0) ? Color.RED : Color.GREEN;
           int colorValue = color.getRGB();
           int w = x1 - x0 ;
           int h = y1 - y0 ;
           int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
           if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
           if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
           if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
           int longest = Math.abs(w) ;
           int shortest = Math.abs(h) ;
           if (!(longest>shortest)) {
               longest = Math.abs(h) ;
               shortest = Math.abs(w) ;
               if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
               dx2 = 0 ;            
           }
           int numerator = longest >> 1 ;
           for (int i=0;i<=longest;i++) {
               bi.setRGB(x0,y0,colorValue) ;
               numerator += shortest ;
               if (!(numerator<longest)) {
                   numerator -= longest ;
                   x0 += dx1 ;
                   y0 += dy1 ;
               } else {
                   x0 += dx2 ;
                   y0 += dy2 ;
               }
           }
       	
       }
	   
	   public double[][] perspectiveProj(double[][] dataLines){
		   double Xe=0, Ye=0, Ze=0;
		   double Xs=0, Ys=0;
		   double delta = 2.5;
		   double screen = 100;
		   double S = 50;
		   double Vsx=250, Vsy=250, Vcx=250, Vcy = 250;
		   int rows = dataLines.length;
		   double [][]perspectiveLinesZ = new double[rows][4];
		   
		  for(int y = 0;y<rows;y++){
			
			  Xe = dataLines[y][0];
			  Ye = dataLines[y][1];
			  Ze = dataLines[y][2];	
//			  System.out.print(Xe+ ","+Ye+","+Ze);
			  Xs = ((delta*Xe)/(S*Ze))*Vsx + Vcx;
			  Ys = ((delta*Ye)/(S*Ze))*Vsy + Vcy;
			  perspectiveLinesZ[y][0]= Xs;
			  perspectiveLinesZ[y][1]= Ys;
	//		  System.out.print("::::");
			  Xe = dataLines[y][3];
			  Ye = dataLines[y][4];
			  Ze = dataLines[y][5];	
		//	  System.out.println(Xe+ ","+Ye+","+Ze);
			  Xs = ((delta*Xe)/(S*Ze))*Vsx + Vcx;
			  Ys = ((delta*Ye)/(S*Ze))*Vsy + Vcy;
			  perspectiveLinesZ[y][2]= Xs;
			  perspectiveLinesZ[y][3]= Ys;
		  }
		   
		   
		   
		   return perspectiveLinesZ;
		   
	   }
	   
	    public void createGUI(double[][] perspective6Lines){
	        JFrame frame1 = new JFrame("3D Transformations");
	        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

	        frame1.add(new Transformations(perspective6Lines) );
	       
	        frame1.setLocationByPlatform( true );
	        frame1.pack();
	        frame1.setVisible( true );
        }
		
	 public static void main(String[] args)
	    {
		 Scanner keyboard = new Scanner(System.in);
		 int x = 0;
		 int y = 6;
		 
		 //ask user for file name
		 System.out.println("Enter a file name to use as input: ");
		 String fileName = keyboard.nextLine();
		 //new Lines trans class
		 Transformations tran = new Transformations();
		 //print out num of lines
		 int numLines = tran.numLines(fileName);
		 x = numLines;
		 boolean program = true;
		 
		 while(program){
			 
		BufferedImage bi = new BufferedImage(800,800, BufferedImage.TYPE_INT_RGB);
		double [][] dataLines = new double[x][y];
		tran.inputLines(dataLines, numLines,fileName);
        //tran.createGUI(dataLines);
		
            
 	
		// System.out.println("This is the current set of lines: ");
		 //tran.outputLines(dataLines, numLines,6);
		 //System.out.println();
		 	 int option;		 
			 System.out.println("Basic Translate: 1 ");
			 System.out.println("Basic Scale: 2 ");
			 System.out.println("Basic Rotate: 3 ");
			 System.out.println("Original Lines: 4");
			 System.out.println("Exit: 5 ");
			 System.out.print("Please select an option: ");
			 option = keyboard.nextInt();
			 
			 switch(option){
			 case 1: 
				 
				 System.out.println("Enter a value to translate X coordinates:  ");
				 double Tx = keyboard.nextDouble();
				 System.out.println("Enter a value to translate Y coordinates:  ");
				 double Ty = keyboard.nextDouble();
				 System.out.println("Enter a value to translate Z coordinates:  ");
				 double Tz = keyboard.nextDouble();
				 double[][] basicTranslate = new double[4][4];
				 basicTranslate = tran.basicTranslate(Tx, Ty,Tz);
				 tran.applyTransformation(basicTranslate, dataLines, numLines);
				 double[][]perspectiveLines = tran.perspectiveProj(dataLines);
				 
				 System.out.println("Translated coordinates:");
				 tran.outputLines(dataLines,numLines,6);
				 System.out.println();
				 System.out.print("Perspective Coordinates");
				 tran.outputLines(perspectiveLines, numLines, 4);
				 System.out.println();
				 tran.createGUI(perspectiveLines);
				 break;
				 
			 case 2:
				 
				 System.out.println("Enter a value to scale X coordinates:  ");
				 double Sx = keyboard.nextDouble();
				 System.out.println("Enter a value to scale Y coordinates:  ");
				 double Sy = keyboard.nextDouble();
				 System.out.println("Enter a value to scale Z coordinates:  ");
				 double Sz = keyboard.nextDouble();
				 double [][] basicScale = new double[4][4];
				 basicScale = tran.basicScale(Sx, Sy, Sz);
				 tran.applyTransformation(basicScale, dataLines, numLines);
				 double[][]perspectiveLines2 = tran.perspectiveProj(dataLines);
				 System.out.println("Scaled coordinates: ");
				 tran.outputLines(dataLines, numLines, 6);
				 System.out.println();
				 System.out.println("Perspective Coordinates");
				 tran.outputLines(perspectiveLines2, numLines, 4);
				 System.out.println();
				 tran.createGUI(perspectiveLines2);
				 
				 break;
				 
			 case 3: 
				 
				 System.out.println("Enter X, Y or Z for the axis in which to rotate: ");
				 String axis = keyboard.next();
				 System.out.println("Enter an angle value: ");
				 double Rval = keyboard.nextDouble();
				 double [][] basicRotate = new double[4][4];
				 
				 if(axis.equalsIgnoreCase("X")){
					 System.out.print("X");
					 basicRotate = tran.basicRotateX(Rval);
				 }
				 else if(axis.equalsIgnoreCase("Y")){
					 basicRotate = tran.basicRotateY(Rval); 
				 }
				 else if(axis.equalsIgnoreCase("z")){
					 basicRotate = tran.basicRotateZ(Rval);
				 }
				 tran.applyTransformation(basicRotate, dataLines, numLines);
				 double[][] perspectiveR = tran.perspectiveProj(dataLines);
				 System.out.println("Rotated coordinates: ");
				 tran.outputLines(dataLines, numLines, 6);
				 System.out.println();
				 System.out.println("Perspective Coordinates");
				 tran.outputLines(perspectiveR, numLines, 4);
				 System.out.println();
				 tran.createGUI(perspectiveR);
				 
				 
				 
				 break;
				 
			 case 4:
				 double [][] perspectiveO = tran.perspectiveProj(dataLines);
				 System.out.println("Perspective Coordinates");
				 tran.outputLines(perspectiveO, numLines, 4);
				 System.out.println();
				 tran.createGUI(perspectiveO);
				 break;				 

				 
			 case 5:
				 System.exit(0);
				 break;

			 }
		 }
		 
		 
	    }
		 
    }
	

