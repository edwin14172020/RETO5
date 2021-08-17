package com.example.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.model.vo.ProyectoBancoVo;
import com.example.util.JDBCUtilities;
import javax.swing.table.DefaultTableModel;
import ventana.busqueda;


public class ProyectoBancoDao {

         
    public List<ProyectoBancoVo> listar(String banco) throws SQLException {
        var respuesta = new ArrayList<ProyectoBancoVo>();
        var conn = JDBCUtilities.getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;
        var consulta = "SELECT P.ID_PROYECTO AS ID, P.CONSTRUCTORA, P.CIUDAD, P.CLASIFICACION,"
                + "       T.ESTRATO, L.NOMBRE||' '||L.PRIMER_APELLIDO||' '||L.SEGUNDO_APELLIDO AS LIDER" 
                + " FROM PROYECTO P"
                + " JOIN TIPO T ON (P.ID_TIPO = T.ID_TIPO)" 
                + " JOIN LIDER L ON (P.ID_LIDER = L.ID_LIDER)"
                + " WHERE P.BANCO_VINCULADO = ?" 
                + " ORDER BY FECHA_INICIO DESC, CIUDAD, CONSTRUCTORA";
        try {
            stmt = conn.prepareStatement(consulta);
            stmt.setString(1, banco);
            rset = stmt.executeQuery();
            
            busqueda ventana = new busqueda();
            
            DefaultTableModel modelo = (DefaultTableModel) ventana.Tabla_Datos.getModel();
            
            modelo.setColumnIdentifiers(new Object[]{"ID","CONSTRUCTORA","CIUDAD","CLASIFICACION","ESTRATO","LIDER"});
            
            while (rset.next()) {
                var vo = new ProyectoBancoVo();
                vo.setId(rset.getInt("id"));
                vo.setConstructora(rset.getString("constructora"));
                vo.setCiudad(rset.getString("ciudad"));
                vo.setClasificacion(rset.getString("clasificacion"));
                vo.setEstrato(rset.getInt("estrato"));
                vo.setLider(rset.getString("lider"));
                
                modelo.addRow(new Object []{rset.getString("id"),rset.getString("constructora"),rset.getString("ciudad"),rset.getString("clasificacion"),rset.getInt("estrato"),rset.getString("lider")});
                respuesta.add(vo);
            
            ventana.Panel.setVisible(true);
            
            ventana.setVisible(true);
                respuesta.add(vo);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return respuesta;
    }
}
