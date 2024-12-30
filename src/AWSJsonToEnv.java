import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AWSJsonToEnv {

    static void createAndShowGUI() {
        JFrame frame = new JFrame("SSM JSON to .env Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Select JSON file to convert:");
        panel.add(label, BorderLayout.NORTH);

        JButton openButton = new JButton("Open JSON File");
        JButton saveButton = new JButton("Save as .env");
        saveButton.setEnabled(false);

        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        panel.add(scrollPane, BorderLayout.SOUTH);

        JFileChooser fileChooser = new JFileChooser();

        final File[] selectedFile = new File[1];

        openButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile();
                statusArea.setText("Selected file: " + selectedFile[0].getAbsolutePath() + " (will be converted to .env)");
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(e -> {
            if (selectedFile[0] != null) {
                int returnValue = fileChooser.showSaveDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File outputFile = fileChooser.getSelectedFile();
                    try {
                        convertJsonToEnv(selectedFile[0].getAbsolutePath(), outputFile.getAbsolutePath());
                        statusArea.append("\nConversion successful! Saved to: " + outputFile.getAbsolutePath());
                    } catch (Exception ex) {
                        statusArea.append("\nError: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void convertJsonToEnv(String inputFilePath, String outputFilePath) throws Exception {
        // Read the input JSON file
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        JSONObject json = new JSONObject(content);

        // Extract parameters and convert to .env format
        List<String> envLines = json.getJSONArray("Parameters").toList().stream()
                .map(obj -> (HashMap<?, ?>) obj)
                .map(param -> {
                    String name = ((String) param.get("Name")).substring(((String) param.get("Name")).lastIndexOf('/') + 1);
                    String value = (String) param.get("Value");
                    return name + "=" + value;
                })
                .collect(Collectors.toList());

        // Write to the output .env file
        Files.write(Paths.get(outputFilePath), envLines);
    }

}
