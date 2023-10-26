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

public class GUI implements WindowListener, MouseListener {

    public JFrame frame;
    public JLabel label;
    public JButton crypte;
    public JButton decrypte;
    public TripleDES tripleDES;

    public GUI() {
        this.frame = new JFrame("TripleDES");
        this.frame.addWindowListener(this);
        this.frame.setLayout(new FlowLayout());
        this.frame.setSize(500, 500);

        this.label = new JLabel("Cryptage/Decryptage DES");
        this.crypte = new JButton("Crypter");
        this.decrypte = new JButton("Decrypter");
        this.crypte.addMouseListener(this);
        this.decrypte.addMouseListener(this);

        this.frame.add(this.label);
        this.frame.add(this.crypte);
        this.frame.add(this.decrypte);

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
        if (e.getSource() == this.crypte) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
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
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append("\n");
                }
                try {
                    FileWriter fileWriter = new FileWriter(new File(parent, file.getName().substring(0, file.getName().length() - 4) + "_crypte.txt"));
                    fileWriter.write(Arrays.toString(this.tripleDES.cryptage(String.valueOf(text))));
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource() == this.decrypte) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
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
                String text = scanner.nextLine();
                try {
                    FileWriter fileWriter = new FileWriter(new File(parent, file.getName().substring(0, file.getName().length()-4) + "_decrypte.txt"));
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
