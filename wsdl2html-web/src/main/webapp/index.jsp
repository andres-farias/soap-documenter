<%--
  Created by Andrés Farías@Globant.
  Date: 4/8/20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Generador de documentación HTML desde WSDL</title>

    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"/>
</head>

<body>

<h1>Generador de documentación HTML desde WSDL</h1>

<h2>Introducción</h2>

<p>Esta aplicación web permite transformar un WSDL disponible en una URL, en HTML basado en su documentación.</p>

<h2>Uso</h2>

<p>El uso de la aplicación, por el momento, se realiza enviando una petición HTTP de tipo GET a la URL relativa <b>/transformFromFile</b>
    con la URL del WSDL que se desea transformar, en un parámetro de la consulta (<i>query string parameter</i> en
    inglés) definido como <b>wsdl</b>.</p>

<p>Por ahora, la transformación se hace utilizando lo definido en el archivo XSTL por defecto.</p>

<p>El resultado es embebido en la respuesta HTTP como un elemento <b>txt/html</b>, permitiendo el despliegue inmediato
    del resultado.

<h3>Ejemplo de uso</h3>

Considere el servicio web para realizar operaciones de suma y multiplicación con números, ubicado en el siguiente <a
        href="http://www.dneonline.com/calculator.asmx">end-point</a>. El WSDL de este servicio se encuentra ubicado en
la URL <a href="http://www.dneonline.com/calculator.asmx?wsdl">http://www.dneonline.com/calculator.asmx?wsdl</a>.

<p>Para este end-point se debe enviar una petición HTTP GET como sigue:</p>

<b>http://host/transformFromFile?wsdl=http://www.dneonline.com/calculator.asmx?wsdl</b>

<!-- Bootstrap's Jquery required libraries -->
<script type="application/javascript" src="https://code.jquery.com/jquery-3.4.1.slim.min.js"/>
<script type="application/javascript" src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"/>
<script type="application/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"/>

</body>
</html>
