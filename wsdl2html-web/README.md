# WSDL to HTML -- The Web Application version

This module implements a web application that allows you to dynamically generate an `HTML` document with the
 documentation extracted from a source `WSDL` file.
 
The generation of the documentation is performed via an XML transformation specification (technology known [XSTL
](https://en.wikipedia.org/wiki/XSLT), acronym of _Extensible Stylesheet Language Transformations_) which is embedded
 on the web application.

The current XSTL file, [wsdl-viewer.xsl](./src/main/resources/wsdl-viewer.xsl) version 3.1.01 is property of Tomi
 Vanek and can be obtained from [here](cl.bice.apps.wsdl2html.http://tomi.vanek.sk/xml/wsdl-viewer.xsl). The transformation was inspired by
  the article _[Uche Ogbuji: WSDL processing with XSLT](cl.bice.apps.wsdl2html.http://www-106.ibm.com/developerworks/library/ws-trans/index.html)_.
 
On later releases it's planned to add the following functionalities:
 1. Allows the user to specify an alternative `XSTL` file to perform the HTML documentation generation.
 
## Use

The web application provides the _HTML documentation generation from a WSDL file_ functionality through an `HTTP GET`
 endpoint  `/transformFromFile` providing a `WSDL`'s URL on the _query string parameter_ named `wsdl`.

The generated HTML file es embedded on the HTTP response as an ```txt/html``` content, allowing the document to be
 rendered directly onto the browser upon the reception of the response.

### Use example

As an example, please consider the web service available at this [end-point](cl.bice.apps.wsdl2html.http://www.dneonline.com/calculator.asmx
) which allows one to perform basic math operations, such as to add and multiply two numbers. The `WSDL` file
 describing this `SOAP` web service is located at this URL 
 [cl.bice.apps.wsdl2html.http://www.dneonline.com/calculator.asmx?wsdl](cl.bice.apps.wsdl2html.http://www.dneonline.com/calculator.asmx?wsdl). 

Assuming this web application (WSDL2HTML) on localhost, in order to generate the HTML documentation the following
 `HTTP request` is to be submitted:

```
http://localhost/transformFromFile?wsdl=http://www.dneonline.com/calculator.asmx?wsdl
```

The reponse to that request has been stored and it's available [here](src/test/resources/output.html), only for
 exemplification purposes.
 
## HELP

The default landing (path `/`) provides helps on using the application. 
