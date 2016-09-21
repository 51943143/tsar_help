package com.tsar.server;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("t_server_metric")
@PK({ "dest_url", "time" })
public class ServerMetric {

	@Column
	private String dest_url;
	@Column
	private String time;
	@Column
	private String cpu;
	@Column
	private String mem;
	@Column
	private String tcp_retran;
	@Column
	private String traffic_in;
	@Column
	private String traffic_out;
	@Column
	private String io_sda;
	@Column
	private String load_rate;
	@Column
	private Date lastUpdateTime;

	
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getDest_url() {
		return dest_url;
	}

	public void setDest_url(String dest_url) {
		this.dest_url = dest_url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getTcp_retran() {
		return tcp_retran;
	}

	public void setTcp_retran(String tcp_retran) {
		this.tcp_retran = tcp_retran;
	}

	public String getTraffic_in() {
		return traffic_in;
	}

	public void setTraffic_in(String traffic_in) {
		this.traffic_in = traffic_in;
	}

	public String getTraffic_out() {
		return traffic_out;
	}

	public void setTraffic_out(String traffic_out) {
		this.traffic_out = traffic_out;
	}

	public String getIo_sda() {
		return io_sda;
	}

	public void setIo_sda(String io_sda) {
		this.io_sda = io_sda;
	}

	public String getLoad_rate() {
		return load_rate;
	}

	public void setLoad_rate(String load_rate) {
		this.load_rate = load_rate;
	}



}
