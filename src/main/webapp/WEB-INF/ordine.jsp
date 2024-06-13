<%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 02/07/2023
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Effettua Ordine</title>
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/ordine.css">
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<main>
    <div id="blocco-titolo-Acquisto">
        <h2> Procedi All'Acquisto </h2>
    </div>

    <div id="blocco-totale-ordine">

        <form action="effettuaOrdine" method="post">
            <div class="blocco-campi-ordine">
                <div class="blocco-input-ordine">

                    <h3> DATI CLIENTE</h3>

                    <label>&nbsp;&nbsp;&nbsp;Nome</label>
                    <div>
                        <input type="text" name="nome" pattern="^[a-zA-Z]{4,30}$" title="Solo lettere minuscole o maiuscole minmo 4 massimo 30" required>
                    </div>
                </div>

                <div class="blocco-input-ordine">
                    <label>&nbsp;&nbsp;&nbsp;Cognome</label>
                    <div>
                        <input type="text" name="cognome" pattern="^[a-zA-Z]{4,30}$" title="Solo lettere minuscole o maiuscole minmo 4 massimo 30" required>
                    </div>
                </div>
            </div>


            <div class="blocco-campi-ordine">

                <div class="blocco-input-ordine">

                    <h3> DATI PAGAMENTO</h3>

                    <label>&nbsp;&nbsp;&nbsp;Numero Carta</label>
                    <div>
                        <input type="text" name="numeroCarta" pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}" title="4 numeri formati da 4 cifre suddivisi da spazzi" required>
                    </div>
                </div>

                <div class="blocco-input-ordine">
                    <label>&nbsp;&nbsp;&nbsp;Data Scadenza</label>
                    <div>
                        <input type="date" name="dataScadenza" title="Data di scadenza della carta" required>
                    </div>
                </div>

            </div>


            <div class="blocco-campi-ordine">

                <div class="blocco-input-ordine">
                    <label>&nbsp;&nbsp;&nbsp;Codice CVV</label>
                    <div>
                        <input type="text" name="CCV" pattern="\d{3}$" title="Numero formato da 3 cifre" required>
                    </div>
                </div>

            </div>


            <div class="blocco-campi-ordine">
                <div class="blocco-input-ordine">

                    <h3> INDIRIZZO DI CONSAGNA</h3>

                    <label>&nbsp;&nbsp;&nbsp;Indirizzo Via</label>
                    <div>
                        <input type="text" name="Via" pattern="^[a-zA-Z0-9 ]{3,30}$" title="Sono ammesse lettere minuscole, maiuscole, numeri e spazzi minimo 3 massimo 30" required>
                    </div>
                </div>

                <div class="blocco-input-ordine">
                    <label>&nbsp;&nbsp;&nbsp;Indirizzo Numero Civico</label>
                    <div>
                        <input type="text" name="Civico" pattern="^\d{1,4}$" title="Solo numeri minimo 1 massimo 4" required>
                    </div>
                </div>

            </div>


            <div class="blocco-campi-ordine">

                <div class="blocco-input-ordine">
                    <label>&nbsp;&nbsp;&nbsp;Indirizzo CAP</label>
                    <div>
                        <input type="text" name="CAP" pattern="^\d{5}$" title="Un numero formato da esattamente 5 cifre" required>
                    </div>
                </div>

            </div>


            <div id="blocco-input-bottne">
                <input type="submit" value="PROCEDI AL PAGAMENTO">
            </div>

        </form>
    </div>

</main>
</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>

