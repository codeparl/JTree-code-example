package jtreedemos;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.util.Iterator;



public class DirectoryWalkerTask extends SwingWorker<Void, Void> {
    private File startDir;
    private JTree directoryTree;
    private DefaultMutableTreeNode parentNode;
    private JProgressBar progressBar;
    private boolean setModel =  true;
    private TreePath treePath;

    public DirectoryWalkerTask(File dir, JTree t, JProgressBar pBar,boolean setModel, TreePath...treePaths) {

        this.startDir = dir;
        this.directoryTree = t;
        progressBar = pBar;
        this.setModel =  setModel;
       
        if(treePaths.length > 0)
        this.treePath =  treePaths[0];
        else this.treePath  =  null;
    }

    @Override
    protected Void doInBackground() throws Exception {
        progressBar.setVisible(true);
    
        if(setModel) scanOnce();

        if(!setModel & treePath != null)
          scanWhenClicked(treePath);

        return  null;
    }// end method

    @Override
    protected void done() {
        directoryTree.setVisible(true);
        progressBar.setVisible(false);
        progressBar.setIndeterminate(false);
        Toolkit.getDefaultToolkit().beep();
    }// end method

    /**
     * Recursive method that walks the file tree and creates nodes from visited
     * directories. This method is quite slow as it scans all subdirctories. The
     * best way is to scan parent dir and scan sub-directories when a click happens
     * on the them.
     * 
     * @param dir      the main directory/folder to scan
     * @param treeNode the root node of the JTree
     */

  public void createFolderNodes(File dir, DefaultMutableTreeNode treeNode) {

        if (!dir.isDirectory())
            return; // base case

        // get file list

        File[] fileList = null;
        if (dir.isDirectory() && !dir.isHidden())
            fileList = dir.listFiles();
        // create a folder node
        DefaultMutableTreeNode dirNode = new DefaultMutableTreeNode(dir.getName());

        // when no folders to process, stop and return
        if (fileList == null)
            return;

        // create a leaf node for each file in this folder
        for (int i = 0; i < fileList.length; i++) {

            // if not folder, create a leaf
            if (!fileList[i].isDirectory())
                dirNode.add(new DefaultMutableTreeNode(fileList[i].getName()));
            // scan all files and create new node
            createFolderNodes(fileList[i], dirNode);
        }

        treeNode.add(dirNode);

    }

    public   void scanOnce() {
        parentNode = new DefaultMutableTreeNode(startDir.getAbsolutePath());

        //the node to add its children
        File[] files = startDir.listFiles();
        int totalFile = files.length;
       
        if(setModel){
            directoryTree.setModel(new DefaultTreeModel(parentNode));
            
        }
         

        for (int i = 0; i < totalFile; ++i) {
            if (files[i].isDirectory()) {
                File[] dirs = files[i].listFiles();
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode(files[i].getName());
                for (File file : dirs)
                    branchNode.add(new DefaultMutableTreeNode(file.getName()));
                    parentNode.add(branchNode);
            } else
            parentNode.add(new DefaultMutableTreeNode(files[i].getName()));
        }


        parentNode = (DefaultMutableTreeNode) directoryTree.getModel().getRoot();
        ((DefaultTreeModel) directoryTree.getModel()).reload(parentNode);
     
    }

    public void scanWhenClicked(TreePath treePath){

        String dirPath = TreeDemo.joinPath(treePath,false);
        
        // Get all child nodes of this clicked node/directory as
        // iterable TreeNodes
        DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        Iterator<TreeNode> nodeIterator = thisNode.children().asIterator();

        nodeIterator.forEachRemaining((node) -> {
            // get this node and clear all its children
            // whenever its parent expands
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node;
            parentNode.removeAllChildren();

            // use TreePath to get the last item of
            // this node's path list and create a File
            // object to get all files in this folder
            // on the system.
            TreePath tp = new TreePath(parentNode.getPath());
            String name = dirPath + tp.getLastPathComponent().toString();
            File p = new File(name);

            // create new child nodes for this directory
            // only if this path exists and is a directory
            if (p.exists() && p.isDirectory())
                for (File file : p.listFiles())
                    parentNode.add(new DefaultMutableTreeNode(file.getName()));
        });
    }
}
