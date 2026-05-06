package gaming_café_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Home extends javax.swing.JFrame {

    private final HashMap<String, Timer> deviceTimers = new HashMap<>();
    private final HashMap<String, Integer> deviceRemainingSeconds = new HashMap<>();
    private final HashMap<String, String> deviceIPs = new HashMap<>();
    private String selectedDevice = null;

    public Home() {
        initComponents();
        initializeDeviceIPs();
    }

    public Home(String userEmail) {

        initComponents();

        String email = userEmail;

        if (!email.equals("admin@gmail.com")) {

            buttonManageUsers.setEnabled(false);  // Disables the button and grays it out

        }

        initializeDeviceIPs(); // Ensure this is called to initialize timers

        // Optionally set a default selected device if needed
        selectedDevice = "PC1"; // or any default device

        updateSelectedIPAddress(selectedDevice); // Set the initial device

    }

    private void initializeDeviceIPs() {
        deviceIPs.put("PC1", "192.168.1.101");
        deviceIPs.put("PC2", "192.168.1.102");
        deviceIPs.put("PC3", "192.168.1.103");
        deviceIPs.put("PC4", "192.168.1.104");
        deviceIPs.put("PS5", "192.168.1.201");
        deviceIPs.put("Xbox", "192.168.1.202");
        deviceIPs.put("VR", "192.168.1.203");
        deviceIPs.put("AdminPC", "N/A");
        deviceIPs.put("Server", "N/A");

        // Initialize timers and remaining seconds for each device
        for (String device : deviceIPs.keySet()) {
            deviceTimers.put(device, createTimer(device));
            deviceRemainingSeconds.put(device, 0);
            deviceTimers.get(device).start(); // Ensure timers start
        }
    }

    private Timer createTimer(String device) {
        return new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int remaining = deviceRemainingSeconds.get(device);
                if (remaining > 0) {
                    deviceRemainingSeconds.put(device, remaining - 1);
                    if (device.equals(selectedDevice)) {
                        updateTimerLabel();
                    }
                } else {
                    deviceTimers.get(device).stop();
                }
            }
        });
    }

    private void updateTimerLabel() {
        // Check if selectedDevice is null
        if (selectedDevice == null) {
            return;  // Exit if no device is selected
        }

        // Check if the selectedDevice exists in the map and the value is not null
        Integer remainingSeconds = deviceRemainingSeconds.get(selectedDevice);

        // If remainingSeconds is null, handle the case (e.g., set to 0 or show an error)
        if (remainingSeconds == null) {
            remainingSeconds = 0;  // Or handle this case in a way that makes sense for your application
        }

        // Convert remaining seconds to hours, minutes, and seconds
        int hours = remainingSeconds / 3600;
        int minutes = (remainingSeconds % 3600) / 60;
        int seconds = remainingSeconds % 60;

        // Update the label with the formatted time
        txtTimeLeft.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        // Change the text color to red if time <= 10 minutes (600 seconds)
        if (remainingSeconds <= 600) {
            txtTimeLeft.setForeground(Color.RED);
        } else {
            txtTimeLeft.setForeground(Color.BLACK);
        }
    }

    private void updateSelectedIPAddress(String device) {
        selectedDevice = device;
        txtSelectedIPAddress.setText(deviceIPs.getOrDefault(device, "Unknown"));
        updateTimerLabel();

        // Disable buttons for Admin and Server devices
        if (device.equals("AdminPC") || device.equals("Server")) {
            buttonTerminateSession.setEnabled(false);
            buttonAdd30Minutes.setEnabled(false);
            buttonAdd60Minutes.setEnabled(false);
        } else {
            buttonTerminateSession.setEnabled(true);
            buttonAdd30Minutes.setEnabled(true);
            buttonAdd60Minutes.setEnabled(true);
        }

        // Start the timer for the selected device
        if (deviceTimers.containsKey(device)) {
            deviceTimers.get(device).start();  // Ensure the timer starts
        }
    }

    private void addTime(String device, int minutesToAdd, double amountToDeduct) {
        // Get the current balance from the text field
        String currentBalanceText = txtAmount.getText().replace("PKR", "").replace("BALANCE:", "").trim();

        // Parse the balance or set it to 0 if the text field is empty
        double currentBalance = currentBalanceText.isEmpty() ? 0 : Double.parseDouble(currentBalanceText);

        // Ensure the balance is sufficient for the deduction
        if (currentBalance >= amountToDeduct) {
            // Deduct the amount from the balance
            currentBalance -= amountToDeduct;

            // Update the balance in the text field
            txtAmount.setText(String.format("BALANCE: PKR %.2f", currentBalance));
        } else {
            // Show an error message if the balance is insufficient
            JOptionPane.showMessageDialog(this, "Insufficient balance to add time.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add time to the selected device
        Integer currentSeconds = deviceRemainingSeconds.get(device);

        // If currentSeconds is null, initialize it to 0 (this handles devices that may not have time set yet)
        if (currentSeconds == null) {
            currentSeconds = 0;
        }

        // Add the specified minutes to the current time (converted to seconds)
        deviceRemainingSeconds.put(device, currentSeconds + minutesToAdd * 60);

        // Update the timer if the selected device is the one being updated
        if (device.equals(selectedDevice)) {
            updateTimerLabel();
        }

        // Start the timer for the device (if it exists in deviceTimers)
        if (deviceTimers.containsKey(device)) {
            deviceTimers.get(device).start();
        }
    }

    private void terminateSession(String device) {
        deviceTimers.get(device).stop();
        deviceRemainingSeconds.put(device, 0);
        if (device.equals(selectedDevice)) {
            updateTimerLabel();
        }
    }

    private void processPayment() {
        // Load the icon image from the specified file path
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/payment-60.png"));

        // Show a dialog asking for the amount to deposit with the icon
        String input = (String) JOptionPane.showInputDialog(this, "Enter the amount to deposit (PKR)", "Deposit Payment", JOptionPane.PLAIN_MESSAGE, icon, null, "");

        // Check if the user clicked Cancel or didn't enter anything
        if (input != null && !input.trim().isEmpty()) {
            try {
                // Convert the input to a number
                double depositAmount = Double.parseDouble(input.trim());

                // Get the current balance (convert from txtAmount label text)
                String currentBalanceText = txtAmount.getText().replace("PKR", "").replace("BALANCE:", "").replace("PLEASE DEPOSIT PAYMENT", "").trim();

                double currentBalance = 0;
                if (!currentBalanceText.isEmpty()) {
                    currentBalance = Double.parseDouble(currentBalanceText);
                }

                // Add the deposit amount to the current balance
                double newBalance = currentBalance + depositAmount;

                // Update the txtAmount label with the new balance in PKR, with "BALANCE: " added
                txtAmount.setText(String.format("BALANCE: PKR %.2f", newBalance));

            } catch (NumberFormatException e) {
                // Show an error message if the input is not a valid number
                JOptionPane.showMessageDialog(this, "Invalid amount entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void showHelpDialog() {
        // HTML content for the help dialog with bold IDs
        String message = "<html>"
                + "<p><b>https://github.com/hilalnwb</b></p>"
                + "</html>";

        // Load the help icon image from the specified file path
        ImageIcon icon = new ImageIcon("src/images/help-60.png");

        // Show the message dialog with the formatted HTML and icon
        JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.INFORMATION_MESSAGE, icon);
    }

// Method to show refreshment selection dialog and deduct price from balance
    private void showRefreshmentSelectionDialog() {
        // Refreshment options with their prices
        String[] options = {
            "Samosa - 50 PKR",
            "Shawarma - 150 PKR",
            "French Fries - 100 PKR",
            "7up - 100 PKR",
            "Sprite - 100 PKR",
            "Juice - 50 PKR"
        };

        // Display a pop-up dialog with the refreshment options
        String selectedOption = (String) JOptionPane.showInputDialog(
                this,
                "Select a refreshment:",
                "Refreshments",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selectedOption != null && !selectedOption.isEmpty()) {
            // Parse the price from the selected option
            double price = getPriceFromOption(selectedOption);

            // Deduct the price from the balance (txtAmount)
            deductAmount(price);
        }
    }

// Helper method to extract price from the selected option
    private double getPriceFromOption(String option) {
        if (option.contains("50 PKR")) {
            return 50.0;
        } else if (option.contains("100 PKR")) {
            return 100.0;
        } else if (option.contains("150 PKR")) {
            return 150.0;
        }
        return 0.0; // Default to 0 if no match
    }

// Method to deduct the amount from the balance
    private void deductAmount(double amount) {
        // Get the current balance from the txtAmount label
        String currentBalanceText = txtAmount.getText().replace("PKR", "").replace("BALANCE:", "").trim();

        // Parse the balance or set it to 0 if the text field is empty
        double currentBalance = currentBalanceText.isEmpty() ? 0 : Double.parseDouble(currentBalanceText);

        // Ensure the balance is sufficient for the deduction
        if (currentBalance >= amount) {
            // Deduct the amount from the balance
            currentBalance -= amount;

            // Update the balance in the text field
            txtAmount.setText(String.format("BALANCE: PKR %.2f", currentBalance));
        } else {
            // Show an error message if the balance is insufficient
            JOptionPane.showMessageDialog(this, "Insufficient balance to purchase this item.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSelectedIPAddress = new javax.swing.JLabel();
        txtTimeLeft = new javax.swing.JLabel();
        txtAmount = new javax.swing.JLabel();
        buttonLogout = new javax.swing.JButton();
        buttonGames = new javax.swing.JButton();
        buttonRefreshments = new javax.swing.JButton();
        buttonPayment = new javax.swing.JButton();
        buttonHelp = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        buttonPC3 = new javax.swing.JButton();
        buttonPC2 = new javax.swing.JButton();
        buttonPC4 = new javax.swing.JButton();
        buttonPS5 = new javax.swing.JButton();
        buttonPC1 = new javax.swing.JButton();
        buttonServer = new javax.swing.JButton();
        buttonVR = new javax.swing.JButton();
        buttonAdminPC = new javax.swing.JButton();
        buttonXbox = new javax.swing.JButton();
        buttonTerminateSession = new javax.swing.JButton();
        buttonAdd60Minutes = new javax.swing.JButton();
        buttonAdd30Minutes = new javax.swing.JButton();
        buttonManageUsers = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSelectedIPAddress.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        txtSelectedIPAddress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSelectedIPAddress.setText("IP ADDRESS");
        getContentPane().add(txtSelectedIPAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 210, 30));

        txtTimeLeft.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        txtTimeLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTimeLeft.setText("TIME");
        getContentPane().add(txtTimeLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 131, 210, -1));

        txtAmount.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        txtAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAmount.setText("PLEASE DEPOSIT PAYMENT");
        txtAmount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(txtAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 580, 250, 70));

        buttonLogout.setBackground(new java.awt.Color(254, 254, 254));
        buttonLogout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-back-35.png"))); // NOI18N
        buttonLogout.setText("Logout ");
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });
        getContentPane().add(buttonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(852, 12, 120, 50));

        buttonGames.setBackground(new java.awt.Color(174, 231, 248));
        buttonGames.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        buttonGames.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/controller-60.png"))); // NOI18N
        buttonGames.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Games", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonGames.setMaximumSize(new java.awt.Dimension(70, 86));
        buttonGames.setMinimumSize(new java.awt.Dimension(70, 86));
        buttonGames.setPreferredSize(new java.awt.Dimension(70, 86));
        buttonGames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGamesActionPerformed(evt);
            }
        });
        getContentPane().add(buttonGames, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, -1));

        buttonRefreshments.setBackground(new java.awt.Color(207, 255, 246));
        buttonRefreshments.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        buttonRefreshments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refreshments-color-60.png"))); // NOI18N
        buttonRefreshments.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Refreshments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonRefreshments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshmentsActionPerformed(evt);
            }
        });
        getContentPane().add(buttonRefreshments, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 200, -1));

        buttonPayment.setBackground(new java.awt.Color(174, 231, 248));
        buttonPayment.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        buttonPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/payment-60.png"))); // NOI18N
        buttonPayment.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPaymentActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 200, -1));

        buttonHelp.setBackground(new java.awt.Color(207, 255, 246));
        buttonHelp.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        buttonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help-60.png"))); // NOI18N
        buttonHelp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Help", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHelpActionPerformed(evt);
            }
        });
        getContentPane().add(buttonHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 200, -1));

        buttonExit.setBackground(new java.awt.Color(174, 231, 248));
        buttonExit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        buttonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit-60.png"))); // NOI18N
        buttonExit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Exit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 200, -1));

        buttonPC3.setBackground(java.awt.SystemColor.controlHighlight);
        buttonPC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PC-off-125.png"))); // NOI18N
        buttonPC3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.103 (PC-3)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPC3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPC3.setOpaque(true);
        buttonPC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPC3ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 170, -1));

        buttonPC2.setBackground(java.awt.SystemColor.controlHighlight);
        buttonPC2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PC-off-125.png"))); // NOI18N
        buttonPC2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.102 (PC-2)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPC2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPC2.setOpaque(true);
        buttonPC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPC2ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 170, -1));

        buttonPC4.setBackground(java.awt.SystemColor.controlHighlight);
        buttonPC4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PC-off-125.png"))); // NOI18N
        buttonPC4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.104 (PC-4)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPC4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPC4.setOpaque(true);
        buttonPC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPC4ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPC4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, 170, -1));

        buttonPS5.setBackground(java.awt.SystemColor.controlHighlight);
        buttonPS5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PS5-125.png"))); // NOI18N
        buttonPS5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.201 (PS5)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPS5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPS5.setOpaque(true);
        buttonPS5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPS5ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPS5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 460, 170, -1));

        buttonPC1.setBackground(java.awt.SystemColor.controlHighlight);
        buttonPC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PC-off-125.png"))); // NOI18N
        buttonPC1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.101 (PC-1)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonPC1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonPC1.setOpaque(true);
        buttonPC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPC1ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonPC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 170, -1));

        buttonServer.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusedBackground"));
        buttonServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/server-125.png"))); // NOI18N
        buttonServer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.2 (Server)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonServer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonServer.setOpaque(true);
        buttonServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonServerActionPerformed(evt);
            }
        });
        getContentPane().add(buttonServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 170, 150));

        buttonVR.setBackground(java.awt.SystemColor.controlHighlight);
        buttonVR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/VR-125.png"))); // NOI18N
        buttonVR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.203 (VR)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonVR.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonVR.setOpaque(true);
        buttonVR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVRActionPerformed(evt);
            }
        });
        getContentPane().add(buttonVR, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, 170, -1));

        buttonAdminPC.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusedBackground"));
        buttonAdminPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin-125.png"))); // NOI18N
        buttonAdminPC.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.1 (Admin)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonAdminPC.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonAdminPC.setOpaque(true);
        buttonAdminPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdminPCActionPerformed(evt);
            }
        });
        getContentPane().add(buttonAdminPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 170, -1));

        buttonXbox.setBackground(java.awt.SystemColor.controlHighlight);
        buttonXbox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xbox-125.png"))); // NOI18N
        buttonXbox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "192.168.1.202 (Xbox)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        buttonXbox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonXbox.setOpaque(true);
        buttonXbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonXboxActionPerformed(evt);
            }
        });
        getContentPane().add(buttonXbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 460, 170, -1));

        buttonTerminateSession.setBackground(new java.awt.Color(254, 254, 254));
        buttonTerminateSession.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonTerminateSession.setForeground(new java.awt.Color(204, 0, 51));
        buttonTerminateSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/power-outline.png"))); // NOI18N
        buttonTerminateSession.setText("Terminate Session");
        buttonTerminateSession.setVerifyInputWhenFocusTarget(false);
        buttonTerminateSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTerminateSessionActionPerformed(evt);
            }
        });
        getContentPane().add(buttonTerminateSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 210, -1));

        buttonAdd60Minutes.setBackground(new java.awt.Color(254, 254, 254));
        buttonAdd60Minutes.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonAdd60Minutes.setForeground(new java.awt.Color(0, 153, 0));
        buttonAdd60Minutes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus-outline.png"))); // NOI18N
        buttonAdd60Minutes.setText("60 Minutes");
        buttonAdd60Minutes.setVerifyInputWhenFocusTarget(false);
        buttonAdd60Minutes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdd60MinutesActionPerformed(evt);
            }
        });
        getContentPane().add(buttonAdd60Minutes, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, 210, -1));

        buttonAdd30Minutes.setBackground(new java.awt.Color(254, 254, 254));
        buttonAdd30Minutes.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonAdd30Minutes.setForeground(new java.awt.Color(0, 153, 0));
        buttonAdd30Minutes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus-outline.png"))); // NOI18N
        buttonAdd30Minutes.setText("30 Minutes");
        buttonAdd30Minutes.setVerifyInputWhenFocusTarget(false);
        buttonAdd30Minutes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdd30MinutesActionPerformed(evt);
            }
        });
        getContentPane().add(buttonAdd30Minutes, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 230, 210, -1));

        buttonManageUsers.setBackground(new java.awt.Color(254, 254, 254));
        buttonManageUsers.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        buttonManageUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/person-done-outline.png"))); // NOI18N
        buttonManageUsers.setText("Manage");
        buttonManageUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonManageUsersActionPerformed(evt);
            }
        });
        getContentPane().add(buttonManageUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 12, 110, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard-bg-color.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGamesActionPerformed
        // TODO add your handling code here:
        // Create an instance of GamesPage

        GamesPage gamesPage = new GamesPage();

        // Optionally, dispose of the current frame (Home) if you don't want to return to it
        this.dispose(); // This will close the current frame

        // Set the GamesPage to be visible
        gamesPage.setVisible(true);
    }//GEN-LAST:event_buttonGamesActionPerformed

    private void buttonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPaymentActionPerformed
        // TODO add your handling code here:
        processPayment();
    }//GEN-LAST:event_buttonPaymentActionPerformed

    private void buttonHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHelpActionPerformed
        // TODO add your handling code here:
        showHelpDialog();
    }//GEN-LAST:event_buttonHelpActionPerformed

    private void buttonRefreshmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshmentsActionPerformed
        // TODO add your handling code here:
        showRefreshmentSelectionDialog();
    }//GEN-LAST:event_buttonRefreshmentsActionPerformed

    private void buttonPC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPC3ActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("PC3");
    }//GEN-LAST:event_buttonPC3ActionPerformed

    private void buttonPC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPC2ActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("PC2");
    }//GEN-LAST:event_buttonPC2ActionPerformed

    private void buttonPC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPC4ActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("PC4");
    }//GEN-LAST:event_buttonPC4ActionPerformed

    private void buttonPS5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPS5ActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("PS5");
    }//GEN-LAST:event_buttonPS5ActionPerformed

    private void buttonPC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPC1ActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("PC1");
    }//GEN-LAST:event_buttonPC1ActionPerformed

    private void buttonServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonServerActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("Server");
    }//GEN-LAST:event_buttonServerActionPerformed

    private void buttonVRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVRActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("VR");
    }//GEN-LAST:event_buttonVRActionPerformed

    private void buttonAdminPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdminPCActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("AdminPC");
    }//GEN-LAST:event_buttonAdminPCActionPerformed

    private void buttonXboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonXboxActionPerformed
        // TODO add your handling code here:
        updateSelectedIPAddress("Xbox");
    }//GEN-LAST:event_buttonXboxActionPerformed

    private void buttonAdd60MinutesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdd60MinutesActionPerformed
        if (selectedDevice != null) {
            addTime(selectedDevice, 60, 1000.00);  // Deduct 1000 PKR for 60 minutes
        }
    }//GEN-LAST:event_buttonAdd60MinutesActionPerformed

    private void buttonAdd30MinutesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdd30MinutesActionPerformed
        // TODO add your handling code here:
        if (selectedDevice != null) {
            addTime(selectedDevice, 30, 500.00);  // Deduct 500 PKR for 30 minutes
        }
    }//GEN-LAST:event_buttonAdd30MinutesActionPerformed

    private void buttonTerminateSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTerminateSessionActionPerformed
        // TODO add your handling code here:
        if (selectedDevice != null) {
            terminateSession(selectedDevice);
        }
    }//GEN-LAST:event_buttonTerminateSessionActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Select", JOptionPane.YES_NO_OPTION);

        // If the user clicked "Yes" (a == 0), exit the application
        if (a == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoutActionPerformed
        // TODO add your handling code here:
        // Hide the current window (Home)
        this.setVisible(false);

        // Create a new instance of LoginAccountPage and show it
        new LoginAccountPage().setVisible(true);
    }//GEN-LAST:event_buttonLogoutActionPerformed

    private void buttonManageUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonManageUsersActionPerformed
        // TODO add your handling code here:
        // Create an instance of ManageUsers and make it visible
        ManageUsers manageUsersFrame = new ManageUsers();
        manageUsersFrame.setVisible(true);

        // Optionally, close the current frame if needed
        this.dispose(); // This will close the current frame (if you want to close the current window)
    }//GEN-LAST:event_buttonManageUsersActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Home().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd30Minutes;
    private javax.swing.JButton buttonAdd60Minutes;
    private javax.swing.JButton buttonAdminPC;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonGames;
    private javax.swing.JButton buttonHelp;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonManageUsers;
    private javax.swing.JButton buttonPC1;
    private javax.swing.JButton buttonPC2;
    private javax.swing.JButton buttonPC3;
    private javax.swing.JButton buttonPC4;
    private javax.swing.JButton buttonPS5;
    private javax.swing.JButton buttonPayment;
    private javax.swing.JButton buttonRefreshments;
    private javax.swing.JButton buttonServer;
    private javax.swing.JButton buttonTerminateSession;
    private javax.swing.JButton buttonVR;
    private javax.swing.JButton buttonXbox;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel txtAmount;
    private javax.swing.JLabel txtSelectedIPAddress;
    private javax.swing.JLabel txtTimeLeft;
    // End of variables declaration//GEN-END:variables
}
