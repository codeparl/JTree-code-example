package jtreedemos;

import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import javax.swing.event.TreeWillExpandListener;
import java.beans.PropertyChangeListener;

public class TreeDemo extends JTree implements PropertyChangeListener {
    protected DefaultTreeModel treeModel;
    private JProgressBar progressBar;
    private File rootDirectory;
    private String selectedpath = null;

    public TreeDemo(JProgressBar progressBar) {
        super();
        this.progressBar = progressBar;
        DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setSelectionModel(selectionModel);

        // Make sure to load sub-directories before
        // expanding any node of this JTree
        addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                TreePath treePath = new TreePath(event.getPath().getPath());
                loadFileTree(rootDirectory, false, treePath);
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
            }
        });

        addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getNewLeadSelectionPath() != null) {
                    TreePath treePath = new TreePath(e.getNewLeadSelectionPath().getPath());
                    selectedpath = TreeDemo.joinPath(treePath, true);
                    selectedpath.concat(e.getSource().toString());
                }
            }

        });

       

        setCellRenderer(new TreeCellRendererDemo());
        setVisible(false);
    }

    public void readFile(JTextArea textArea) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {

                    if (selectedpath == null)
                        return;

                    File filePath = new File(selectedpath);
                    if (!filePath.exists() || filePath.isDirectory())
                        return;

                    try {
                        textArea.read(new BufferedReader(new FileReader(filePath)), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }// end method

         
        });

    }

    public void onBrowseFile(File filePath) {
        rootDirectory = filePath;
        loadFileTree(rootDirectory, true, null);

    }

    public static String joinPath(TreePath treePath, boolean lastSlash) {
        StringBuilder dirPath = new StringBuilder();
        String last = treePath.getLastPathComponent().toString();
        for (Object path : treePath.getPath()) {
            dirPath.append(path);
            if (lastSlash) {

                if (!last.equalsIgnoreCase(path.toString()))
                    dirPath.append(File.separator);
            } else
                dirPath.append(File.separator);
        }
        return dirPath.toString();
    }

    private void loadFileTree(File dir, boolean setModel, TreePath treePath) {
      
      if(!dir.exists()){

        JOptionPane.showMessageDialog(
            getParent().getParent(),
             "Please double chack your directory path.",
              "Invalid directory path",
               JOptionPane.ERROR_MESSAGE);
        return;

      }
        DirectoryWalkerTask task;

        if (treePath == null)
            task = new DirectoryWalkerTask(dir, TreeDemo.this, progressBar, setModel);
        else
            task = new DirectoryWalkerTask(dir, TreeDemo.this, progressBar, setModel, treePath);

        task.addPropertyChangeListener(this);
        task.execute();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()))
            progressBar.setString("Scanning Directories...");
    }

}// end main class
