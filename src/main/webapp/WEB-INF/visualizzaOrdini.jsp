<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Been.Ordine" %>
<%@ page import="model.Been.Prodotto" %>
<%@ page import="model.Been.Acquisto" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 02/07/2023
  Time: 00:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ordini</title>
    <link rel="stylesheet" href="./css/ordini.css">
    <link rel="stylesheet" href="./css/prodottiConsigliati.css">

    <% int numeroOrdiniM=0;%>

    <script>
        function mostraProdotti(){
            let i= <%= numeroOrdiniM%>;
            let j=0;
            for(i;j<10 && document.getElementById(i)!=null;i++){
                let element = document.getElementById(i);
                if(window.getComputedStyle(element).display == "none"){
                    element.style.display= "table";
                    j++;
                    <% numeroOrdiniM+=1;%>
                }
            }

            if(document.getElementById(i)==null || j<10){
                document.getElementById("buttonMostraProdotti").style.display= "none";
            }
        }
    </script>

</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<div id="carrello-odrini">

    <div class="carrello-lable-testa">
        <h2><a href="pageCarrello"> Carrello  </a></h2>
    </div>

    <div class="carrello-lable-testa">
        <h2><a href="pageOrdini"> Ordini </a></h2>
        <div>
        </div>
    </div>

</div>

<%-- se la lista di ordini è vuota bisogno far apparire la scritta, Non ci sono Ordini, effettua un acquisto per visualizzarene uno.*/--%>

<% if(((ArrayList<Ordine>) request.getAttribute("ordini")).isEmpty()){%>
    <h2> Non ci sono Ordini, effettua un acquisto per visualizzarene uno. </h2>
<%}else{%>

<%  ArrayList<Ordine> ordini= (ArrayList<Ordine>) request.getAttribute("ordini");
    ArrayList<Integer> prezzoOrdini = (ArrayList<Integer>) request.getAttribute("prezzoOrdini");
    int i=0;
    for(Ordine ordine : ordini){%>

<table id="<%= i%>">
    <tr class="riga-header display-none-530px">
        <th>ID ORDINE</th>
        <th>DATA</th>
        <th>PREZZO TOTALE</th>
        <th>VIA</th>
        <th>CIVICO</th>
        <th>CAP</th>
    </tr>

    <tr class="riga-header apparizione-530px">
        <th colspan="2">ID ORDINE</th>
        <th colspan="2">DATA</th>
        <th colspan="2">PREZZO TOTALE</th>
    </tr>

    <tr class="apparizione-530px">
        <td colspan="2">#<%= ordine.getIdOrdine()%></td>
        <td colspan="2"><%= ordine.getData().toString()%></td>
        <td colspan="2"><%= prezzoOrdini.get(i)%>€</td>
    </tr>

    <tr class="riga-header apparizione-530px">
        <th colspan="2">VIA</th>
        <th colspan="2">CIVICO</th>
        <th colspan="2">CAP</th>
    </tr>

    <tr class="apparizione-530px">
        <td colspan="2"><%= ordine.getVia()%></td>
        <td colspan="2"><%= ordine.getCivico()%></td>
        <td colspan="2"><%= ordine.getCap()%></td>
    </tr>

    <tr class="display-none-530px">
        <td>#<%= ordine.getIdOrdine()%></td>
        <td><%= ordine.getData().toString()%></td>
        <td><%= prezzoOrdini.get(i)%>€</td>
        <td><%= ordine.getVia()%></td>
        <td><%= ordine.getCivico()%></td>
        <td><%= ordine.getCap()%></td>
    </tr>

    <tr class="riga-header">
        <th colspan="2">NOME PRODOTTO</th>
        <th colspan="2">NUMERO PEZZI</th>
        <th colspan="2">PREZZO ACQUISTO</th>
    </tr>

    <% for(Acquisto acquisto : ordine.getAcquisti()){%>
    <tr>
        <td colspan="2">  <a href="pageProdotto?idProduct=<%= acquisto.getProdotto().getiDProdotto()%>&numberSpecific=<%= acquisto.getProdotto().getNumero()%>"> <%= acquisto.getProdotto().getNome()%> </a></td>
        <td colspan="2"><%= acquisto.getNumeroPezzi()%></td>
        <td colspan="2"><%= acquisto.getPrezzoAcquisto()%>€</td>
    </tr>
    <%}%>

</table>
<%  i++;
}%>

<% }%>

<div id="blocco-bottone-mostraProdotti">
    <button id="buttonMostraProdotti" onclick="mostraProdotti()" > Mostra Ancora </button>
</div>


<div>
    <h2 id="titolo-prodottiConsigliati">TI PPOTREBBE PIACERE ACNHE...</h2>
</div>

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

<script>
    mostraProdotti();
</script>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
