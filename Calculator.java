package Calculator;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class Calculator {
    private JFrame frame;

    private JLabel displayLabel;

    private String[] buttonsValues;

    private String[] rightSymbols;

    private String[] topSymbols;

    String a = "0";
    String operator = null;
    String b = null;
    /**
     * Constructor: Initializes the Calculator GUI.
     * Sets up the frame, display label, and all buttons.
     */
    public Calculator(){
        // Initialize button labels and symbols
        this.buttonsValues = new String[]{
                "AC", "+/-", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "√", "="};
        this.rightSymbols = new String[]{"÷", "×", "-", "+", "="};
        this.topSymbols = new String[]{"AC", "+/-", "%"};

        // Create UI components
        this.createFrame();
        this.createLabelPanel();
        this.createButtonsPanel();

        // Make the frame visible
        this.frame.setVisible(true);
    }
    /**
     * This is a private method that creates the main JFrame window.
     */
    private void createFrame(){
        this.frame = new JFrame();
        this.frame.setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        this.frame.setLocationRelativeTo(null); // Center window
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
    }
    /**
     * This is a private method that creates the panel that displays
     * the current input/result.
     */
    private void createLabelPanel(){
        this.displayLabel = new JLabel();
        JPanel displayPanel = new JPanel();

        // Style the label
        displayLabel.setBackground(Constants.CUSTOM_BLACK);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Add label to panel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, 120));
        displayPanel.add(displayLabel);

        // Add panel to frame
        this.frame.add(displayPanel, BorderLayout.NORTH);
    }

    /**
     * This is a private method that creates the panel with
     * all calculator buttons and adds action listeners.
     */
    private void createButtonsPanel(){
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5,4,5,5));
        buttonsPanel.setBackground(Constants.CUSTOM_BLACK);
        this.frame.add(buttonsPanel);

        // Iterate through all button values to create buttons
        for (int i = 0; i < this.buttonsValues.length; i++){
            JButton button = new JButton();
            String buttonValue = this.buttonsValues[i]; // current button label

            // Set styling and add button to panel
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(Constants.CUSTOM_BLACK));

            // Style button based on type
            if (Arrays.asList(this.rightSymbols).contains(buttonValue)) {
                button.setBackground(Constants.CUSTOM_LIGHT_GREY);
                button.setOpaque(true);
                button.setForeground(Constants.CUSTOM_BLACK);
            } else if (Arrays.asList(this.topSymbols).contains(buttonValue)) {
                button.setBackground(Constants.CUSTOM_ORANGE);
                button.setOpaque(true);
                button.setForeground(Color.BLACK);
            } else {
                button.setBackground(Constants.CUSTOM_DARK_GRAY);
                button.setOpaque(true);
                button.setForeground(Color.BLACK);
            }

            buttonsPanel.add(button);

            // Add action listener to handle button click
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent i){
                    JButton button1 = (JButton) i.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(Calculator.this.rightSymbols).contains(buttonValue)){
                        if (buttonValue == "="){
                            if (a != null){
                                b = displayLabel.getText();
                                double numA = Double.parseDouble(a);
                                double numB = Double.parseDouble(b);

                                if (operator == "+"){
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                } else if (operator == "-"){
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                } else if (operator == "÷"){
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                } else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                }
                                clearAll();
                            }
                        } else if ("+-×÷".contains(buttonValue)){
                            if (operator == null){
                                a = displayLabel.getText();
                                displayLabel.setText("0");
                                b = "0";
                            }
                            operator = buttonValue;

                        }

                    } else if (Arrays.asList(Calculator.this.topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            Calculator.this.displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else {
                        if (buttonValue == "."){
                            if (!displayLabel.getText().contains(".")) {
                                Calculator.this.displayLabel.setText(Calculator.this.displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)){
                            if (Calculator.this.displayLabel.getText() == "0"){
                                Calculator.this.displayLabel.setText(buttonValue);
                            } else {
                                Calculator.this.displayLabel.setText(Calculator.this.displayLabel.getText() + buttonValue);
                            }
                        }
                    }

                }

            });
        }
    }

    /**
     * This is a method that clears all operands and the operator.
     */
    public void clearAll(){
        a = "0";
        operator = null;
        b = "0";
    }

    /**
     * This is a method that removes unnecessary ".0" from a number and
     * returns it as string similar to traditional calulators.
     * Example: 5.0 -> "5", 5.25 -> "5.25"
     */
    public String removeZeroDecimal(double numDisplay){
        if (numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

}

}
