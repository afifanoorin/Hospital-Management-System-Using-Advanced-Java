/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class adminLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              Connection connection= DriverManager.getConnection("jdbc:ucanaccess://E:\\hms.accdb");//Establishing Connection
              System.out.println("Connected Successfully");
            out.println("Connected Successfully");
              String username= request.getParameter("username");
              String password= request.getParameter("password"); 
               long time = System.currentTimeMillis(); 
                java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                String timees=""+timestamp;
                String tim[]=timees.split(" ");
                String times=""+tim[1]; 
                String mtime = times.split("\\.")[0];   
                java.util.Date datee = new java.util.Date();  
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                String strDate= formatter.format(datee)+" "+mtime;  
                int status=1;
                String sql ="SELECT  * FROM admin WHERE UserName=? and Password=?";
                 PreparedStatement ps=connection.prepareStatement(sql); 
                ps.setString(1, username); 
                ps.setString(2, password);    
                ResultSet rs= ps.executeQuery();
                if(rs.next())
                    { 
                        HttpSession session=request.getSession();  
                        session.setAttribute("aid",rs.getInt(1));  
                        session.setAttribute("aname", rs.getString(2));  
                        
                        response.sendRedirect("admin/dashboard.jsp?log=success"); 
                    }
                else{ 
                  
//                     out.println("<script>alert('Invalid Details');</script>");
                    response.sendRedirect("admin/index.jsp?log=error");out.println("error");
                }
            
        }catch(Exception e)
        {
            System.out.println("Error"+e);
        }   finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
