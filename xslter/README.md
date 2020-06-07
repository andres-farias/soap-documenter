# XSLTer

## Descripción

**XSLTer** es un programa escrito en Java, desarrollado por [Andrés Farías](mailto:a.farias@globant.com) de [Globant](https://wwww.goblant.com), que permite transformar un archivo WSDL en un archivo HTML que presenta la documentación existente en el WSDL en un formato más conveniente para la documentación.

## Uso

**XSLTer** es una aplicación *Java*, empaquetada en un archivo de extensión `jar`, y por ende, para poder ejecutarlo, es necesario contar con Java 1.8.

```elixir
java -jar xslter.jar [options] miArchivoWSDL.wsdl miArchivoSalida.html
```

### Ayuda (--h)

Para acceder a la ayuda de la aplicación, que explica cómo se usan los distintos argumentos, se utiliza la opción ````--help```` o ````-h````:
```elixir
java -jar xslter.jar -h
```

O bien:
```shell
java -jar xslter.jar -help
```

El resultado esperado sería:

```
usage: XLSTer [-h] [-x XSLT] wsdl_file html_out_file

anXSLTFile.xslt  myWsdl.wsdl anOutputFile.html

positional arguments:
  wsdl_file              The WSDL file to be converted to HTML
  html_out_file          The HTML file where the output will be directed

named arguments:
  -h, --help             show this help message and exit
  -x XSLT, --xslt XSLT   The XSLT file that will transforms an WSDL file
```

## Build and configuration

### Building the JAR

The software is build into a JAR file. This is done with the gradle task `jar`. Keep in mind that this package will
 not contain any dependency, and therefore, in orde to be able to execute the program later, you should include those
  dependencies on your classpath with the `-cp` option, followed by all the JAR files missing.
```
%> ./gradlew jar
```

If you don't want to have to provide an additional path to your classpath, you can use the `shadowJar` task provided
 with an extended plugin. In this case all the dependencies are decompiled and injected to the final `jar`, saving
  you to extend your classpath.  
```
%> ./gradlew shadowJar
```

In both cases the output directory for the `jar` file is the `build/libs` folder.

### Specific XSLT file (-x o --xslt)

**XSLTer** is packaged with a transformation `XSL` [file](src/main/resources/wsdl-viewer.xsl) that is used as default
 to generate an `HTML` documentation file from a `WSDL` files. This default behaviour can be overridden with the ```-x
 ``` o ```xslt``` option, as it's showed on the following example:

```shell
java -jar xslter.jar -xslt myTransformationFile.xsl mywsdl.wsdl output.html
```

## Testing

Once you have built the `jar` (lets assume with the `shadoJar` task), you will be able to test the program with an
 `WSDL` [file](src/test/resources/salesforce-cliente-creacion.wsdl) embedded on the resource's test folder. You can
  try the software with the following command (from the project's root folder):

  ```shell
  %> java -jar xslter.jar src/test/resources/salesforce-cliente-creacion.wsdl output.html
  ```
 