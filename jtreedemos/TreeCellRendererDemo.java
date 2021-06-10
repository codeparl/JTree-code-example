package jtreedemos;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.io.File;


public class TreeCellRendererDemo extends DefaultTreeCellRenderer   {


    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){

        super.getTreeCellRendererComponent(tree, value, leaf, expanded, leaf, row, hasFocus);

        //get the DefaultMutableTreeNode if this object
        // is a DefaultMutableTreeNode
        DefaultMutableTreeNode treeNode  = null;
        
        if(value instanceof DefaultMutableTreeNode )
        treeNode = (DefaultMutableTreeNode) value;

        TreePath  paths = new TreePath(treeNode.getPath()) ;
        String joinedPath = TreeDemo.joinPath(paths,false);
        File  filepath  =  new File(joinedPath);
        setText(value.toString());

        if(selected){
            setOpaque(false);
            setBackground(tree.getBackground());
        }else{
            setOpaque(true);
            setForeground(tree.getForeground());
        }

            String fileExtension  = getExtension(getText())  ;
            switch (fileExtension) {
                case ".jpg":
                case ".png":
                case ".gif":
                case ".GIF":    
                case ".icon":
                case ".JPEG":
                case ".JPG":
                setIcon(createIcont("images/pic.png"));
                break;
                case ".doc":
                case ".docx":
                setIcon(createIcont("images/wrd.png"));    
                break;
                
                case ".mp4":
                case ".mp3":
                case ".flv":
                case ".aiff":
                case ".mpeg":
                case ".wmv":
                    
                setIcon(createIcont("images/vd.png"));    
                break;
                
                case ".xml":
                setIcon(createIcont("images/xml.png"));    
                break;
         
                case ".java":
                setIcon(createIcont("images/java.png"));    
                break;
                
                case ".zip":
                case ".rar":
                case ".jar":
                case ".gzip":
                setIcon(createIcont("images/zip.png"));    
                break;
                
               case ".html":
               case ".htm":
                setIcon(createIcont("images/html.png"));    
                break;
                
                case ".pdf":
                setIcon(createIcont("images/pdf.png"));    
                break;
                
                default:
                setIcon(createIcont("images/file.png"));   
                break;
            }


        if(leaf && filepath.isDirectory())
            setIcon(createIcont("images/folder.png"));
            
               //check if it is a folder 
            if(!leaf)
            setIcon(createIcont("images/folder.png"));
            
            
           if(expanded && !leaf)
             setIcon(createIcont("images/open-folder.png"));
           
           

        return this;
    }

    public  String getExtension(String file){
        if(file.contains("."))
         return file.substring(file.lastIndexOf("."), file.length());
      return ".txt";
      }
    

      /**
       * helper function to create an icon from 
       *icon images in the images folder
       @param name the name of the image icon to create
      */
      public  ImageIcon createIcont(String name){
        java.net.URL  url =  getClass().getResource(name);
        if(url != null)
            return new ImageIcon(url);
        else{
           System.err.println("Url not font");
           return null;
        }
       
       }
}
