package MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StoreUI extends JDialog
{
    private String ID;

    private Container contents;
    private JButton[] buttons;
    private JTextArea text;
    private JPanel panel1, panel2;
    private ButtonHandler bh;

    private ArrayList<User> users;
    private User user;

    private boolean found;

    private FileIO io;
    public StoreUI(JFrame frame, String ID){
        super(frame, "Store", true);
        contents = getContentPane();
        contents.setLayout(new BorderLayout());

        this.ID = ID;
        findUser();

        bh = new ButtonHandler();
        buttons = new JButton[9];       

        panel1 = Pane();
        panel2 = createButtons();

        contents.add(panel1, BorderLayout.WEST);
        contents.add(panel2, BorderLayout.EAST);

        setSize(360,280);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JPanel createButtons(){
        JPanel panel = new JPanel();   
        panel.setLayout(new GridLayout(8,1));
        for(int i = 0; i < user.getTools().length; i++){
            buttons[i] = new JButton("Buy it");
            buttons[i].addActionListener(bh);
            if(!found){
                buttons[i].setEnabled(false);
            }
            panel.add(buttons[i],0,i);
        }
        return panel;
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
            JOptionPane.showMessageDialog(null, "You're a guest, please buying after login", "Message", JOptionPane.INFORMATION_MESSAGE);            
        }
    }

    public JPanel Pane(){
        JPanel panel = new JPanel();
        text = new JTextArea("");
        text.setEditable(false);
        text.setBackground(null);
        String b = "";
        for(int i = 0; i < user.getTools().length; i++){
            int num = user.checkToolNum(i);            
            switch(i){
                case 0: b += "Elephant searcher:     \t50 coins";
                break;
                case 1: b += "Lion searcher:     \t40 coins";
                break;
                case 2: b += "Tiger searcher:     \t35 coins";
                break;
                case 3: b += "Leopard searcher:     \t30 coins";
                break;
                case 4: b += "Wolf searcher:     \t25 coins";
                break;
                case 5: b += "Dog searcher:     \t15 coins";
                break;
                case 6: b += "Cat searcher:     \t10 coins";
                break;
                case 7: b += "Rat searcher:     \t15 coins";
                break;
            }
            b += "\n\n";
        }
        text.setText(b);
        panel.add(text);
        return panel;
    }

    public void buy(int index){      
        int price = 0;
        switch(index){
            case 0: price = 50;
            break;
            case 1: price = 40; 
            break;
            case 2: price = 35;
            break;
            case 3: price = 30;
            break;
            case 4: price = 25;
            break;
            case 5: price = 15;
            break;
            case 6: price = 10;
            break;
            case 7: price = 15;
            break;
        }
        
        if(user.getCoins()-price < 0){
            JOptionPane.showMessageDialog(null, "Your coins aren't enough", "Message", JOptionPane.INFORMATION_MESSAGE); 
        }
        else{
            user.setToolNum(index, user.checkToolNum(index) + 1);
            user.setCoins(user.getCoins() - price);
            JOptionPane.showMessageDialog(null,"You buy it successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }        
        io.writeObjFile("Users.dat", users);
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                for(int i = 0; i < user.getTools().length; i++){
                    if(ae.getSource() == buttons[i]){
                        buy(i);
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unknown error when checking package", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

