import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.FileWriter;

public class HealthMonitor {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Smart Health Monitoring System");
        frame.setSize(800,550);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("SMART HEALTH MONITORING SYSTEM");
        title.setFont(new Font("Arial",Font.BOLD,16));
        title.setBounds(270,10,300,30);
        frame.add(title);

        JLabel nameLabel = new JLabel("Patient Name:");
        nameLabel.setBounds(30,60,120,25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150,60,150,25);
        frame.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30,100,120,25);
        frame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(150,100,150,25);
        frame.add(ageField);

        JLabel hrLabel = new JLabel("Heart Rate:");
        hrLabel.setBounds(30,140,120,25);
        frame.add(hrLabel);

        JTextField hrField = new JTextField();
        hrField.setBounds(150,140,150,25);
        frame.add(hrField);

        JLabel spo2Label = new JLabel("SpO2:");
        spo2Label.setBounds(30,180,120,25);
        frame.add(spo2Label);

        JTextField spo2Field = new JTextField();
        spo2Field.setBounds(150,180,150,25);
        frame.add(spo2Field);

        JLabel tempLabel = new JLabel("Temperature:");
        tempLabel.setBounds(30,220,120,25);
        frame.add(tempLabel);

        JTextField tempField = new JTextField();
        tempField.setBounds(150,220,150,25);
        frame.add(tempField);

        String columns[] = {"Name","Age","Heart Rate","SpO2","Temperature","Health Status"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);

        // Row color rendering
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,Object value,
                    boolean isSelected,boolean hasFocus,int row,int column) {

                Component c = super.getTableCellRendererComponent(
                        table,value,isSelected,hasFocus,row,column);

                String status = table.getModel().getValueAt(row,5).toString();

                if(status.equals("High Heart Rate") ||
                   status.equals("Low Oxygen") ||
                   status.equals("Fever")) {

                    c.setBackground(Color.PINK);

                } else {

                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(330,60,420,330);
        frame.add(scrollPane);

        JButton addButton = new JButton("Add Patient");
        addButton.setBounds(80,270,150,30);
        frame.add(addButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(80,310,150,30);
        frame.add(resetButton);

        JButton deleteButton = new JButton("Delete Patient");
        deleteButton.setBounds(80,350,150,30);
        frame.add(deleteButton);

        JButton saveButton = new JButton("Save Data");
        saveButton.setBounds(80,390,150,30);
        frame.add(saveButton);

        JButton clearButton = new JButton("Clear Table");
        clearButton.setBounds(80,430,150,30);
        frame.add(clearButton);

        addButton.addActionListener(e -> {

            try {

                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int hr = Integer.parseInt(hrField.getText());
                int spo2 = Integer.parseInt(spo2Field.getText());
                double temp = Double.parseDouble(tempField.getText());

                String status;

                if(hr > 100) {
                    status = "High Heart Rate";
                }
                else if(spo2 < 95) {
                    status = "Low Oxygen";
                }
                else if(temp > 37.5) {
                    status = "Fever";
                }
                else {
                    status = "Normal";
                }

                model.addRow(new Object[]{name,age,hr,spo2,temp,status});

            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(frame,"Enter valid values!");
            }

        });

        resetButton.addActionListener(e -> {

            nameField.setText("");
            ageField.setText("");
            hrField.setText("");
            spo2Field.setText("");
            tempField.setText("");

        });

        deleteButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if(selectedRow != -1){
                model.removeRow(selectedRow);
            }
            else{
                JOptionPane.showMessageDialog(frame,"Select a patient to delete");
            }

        });

        clearButton.addActionListener(e -> {

            model.setRowCount(0);

        });

        saveButton.addActionListener(e -> {

            try {

                FileWriter writer = new FileWriter("patients.txt");

                for(int i=0;i<table.getRowCount();i++) {

                    for(int j=0;j<table.getColumnCount();j++) {

                        writer.write(table.getValueAt(i,j).toString()+" ");

                    }

                    writer.write("\n");
                }

                writer.close();

                JOptionPane.showMessageDialog(frame,"Data saved successfully");

            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        });

        frame.setVisible(true);
    }
}
