/**
 * 
 */
package MainGame;

/**
 * @author wujiarui
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawUI extends JDialog
{
    private String ID;

    private Container contents;
    private JButton btnOne, btnTen; //draw one time or ten times
    private JTextArea money;        //show the remained coins

    private ArrayList<User> users;
    private User user;

    private boolean found;

    private FileIO io;
    public DrawUI(JFrame frame, String ID){
        super(frame, "Draw Fragments", true);
        contents = getContentPane();
        contents.setLayout(new FlowLayout());

        this.ID = ID;
        findUser();

        btnOne = new JButton("Draw 1 time");
        btnTen = new JButton("Draw 10 times");
        money = new JTextArea("");
        money.setBackground(null);
        money.setEditable(false);        
        if(found){
            money.setText("Coins: " + user.getCoins());            
        }
        else{
            btnOne.setEnabled(false);
            btnTen.setEnabled(false);
        }

        ButtonHandler bh = new ButtonHandler();
        btnOne.addActionListener(bh);
        btnTen.addActionListener(bh);

        contents.add(money);
        contents.add(btnOne);
        contents.add(btnTen);

        setSize(180,150);
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
            JOptionPane.showMessageDialog(null, "You're a guest, please drawing after login", "Message", JOptionPane.INFORMATION_MESSAGE);            
        }
    }

    public void draw(int times){
        if((user.getCoins() - 10*times) >= 0){
            String result = "";
            Random random = new Random();
            user.setCoins(user.getCoins() - 10 *times);
            money.setText("Coins: " + user.getCoins());
            for(int i = 0; i < times; i++){
                int draw = random.nextInt(20);
                if(draw >= 0 && draw < 8){//5% for each type of fragment
                    user.setFragment(draw, user.checkFragment(draw) + 1);
                    String b = "";       //name of fragment
                    switch(draw){
                        case 0: b = "Elephant Fragment";
                        break;
                        case 1: b = "Lion Fragment";
                        break;
                        case 2: b = "Tiger Fragment";
                        break;
                        case 3: b = "Leopard Fragment";
                        break;
                        case 4: b = "Wolf Fragment";
                        break;
                        case 5: b = "Dog Fragment";
                        break;
                        case 6: b = "Cat Fragment";
                        break;
                        case 7: b = "Rat Fragment";
                        break;
                    }
                    result +=  b + "\n";
                }
                else{
                    result += "nothing\n";
                }
            }
            io.writeObjFile("Users.dat", users);
            JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Your coins aren't enough", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                if(ae.getSource() == btnOne){
                    draw(1);
                }
                else if(ae.getSource() == btnTen){
                    draw(10);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unknown error when drawing", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}

