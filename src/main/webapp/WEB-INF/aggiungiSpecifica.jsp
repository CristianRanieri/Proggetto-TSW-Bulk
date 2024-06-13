<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Been.Prodotto" %><%--
  Created by IntelliJ IDEA.
  User: 174907
  Date: 04/07/2023
  Time: 09:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Creazione Prodotto</title>
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/inserimentoProdotto.css">

</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<% Prodotto prodotto= (Prodotto) request.getAttribute("prodotto");%>

<main>
    <div id="blocco-titolo-insProdotto">
        <h2> Insreimento Prodotto </h2>
    </div>

    <form action="aggiungiSpecificaProdotto" method="post" enctype="multipart/form-data">
        <div id="blocco-dati-prodotto">
            <div class="blocco-input-prodotto-totale">
                <div class="blocco-input-prodotto">

                    <h3> DATI PRODOTTO</h3>

                    <label>&nbsp;&nbsp;&nbsp;Nome</label>
                    <div>
                        <input type="text" disabled value="<%= prodotto.getNome()%>">
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Categoria</label>
                    <div>
                        <input type="text" disabled value="<%= prodotto.getCategoria()%>">
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Specifica</label>
                    <div>
                        <input type="text" disabled value="<%= prodotto.getSpecifica()%>">
                    </div>
                </div>

                <div class="blocco-input-prodotto">

                    <h3> DATI SPECIFICA</h3>

                    <label>&nbsp;&nbsp;&nbsp;Prezzo</label>
                    <div>
                        <input type="text" name="prezzo" pattern="^\d{1,3}\.\d{2}$" title="Numero formato da esattamente 3 o 4 cifre, suddivise in due coppie da 2 cifre separate da un punto, deve essere maggiore di 0.00" required>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Quantita(Formato in grammi)</label>
                    <div>
                        <input list="quantita" name="quantita" pattern="^\d{2,4}$" title="Numero formato da minimo 2 cifre massimo 4" required>
                        <datalist id="quantita">
                            <% for(Integer qua : (ArrayList<Integer>)application.getAttribute("quantita")){%>
                            <option value="<%= qua%>">
                                    <%}%>
                        </datalist>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Gusto</label>
                    <div>
                        <input list="gusti" name="gusto" minlength="4" maxlength="30" pattern="^[A-Za-z]{4,30}$"  title="Stringa formata da lettere minuscole, maiuscole minimo 4 caratteri massimo 30" required>
                        <datalist id="gusti">
                            <% for(String gusto : (ArrayList<String>)application.getAttribute("gusti")){%>
                            <option value="<%= gusto%>">
                                    <%}%>
                        </datalist>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;Numero Pezzi</label>
                    <div>
                        <input type="text" name="numeroPezzi" pattern="^\d{2,4}$" title="Numero formato da minimo 2 cifre massimo 4" required>
                    </div>
                </div>

                <div class="blocco-input-prodotto">

                    <h3> IMMAGINI PRODOTTO</h3>

                    <label>&nbsp;&nbsp;&nbsp;I Immagine</label>
                    <label for="immagine1" class="file-scelta" title="Immagine principale del prodotto obbligatoria">Scegli Un File</label>
                    <div>
                        <input type="file" id="immagine1" name="imagine1" accept="image/*" required>
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;II Immagine(non obbligatoria)</label>
                    <div>
                        <label for="immagine2" class="file-scelta" title="Immagine secondaria del prodotto non obbligatoria">Scegli Un File</label>
                        <input type="file" id="immagine2" name="imagine2" accept="image/*">
                    </div>
                </div>

                <div class="blocco-input-prodotto">
                    <label>&nbsp;&nbsp;&nbsp;III Immagine(non obbligatoria)</label>
                    <div>
                        <label for="immagine3" class="file-scelta" title="Immagine secondaria del prodotto non obbligatoria">Scegli Un File</label>
                        <input type="file" id="immagine3" name="imagine3" accept="image/*">
                    </div>
                </div>

            </div>

            <div class="blocco-input-prodotto-totale">
                <h3> DESCRIZIONE PRODOTTO </h3>
                <textarea disabled><%= prodotto.getDescrizione()%></textarea>
            </div>
        </div>

        <div id="blocco-bottone-inserimento">
            <input type="submit" value="INSERISCI SPECIFICA">
        </div>

        <input type="hidden" name="idProdotto" value="<%= prodotto.getiDProdotto()%>">
    </form>

</main>

</body>

<jsp:include page="footer.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

</html>
