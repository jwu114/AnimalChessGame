package MainGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginUI extends JFrame
{
    private Container contents;

    private JLabel labelID, labelPassword;
    private JTextField txtID;
    private JPasswordField password;

    private JButton btnLogin;
    private JButton btnRegister;

    
    public LoginUI(){
        super("Login");
        contents = getContentPane();

        contents.add(createLogin());

        setSize(250,130);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JComponent createLogin(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        labelID = new JLabel( "Enter ID" ); // label for ID
        txtID = new JTextField( "", 12 );   // instantiate ID text field

        labelPassword = new JLabel( "Enter password" ); // password label
        password = new JPasswordField( 8 ); // instantiate password field
        password.setEchoChar( '*' );

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        panel.add(labelID);        
        panel.add(txtID);
        panel.add(labelPassword);
        panel.add(password);
        panel.add(btnLogin);
        panel.add(btnRegister);

        ButtonHandler bh = new ButtonHandler();
        btnLogin.addActionListener(bh);
        btnRegister.addActionListener(bh);
        txtID.addActionListener(bh);
        password.addActionListener(bh);

        return panel;
    }
    
    public JFrame getFrame(){
        return this;
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            try{
                if(ae.getSource() == btnLogin){
                    FileIO io = new FileIO();
                    ArrayList<User> users = io.readObjFile("Users.dat");  //get Info of users from binary file
                    if(users.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No user, please register a new one.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        boolean found = false;  //find username and password
                        for(int i = 0; i < users.size(); i++){
                            if ((users.get(i).getUserName()).equals(txtID.getText())){  //username is found
                                if ((users.get(i).getPassword()).equals(password.getText())){
                                    found = true;  //password is correct
                                }                            
                            }                           
                        }
                         
                        if(found){  //password is correct
                            dispose();
                            MenuUI menu = new MenuUI(txtID.getText()); //add user information
                            menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Incorrect username or password", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                else if(ae.getSource() == btnRegister){ 
                    RegisterUI register = new RegisterUI(getFrame());  //create RegisterUI class
                    register.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Unknown error when login", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

