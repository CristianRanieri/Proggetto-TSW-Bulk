<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Been.Prodotto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="./css/home.css">
    <link rel="stylesheet" href="./css/chiSiamo.css">

</head>
<body>

<jsp:include page="header.jsp">
        <jsp:param name="header" value=""/>
</jsp:include>

<main>
    <div id="imageHome">
        <img src="./immage/banner-home.webp">
        <div>
            <form action="prodottiRicerca">
                <input type="hidden" name="nomeProdotto" value=" ">
                <button formaction="pageRicercaProdottiNome" class="homeButton">Scopri i prodotti</button>
            </form>
        </div>
        <h2>Prodotti piu venduti</h2>
    </div>


    <div id="piuVenduti">
        <ul id="prodotti">
            <% for(Prodotto prodotto : ((ArrayList<Prodotto>) request.getAttribute("prodottiPiuVenduti"))){%>
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

    <div id="chiSiamo2">
        <div>
            <img src="./immage/immagineProdotti.webp">
        </div>

        <div id="testo2" >
            <h2>Chi Siamo?</h2>
            <h3>BulkOn è una piccola realta italiana, un azienda nata il 2023 la quale cerca di portare una ventata di frescenza nel settore della nutrizione sportiva.<br> Cio viene fatto attraverso la creazione di gusti nuovi e sorprendenti e prezzi molto competitivi!</h3>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>


</body>
</html>