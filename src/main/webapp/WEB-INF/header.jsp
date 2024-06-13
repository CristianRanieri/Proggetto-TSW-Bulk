<%@ page import="model.Been.Categoria" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 05/07/2023
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0">

    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/button.css">

    <script>
        function openNav() {
            document.getElementById("sideBar").style.width = "250px";
        }

        function closeNav() {
            document.getElementById("sideBar").style.width = "0";
        }

        function mostraMenu(nome,numero){
            let element= document.getElementById(nome+"-"+"0");
            if(window.getComputedStyle(element).display == "none") {
                for (let i = 0; i < numero; i++) {
                    document.getElementById(nome +"-"+ i).style.display = "block";
                }
            }else {
                for (let i = 0; i < numero; i++) {
                    document.getElementById(nome +"-"+ i).style.display = "none";
                }
            }
        }
    </script>
</head>
<body>

<header>
    <nav id="menuBar">
        <a href="index.jsp"> <img src="./immage/logo-bulk.png" title="BulkOn"> </a>
    </nav>

    <div id="immagine-logo-390px">
        <a href="index.jsp"><img id="imagineLogo" src="./immage/logo-bulk.png" title="BulkOn"></a>
    </div>

    <form id="formSearch" action="pageRicerca">
        <input id="textChange" name="nomeProdotto" type="text" placeholder="Search...">
        <button formaction="pageRicercaProdottiNome" id="imageSearch"><img src="./immage/freccia.png"></button>
    </form>

    <form id="formLogin" action="pageLogin">
        <% if(session.getAttribute("utente")==null){%>
            <button type="submit" class="styleButton">LOGIN</button>
        <%}else {%>
            <button type="submit" class="styleButton">UTENTE</button>
        <%}%>
    </form>

    <form id="formCarrello" action="pageCarrello">
        <button type="submit" class="styleButton">CARRELLO</button>
    </form>

    <ul class="menuBar">
        <li class="menuBar"><a href="index.jsp">HOME</a></li>

        <% for(Categoria cat: ((ArrayList<Categoria>)application.getAttribute("categorie"))){%>
            <li class="menuBar"><a href="pageRicercaProdottiNome?nomeProdotto=<%= cat.getNome()%>"><%= cat.getNome()%></a></li>
            <div class="dropDownList">
                    <% for(String s: cat.getSpecifiche()){%>
                        <a class="dropElement" href="pageProdotti?category=<%= cat.getNome()%>&specific=<%= s%>"><%= s%></a>
                    <%}%>
            </div>
        <%}%>

    </ul>

    <div id="sideBar">
        <a id="closeBar" onclick="closeNav()">&times;</a>
        <% int j=0;
            for(Categoria cat: ((ArrayList<Categoria>)application.getAttribute("categorie"))){%>
        <a class="dropElement" onclick="mostraMenu(<%= j%>,<%= cat.getSpecifiche().size()%>)"><%= cat.getNome()%></a>

            <%  int i=0;
                for(String s: cat.getSpecifiche()){%>
                    <a class="dropElement" style="display: none" id="<%= j%>-<%= i%>" href="pageProdotti?category=<%= cat.getNome()%>&specific=<%= s%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= s%></a>
                <% i++;%>
            <%}%>
            <% j++;%>
        <%}%>
        <a class="dropElement"></a>
        <% if(session.getAttribute("utente")==null){%>
            <a class="dropElement" href="pageLogin">LOGIN</a>
        <%}else {%>
            <a class="dropElement" href="pageLogin">UTENTE</a>
        <%}%>

        <a class="dropElement" href="pageCarrello">CARRELLO</a>
        <a class="dropElement"></a>
        <a class="dropElement"></a>

    </div>

    <div id="divSideSpan">
        <span id="sideSpan" onclick="openNav()">&#9776; </span>
    </div>
</header>

</body>
</html>
