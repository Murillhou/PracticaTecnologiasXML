<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*" />
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:template match="/">
		<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
			<head>
				<meta http-equiv="Content-Style-Type" content="text/css" />
				<link rel="stylesheet" href="estilo.css" type="text/css" />
				<title> Noticias </title>
				
			</head>
			<body>
				<xsl:for-each select="noticias/articulo">
					<xsl:sort select="titulo" order="ascending" />
					<div class="separador">
					<table align="center">
						<tr class="titulo">
							<td colspan="2">
								<xsl:value-of select="titulo"/>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:value-of select="fecha" />
							</td>
							<td>
								<xsl:value-of select="categoria" />
							</td>
						</tr>
						<tr class="texto">
							<td colspan="2">
								<xsl:value-of select="descripcion" />
							</td>
						</tr>
					</table>
					</div>
					<br/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>