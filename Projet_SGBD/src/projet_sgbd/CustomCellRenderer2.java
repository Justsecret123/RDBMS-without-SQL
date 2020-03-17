/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_sgbd;

import java.awt.Color;
import java.awt.Component;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import static javax.swing.SwingConstants.TOP;

/**
 *
 * @author Ibrah
 */
public class CustomCellRenderer2 extends JLabel implements ListCellRenderer<Object> {

      public CustomCellRenderer2() {
            setIconTextGap(10);
            
        }
        
        @Override
        public Component getListCellRendererComponent(JList<?> list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Table_30px.png"));
            setIcon(icon);
            setText(value.toString());
            
            
            Color bg;
            Color fg;
            
            if(isSelected){
                setBackground(Color.BLACK);
                setForeground(Color.BLUE);
            }
            else{
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            setBounds(TOP, TOP, WIDTH, HEIGHT);
            setSize(200,200);
            
            setBorder(BorderFactory.createLineBorder(java.awt.SystemColor.lightGray, 1));
            setFont(new java.awt.Font("Segeo UI Light",2,12));
            
            
            return this;
        }
}
