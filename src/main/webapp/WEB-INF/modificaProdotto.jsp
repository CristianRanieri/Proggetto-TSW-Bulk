<%@ page import="model.Been.Prodotto" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 03/07/2023
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Creazione Prodotto</title>
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/inserimentoProdotto.css">
    <link rel="stylesheet" href="./css/modificaSpecifica.css">

</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<% Prodotto prodotto= (Prodotto)request.getAttribute("prodotto");%>
<main>
    <div id="blocco-titolo-insProdotto">
        <h2> Insreimento Prodotto </h2>
    </div>

    <form action="modificaProdotto">
        <div id="blocco-dati-prodotto">

            <div class="blocco-input-prodotto-totale">

                <div class="blocco-input-prodotto">

                    <h3> DATI PRODOTTO</h3>

                    <label>&nbsp;&nbsp;&nbsp;Nome Prodotto</label>
                    <div>
                        <input type="text" value="<%= prodotto.getNome()%>" disabled>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Categoria</label>
                    <div>
                        <input type="text" value="<%= prodotto.getCategoria()%>" disabled>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Specifica</label>
                    <div>
                        <input type="text" value="<%= prodotto.getSpecifica()%>" disabled>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Gusto</label>
                    <div>
                        <input type="text" value="<%= prodotto.getGusto()%>" disabled>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Quantita(Formato in grammi)</label>
                    <div>
                        <input type="text" value="<%= prodotto.getQuantita()%>gr" disabled>
                    </div>
                </div>

            </div>

            <div class="blocco-input-prodotto-totale">

                <div class="blocco-input-prodotto">

                    <h3> DATI SPECIFICA</h3>

                    <label>&nbsp;&nbsp;&nbsp;Prezzo</label>
                    <div>
                        <input type="text" name="prezzoProdotto" pattern="^\d{1,3}\.\d{2}$" value="<%= prodotto.getPrezzo()%>" title="Numero formato da esattamente 3 o 4 cifre, suddivise in due coppie da 2 cifre separate da un punto, deve essere maggiore di 0.00" required>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Numero Pezzi</label>
                    <div>
                        <input type="text" name="numeroPezzi" pattern="^\d{1,4}$" value="<%= prodotto.getNumeroPezzi()%>" title="Numero formato da minimo 2 cifre massimo 4" required>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label id="inCatalogo">In Catalogo:&nbsp;&nbsp;&nbsp;</label>
                    <div id="blocco-inCatalogo">
                        <% if(prodotto.isInCatalogo()){%>
                            <input type="checkbox" name="inCatalogo" checked>
                        <%}else {%>
                            <input type="checkbox" name="inCatalogo">
                        <%}%>
                    </div>
                </div>

            </div>
        </div>

        <div id="blocco-bottone-inserimento">
            <input type="submit" value="MODIFICA SPECIFICA">
        </div>

        <input type="hidden" name="idProdotto" value="<%= prodotto.getiDProdotto()%>">
        <input type="hidden" name="numeroSpecifica" value="<%= prodotto.getNumero()%>">
    </form>

</main>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
