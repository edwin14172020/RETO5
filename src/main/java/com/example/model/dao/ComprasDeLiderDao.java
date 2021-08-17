package com.example.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.example.model.vo.ComprasDeLiderVo;
import com.example.util.JDBCUtilities;
import javax.swing.table.DefaultTableModel;
import ventana.busqueda;


public class ComprasDeLiderDao {
    public List<ComprasDeLiderVo> listar() throws SQLException {
        var respuesta = new ArrayList<ComprasDeLiderVo>();
        var conn = JDBCUtilities.getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        var consulta = "SELECT L.NOMBRE ||' '||L.PRIMER_APELLIDO ||' '||L.SEGUNDO_APELLIDO AS LIDER,"
                + "       SUM(C.CANTIDAD * MC.PRECIO_UNIDAD) AS VALOR"
                + " FROM LIDER L"
                + " JOIN PROYECTO P ON (P.ID_LIDER = L.ID_LIDER)"
                + " JOIN COMPRA C ON (P.ID_PROYECTO = C.ID_PROYECTO)"
                + " JOIN MATERIALCONSTRUCCION MC ON (C.ID_MATERIALCONSTRUCCION = MC.ID_MATERIALCONSTRUCCION)"
                + " GROUP BY L.NOMBRE ||' '||L.PRIMER_APELLIDO ||' '||L.SEGUNDO_APELLIDO"
                + " ORDER BY VALOR DESC"
                + " LIMIT 10;";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(consulta);
            
            busqueda ventana = new busqueda();
            
            DefaultTableModel modelo = (DefaultTableModel) ventana.Tabla_Datos.getModel();
            
            modelo.setColumnIdentifiers(new Object[]{"LIDER","VALOR"});
           
            while (rset.next()) {
                var vo = new ComprasDeLiderVo();
                vo.setLider(rset.getString("Lider"));
                vo.setValor(rset.getDouble("Valor"));
                
                modelo.addRow(new Object []{rset.getString("Lider"),rset.getDouble("Valor")});

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
}