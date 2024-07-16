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
public class editProfile extends HttpServlet {

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
              String fname= request.getParameter("fname");
              String address= request.getParameter("address");
              String city= request.getParameter("city");
              String gender= request.getParameter("gender");
              String email=request.getParameter("email");  
               HttpSession session=request.getSession();
               long time = System.currentTimeMillis(); 
                java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                String timees=""+timestamp;
                String tim[]=timees.split(" ");
                String times=""+tim[1]; 
                String mtime = times.split("\\.")[0];   
                java.util.Date datee = new java.util.Date();  
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                String strDate= formatter.format(datee)+" "+mtime;  
                int status=1;//
                int uid= Integer.parseInt(session.getAttribute("uid").toString()); 
               String sql="Update users set fullName=?,address=?,city=?,gender=?, updationDate=? where id=?"; 
                PreparedStatement ps=connection.prepareStatement(sql); 
                ps.setString(1, fname); 
                ps.setString(2, address); 
                ps.setString(3, city);  
                ps.setString(4, gender); 
                ps.setString(5, strDate);  
                ps.setInt(6, uid);   
                int i=ps.executeUpdate();
                if(i!=0)
                    {
//                        InetAddress localhost = InetAddress.getLocalHost();
//                        System.out.println("System IP Address : " +(localhost.getHostAddress()).trim());
//                        String userip=localhost.getHostAddress().toString();
//                    String log="insert into userlog(uid,username,userip,loginTime,logout,status) values(?,?,?,?,?,?)";
//                     PreparedStatement ps1=connection.prepareStatement(log); 
//                    ps1.setInt(1, uid); 
//                    ps1.setString(2, fname); 
//                    ps1.setString(3, userip); 
//                    ps1.setString(4, strDate); 
//                    ps1.setString(5, " "); 
//                    ps1.setInt(6, status);
//                   int i1=ps1.executeUpdate();
                        response.sendRedirect("hms/edit-profile.jsp?mess=success");out.println("inserted");
                    
                    }
                else{ 
                    response.sendRedirect("hms/edit-profile.jsp?mess=error");out.println("error");
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
