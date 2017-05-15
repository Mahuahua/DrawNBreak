package Draw; /**
 * Created by Zhaoyang on 2/24/2016.
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    public static JMenuBar menuBar;
    public static JScrollPane jsp;
    public static JPanel p;
    public static Model model;
    public static DrawingBoard view1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pain_t");
        model = new Model();

        view1 = new DrawingBoard(model);
        model.addObserver(view1);

        PlayBack view2 = new PlayBack(model);
        model.addObserver(view2);

        DrawingOptions view3 = new DrawingOptions(model);
        model.addObserver(view3);


        model.notifyObservers();

        // create the window
        p = new JPanel();
        frame.getContentPane().add(p);
        p.setLayout(new BorderLayout());
        p.add(view1, BorderLayout.CENTER);
        p.add(view2, BorderLayout.SOUTH);
        p.add(view3, BorderLayout.WEST);
        frame.setMinimumSize(new Dimension(400, 450));
        p.setPreferredSize(new Dimension(800, 600));
        view1.setPreferredSize(new Dimension(650, 450));
        view2.setPreferredSize(new Dimension(800, 100));
        view3.setPreferredSize(new Dimension(100, 600));

        addScroll();


        menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic('F');
        JMenu menuView = new JMenu("View");
        menuView.setMnemonic('V');

        JMenuItem open = new JMenuItem();
        open.setText("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
                        "Binary", "ser");
                FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
                        "XML", "xml");
                // FileNameExtensionFilter filter3 = new FileNameExtensionFilter("TXT", "txt");
                chooser.setFileFilter(filter1);
                chooser.setFileFilter(filter2);
                //chooser.setFileFilter(filter3);
                int returnVal = chooser.showOpenDialog(open);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (chooser.getSelectedFile().toString().length() > 4 && chooser.getSelectedFile().toString().endsWith(".xml")) {
                        try {

                            InputStream is = new FileInputStream(chooser.getSelectedFile());
                            JAXBContext jc = JAXBContext.newInstance(Model.class);
                            Unmarshaller u = jc.createUnmarshaller();
                            Object saveModel = u.unmarshal(is);
                            model.setPaststrokes(((Model) saveModel).getpastStrokes());
                            model.jsValue = ((Model) saveModel).jsValue;
                            model.forceChange();
                            is.close();
                        } catch (Exception x) {
                            x.printStackTrace();
                        }

                    } else {
                        try {
                            FileInputStream fileIn = new FileInputStream(chooser.getSelectedFile());
                            ObjectInputStream in = new ObjectInputStream(fileIn);
                            Model saveModel = (Model) in.readObject();
                            model.setPaststrokes(saveModel.getpastStrokes());
                            model.jsValue = ((Model) saveModel).jsValue;
                            model.forceChange();
                            in.close();
                            fileIn.close();
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        });
        menuFile.add(open);


        JMenuItem save = new JMenuItem();
        save.setEnabled(false);
        save.setText("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
                        "Binary", "ser");
                FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
                        "XML", "xml");
                // FileNameExtensionFilter filter3 = new FileNameExtensionFilter(   "TXT", "txt");
                chooser.setFileFilter(filter1);
                chooser.setFileFilter(filter2);
                //chooser.setFileFilter(filter3);
                int returnVal = chooser.showSaveDialog(save);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (chooser.getSelectedFile().toString().length() > 4 && chooser.getSelectedFile().toString().endsWith(".xml")) {
                        try {
                            final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                            JAXBContext context = JAXBContext.newInstance(Model.class);
                            Marshaller m = context.createMarshaller();
                            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                            m.marshal(model, bos);
                            bos.flush();
                            bos.close();
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                    } else {
                        try {
                            FileOutputStream fileOut = new FileOutputStream(chooser.getSelectedFile());
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(model);
                            out.close();
                            fileOut.close();
                        } catch (Exception ex) {
                            System.out.println("error saving to binary");
                        }
                    }
                }

            }
        });
        menuFile.add(save);


        JMenuItem fz = new JMenuItem();
        fz.setText("full-size");
        fz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setToFullSize();

            }
        });

        menuView.add(fz);

        JMenuItem fit = new JMenuItem();
        fit.setText("fit");
        fit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setToFit();
            }
        });

        menuView.add(fit);

        menuBar.add(menuFile);
        menuBar.add(menuView);
        frame.setJMenuBar(menuBar);


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (model.getpastStrokes().size() != 0 || model.getCurStroke().coorx.size() != 0) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Do you want to save?", "Confirm exit",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmed == JOptionPane.YES_OPTION) {
                        save.doClick();
                    } else {
                        frame.dispose();
                    }
                } else {

                }
            }
        });
    }


    public static void removeScroll() {
        p.remove(jsp);
        p.add(view1);
        p.revalidate();
    }

    public static void addScroll() {
        jsp = new JScrollPane(view1);
        p.add(jsp);
        p.revalidate();
    }

    public static void setSaveBarEnable(boolean b) {
        menuBar.getMenu(0).getItem(1).setEnabled(b);
    }
}
