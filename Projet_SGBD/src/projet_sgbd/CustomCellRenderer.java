/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_sgbd;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
/**
 *
 * @author Ibrah
 */
public class CustomCellRenderer extends JLabel implements ListCellRenderer<Object>{

        public CustomCellRenderer() {
            setIconTextGap(10);
            
        }
        
        @Override
        public Component getListCellRendererComponent(JList<?> list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Database_15px.png"));
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
            
            setBorder(getBorder());
            setFont(new java.awt.Font("Segeo UI Light",2,12));
            
            
            return this;
        }

        
        
    }
