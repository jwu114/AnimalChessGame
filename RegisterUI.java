package MainGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RegisterUI extends JDialog
{
    private Container contents;

    private JLabel labelID, labelPassword;
    private JTextField txtID;
    private JPasswordField password;

    private JButton btnFinish;
    private JButton btnBack;

    public RegisterUI(JFrame frame){
        super(frame, "Register", true);
        contents = getContentPane();

        contents.add(createRegister());

        setSize(250,130);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JComponent createRegister(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        labelID = new JLabel( "Enter new ID" ); // label for ID
        txtID = new JTextField( "", 12 );   // instantiate ID text field

        labelPassword = new JLabel( "Enter new password" ); // password label
        password = new JPasswordField( 8 ); // instantiate password field
        password.setEchoChar( '*' );

        btnBack = new JButton("Back");
        btnFinish = new JButton("Finish");

        panel.add(labelID);        
        panel.add(txtID);
        panel.add(labelPassword);
        panel.add(password);
        panel.add(btnBack);
        panel.add(btnFinish);

        ButtonHandler bh = new ButtonHandler();
        btnBack.addActionListener(bh);
        btnFinish.addActionListener(bh);
        txtID.addActionListener(bh);
        password.addActionListener(bh);

        return panel;
    }

    public boolean checkSameID(String newID){
        boolean same = false;
        FileIO io = new FileIO();
        ArrayList<User> users = io.readObjFile("Users.dat");
        for(int i = 0; i < users.size(); i++){
            if ((users.get(i).getUserName()).equals(newID)){  //username is found
                same = true;
            }                           
        }
        return same;
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                if(ae.getSource() == btnBack){
                    dispose();  //go back to LoginUI
                }
                else if(ae.getSource() == btnFinish){ //need to check the same username
                    FileIO io = new FileIO();
                    ArrayList<User> users = io.readObjFile("Users.dat");

                    if(!checkSameID(txtID.getText()) && !(txtID.getText()).equals("Guest") && !(txtID.getText()).equals("")){    //"Guest" is an unavailable name
                        User user = new User(txtID.getText(), password.getText());   //create new User class according to the input of users
                        users.add(user);
                        io.writeObjFile("Users.dat", users);  //store new user's information
                        JOptionPane.showMessageDialog(null, "Register successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The ID has been used or is unavailable, please change another.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unknown error when register", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

