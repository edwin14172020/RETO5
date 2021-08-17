package com.example;

import com.example.view.ReportesView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
    var reportesView = new ReportesView();
    var banco = "Conavi";
    reportesView.proyectosFinanciadosPorBanco(banco);
        }
    }

