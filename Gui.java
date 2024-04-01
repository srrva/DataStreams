import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Gui extends JFrame {
    private JPanel orgPnl;
    private JPanel editPnl;
    private JTextField searchTF;
    private JTextArea resultTA;
    public static JFrame frame;
    public JTextArea FTA;

    public Gui() {
        frame = new JFrame("Text File Search...");
        orgPnl = orgPnel();
        frame.add(orgPnl, BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        JPanel fileLoad = new JPanel();
        frame.add(fileLoad, BorderLayout.SOUTH);


        FTA = new JTextArea();
        frame.add(FTA, BorderLayout.EAST);
        searchTF = new JTextField();
        searchTF.setPreferredSize(new Dimension(300, 30));
        searchTF.addActionListener(e -> performSearch());


        JButton loadBtn = new JButton("Load File");
        loadBtn.addActionListener(e -> rdFile());
        fileLoad.add(loadBtn);

        JPanel searchPnl = new JPanel();
        searchPnl.add(new JLabel("Enter a String: "));
        searchPnl.add(searchTF);
        frame.add(searchPnl, BorderLayout.NORTH);

        JButton ldBtn = new JButton("Search");
        loadBtn.addActionListener(e -> performSearch());
        searchPnl.add(ldBtn);

        editPnl = new JPanel(new BorderLayout());
        frame.add(orgPnl, BorderLayout.WEST);
    }

    public JPanel orgPnel() {
        orgPnl = new JPanel();
        orgPnl.setLayout(new BorderLayout());
        return orgPnl;
    }

    private void rdFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            displayFileContents(selectedFile);
        } else {
            System.out.println("File selection incomplete..");
        }
    }

    private void displayFileContents(File file) {
        try {
            Path filePath = Paths.get(file.getAbsolutePath());
            List<String> lines = Files.readAllLines(filePath);
            String fileContent = lines.stream().collect(Collectors.joining("\n"));

            resultTA = new JTextArea(fileContent);
            orgPnl.add(new JScrollPane(resultTA), BorderLayout.CENTER);
            frame.revalidate();
        } catch (Exception e) {
        }

    }

    public void performSearch() {
        String searchString = searchTF.getText();
        if (resultTA != null) {
            String fileContent = resultTA.getText();

            if (!searchString.isEmpty() && !fileContent.isEmpty()) {
                String filteredContent = fileContent.lines()
                        .filter(line -> line.contains(searchString))
                        .collect(Collectors.joining("\n"));
                FTA.setText(filteredContent);
            } else {
                JOptionPane.showMessageDialog(this, "Please load a file and enter a search string.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please load a file first.");
        }
    }
}