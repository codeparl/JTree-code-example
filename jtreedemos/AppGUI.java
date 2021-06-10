package jtreedemos;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeModel;


import java.awt.*;

import java.io.File;

public class AppGUI extends JPanel {

    private TreeDemo treeDemo;
    protected DefaultTreeModel treeModel;
    private JButton browseDirButton;
    private JTextField dirTextField;
    private JButton dirScannButton;
    private JProgressBar progressBar;
    private JTextArea tArea;

    public AppGUI() {
        super(new BorderLayout());
        createAndSetComps();
        placeComps();

    }

    private void createAndSetComps() {

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        treeDemo = new TreeDemo(progressBar);

        tArea = new JTextArea();
        tArea.setEditable(false);
        tArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 22));

        browseDirButton = new JButton("Browse...");
        dirTextField = new JTextField(23);

        dirTextField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        dirTextField.setPreferredSize(new Dimension(dirTextField.getSize().width, 35));

        dirScannButton = new JButton("Scan...");

        Font fonts = new Font(dirScannButton.getFont().getFamily(), Font.PLAIN, 18);

        dirScannButton.setFont(fonts);
        browseDirButton.setFont(fonts);
        dirScannButton.getPreferredSize().height = 30;
        browseDirButton.getPreferredSize().height = 30;
        browseDirButton.setFocusable(false);
        dirScannButton.setFocusable(false);

        browseDirButton.addActionListener((e) -> browsePath());
        dirScannButton.addActionListener((e) -> {
            String pathStr = dirTextField.getText();
            treeDemo.onBrowseFile(new File(pathStr));
        });

        treeDemo.readFile(tArea);
    }

    private void placeComps() {
        JPanel pane = new JPanel();
        pane.add(dirTextField);
        pane.add(browseDirButton);
        pane.add(dirScannButton);
        add(pane, BorderLayout.NORTH);

        JScrollPane treeScrollPane = new JScrollPane(treeDemo);
        JScrollPane textScrollPane = new JScrollPane(tArea);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, textScrollPane);
        splitPane.setDividerSize(10);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        pane = new JPanel();
        pane.add(progressBar);

        add(pane, BorderLayout.SOUTH);
    }

    private void browsePath() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int val = fc.showOpenDialog(this);
        if (val == JFileChooser.APPROVE_OPTION) {
            File rootFolder = fc.getSelectedFile();
            dirTextField.setText(rootFolder.getAbsolutePath());
        } // end if

    }

}
