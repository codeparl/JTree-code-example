package jtreedemos;

public class TreeWalkTask {
    


    static public void createFolderNodes(File dir, DefaultMutableTreeNode treeNode) {

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
}
