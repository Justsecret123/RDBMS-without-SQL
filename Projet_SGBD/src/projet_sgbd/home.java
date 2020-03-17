/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_sgbd;
import java.awt.Button;
import java.awt.Color;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sun.swing.DefaultLayoutStyle;

/**
 *
 * @author Ibrah
 */
public class home extends javax.swing.JFrame {

    static Connection connection;
    static String database;
    
  
 /*
 * @author Ibrah
     * @param connection_
     * @param database_
 */
    public home(Connection connection_, String database_) {
       
        
        initComponents();
        home.connection = connection_;
        home.database = database_;
        
        
        /*---------------LISTS----------------------------------*/
        
        list_user_databases.setBorder(null);
        list_user_databases.setSelectionBackground(Color.BLUE);
        /*------------------------------------------------------*/
        
       
        this.setLocationRelativeTo(null);
        
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, java.awt.SystemColor.textHighlight));
        
       
       /*-------------------Buttons---------------------*/
        button_quit.setBorder(null);
        button_settings.setBorder(null);
        button_settings.setContentAreaFilled(false);
        button_reduce.setBorder(null);
        button_quit.setBorderPainted(false);
        button_reduce.setBorderPainted(false);
        button_quit.setContentAreaFilled(false);
        button_reduce.setContentAreaFilled(false);
        button_new_database.setBorder(null);
        button_new_database.setContentAreaFilled(false);
        
        /*---------------------------------------------*/
        
        panel_mask_tables.setBorder(null);
        
        pane_elements.setBackgroundAt(0,Color.red);
        Button b = new Button("Tables");
        
        pane_elements.getComponentAt(0).setBackground(Color.red);
        
   
        tab_tables.setBackground(java.awt.SystemColor.textHighlight);
        tab_tables.setBorderPainted(false);
        tab_tables.add(new Button(database_));
        
                
        displayUserDatabases();
       
        
    }
    
    static void deleteTable(String table){
        try{
            Statement s = connection.createStatement();
            s.execute("DROP TABLE " + table);
            JOptionPane.showMessageDialog(list_views, "Table supprimée!");
            displayUserTables(connection.getCatalog());
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(list_views, "Erreur lors de la suppression de la table! Message d'erreur: " + e.getMessage());
        }
    }
    
    
    static void displayUserDatabases(){
        
        try{
            Statement s = connection.createStatement();
            if("MySQL".equals(database)){
                ResultSet result = s.executeQuery("show databases;");
                DefaultListModel model = new DefaultListModel();
                while(result.next()){
                    model.addElement(result.getString(1));
                }
                list_user_databases.setModel(model);
                result.close();
                s.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(list_user_tables, "Erreur lors de la collecte des bases de données!");
        }
    }
    
    static void displayProcedures(){
        try{
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("show procedure status where db = '" + list_user_databases.getSelectedValue() + "'");
            DefaultTableModel model = new DefaultTableModel(null, new Object[]{"Base de données","Nom de la procédure","Type","Definer","Modifiée lé","Créée le"});
            while(result.next()){
                model.addRow(new Object[]{result.getString(1),result.getString(2),result.getString(3),result.getString(4), result.getString(5), result.getString(6)});
            }
            table_procedures.setModel(model);
            result.close();
            s.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(list_views, "Erreur lors de la collecte des procédures! Message d'erreur: " + e.getMessage());
        }
    }
    
    static void displayFunctions(){
        try{
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("show function status where db = '" + list_user_databases.getSelectedValue() + "'");
            DefaultTableModel model = new DefaultTableModel(null,new Object[]{"Base de données", "Nom de la fonction","Type","Utilisateur","Modifiée le","Créée le","Description"});
            while(result.next()){
                model.addRow(new Object[]{result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(8)});
            }
            table_functions.setModel(model);
            result.close();
            s.close();
        }catch(SQLException e){
            
        }
    }
    
    static void displayViews(){
        try {
            Statement s = connection.createStatement();
            if("MySQL".equals(database)){
                ResultSet result = s.executeQuery("show FULL tables where table_type = 'VIEW'");
                DefaultListModel model = new DefaultListModel();
                while(result.next()){
                    model.addElement(result.getString(1));
                }
                list_views.setModel(model);
                result.close();
                s.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(list_views, "Erreur lors de la collecte des vues! Message d'erreur: " + e.getMessage());
        }
    }
    
    static void displayConstraints(){
        
    }
    
    static void displayUserTables(String schema){
        try{
            connection.setCatalog(schema);
            Statement s = connection.createStatement();
            if("MySQL".equals(database)){
                ResultSet result = s.executeQuery("show tables;");
                DefaultListModel model = new DefaultListModel();                
                while(result.next()){
                    model.addElement(result.getString(1));
                }
                list_user_tables.setModel(model);
                result.close();
                s.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(list_user_tables, "Erreur lors de la collection des tables!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_reduce = new javax.swing.JButton();
        button_quit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_user_databases = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        pane_elements = new javax.swing.JTabbedPane();
        tab_tables = new javax.swing.JToolBar();
        panel_mask_tables = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_user_tables = new javax.swing.JList<>();
        button_new_table = new javax.swing.JButton();
        button_modify_table = new javax.swing.JButton();
        button_delete_table = new javax.swing.JButton();
        tab_vues = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_views = new javax.swing.JList<>();
        button_new_view = new javax.swing.JButton();
        button_modify_view = new javax.swing.JButton();
        button_delete_view = new javax.swing.JButton();
        tab_procedures = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_procedures = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        tab_functions = new javax.swing.JDesktopPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_functions = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        button_new_database = new javax.swing.JButton();
        button_settings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(150, 150));
        setUndecorated(true);

        button_reduce.setBackground(new java.awt.Color(255, 255, 255));
        button_reduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Reduce_30px.png"))); // NOI18N
        button_reduce.setAlignmentY(0.0F);
        button_reduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reduceActionPerformed(evt);
            }
        });

        button_quit.setBackground(new java.awt.Color(255, 255, 255));
        button_quit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Close Window_30px.png"))); // NOI18N
        button_quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_quitActionPerformed(evt);
            }
        });

        list_user_databases.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        list_user_databases.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        list_user_databases.setCellRenderer(new CustomCellRenderer());
        list_user_databases.setSelectionBackground(new java.awt.Color(0, 0, 0));
        list_user_databases.setSelectionForeground(new java.awt.Color(0, 153, 255));
        list_user_databases.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_user_databasesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_user_databases);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Bases de données: ");

        pane_elements.setBackground(new java.awt.Color(255, 255, 255));
        pane_elements.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pane_elements.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        pane_elements.setOpaque(true);

        tab_tables.setBackground(new java.awt.Color(255, 255, 255));
        tab_tables.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tab_tables.setFloatable(false);
        tab_tables.setRollover(true);
        tab_tables.setBorderPainted(false);

        panel_mask_tables.setBackground(new java.awt.Color(255, 255, 255));
        panel_mask_tables.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel_mask_tables.setAutoscrolls(true);

        list_user_tables.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        list_user_tables.setCellRenderer(new CustomCellRenderer2());
        jScrollPane2.setViewportView(list_user_tables);

        button_new_table.setBackground(java.awt.SystemColor.textHighlight);
        button_new_table.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_new_table.setForeground(new java.awt.Color(255, 255, 255));
        button_new_table.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        button_new_table.setText("Nouvelle table");
        button_new_table.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        button_new_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_new_tableActionPerformed(evt);
            }
        });

        button_modify_table.setBackground(java.awt.SystemColor.textHighlight);
        button_modify_table.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_modify_table.setForeground(new java.awt.Color(255, 255, 255));
        button_modify_table.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Settings_15px.png"))); // NOI18N
        button_modify_table.setText("Afficher/Modifier");
        button_modify_table.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        button_modify_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_modify_tableActionPerformed(evt);
            }
        });

        button_delete_table.setBackground(java.awt.SystemColor.textHighlight);
        button_delete_table.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_delete_table.setForeground(new java.awt.Color(255, 255, 255));
        button_delete_table.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash_15px.png"))); // NOI18N
        button_delete_table.setText("Supprimer");
        button_delete_table.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        button_delete_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_tableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_mask_tablesLayout = new javax.swing.GroupLayout(panel_mask_tables);
        panel_mask_tables.setLayout(panel_mask_tablesLayout);
        panel_mask_tablesLayout.setHorizontalGroup(
            panel_mask_tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mask_tablesLayout.createSequentialGroup()
                .addGroup(panel_mask_tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_mask_tablesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_new_table)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_mask_tablesLayout.createSequentialGroup()
                .addGap(0, 280, Short.MAX_VALUE)
                .addComponent(button_modify_table, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_delete_table, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        panel_mask_tablesLayout.setVerticalGroup(
            panel_mask_tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mask_tablesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_new_table)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panel_mask_tablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_modify_table)
                    .addComponent(button_delete_table))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        tab_tables.add(panel_mask_tables);

        pane_elements.addTab("<html><span sytle=\"margin: 3px;\">Tables</span></html>", new javax.swing.ImageIcon(getClass().getResource("/icons/TAbles_15px.png")), tab_tables); // NOI18N

        tab_vues.setBackground(new java.awt.Color(153, 153, 153));
        tab_vues.setBorder(null);
        tab_vues.setFloatable(false);
        tab_vues.setRollover(true);
        tab_vues.setAutoscrolls(true);
        tab_vues.setBorderPainted(false);

        list_views.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        list_views.setCellRenderer(new CustomCellRenderer3());
        jScrollPane3.setViewportView(list_views);

        button_new_view.setBackground(java.awt.SystemColor.textHighlight);
        button_new_view.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_new_view.setForeground(new java.awt.Color(255, 255, 255));
        button_new_view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        button_new_view.setText("Nouvelle vue");
        button_new_view.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        button_modify_view.setBackground(java.awt.SystemColor.textHighlight);
        button_modify_view.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_modify_view.setForeground(new java.awt.Color(255, 255, 255));
        button_modify_view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Settings_15px.png"))); // NOI18N
        button_modify_view.setText("Afficher/Modifier");
        button_modify_view.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        button_delete_view.setBackground(java.awt.SystemColor.textHighlight);
        button_delete_view.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_delete_view.setForeground(new java.awt.Color(255, 255, 255));
        button_delete_view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash_15px.png"))); // NOI18N
        button_delete_view.setText("Supprimer");
        button_delete_view.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(button_modify_view)
                        .addGap(34, 34, 34)
                        .addComponent(button_delete_view, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(button_new_view, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(button_new_view)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_delete_view)
                    .addComponent(button_modify_view))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        tab_vues.add(jPanel1);

        pane_elements.addTab("<html><span sytle=\"margin: 3px;\">Vues</span></html>", new javax.swing.ImageIcon(getClass().getResource("/icons/Views_15px.png")), tab_vues); // NOI18N

        tab_procedures.setBorder(null);
        tab_procedures.setFloatable(false);
        tab_procedures.setRollover(true);
        tab_procedures.setBorderPainted(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        table_procedures.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(table_procedures);

        jButton1.setBackground(java.awt.SystemColor.textHighlight);
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        jButton1.setText("Nouvelle procédure");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton2.setBackground(java.awt.SystemColor.textHighlight);
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Settings_15px.png"))); // NOI18N
        jButton2.setText("Afficher/Modifier");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton3.setBackground(java.awt.SystemColor.textHighlight);
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash_15px.png"))); // NOI18N
        jButton3.setText("Supprimer");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(29, 29, 29)
                        .addComponent(jButton3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 41, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        tab_procedures.add(jPanel2);

        pane_elements.addTab("<html><span sytle=\"margin: 3px;\">Procédures</span></html>", new javax.swing.ImageIcon(getClass().getResource("/icons/Process_15px.png")), tab_procedures); // NOI18N

        tab_functions.setBackground(new java.awt.Color(255, 255, 255));

        table_functions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(table_functions);

        jButton4.setBackground(java.awt.SystemColor.textHighlight);
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        jButton4.setText("Nouvelle fonction");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton5.setBackground(java.awt.SystemColor.textHighlight);
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Settings_15px.png"))); // NOI18N
        jButton5.setText("Afficher/Modifier");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton6.setBackground(java.awt.SystemColor.textHighlight);
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash_15px.png"))); // NOI18N
        jButton6.setText("Supprimer");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        tab_functions.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        tab_functions.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        tab_functions.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        tab_functions.setLayer(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout tab_functionsLayout = new javax.swing.GroupLayout(tab_functions);
        tab_functions.setLayout(tab_functionsLayout);
        tab_functionsLayout.setHorizontalGroup(
            tab_functionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_functionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab_functionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab_functionsLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_functionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tab_functionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_functionsLayout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addGap(33, 33, 33)
                                .addComponent(jButton6)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_functionsLayout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        tab_functionsLayout.setVerticalGroup(
            tab_functionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_functionsLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(tab_functionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pane_elements.addTab("<html><span sytle=\"margin: 3px;\">Fonctions</span></html>", new javax.swing.ImageIcon(getClass().getResource("/icons/Functions_15px.png")), tab_functions); // NOI18N

        button_new_database.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add Database_blue_15px.png"))); // NOI18N

        button_settings.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_settings.setForeground(new java.awt.Color(102, 102, 102));
        button_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Settings_30px.png"))); // NOI18N
        button_settings.setText("Paramètres");
        button_settings.setBorder(null);
        button_settings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_settings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_reduce, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_quit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button_new_database)))
                        .addGap(18, 18, 18)
                        .addComponent(pane_elements, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_quit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button_settings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(button_reduce, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(button_new_database, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pane_elements, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_quitActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(rootPane, "Voulez-vous vraiment quitter ?");
        if(choice == 0){
            dispose();
        }
    }//GEN-LAST:event_button_quitActionPerformed

    private void button_reduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reduceActionPerformed
        // TODO add your handling code here:
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_button_reduceActionPerformed

    private void list_user_databasesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_user_databasesValueChanged
        
            // TODO add your handling code here:
            displayUserTables(list_user_databases.getSelectedValue());
            displayViews();
            displayProcedures();
            displayFunctions();
    }//GEN-LAST:event_list_user_databasesValueChanged

    private void button_modify_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_modify_tableActionPerformed
        // TODO add your handling code here:
        String table = list_user_tables.getSelectedValue();
        if(table!=null){
            System.out.println(table);
            Table_Form f = new Table_Form(database, connection, table);
            f.setVisible(rootPaneCheckingEnabled);
        }
    }//GEN-LAST:event_button_modify_tableActionPerformed

    private void button_new_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_new_tableActionPerformed
        // TODO add your handling code here:
        new_table f = new new_table(connection);
        f.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_button_new_tableActionPerformed

    private void button_delete_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_tableActionPerformed
        // TODO add your handling code here:
        String table = list_user_tables.getSelectedValue();
        if(table!=null){
            int choice = JOptionPane.showConfirmDialog(pane_elements, "Voulez-vous supprimer la table " + table + " ?");
            if(choice == 0){
                deleteTable(table);
            }
        }
    }//GEN-LAST:event_button_delete_tableActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home(connection,database).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_delete_table;
    private javax.swing.JButton button_delete_view;
    private javax.swing.JButton button_modify_table;
    private javax.swing.JButton button_modify_view;
    private javax.swing.JButton button_new_database;
    private javax.swing.JButton button_new_table;
    private javax.swing.JButton button_new_view;
    private javax.swing.JButton button_quit;
    private javax.swing.JButton button_reduce;
    private javax.swing.JButton button_settings;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private static javax.swing.JList<String> list_user_databases;
    private static javax.swing.JList<String> list_user_tables;
    private static javax.swing.JList<String> list_views;
    private javax.swing.JTabbedPane pane_elements;
    private javax.swing.JPanel panel_mask_tables;
    private javax.swing.JDesktopPane tab_functions;
    private javax.swing.JToolBar tab_procedures;
    private static javax.swing.JToolBar tab_tables;
    private javax.swing.JToolBar tab_vues;
    private static javax.swing.JTable table_functions;
    private static javax.swing.JTable table_procedures;
    // End of variables declaration//GEN-END:variables
}
