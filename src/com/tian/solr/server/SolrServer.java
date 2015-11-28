package com.tian.solr.server;

import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import com.tian.solr.model.Item;

public class SolrServer {

	public static final String URL = "http://localhost:8080/solr";

	/**
	 * @param url
	 * 2015年10月30日 下午10:35:38
	 * @author:Tian_dd
	 * @blog: tian-dd.top
	 */
	public static HttpSolrServer connectServer(String url) throws Exception {

		HttpSolrServer server = new HttpSolrServer(url);
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
		server.setConnectionTimeout(5000); // 5 seconds to establish TCP
		server.setParser(new XMLResponseParser()); // binary parser is used by
		server.setSoTimeout(1000); // socket read timeout
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		server.setAllowCompression(true);

		return server;

	}
	
	/**
	 * 2015年10月30日 下午10:36:09
	 * @author:Tian_dd
	 * @blog: tian-dd.top
	 */
	public static void solrOrder() throws Exception {

		HttpSolrServer server = SolrServer.connectServer(URL);
		SolrQuery query = new SolrQuery();
	    query.setQuery("id:*e*");// 多条件使用空格分隔  
		query.setFilterQueries("name:*是*");
		query.setQuery("*:*");
		query.setHighlightSimplePre("<em>");  
		query.setHighlightSimplePost("</em>");  
		query.addHighlightField("name");  
		query.setHighlight(true);  
		query.setHighlightFragsize(72);  
		query.setHighlightSnippets(3);  
		QueryResponse rsp = server.query(query);
		List<Item> beans = rsp.getBeans(Item.class);
		System.out.println(beans.size());
		
		for (Item item : beans) {
			System.out.println(item.getId());
			System.out.println(item.getName());
		}
	}
	
	/**
	 * 2015年10月30日 下午10:36:38
	 * @author:Tian_dd
	 * @blog: tian-dd.top
	 */
	public static void addBean () throws Exception{
		
		HttpSolrServer server = SolrServer.connectServer(URL);
		
		Item item = new Item();
		item.setId("three");
		item.setName("人最幸福的事情是家庭和睦");
		server.addBean(item);
		
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.setAction(ACTION.COMMIT, false, false);
		UpdateResponse uRequest = updateRequest.process(server);
		
	}
	
	/**
	 * 2015年10月30日 下午10:36:45
	 * @author:Tian_dd
	 * @blog: tian-dd.top
	 */
	public static void deleteBean(String id) throws Exception{
		
		HttpSolrServer server = SolrServer.connectServer(URL);
		
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.deleteById(id);
		updateRequest.setAction(ACTION.COMMIT, false, false);
		UpdateResponse uRequest = updateRequest.process(server);
		
	}

}
