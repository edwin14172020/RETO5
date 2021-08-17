package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import com.example.model.dao.ComprasDeLiderDao;
import com.example.model.dao.DeudasPorProyectoDao;
import com.example.model.dao.ProyectoBancoDao;
import com.example.model.vo.ComprasDeLiderVo;
import com.example.model.vo.DeudasPorProyectoVo;
import com.example.model.vo.ProyectoBancoVo;

public class ReportesController {

    public ProyectoBancoDao proyectoBancoDao;
    public ComprasDeLiderDao comprasDeLiderDao;
    public DeudasPorProyectoDao pagadoPorProyectoDao;

    public ReportesController() {
        proyectoBancoDao = new ProyectoBancoDao();
        comprasDeLiderDao = new ComprasDeLiderDao();
        pagadoPorProyectoDao = new DeudasPorProyectoDao();
    }

    public List<ProyectoBancoVo> listarProyectosPorBanco(String banco) throws SQLException {
        
        return proyectoBancoDao.listar(banco);
        
    }

    public List<DeudasPorProyectoVo> listarTotalAdeudadoProyectos(Double limite) throws SQLException {
        return pagadoPorProyectoDao.listar(limite);
    }

    public List<ComprasDeLiderVo> listarLideresQueMasGastan() throws SQLException {
        return comprasDeLiderDao.listar();
    }
}