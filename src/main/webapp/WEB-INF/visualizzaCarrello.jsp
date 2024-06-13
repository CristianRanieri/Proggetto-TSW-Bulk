<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Been.Carrello" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Been.Prodotto" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 02/05/2023
  Time: 00:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title> Carrello </title>
    <link rel="stylesheet" href="./css/carrello.css">
    <link rel="stylesheet" href="./css/prodottiConsigliati.css">

</head>

<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<div id="carrello-odrini">

    <div class="carrello-lable-testa">
        <h2><a href="pageCarrello"> Carrello </a></h2>
        <div>
        </div>
    </div>

    <div class="carrello-lable-testa">
        <h2><a href="pageOrdini"> Ordini </a></h2>
    </div>

</div>


<div id="carrello-toatale">

    <% if(request.getAttribute("error")!=null){%>
        <div id="blocco-messaggio-cambiamenti">
            <h2> ATTENZIONE!</h2>
            <h3>Per alcuni prodotti non è più disponibili il numero di pezzi richiesti, quindi sono stati decrementati al numero massimo disponibile attuale, mente i prodotti non più disponibili o non più in catalogo sono stati rimossi dal carrello.</h3>
        </div>
    <% }%>


    <% if(session.getAttribute("carrello")==null || ((Carrello)session.getAttribute("carrello")).getContenutoCarrello().isEmpty() || ((ArrayList<Prodotto>)request.getAttribute("prodotti")).isEmpty()){%>
        <h2> Il carrello è vuoto, aggiungi degli articoli per effettuare un acquisto. </h2>
</div>
    <% }else{%>

    <main>
        <div class="blocco-prodotto-carrello">
            <div class="blocco-nomi-campi">
                <div class="carrello-lable-immagine">
                    <h4> PRODOTTI </h4>
                </div>

                <div class="carrello-lable-info">

                </div>

                <div class="carrello-lable-numeroPezzi">
                    <h4> NUMERO PEZZI </h4>
                </div>

                <div class="carrello-lable-prezzo">
                    <h4> PREZZO </h4>
                </div>
            </div>

            <% ArrayList<Prodotto> prodottos=(ArrayList<Prodotto>) request.getAttribute("prodotti");%>
            <% Prodotto prodotto= prodottos.get(0);%>

            <div class="carrello-valori-campi">

                <div class="carrello-valori-immagine">
                    <a href="pageProdotto?idProduct=<%= prodotto.getiDProdotto()%>&numberSpecific=<%= prodotto.getNumero()%>"> <img src="<%= application.getContextPath()+"/upload/ID_"+prodotto.getiDProdotto()+"_NU_" + prodotto.getNumero() +"_"+"1"+".png"%>"> </a>
                </div>

                <div class="carrello-valori-info">
                    <h3> <%= prodotto.getNome()%> </h3>

                    <h4> GUSTO </h4>
                    <h3> <%= prodotto.getGusto()%> </h3>
                    <h4> FORMATO </h4>
                    <h3> <%= prodotto.getQuantita()%>gr </h3>
                </div>

                <div class="carrello-valori-numeroPezzi">
                    <form action="aggiuntaCarrello">
                        <input type="hidden" name="idProduct" value="<%= prodotto.getiDProdotto()%>" >
                        <input type="hidden" name="numberSpecific" value="<%= prodotto.getNumero()%>">
                        <input type="hidden" name="numeroPezzi" value="1">
                        <button type="submit" formaction="decrementaCarrello" class="carrello-decremento-incremnto"> - </button>
                        <label> <h3 style="color: black"> <%= ((Carrello)session.getAttribute("carrello")).getContenutoCarrello().get(0).getNumeroPezzi()%> </h3> </label>
                        <button type="submit"  class="carrello-decremento-incremnto"> + </button>
                    </form>
                </div>

                <div class="carrello-valori-prezzo">
                    <h3> <%= prodotto.getPrezzo()%>€ </h3>
                </div>
            </div>
        </div>


        <% for(int i = 1; i< prodottos.size(); i++){%>

        <div class="blocco-prodotto-carrello">

            <div class="carrello-valori-campi">

                <div class="carrello-valori-immagine">
                    <a href="pageProdotto?idProduct=<%= prodottos.get(i).getiDProdotto()%>&numberSpecific=<%= prodottos.get(i).getNumero()%>"> <img src="<%= application.getContextPath()+"/upload/ID_"+prodottos.get(i).getiDProdotto()+"_NU_" + prodottos.get(i).getNumero() +"_"+"1"+".png"%>"> </a>
                </div>

                <div class="carrello-valori-info">
                    <h3> <%= prodottos.get(i).getNome()%> </h3>

                    <h4> GUSTO </h4>
                    <h3> <%= prodottos.get(i).getGusto()%> </h3>
                    <h4> FORMATO </h4>
                    <h3> <%= prodottos.get(i).getQuantita()%>gr </h3>
                </div>

                <div class="carrello-valori-numeroPezzi">
                    <form action="aggiuntaCarrello">
                        <input type="hidden" name="idProduct" value="<%= prodottos.get(i).getiDProdotto()%>" >
                        <input type="hidden" name="numberSpecific" value="<%= prodottos.get(i).getNumero()%>">
                        <input type="hidden" name="numeroPezzi" value="1">
                        <button type="submit" formaction="decrementaCarrello" class="carrello-decremento-incremnto"> - </button>
                        <label><h3 style="color: black"> <%= ((Carrello)session.getAttribute("carrello")).getContenutoCarrello().get(i).getNumeroPezzi()%> </h3> </label>
                        <button type="submit"  class="carrello-decremento-incremnto"> + </button>
                    </form>
                </div>

                <div class="carrello-valori-prezzo">
                    <h3> <%= prodottos.get(i).getPrezzo()%>€ </h3>
                </div>
            </div>
        </div>

        <% }%>
        <%-- tutto il resto viene duplicato per n volte --%>


    </main>

    <div id="carrello-effettua-ordine">

        <div id="carrello-sommario">
            <h2> Sommario </h2>
        </div>

        <div id="carrello-spedizione">
            <h3> Spendi 30.00€ e la spedizione sara gratuita </h3>
        </div>

        <div id="carrello-info-prezzo">

            <div id="carrello-subtotale">
                <div id="carrello-lable-subtotale">
                    <h4> Subtotale</h4>
                </div>

                <div id="carrello-prezzo-subtotale">
                    <h4><%= request.getAttribute("prezzoTotale")%>€ </h4>
                </div>
            </div>

            <div id="carrello-totale">
                <div id="carrello-lable-totale">
                    <h3>Totale ordine</h3>
                </div>

                <div id="carrello-prezzo-totale">
                    <h3><%= request.getAttribute("prezzoTotale")%>€ </h3>
                </div>
            </div>

            <div id="carrello-bottone-ordine">
                <form action="pageEffettuaOrdine">
                    <button type="submit" id="bottone-ordine"> PROCEDI ALL'ACQUISTO </button>
                </form>
            </div>
        </div>

    </div>

</div>

<% }%>


<h2 id="titolo-prodottiConsigliati">TI PPOTREBBE PIACERE ACNHE...</h2>

<div id="consigliati">
    <ul id="prodotti">
        <% for(Prodotto prodotto : ((ArrayList<Prodotto>) request.getAttribute("prodottiInteresse"))){%>
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


</body>

<jsp:include page="footer.jsp">
    <jsp:param name="footer" value=""/>
</jsp:include>

</html>
