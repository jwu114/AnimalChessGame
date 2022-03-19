package MainGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PackageUI extends JDialog
{
    private String ID;

    private Container contents;
    private JButton btnTool, btnFragment;
    private JTextArea text;

    private ArrayList<User> users;
    private User user;

    private boolean found;

    private FileIO io;
    public PackageUI(JFrame frame, String ID){
        super(frame, "Package", true);
        contents = getContentPane();
        contents.setLayout(new FlowLayout());

        this.ID = ID;
        findUser();
        
        text = new JTextArea("");
        btnTool = new JButton("Tool");
        btnFragment = new JButton("Fragment");
       
        text.setEditable(false);
        text.setBackground(null);
        
        if(!found){
            btnTool.setEnabled(false);
            btnFragment.setEnabled(false);  
        }

        ButtonHandler bh = new ButtonHandler();
        btnTool.addActionListener(bh);
        btnFragment.addActionListener(bh);

        contents.add(text);
        contents.add(btnTool);
        contents.add(btnFragment);

        setSize(280,300);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void findUser(){
        io = new FileIO();
        users = io.readObjFile("Users.dat");
        found = false;
        for(int i = 0; i < users.size(); i++){
            if(ID.equals(users.get(i).getUserName())){
                user = users.get(i);
                found = true;
            }
        }
        if(!found){//user isn't existed
            JOptionPane.showMessageDialog(null, "You're a guest, please checking package after login", "Message", JOptionPane.INFORMATION_MESSAGE);            
        }
    }
    
    public void showFragment(){
        String b = "";
        for(int i = 0; i < 8; i++){
            int num = user.checkFragment(i);            
            switch(i){
                    case 0: b += "Elephant Fragment: ";
                    break;
                    case 1: b += "Lion Fragment: ";
                    break;
                    case 2: b += "Tiger Fragment: ";
                    break;
                    case 3: b += "Leopard Fragment: ";
                    break;
                    case 4: b += "Wolf Fragment: ";
                    break;
                    case 5: b += "Dog Fragment: ";
                    break;
                    case 6: b += "Cat Fragment: ";
                    break;
                    case 7: b += "Rat Fragment: ";
                    break;
                }
                b += "  \t" + num + "(20)(50)\n";
        }
        text.setText(b);
    }
    
    public void showTool(){
        String b = "";
        for(int i = 0; i < user.getTools().length; i++){
            int num = user.checkToolNum(i);            
            switch(i){
                    case 0: b += "Elephant searcher: ";
                    break;
                    case 1: b += "Lion searcher: ";
                    break;
                    case 2: b += "Tiger searcher: ";
                    break;
                    case 3: b += "Leopard searcher: ";
                    break;
                    case 4: b += "Wolf searcher: ";
                    break;
                    case 5: b += "Dog searcher: ";
                    break;
                    case 6: b += "Cat searcher: ";
                    break;
                    case 7: b += "Rat searcher: ";
                    break;
                }
                b += "    \t" + num + "\n";
        }
        text.setText(b);
    }
    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                if(ae.getSource() == btnTool){
                    showTool();
                }
                else if(ae.getSource() == btnFragment){
                    showFragment();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unknown error when checking package", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

