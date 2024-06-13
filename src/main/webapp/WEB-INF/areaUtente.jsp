<%@ page import="model.Been.Utente" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 06/04/2023
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Area Utente</title>
    <link rel="stylesheet" href="./css/areaUtente.css">
    <link rel="stylesheet" href="./css/thisIsBulkOn.css">

</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>


<div id="blocco-titolo-utente">
    <h2> Area Utente</h2>
</div>


<% if(request.getParameter("error")!=null){%>
    <% if(request.getParameter("error").equals("1")){%>
        <div id="errore-cambio-credenziali">
            <div>
                <h4>Errore cambiamento credenziali non effettuato, non sono stati sepecificati tutti i parametri.</h4>
            </div>
        </div>
    <%}else {%>
        <div id="errore-cambio-credenziali">
            <div>
                <h4>Errore cambiamento credenziali non effettuato, non tutti i formati sono stati rispettati.</h4>
            </div>
        </div>
    <%}%>
<%}%>

<div id="blocco-totale-credenziali">

    <div id="blocco-credenziali-cambiamento">
        <form action="cambiaCredenziali" method="post">

            <div class="blocco-input-nep">
                <label>&nbsp;&nbsp;&nbsp;Nome</label>
                <div>
                    <input type="text" id="nome" name="nome" value="${utente.nome}" maxlength="30" pattern="^[a-zA-Z ]{3,29}[a-zA-Z]$" title="Utilizare solo lettere minuscole o maiuscole minimo 4 massimo 30." required>
                </div>
            </div>

            <div class="blocco-input-nep">
                <label>&nbsp;&nbsp;&nbsp;Password</label>
                <div>
                    <input type="password" name="pass" placeholder="**************" minlength="8" maxlength="16" title="Da 8 a 16 caratteri, tra cui un numero, una maiuscola e un carattere speciale tra i seguenti !@#$%^&*" pattern="^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$" required>
                </div>
            </div>

            <div id="blocco-input-bottone">
                <input type="submit" id="bottone-login" value="CAMBIA CREDENZIALI">
            </div>

        </form>
    </div>

    <div id="blocco-credenziali-nonCambiamento">

        <div class="blocco-input-nep">
            <label>&nbsp;&nbsp;&nbsp;Indirizzo email</label>
            <div>
                <input value="${utente.email}" disabled>
            </div>
        </div>

        <div id="blocco-input-admin">
            <% if(((Utente)request.getSession().getAttribute("utente")).isAdmin()){%>
                <label>Sei un Admin? &nbsp;&nbsp;&nbsp;SI</label>
            <% }else{%>
                <label>Sei un Admin? &nbsp;&nbsp;&nbsp;NO</label>
            <% }%>
        </div>

        <div id="blocco-logout-bottone">
            <form action="logout">
                <input type="submit" id="bottone-logout" value="LOGOUT">
            </form>
        </div>

    </div>
</div>


<div id="blocco-thisIsNutrition">
    <div id="blocco-testo-immagini">
        <h2> THIS IS BULKON </h2>
        <h4> Supportaci condividendo il tuo percorso sui vari social. </h4>
    </div>

    <div id="blocco-immagini">
        <div id="blocco-immagine-principale">
            <img src="./immage/immagine-principale.jpg">
        </div>

        <div id="blocco-immagini-secondarie">

            <div id="blocco-immagine-1">
                <img src="./immage/immagine-seconaria-1.jpg">
            </div>

            <div id="blocco-immagine-2">
                <img src="./immage/immagine-secondaria2.jpg">
            </div>

            <div id="blocco-immagine-3">
                <img src="./immage/immagine-secondaria-3.jpg">
            </div>

            <div id="blocco-immagine-4">
                <img src="./immage/immagine-secondaria-4.jpg">
            </div>

        </div>
    </div>
</div>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
