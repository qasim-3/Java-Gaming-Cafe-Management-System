package gaming_café_management_system;

import data_access_object.userDAO;
import javax.swing.JOptionPane;
import model.User;

public class LoginAccountPage extends javax.swing.JFrame {

    public String emailPattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";

    public LoginAccountPage() {
        initComponents();
        buttonLoginAccount.setEnabled(false);
    }

    public void clear() {
        txtEmail.setText("");
        txtPassword.setText("");
        buttonLoginAccount.setEnabled(false);
    }

    public void validateFields() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (email.matches(emailPattern) && !password.equals("")) {
            buttonLoginAccount.setEnabled(true);
        } else {
            buttonLoginAccount.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        buttonLoginAccount = new javax.swing.JButton();
        buttonRegister = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 320, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email-outline(1)(1).png"))); // NOI18N
        jLabel3.setText("Email");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 230, -1));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 230, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shield-outline.png"))); // NOI18N
        jLabel4.setText("Password");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        buttonExit.setBackground(new java.awt.Color(254, 254, 254));
        buttonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-back-outline.png"))); // NOI18N
        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 340, -1));

        buttonLoginAccount.setBackground(new java.awt.Color(254, 254, 254));
        buttonLoginAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-front.png"))); // NOI18N
        buttonLoginAccount.setText("Login");
        buttonLoginAccount.setVerifyInputWhenFocusTarget(false);
        buttonLoginAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginAccountActionPerformed(evt);
            }
        });
        getContentPane().add(buttonLoginAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 340, -1));

        buttonRegister.setBackground(new java.awt.Color(254, 254, 254));
        buttonRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/person-add-outline.png"))); // NOI18N
        buttonRegister.setText("Register");
        buttonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegisterActionPerformed(evt);
            }
        });
        getContentPane().add(buttonRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 340, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login-bg.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLoginAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginAccountActionPerformed
        // TODO add your handling code here:

        String email = txtEmail.getText();
        String password = txtPassword.getText();
        User user = null;
        user = userDAO.login(email, password);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "<html><b style =\"color:red\">Incorrect Email or Password</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
        } else {
            if (user.getStatus().equals("false")) {
                JOptionPane.showMessageDialog(null, "<html><b>Wait for Admin Approval</b></html>", "Approval Required", JOptionPane.INFORMATION_MESSAGE);
                clear();
            }
            if (user.getStatus().equals("true")) {
                setVisible(false);
                new Home(email).setVisible(true);
            }
        }


    }//GEN-LAST:event_buttonLoginAccountActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_buttonExitActionPerformed

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void buttonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRegisterActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        new CreateAccountPage().setVisible(true);
    }//GEN-LAST:event_buttonRegisterActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginAccountPage().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLoginAccount;
    private javax.swing.JButton buttonRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
