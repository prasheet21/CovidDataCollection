/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
public class register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
             String name = request.getParameter("full_name" );
            String aadhar = request.getParameter("adhar") ;
            String contact = request.getParameter("contact") ;
            String address = request.getParameter("address") ;
            String company = request.getParameter("company") ;
            String members = request.getParameter("famMem") ;
            String date_from = request.getParameter("date_from") ;
            String breakfast = request.getParameter("BreakNeed") ;
            String lunch = request.getParameter("LunchNeed") ;
            String dinner = request.getParameter("DinnerNeed") ;
            
            try{
                
                Class.forName("com.mysql.jdbc.Driver") ;
                                
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid19_help","root" , "root") ;
                
                PreparedStatement ps1 = con.prepareStatement("SELECT adhar , mob FROM worker WHERE mob=? ") ;
                ps1.setString(1 , contact) ;
                
                ResultSet rs1 = ps1.executeQuery();
                
                PreparedStatement ps2 = con.prepareStatement("SELECT adhar , mob FROM worker WHERE adhar=? ") ;
                ps2.setString(1 , aadhar) ;
                ResultSet rs2 = ps2.executeQuery(); 
                
                
                if(rs1.next() == true || rs2.next() == true)
                {
                    out.print("copy") ;
                }
                else
                {
                   
                    PreparedStatement pstmt = con.prepareStatement("insert into covid19_help.worker (name , adhar , mob , number_family , from_date , address , company , Breakfast , Lunch , Dinner) values(? , ? , ? , ? , ? , ? , ? , ? , ? , ?)") ;
                    pstmt.setString(1 , name) ;
                    pstmt.setString(2 , aadhar) ;
                    pstmt.setString(3 , contact) ;
                    pstmt.setString(4 , members) ;
                    pstmt.setString(5 , date_from) ;
                    pstmt.setString(6 , address) ;
                    pstmt.setString(7 ,company) ;
                    pstmt.setString(8 , breakfast) ;
                    pstmt.setString(9 , lunch) ;
                    pstmt.setString(10 , dinner) ;
                    
                     pstmt.executeUpdate() ;
                    
                    out.print("done") ;
                   
                }
                
               
                
            }
            catch(Exception e)
            {
                out.print(e.getMessage());
                out.print("error");
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
}
