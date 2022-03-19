package MainGame;


import java.io.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO
{
    public FileIO(){
        //
    }
    
    /*
    public void writeFile(String text, String fileName){
        PrintWriter writer = null;
        try{

            writer = new PrintWriter(new FileOutputStream(fileName+".txt"));
            writer.println(text+"\n");

        }catch(FileNotFoundException e){
            System.out.println("Error opening the file "+ fileName);
        }
        writer.close();
    }

    public String readFile(String fileName){
        String text ="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName+".txt"));
            String temp = "";
            while((temp = reader.readLine()) != null){
                text = temp + reader.readLine();
            }

            reader.close();

        }catch(FileNotFoundException e){
            System.out.println(fileName + " file cannot be found.");
        }catch(IOException e){
            System.out.println("Error reading the file "+ fileName);
        }
        return text;
    }
    */
   
    public void writeObjFile(String fileName, ArrayList<User> users){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(users);
            outputStream.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error writing to file"+ fileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<User> readObjFile(String fileName){
        ArrayList<User> users = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
            try{
                while(true){
                    users = (ArrayList<User>)inputStream.readObject();
                }
            }catch(EOFException e){

            }catch(ClassNotFoundException e){
            	 JOptionPane.showMessageDialog(null, "Error", "Class Not Found Exception.", JOptionPane.ERROR_MESSAGE);
            }           

            inputStream.close();

        }catch(FileNotFoundException e){
            System.out.println(fileName + " file cannot be found.");
        }catch(IOException e){
      
        }
        return users;
    }
}

