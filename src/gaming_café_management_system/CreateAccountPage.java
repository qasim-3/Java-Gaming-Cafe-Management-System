package gaming_café_management_system;

import model.User;
import data_access_object.userDAO;

public class CreateAccountPage extends javax.swing.JFrame {

    public String emailPattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    public String phonePattern = "^[0-9]*$";

    public CreateAccountPage() {
        initComponents();
        buttonCreateAccount.setEnabled(false);
    }

    public void clear() {
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPhone.setText("");
    }

    public void validateFields() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String phone = txtPhone.getText();
        if (!name.equals("") && email.matches(emailPattern) && !password.equals("")) {
            buttonCreateAccount.setEnabled(true);
        } else {
            buttonCreateAccount.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        buttonExitCreateAccount = new javax.swing.JButton();
        buttonCreateAccount = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create Account");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 320, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/people-outline.png"))); // NOI18N
        jLabel2.setText("Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email-outline(1)(1).png"))); // NOI18N
        jLabel3.setText("Email");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 230, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 230, -1));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 230, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shield-outline.png"))); // NOI18N
        jLabel4.setText("Password");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/phone-outline.png"))); // NOI18N
        jLabel5.setText("Phone");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneKeyReleased(evt);
            }
        });
        getContentPane().add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 230, -1));

        buttonExitCreateAccount.setBackground(new java.awt.Color(254, 254, 254));
        buttonExitCreateAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-back-outline.png"))); // NOI18N
        buttonExitCreateAccount.setText("Back");
        buttonExitCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitCreateAccountActionPerformed(evt);
            }
        });
        getContentPane().add(buttonExitCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 340, -1));

        buttonCreateAccount.setBackground(new java.awt.Color(254, 254, 254));
        buttonCreateAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/person-add-outline.png"))); // NOI18N
        buttonCreateAccount.setText("Create Account");
        buttonCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateAccountActionPerformed(evt);
            }
        });
        getContentPane().add(buttonCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 340, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login-bg.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void buttonCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateAccountActionPerformed
        // Create a new User object
        User user = new User();

        // Set the user details
        user.setName(txtName.getText());
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());
        user.setPhone(txtPhone.getText());

        // Set the status to 'false' by default
        user.setStatus("false");  // Add this line

        // Save the user to the database
        userDAO.save(user);

        // Clear the fields after account creation
        clear();
    }//GEN-LAST:event_buttonCreateAccountActionPerformed

    private void buttonExitCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitCreateAccountActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        new LoginAccountPage().setVisible(true);
    }//GEN-LAST:event_buttonExitCreateAccountActionPerformed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtNameKeyReleased

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtPhoneKeyReleased

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        // TODO add your handling code here:
        validateFields();
    }//GEN-LAST:event_txtPasswordKeyReleased

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateAccountPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreateAccount;
    private javax.swing.JButton buttonExitCreateAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
