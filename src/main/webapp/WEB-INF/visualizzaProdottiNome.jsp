<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Been.Prodotto" %>
<%@ page import="model.Been.Utente" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 04/07/2023
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prodotti</title>
    <link rel="stylesheet" href="./css/prodotti.css">
    <link rel="stylesheet" href="./css/thisIsBulkOn.css">

    <script>
        function stapaPrezzo(){
            let element = document.getElementById("prezzo");
            document.getElementById("stampa-prezzo").innerHTML = element.value+"€";
        }
    </script>

</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<main>
    <div id="blocco-titolo-pagina">
        <h2> Paigna Dei Prodotti</h2>
    </div>

    <% if(session.getAttribute("utente")!=null && ((Utente) session.getAttribute("utente")).isAdmin()){%>
    <div id="blocco-inserisci-prodotto">
        <form action="pageInserisciProdotto">
            <input type="submit" value="INSERISCI PRODOTTO">
        </form>
    </div>
    <%}%>


    <div id="blocco-seleziona-caratteristiche">
        <form action="pageRicercaProdottiNome">
            <div id="blocco-gusto">

                <select name="gusto">
                    <option value="tutti">Tutti</option>
                    <% for(String gusto : (ArrayList<String>)application.getAttribute("gusti")){%>
                        <% if(request.getAttribute("gusto")!=null && request.getAttribute("gusto").equals(gusto)){%>
                            <option value="<%= gusto%>" selected> <%= gusto%> </option>
                        <%} else{%>
                            <option value="<%= gusto%>"> <%= gusto%></option>
                        <%}%>
                    <%}%>
                </select>
            </div>

            <div id="blocco-quantita">

                <select name="quantitaPro">
                    <option value="tutti">Tutti</option>
                    <% for(Integer quantita : (ArrayList<Integer>)application.getAttribute("quantita")){%>
                        <% if(request.getAttribute("quantitaPro")!=null && request.getAttribute("quantitaPro").equals(String.valueOf(quantita))){%>
                            <option value="<%= quantita%>" selected> <%= quantita%> </option>
                        <%} else{%>
                            <option value="<%= quantita%>"> <%= quantita%> </option>
                        <%}%>
                    <%}%>
                </select>
            </div>

            <div id="blocco-prezzo">
                <input type="range" id="prezzo" onchange="stapaPrezzo()" name="prezzo" min="0" max="99" value="<%= request.getAttribute("prezzo")%>"> <span id="stampa-prezzo"> </span>
            </div>

            <div id="blocco-bottone-ricerca">
                <input type="submit"  value="Ricerca">
            </div>

            <%-- per mantenere il nome scritto --%>
            <input type="hidden" name="nomeProdotto" value="<%=request.getParameter("nomeProdotto")%>">
        </form>
    </div>



    <div id="blocco-prodotti">
        <ul id="prodotti">
            <% for(Prodotto prodotto : ((ArrayList<Prodotto>) request.getAttribute("prodotti"))){%>
                <li>
                    <div class="prodottoBlocco">
                        <a href="pageProdotto?idProduct=<%= prodotto.getiDProdotto()%>&numberSpecific=<%= prodotto.getNumero()%>"> <img src="<%= application.getContextPath()+"/upload/ID_"+prodotto.getiDProdotto()+"_NU_" + prodotto.getNumero() +"_"+"1"+".png"%>"> </a>
                        <a class="prodottoBloccoAncora" ><h3 class="nome"><%= prodotto.getNome()%></h3></a>
                        <a class="prodottoBloccoAncora"><h3 class="prezzo">Prezzo: <%= prodotto.getPrezzo()%>€</h3></a>
                    </div>
                </li>
            <%}%>
        </ul>
    </div>

</main>



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

<script>
    stapaPrezzo();
</script>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
