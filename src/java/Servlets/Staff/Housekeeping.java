/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.Model;
import DataModel.ModelException;
import DataModel.Room;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author x3041557
 */
public class Housekeeping extends HttpServlet {

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
            Model model = new Model();
            //check for any actions to process
            String roomNo = request.getParameter("roomNo");
            String roomStatus = request.getParameter("roomStatus");
            List<String> messages = new ArrayList<>();
            
            if(!Objects.isNull(roomNo)) {
                //set this rooms status to the new value
                try {
                    Room r = model.ROOMS.getRoom(Integer.parseInt(roomNo));
                    //only attempt update if form different to db value
                    if(!r.getStatus().equals(roomStatus)) {
                        r.setStatus(roomStatus);
                        model.ROOMS.updateRoom(r);
                        messages.add("confirm#Room " + r.getNo() + " updated to status " + r.getLongStatus());
                    }
                } catch(NumberFormatException e) {
                    //failed to parse the room number to a numeric type
                    messages.add("error#Unrecognised room number");
                } catch(ModelException e) {
                    messages.add("error#Unable to update room status");
                }
            }
            
            //get a list of all rooms
            List<Room> allRooms = model.ROOMS.getAllRooms();
            //only care about rooms with status C or X
            //sort so they apears in room order on checkin page
            List<Room> statusC = allRooms.stream()
                    .filter(r -> r.getStatus().equals("C"))
                    .sorted()
                    .collect(Collectors.toList());
            List<Room> statusX = allRooms.stream()
                    .filter(r -> r.getStatus().equals("X"))
                    .sorted()
                    .collect(Collectors.toList());
            //add these as attributes to forward
            request.setAttribute("statusC", statusC);
            request.setAttribute("statusX", statusX);
            request.setAttribute("messages", messages);
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Staff/housekeeping.jsp");
            dispatcher.forward(request, response);
        } catch(ModelException e) {
            e.printStackTrace(response.getWriter());
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

    /**
     * Handles the HTTP <code>POST</code> method.
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
