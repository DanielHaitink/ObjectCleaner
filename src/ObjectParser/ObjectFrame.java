package ObjectParser;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

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
                    for (File file : droppedFiles) {
                        new ObjectCleaner(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        panel.add(dropArea);
        add(panel);
        setVisible(true);
    }

}
