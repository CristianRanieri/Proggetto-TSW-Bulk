<%@ page import="model.Been.Prodotto" %>
<%@ page import="model.Been.SpecificheProdotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Been.Utente" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 01/05/2023
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prodotto</title>
    <link rel="stylesheet" href="./css/thisIsBulkOn.css">
    <link rel="stylesheet" href="./css/prodotto.css">

    <script>
        function ImageExsists(url){
            const xhttp = new XMLHttpRequest();
            xhttp.open("HEAD",url,false);
            xhttp.send();

            if(xhttp.status=="404"){
                return false;
            }else {
                return true;
            }
        }

        function SetGusti(){
            const xhttp = new XMLHttpRequest();
            let gusto = document.getElementById("gustoSelected");
            let gustoPro = gusto.options[gusto.selectedIndex].value;

            let quantita = document.getElementById("quantitaSelected");
            let quantitaPro = quantita.options[quantita.selectedIndex].value;

            xhttp.onreadystatechange= function () {
                if (this.readyState == 4 && this.status == 200) {
                    const p = JSON.parse(this.responseText);
                    document.getElementById("prezzoProdotto").innerHTML = p.prezzo+"€";
                    document.getElementById("inputNumeroPezzi").max = p.numeroPezzi;
                    if(p.numeroPezzi>0){
                        document.getElementById("bottoneAggiunta").disabled = false;
                        document.getElementById("bottoneAggiunta").style.backgroundColor = "Black";
                        document.getElementById("bottoneAggiunta").style.color = "White";
                    }else {
                        document.getElementById("bottoneAggiunta").disabled = true;
                        document.getElementById("bottoneAggiunta").style.backgroundColor = "#818181";
                        document.getElementById("bottoneAggiunta").style.color = "Black";
                    }
                    document.getElementById("inputNumeroPezzi").value=1;
                    for(let i=0;i<document.getElementsByClassName("numberSpecific").length;i++){
                        document.getElementsByClassName("numberSpecific").item(i).value= p.numeroSpecifica;
                    }

                    for(let i=1;i<=3;i++){
                        let url=location.protocol+"//"+ location.hostname+":"+location.port+document.getElementById("path").innerHTML+"/upload/ID_"+document.getElementById("idProdotto2").innerHTML+"_NU_" + p.numeroSpecifica +"_"+i+".png"
                        if(ImageExsists(url)) {
                            document.getElementsByClassName("imageProdotto").item(i - 1).src = document.getElementById("path").innerHTML + "/upload/ID_" + document.getElementById("idProdotto2").innerHTML + "_NU_" + p.numeroSpecifica + "_" + i + ".png";
                            document.getElementsByClassName("imageProdotto").item(i - 1).style.display = "inline-block";
                        }else
                            document.getElementsByClassName("imageProdotto").item(i-1).style.display = "none";
                    }

                    document.getElementById("quantitaSelected").innerHTML = "";

                    let text = "";
                    for (let i = 0; i < p.quantita.length; i++) {
                        text += "<option value=\"" + p.quantita[i] + "\">" + p.quantita[i] + "gr</option>";
                    }
                    document.getElementById("quantitaSelected").innerHTML = text;
                }
            }
            xhttp.open("GET",document.getElementById("path").innerHTML+"/setSpecificheGusti"+"?gusto="+gustoPro+
                "&quantita="+quantitaPro+"&idProdotto="+ document.getElementById("idProdotto2").innerHTML,true);
            xhttp.send();
        }

        function SetQuantita(){
            const xhttp = new XMLHttpRequest();
            let gusto = document.getElementById("gustoSelected");
            let gustoPro = gusto.options[gusto.selectedIndex].value;

            let quantita = document.getElementById("quantitaSelected");
            let quantitaPro = quantita.options[quantita.selectedIndex].value;

            xhttp.onreadystatechange= function () {
                if (this.readyState == 4 && this.status == 200) {
                    const p = JSON.parse(this.responseText);
                    document.getElementById("prezzoProdotto").innerHTML = p.prezzo+"€";
                    document.getElementById("inputNumeroPezzi").max = p.numeroPezzi;
                    if(p.numeroPezzi>0){
                        document.getElementById("bottoneAggiunta").disabled = false;
                        document.getElementById("bottoneAggiunta").style.backgroundColor = "Black";
                        document.getElementById("bottoneAggiunta").style.color = "White";
                    }else {
                        document.getElementById("bottoneAggiunta").disabled = true;
                        document.getElementById("bottoneAggiunta").style.backgroundColor = "#818181";
                        document.getElementById("bottoneAggiunta").style.color = "Black";
                    }
                    document.getElementById("inputNumeroPezzi").value=1;
                    for(let i=0;i<document.getElementsByClassName("numberSpecific").length;i++){
                        document.getElementsByClassName("numberSpecific").item(i).value= p.numeroSpecifica;
                    }

                    for(let i=1;i<=3;i++){
                        let url=location.protocol+"//"+ location.hostname+":"+location.port+document.getElementById("path").innerHTML+"/upload/ID_"+document.getElementById("idProdotto2").innerHTML+"_NU_" + p.numeroSpecifica +"_"+i+".png"
                        if(ImageExsists(url)) {
                            document.getElementsByClassName("imageProdotto").item(i - 1).src = document.getElementById("path").innerHTML + "/upload/ID_" + document.getElementById("idProdotto2").innerHTML + "_NU_" + p.numeroSpecifica + "_" + i + ".png";
                            document.getElementsByClassName("imageProdotto").item(i - 1).style.display = "inline-block";
                        }else
                            document.getElementsByClassName("imageProdotto").item(i-1).style.display = "none";
                    }

                }
            }
            xhttp.open("GET",document.getElementById("path").innerHTML+"/setSpecificheQuantia"+"?gusto="+gustoPro+
                "&quantita="+quantitaPro+"&idProdotto="+ document.getElementById("idProdotto2").innerHTML,true);
            xhttp.send();
        }
    </script>

</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<%-- Servono per lo script --%>
<p id="path" hidden="true"><%= application.getContextPath()%></p>
<p id="idProdotto2" hidden="true"><%= ((Prodotto)request.getAttribute("prodotto")).getiDProdotto()%></p>


<main>
    <%Utente utente=((Utente) session.getAttribute("utente"));
        if(utente!=null && utente.isAdmin()){%>
    <form action="pageModificaSpecifica">
        <input type="hidden" name="idProdotto" value="<%=((Prodotto)request.getAttribute("prodotto")).getiDProdotto()%>">
        <%--ogni volta che viene cambiato gusto o quantita bisogna aggiornare tramite ajax questo valore --%>
        <input type="hidden" id="numeroSpecifica" name="numeroSpecfica" class="numberSpecific" value="<%=((Prodotto)request.getAttribute("prodotto")).getNumero()%>">

        <div id="blocco-bottoni-admin">
            <div id="blocco-bottone-modificaProdotto">
                <button type="submit" class="bottoneProdotto" > MODIFICA PRODOTTO </button>
            </div>

            <div id="blocco-bottone-aggiungiSpecifica">
                <button type="submit" formaction="pageAggiungiSpecifica" class="bottoneProdotto"> AGGIUNGI UNA SPECIFICA </button>
            </div>
        </div>
    </form>
    <%}%>


    <div id="blocco-immagini-informazioni">
        <div id="blocco-immagini-prodotto">

            <div id="blocco-immagine-principale-prodotto">
                <% String destinazione="upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"1"+".png";
                    if(Files.exists(Paths.get(application.getRealPath(destinazione)))){%>
                        <img class="imageProdotto" src="<%= application.getContextPath()+"/upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"1"+".png"%>">
                <% }else {%>
                    <img class="imageProdotto" style="display: none">
                <% }%>
            </div>

            <div class="blocco-immagine-secondaria-prodotto">
                <% destinazione="upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"2"+".png";
                    if(Files.exists(Paths.get(application.getRealPath(destinazione)))){%>
                        <img class="imageProdotto" src="<%= application.getContextPath()+"/upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"2"+".png"%>">
                <% }else {%>
                    <img class="imageProdotto" style="display: none">
                <% }%>
            </div>

            <div class="blocco-immagine-secondaria-prodotto">
                <% destinazione="upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"3"+".png";
                    if(Files.exists(Paths.get(application.getRealPath(destinazione)))){%>
                        <img class="imageProdotto" src="<%= application.getContextPath()+"/upload/ID_"+((Prodotto)request.getAttribute("prodotto")).getiDProdotto()+"_NU_" + ((Prodotto)request.getAttribute("prodotto")).getNumero() +"_"+"3"+".png"%>">
                <% }else {%>
                    <img class="imageProdotto" style="display: none">
                <% }%>
            </div>
        </div>


        <div id="blocco-informazioni-prodotto">
            <div id="blocco-nome-prodotto">
                <h1> <%= ((Prodotto)request.getAttribute("prodotto")).getNome()%> </h1>
            </div>

            <div id="blocco-prezzo-prodotto">
                <h2 id="prezzoProdotto"> <%= ((Prodotto)request.getAttribute("prodotto")).getPrezzo()%>€ </h2>
            </div>

            <div id="blocco-gusto-prodotto">
                <label>&nbsp;&nbsp;&nbsp;Gusto</label>
                <div id="blocco-select-gusto">
                    <select name="gusto" id="gustoSelected" onchange="SetGusti()">
                        <% for(String gusto : ((List<String>)request.getAttribute("gusti"))){%>
                            <% if(gusto.equals(((Prodotto)request.getAttribute("prodotto")).getGusto())){%>
                                <option value="<%= gusto%>" selected> <%= gusto%> </option>
                            <%}else{%>
                                <option value="<%= gusto%>"> <%= gusto%> </option>
                            <%}%>
                        <%}%>
                    </select>
                </div>
            </div>

            <div id="blocco-quantita-prodotto">
                <label>&nbsp;&nbsp;&nbsp;Quantita</label>
                <div id="blocco-select-quantita">
                    <select name="quantita" id="quantitaSelected" onchange="SetQuantita()">
                        <%-- Deve mostrare tutte le quantita dello specifico prodotto--%>
                        <% for(Integer quantita : ((List<Integer>)request.getAttribute("quantita"))){%>
                            <% if(String.valueOf(quantita).equals(String.valueOf(((Prodotto)request.getAttribute("prodotto")).getQuantita()))){%>
                                <option value="<%= quantita%>" selected> <%= quantita%> gr</option>
                            <%}else{%>
                                <option value="<%= quantita%>"> <%= quantita%> gr</option>
                            <%}%>
                        <%}%>
                    </select>
                </div>
            </div>

            <form action="aggiuntaCarrello">
                <input type="hidden" name="idProduct" value="<%=((Prodotto)request.getAttribute("prodotto")).getiDProdotto()%>">
                <%--ogni volta che viene cambiato gusto o quantita bisogna aggiornare tramite ajax questo valore --%>
                <input type="hidden" name="numberSpecific" class="numberSpecific" value="<%=((Prodotto)request.getAttribute("prodotto")).getNumero()%>">

                <div id="blocco-numeroPezzi-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Numero Pezzi</label>
                    <div>
                        <input type="number" id="inputNumeroPezzi" name="numeroPezzi" value="1" min="1" max="<%= ((Prodotto)request.getAttribute("prodotto")).getNumeroPezzi()%>">
                    </div>
                </div>


                <div id="blocco-bottone-aggiungiCarrello">
                    <% if(((Prodotto)request.getAttribute("prodotto")).getNumeroPezzi()<=0){%>
                        <input type="submit" value="AGGIUNGI AL CARRELLO" class="bottoneProdotto" id="bottoneAggiunta" style="background-color: #818181;color: black" disabled>
                    <% }else{%>
                        <input type="submit" value="AGGIUNGI AL CARRELLO" class="bottoneProdotto" id="bottoneAggiunta">
                    <%}%>
                </div>
            </form>

        </div>


        <div id="blocco-descrizione">
            <h2> Descrizione del Prodotto </h2>
            <h4> <%= ((Prodotto)request.getAttribute("prodotto")).getDescrizione()%> </h4>
        </div>

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

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
