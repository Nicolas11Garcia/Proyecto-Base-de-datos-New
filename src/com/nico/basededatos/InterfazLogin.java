package com.nico.basededatos;

import com.nico.basededatos.dao.Dao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.MemoryNotificationInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InterfazLogin extends JFrame{
    private JTextField textoUsuario;
    private JPasswordField textoPass;
    private JButton entrarButton;
    private JPanel panelLogin;
    private JButton registarseButton;

    public InterfazLogin() throws SQLException {
        super("Login");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
        add(panelLogin);

        // Conexion sql
        String ip = "localhost";
        int port = 3306;
        String db = "botilleria";
        String user = "root";
        String pass = "123";
        MyConnection link = new MyConnection(ip,user,pass,db);
        Dao inicio = new Dao(link);



        //Registarse
        registarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                try {
                    Registrase registraseOpcion = new Registrase();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        //Entrar al menu
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textoUsuario.getText();
                String pass = textoPass.getText();
                int usuarioEncontrado = 0;
                String sql = "SELECT * FROM trabajador WHERE username = '"+usuario+"' AND contraseña = SHA2('"+pass+"',0)";

                try {

                    Connection con = link.getCon();
                    Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()){
                        usuarioEncontrado = usuarioEncontrado + 1;

                        if(usuarioEncontrado == 1){
                            setVisible(false);
                            MenuPrincipal menu = new MenuPrincipal();
                        }

                    }else{
                        JOptionPane.showMessageDialog(panelLogin,"El usuario o la contraseña son invalidos, porfavor intente nuevamente");
                    }
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }




            }
        });





    }
}
