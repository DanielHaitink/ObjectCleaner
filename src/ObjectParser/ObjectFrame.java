package ObjectParser;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Created by Daniel on 06-Mar-17.
 */
public class ObjectFrame extends JFrame {

    private JPanel panel;

    public ObjectFrame() {
        setTitle("Object Cleaner");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new JPanel(new GridLayout(1,1));
        JLabel dropArea = new JLabel("Drop Files Here!", JLabel.CENTER);
        //dropArea.setEditable(false);

        dropArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    if (showConfirmationDialog(droppedFiles) == JOptionPane.NO_OPTION)
                        return;

                    Vector<File> failed = new Vector<File>();
                    for (File file : droppedFiles) {
                        if (new ObjectCleaner(file).clean() != 1)
                            failed.add(file);
                    }

                    if(failed.size() > 0){
                        showErrorDialog(failed);
                    }else {
                        Object[] options = {"OK"};
                        JOptionPane.showOptionDialog(null, "Converted " + droppedFiles.size() + " files!", "Done!", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Error during parsing files!\n" + e.getMessage(), "Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                }
            }
        });
        panel.add(dropArea);
        add(panel);
        setVisible(true);
    }

    private int showErrorDialog(Vector<File> files) {
        Object[] options = {"OK"};
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("The following files returned an error:"));
        panel.add(new JScrollPane(new JList<File>(files)));
        return JOptionPane.showOptionDialog(null, panel, "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }

    private int showConfirmationDialog(List<File> files) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Are you sure you want to convert these files?"));
        panel.add(new JScrollPane(new JList<File>(new Vector<File>(files))));
        return JOptionPane.showConfirmDialog(null, panel, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

}
