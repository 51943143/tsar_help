package com.tsar.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServerMetricLoadService {
	private static Logger logger = LoggerFactory.getLogger(ServerMetricLoadService.class);
	
	@Autowired
	private Dao dao;

	@Value("${tsar.query_frequency}")
	private int query_frequency;

	public int getQuery_frequency() {
		return query_frequency;
	}

	@Value("${tsar.dest_url}")
	private String dest_url = "";

	private String[] parseDestUrl() {
		if (Strings.isEmpty(this.dest_url))
			throw Lang.makeThrow("Error:  dest_url is null.......................");
		return this.dest_url.split(";");
	}

	public List<String> getDestIp() {
		List<String> iplist = new ArrayList<String>();
		String[] destlist = this.parseDestUrl();
		for (String dest : destlist) {
			String s = dest.substring(0, dest.lastIndexOf(":"));
			if (s.indexOf("//") > -1) {
				s = s.substring(s.indexOf("//") + 2);
			}
			s=s.replace(".", "_");
			iplist.add(s);
		}
		return iplist;

	}

	public List<ServerMetric> loadAndSave() {
		List<ServerMetric> newlist = new ArrayList<ServerMetric>();
		String[] urlList = this.parseDestUrl();
		for (String url : urlList) {
			try{
			newlist.addAll(this.loadAndSave(url));
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		return newlist;
	}

	@Transactional
	public List<ServerMetric> loadAndSave(String destUrl) {
		List<ServerMetric> list = this.loadDatas(destUrl);
		return this.saveAndNotify(list);
	}

	private List<ServerMetric> saveAndNotify(List<ServerMetric> list) {
		List<ServerMetric> newlist = new ArrayList<ServerMetric>();
		for (ServerMetric m : list) {
			if (this.save(m)) {
				newlist.add(m);
			}
		}
		return newlist;
	}

	private boolean save(ServerMetric m) {
		if (this.dao.fetchx(ServerMetric.class, m.getDest_url(), m.getTime()) == null) {
			this.dao.insert(m);
			return true;
		}
		return false;
	}

	private List<ServerMetric> loadDatas(String destUrl) {
		Date d = new Date();
		Response r = Http.get(destUrl);
		// System.out.println("loadDatas response:" + r.getContent());
		List<ServerMetric> list = Json.fromJsonAsList(ServerMetric.class, r.getContent());
		for (ServerMetric s : list) {
			s.setDest_url(destUrl);
			s.setLastUpdateTime(d);
		}
		return list;
	}

}
