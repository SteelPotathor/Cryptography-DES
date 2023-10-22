import javax.swing.*;
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

    public Frame frame;
    public Label label;
    public Button crypte;
    public Button decrypte;
    public DES des;

    public GUI() {
        this.frame = new Frame("DES");
        this.frame.addWindowListener(this);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(500, 500);

        this.label = new Label("Logiciel de cryptage/decryptage DES");
        this.crypte = new Button("Crypter");
        this.decrypte = new Button("Decrypter");
        this.crypte.addMouseListener(this);
        this.decrypte.addMouseListener(this);

        this.frame.add(this.label, BorderLayout.NORTH);
        this.frame.add(this.crypte, BorderLayout.WEST);
        this.frame.add(this.decrypte, BorderLayout.EAST);

        this.frame.setVisible(true);
        this.des = new DES();
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
        // pb de même instance sinon decodage incorrect
        if (e.getSource() == this.crypte) {
            System.out.println("Crypte");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
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
                    FileWriter fileWriter = new FileWriter(file.getName() + "_crypte.txt");
                    fileWriter.write(Arrays.toString(this.des.crypte(String.valueOf(text))));
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource() == this.decrypte) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir un fichier");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                String text = scanner.nextLine();
                try {
                    FileWriter fileWriter = new FileWriter(file.getName() + "_decrypte.txt");
                    int[] textInt = Arrays.stream(text.substring(1, text.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
                    fileWriter.write(this.des.decrypte(textInt));
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.out.println("Decrypte");
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
