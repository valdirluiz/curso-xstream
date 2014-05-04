package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class CompraTeste {

	@Test
	public void deveGerarCadaUmDosProdutosDeUmaCompra(){
		String xmlEsperado = 
		"<compra>\n"+
        "  <id>15</id>\n"+
        "  <produtos>\n"+
        "    <produto codigo=\"1587\">\n"+
        "      <nome>geladeira</nome>\n"+
        "      <preco>1000.0</preco>\n"+
        "      <descrição>geladeira duas portas</descrição>\n"+
        "    </produto>\n"+
        "    <produto codigo=\"1588\">\n"+
        "      <nome>ferro de passar</nome>\n"+
        "      <preco>100.0</preco>\n"+
        "      <descrição>ferro com vaporizador</descrição>\n"+
        "    </produto>\n"+
        "  </produtos>\n"+
        "</compra>";
		
		Compra compra = compraComGeladeiraEFerro();
		
		XStream xStream = xstreamParaCompraEProduto();
		
		String xmlGerado = xStream.toXML(compra);
		
		assertEquals(xmlEsperado,xmlGerado);
	}
	
	@Test
	public void deveSerializarColecoesImplicitas(){
		String xmlEsperado = 
			"<compra>\n"+
	        "  <id>15</id>\n"+
	        "  <produto codigo=\"1587\">\n"+
	        "    <nome>geladeira</nome>\n"+
	        "    <preco>1000.0</preco>\n"+
	        "    <descrição>geladeira duas portas</descrição>\n"+
	        "  </produto>\n"+
	        "  <produto codigo=\"1588\">\n"+
	        "    <nome>ferro de passar</nome>\n"+
	        "    <preco>100.0</preco>\n"+
	        "    <descrição>ferro com vaporizador</descrição>\n"+
	        "  </produto>\n"+
	        "</compra>";
			
			Compra compra = compraComGeladeiraEFerro();
			
			XStream xStream = xstreamParaCompraEProduto();
			xStream.addImplicitCollection(Compra.class, "produtos");
			
			String xmlGerado = xStream.toXML(compra);
			
			assertEquals(xmlEsperado,xmlGerado);
	}

	
	@Test
	public void deveGerarUmaCompraComCadaUmDosProdutosDoXml(){
		String xmlDeOrigem = 
		"<compra>\n"+
        "  <id>15</id>\n"+
        "  <produtos>\n"+
        "    <produto codigo=\"1587\">\n"+
        "      <nome>geladeira</nome>\n"+
        "      <preco>1000.0</preco>\n"+
        "      <descrição>geladeira duas portas</descrição>\n"+
        "    </produto>\n"+
        "    <produto codigo=\"1588\">\n"+
        "      <nome>ferro de passar</nome>\n"+
        "      <preco>100.0</preco>\n"+
        "      <descrição>ferro com vaporizador</descrição>\n"+
        "    </produto>\n"+
        "  </produtos>\n"+
        "</compra>";
		
		XStream xStream = xstreamParaCompraEProduto();
		
		Compra compraEsperada = compraComGeladeiraEFerro();
		Compra compraDeserializada = (Compra) xStream.fromXML(xmlDeOrigem);
		
		assertEquals(compraEsperada, compraDeserializada);
		
	}
	
	@Test
	public void deveSerializarDuasGeladeirasIguais(){
		Compra compra = compraDuasGeladeirasIGuais();
		String xmlEsperado = 
			"<compra>\n"+
	        "  <id>15</id>\n"+
	        "  <produtos>\n"+
	        "    <produto codigo=\"1587\">\n"+
	        "      <nome>geladeira</nome>\n"+
	        "      <preco>1000.0</preco>\n"+
	        "      <descrição>geladeira duas portas</descrição>\n"+
	        "    </produto>\n"+
	        "    <produto codigo=\"1587\">\n"+
	        "      <nome>geladeira</nome>\n"+
	        "      <preco>1000.0</preco>\n"+
	        "      <descrição>geladeira duas portas</descrição>\n"+
	        "    </produto>\n"+
	        "  </produtos>\n"+
	        "</compra>";
		
		XStream xStream = xstreamParaCompraEProduto();
		xStream.setMode(XStream.NO_REFERENCES);
		String xmlGerado = xStream.toXML(compra);
		assertEquals(xmlEsperado, xmlGerado);
	}
	
	@Test
	public void deveSerializarLivroEMusica(){
		String resultadoEsperado = 
			  "<compra>\n" 
	        + "  <id>15</id>\n"
	        + "  <produtos class=\"linked-list\">\n" 
	        + "    <livro codigo=\"1589\">\n"
	        + "      <nome>O Pássaro Raro</nome>\n"
	        + "      <preco>100.0</preco>\n"
	        + "      <descrição>dez histórias sobre a existência</descrição>\n"
	        + "    </livro>\n" 
	        + "    <musica codigo=\"1590\">\n"
	        + "      <nome>Meu Passeio</nome>\n"
	        + "      <preco>100.0</preco>\n"
	        + "      <descrição>música livre</descrição>\n"
	        + "    </musica>\n"
	        + "  </produtos>\n" 
	        + "</compra>";
		
		Compra compra = compraComLivroEMusica();
		XStream xStream = xstreamParaCompraEProduto();
		String xmlGerado = xStream.toXML(compra);
		
		assertEquals(resultadoEsperado, xmlGerado);
	}
	
	private Produto ferro(){
		return new Produto("ferro de passar", 100, "ferro com vaporizador", 1588);
	}
	
	private Produto geladeira(){
		return new Produto("geladeira", 1000, "geladeira duas portas", 1587);
	}
	
	private Compra compraComGeladeiraEFerro() {
		List<Produto> produtos = new ArrayList<Produto>();
		Produto geladeira = geladeira();
		Produto ferro = ferro();
		produtos.add(geladeira);
		produtos.add(ferro);
		
		Compra compra = new Compra(15, produtos);
		return compra;
	}
	
	private Compra compraDuasGeladeirasIGuais() {
		List<Produto> produtos = new ArrayList<Produto>();
		Produto geladeira = geladeira();
	
		produtos.add(geladeira);
		produtos.add(geladeira);
		
		Compra compra = new Compra(15, produtos);
		return compra;
	}
	
	private Compra compraComLivroEMusica(){
		Produto livro = new Livro("O Pássaro Raro", 100.0, "dez histórias sobre a existência", 1589);
		Produto musica = new Musica("Meu Passeio", 100.0, "música livre", 1590);
		List<Produto> produtos = new LinkedList<Produto>();
		produtos.add(livro);
		produtos.add(musica);
		
		return new Compra(15, produtos);
		
	}
	
	private XStream xstreamParaCompraEProduto() {
		XStream xStream = new XStream();
		xStream.alias("produto", Produto.class);
		xStream.alias("livro", Livro.class);
		xStream.alias("musica", Musica.class);
		xStream.alias("compra", Compra.class);
		xStream.aliasField("descrição", Produto.class, "descricao");
		xStream.useAttributeFor(Produto.class,"codigo");
		return xStream;
	}
	
}
