

package projet_sgbd;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class SizingSamples {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Sizing Samples");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    DefaultListModel model = new DefaultListModel();
    model.ensureCapacity(100);
    for (int i = 0; i < 100; i++) {
      model.addElement(Integer.toString(i));
    }
    JList jlist2 = new JList(model);
    ListCellRenderer renderer = new FocusedTitleListCellRenderer();
    jlist2.setCellRenderer(renderer);

    JScrollPane scrollPane2 = new JScrollPane(jlist2);
    frame.add(scrollPane2, BorderLayout.CENTER);

    frame.setSize(300, 350);
    frame.setVisible(true);

    jlist2.ensureIndexIsVisible(50);
  }
}

class FocusedTitleListCellRenderer implements ListCellRenderer {
  protected static Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);

  protected static TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(),
      "title");

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

  public Component getListCellRendererComponent(JList list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);
    renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
    return renderer;
  }
}

class IconsCellRenderer implements ListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        
        return null ;
    
    }
    
}