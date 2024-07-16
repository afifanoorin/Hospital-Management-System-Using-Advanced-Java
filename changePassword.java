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
public class changePassword extends HttpServlet {

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
            out.println("<head>");
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              Connection connection= DriverManager.getConnection("jdbc:ucanaccess://E:\\hms.accdb");//Establishing Connection
              System.out.println("Connected Successfully");
              String password= request.getParameter("cpass");
              String newpassword= request.getParameter("npass");
              String confirmpassword= request.getParameter("cfpass"); 
              HttpSession session=request.getSession();
              String uid= session.getAttribute("uid").toString();
              String uname= session.getAttribute("uname").toString();
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
                String sql="Update users set password=?, updationDate=? where id=?"; 
                PreparedStatement ps=connection.prepareStatement(sql); 
                ps.setString(1, newpassword);  
                ps.setString(2, strDate);  
                ps.setInt(3, Integer.parseInt(uid));   
                int i=ps.executeUpdate();
                if(i!=0)
                    {
                        response.sendRedirect("hms/change-password.jsp?mess=success"); 
                     }
                else{ 
                    response.sendRedirect("hms/change-password.jsp?mess=error"); 
                }
            
        }catch(Exception e)
        {
            System.out.println("Error"+e);
        } finally {            
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
