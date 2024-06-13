package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Been.Utente;
import model.DAO.UtenteDAO;
import model.PatternInput;

import java.io.IOException;

@WebServlet("/cambiaCredenziali")
public class CambiaCredenziali extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente = (Utente) req.getSession().getAttribute("utente");

        if(utente==null) {
            resp.sendRedirect("pageLogin");
        }else {
            if(req.getParameter("nome")!=null && req.getParameter("pass")!=null && !req.getParameter("nome").equals("") && !req.getParameter("pass").equals("")){
                if(PatternInput.nome(req.getParameter("nome")) && PatternInput.password(req.getParameter("pass"))){
                    Utente newUtente= new Utente();
                    UtenteDAO utenteDAO= new UtenteDAO();

                    newUtente.setId(utente.getId());

                    newUtente.setNome(req.getParameter("nome"));
                    newUtente.setPassword(req.getParameter("pass"));

                    utenteDAO.cambiaNomeEPassword(newUtente);
                    utente.setNome(req.getParameter("nome"));
                    utente.setPassword(newUtente.getPassword());

                    resp.sendRedirect("AreaUtenti");
                }else
                    resp.sendRedirect("AreaUtenti?error=2");
            }else
                resp.sendRedirect("AreaUtenti?error=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
