<%@ page import="model.PatternInput" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 06/04/2023
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="./css/error.css">

    <script>
        function indietro(){
            window.history.back();
        }
    </script>
</head>

<body>
<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<main>
    <div id="blocco-titolo-pagina-errore">
        <h2> Si è verificato un piccolo problema..., nulla di irreparabile! </h2>
    </div>

    <div id="blocco-errore">
        <div>
            <img src="./immage/immage-error.jpg">
        </div>

        <div id="blocco-gestione-errore">
            <div id="blocco-testo">
                <h3>Errore input</h3>

                <% int numerError=0;
                    if(request.getParameter("error")!=null && PatternInput.numero(request.getParameter("error"))){
                        numerError = Integer.parseInt(request.getParameter("error"));
                        if(numerError==1){%>
                        <h4> È avvenuto un piccolo problema con i valori inseriti, non tutti i parametri sono stati specificati. Controlla i valori inseriti, e riprova.</h4>
                    <% }else if(numerError==2){%>
                        <h4> È avvenuto un piccolo problema con i valori inseriti, non tutti i parametri sono conformi al formato indicato. Controlla i valori inseriti, e riprova.</h4>
                    <%}else if(numerError==3){%>
                        <h4> È avvenuto un piccolo problema, il prodotto indicato non esiste o non è attualmente in catalogo.</h4>
                    <%}else if(numerError==4){%>
                        <h4> È avvenuto un piccolo problema, siamo spiacenti ma non hai i permessi per accedere a quest'area.</h4>
                    <%}else {%>
                        <h4> È avvenuto un piccolo problema, siamo spiacenti ma non sappiamo determinare la natura di esso, crechermo di risolvere il prima possibile, grazie del contributo.</h4>
                    <%}%>
                <%}else {%>
                    <h4> È avvenuto un piccolo problema, siamo spiacenti ma non sappiamo determinare la natura di esso, cerchermo di risolvere il prima possibile, grazie del contributo.</h4>
                <%}%>
            </div>

            <% if(numerError==1 || numerError==2 || numerError==3){%>
            <div id="blocco-bottone-riprova">
                <button onclick="indietro()">RIPROVA</button>
            </div>
            <% }%>
        </div>
    </div>
</main>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
