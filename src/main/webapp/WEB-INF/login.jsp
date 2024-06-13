<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 06/04/2023
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="./css/login.css">
    <link rel="stylesheet" href="./css/chiSiamo.css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

    <script>
        $(document).ready(function (){
            $("h3#testo-croce-error").click(function(){
                $("div#errore-login").css("display","none");
                //$("div#errore-login").hide();
                //document.getElementById("errore-login").style.display= "none";
            });
        });
    </script>

</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<main>
    <c:if test="${requestScope.error}">
        <div id="errore-login">
            <div>
                <h3 id="testo-croce-error"> &times; </h3>
                <h4>Login non effettuato, inserimento di email o password errato.</h4>
            </div>
        </div>
    </c:if>

    <div id="blocco-login">
        <div id="blocco-input-login">
            <div id="titolo-Accedi">
                <h1>Accedi</h1>
            </div>

            <div id="blocco-credenziali-login">
                <form action="login" method="post">
                    <div id="blocco-input-email">
                        <label>&nbsp;&nbsp;&nbsp;Indirizzo email</label>
                        <div>
                            <input type="email" name="email" id="email" maxlength="30" pattern="^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]{1,10}$" title="Dopo la @ almeno un carattere, seguito da un punto e un altro carattere." required>
                        </div>
                    </div>

                    <div id="blocco-input-password">
                        <label>&nbsp;&nbsp;&nbsp;Password</label>
                        <div>
                            <input type="password" name="pass" id="pass" minlength="8" maxlength="16" required>
                        </div>
                    </div>

                    <div id="blocco-input-bottone">
                        <div>
                            <input type="submit" id="bottone-login" value="ACCEDI">
                        </div>
                        <label><a href="pageRegistrazione"> Non hai un account? Creano uno ora. </a></label>
                    </div>
                </form>
            </div>
        </div>

        <div id="blocco-immagine">
            <img id="immagine-logo" src="./immage/login.png">
        </div>
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
