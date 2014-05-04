package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ProdutoTeste {

	@Test
	public void deveGerarXmlComNomePrecoDescricaoAdequados(){
		Produto geladeira = new Produto("Geladeira", 1000 , "Geladeira de duas portas", 1587);
		String xmlEsperado =
			"<produto codigo=\"1587\">\n"+
			"  <nome>Geladeira</nome>\n"+
		    "  <preco>R$ 1.000,00</preco>\n"+
		    "  <descrição>Geladeira de duas portas</descrição>\n"+
	        "</produto>";
		
		XStream xStream = new XStream();
		xStream.alias("produto", Produto.class);
		xStream.aliasField("descrição", Produto.class, "descricao");
		xStream.registerLocalConverter(Produto.class, "preco", new PrecoConverter());
		xStream.useAttributeFor(Produto.class, "codigo");
		String xmlGerado = xStream.toXML(geladeira);
		
		assertEquals(xmlEsperado,xmlGerado);
	}
	
}
