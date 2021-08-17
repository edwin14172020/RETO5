package com.example.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.model.vo.DeudasPorProyectoVo;
import com.example.util.JDBCUtilities;
import javax.swing.table.DefaultTableModel;
import ventana.busqueda;

public class DeudasPorProyectoDao {

    double limite;
    
    public List<DeudasPorProyectoVo> listar() throws SQLException {
        var respuesta = new ArrayList<DeudasPorProyectoVo>();
        var conn = JDBCUtilities.getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;
        var consulta = "SELECT P.ID_PROYECTO AS ID, SUM(C.CANTIDAD * MC.PRECIO_UNIDAD) AS VALOR"
                + " FROM PROYECTO P"
                + " JOIN COMPRA C ON (P.ID_PROYECTO = C.ID_PROYECTO)"
                + " JOIN MATERIALCONSTRUCCION MC ON (C.ID_MATERIALCONSTRUCCION = MC.ID_MATERIALCONSTRUCCION)"
                + " WHERE C.PAGADO = 'No'"
                + " GROUP BY P.ID_PROYECTO"
                + " HAVING SUM(C.CANTIDAD * MC.PRECIO_UNIDAD) > ?"
                + " ORDER BY VALOR DESC";
        try {
            stmt = conn.prepareStatement(consulta);
            stmt.setDouble(1, limite);
            rset = stmt.executeQuery();
            
            busqueda ventana = new busqueda();
            
            DefaultTableModel modelo = (DefaultTableModel) ventana.Tabla_Datos.getModel();
            
            modelo.setColumnIdentifiers(new Object[]{"ID","VALOR"});
            
            while (rset.next()) {
                var vo = new DeudasPorProyectoVo();
                vo.setId(rset.getInt("ID"));
                vo.setValor(rset.getDouble("VALOR"));

                modelo.addRow(new Object []{rset.getString("ID"),rset.getDouble("Valor")});
                respuesta.add(vo);
            }
            ventana.Panel.setVisible(true);
            
            ventana.setVisible(true);
            
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

    public List<DeudasPorProyectoVo> listar(Double limite) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}