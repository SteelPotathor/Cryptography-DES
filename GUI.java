import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is used to create a GUI for the TripleDES class
 */
public class GUI implements WindowListener, MouseListener {

    public JFrame frame;
    public JLabel label;
    public JPanel jPanel;
    public JButton crypte;
    public JButton decrypte;
    public TripleDES tripleDES;

    /**
     * Constructor of the GUI class. This class is used to create a GUI for the TripleDES class
     */
    public GUI() {
        this.frame = new JFrame("TripleDES");
        this.frame.addWindowListener(this);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(500, 500);

        this.label = new JLabel("Cryptage/Decryptage DES", SwingConstants.CENTER);
        this.jPanel = new JPanel();
        this.crypte = new JButton("Crypter");
        this.decrypte = new JButton("Decrypter");
        this.crypte.addMouseListener(this);
        this.decrypte.addMouseListener(this);

        this.jPanel.add(this.crypte);
        this.jPanel.add(this.decrypte);
        this.frame.add(this.label, BorderLayout.CENTER);
        this.frame.add(this.jPanel, BorderLayout.SOUTH);

        this.frame.setVisible(true);
        this.tripleDES = new TripleDES();
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // If the user clicks on the crypte button
        if (e.getSource() == this.crypte) {
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            // Only accept .txt files
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            // Open the file chooser in the user home directory
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            // Show the file chooser
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String parent = fileChooser.getSelectedFile().getParent();
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                StringBuilder text = new StringBuilder();
                // Read the file line by line
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append("\n");
                }
                try {
                    // Write the crypted message in a new file
                    FileWriter fileWriter = new FileWriter(new File(parent, file.getName().substring(0, file.getName().length() - 4) + "_crypte.txt"));
                    fileWriter.write(Arrays.toString(this.tripleDES.cryptage(String.valueOf(text))));
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        // if the user clicks on the decrypte button
        else if (e.getSource() == this.decrypte) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            // Only accept .txt files
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            // Open the file chooser in the user home directory
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            // Show the file chooser
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String parent = fileChooser.getSelectedFile().getParent();
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner scanner = null;
                try {
                    // Read the file line by line
                    scanner = new Scanner(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                String text = scanner.nextLine();
                try {
                    // Write the decrypted message in a new file
                    FileWriter fileWriter = new FileWriter(new File(parent, file.getName().substring(0, file.getName().length() - 4) + "_decrypte.txt"));
                    int[] textInt = Arrays.stream(text.substring(1, text.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
                    fileWriter.write(this.tripleDES.decryptage(textInt));
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
