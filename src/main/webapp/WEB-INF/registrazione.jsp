<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 06/04/2023
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="./css/registrazione.css">
    <link rel="stylesheet" href="./css/chiSiamo.css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

    <script>
        $(document).ready(function (){
            $("h3.testo-croce-error").click(function(){
                $("div#errore-registrazione").css("display","none");
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
    <c:if test="${requestScope.error1}">
        <div id="errore-registrazione">
            <div>
                <h3 class="testo-croce-error"> &times; </h3>
                <h4>Creazione account non effettuata, credenziali non valide.</h4>
            </div>
        </div>
    </c:if>

    <c:if test="${requestScope.error2}">
        <div id="errore-registrazione">
            <div>
                <h3 class="testo-croce-error"> &times; </h3>
                <h4>Creazione account non effettuata, email gia esistente.</h4>
            </div>
        </div>
    </c:if>

    <div id="blocco-login">
        <div id="blocco-input-login">
            <div id="titolo-Registrati">
                <h1>Crea un account</h1>
            </div>

            <div id="blocco-credenziali-login">
                <form action="Registrazione" method="post">

                    <div class="blocco-input-nep">
                        <label>&nbsp;&nbsp;&nbsp;Nome</label>
                        <div>
                            <input type="text" name="nome" maxlength="30" pattern="^[a-zA-Z ]{3,29}[a-zA-Z]$" title="Utilizare solo lettere minuscole o maiuscole minimo 4 massimo 30." required>
                        </div>
                    </div>

                    <div class="blocco-input-nep">
                        <label>&nbsp;&nbsp;&nbsp;Indirizzo email</label>
                        <div>
                            <input type="email" name="email" pattern="^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]{1,10}$" title="Dopo la @ almeno un carattere, seguito da un punto e un altro carattere." maxlength="30">
                        </div>
                    </div>

                    <div class="blocco-input-nep">
                        <label>&nbsp;&nbsp;&nbsp;Password</label>
                        <div>
                            <input type="password" name="pass" minlength="8" maxlength="16" title="Da 8 a 16 caratteri, tra cui un numero, una maiuscola e un carattere speciale," pattern="^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$" required>
                        </div>
                    </div>

                    <%--
                    <div id="blocco-input-admin">
                        <label style="font-size: 18px">Sei un Admin?</label>
                        <div>
                            <input type="checkbox" id="admin" name="admin">
                        </div>
                    </div>
                    --%>

                    <div id="blocco-input-bottone">
                        <div>
                            <input type="submit" id="bottone-login" value="CREA UN ACCOUNT">
                        </div>
                        <label><a href="pageLogin"> Hai gia un account? Accedi ora. </a></label>
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
