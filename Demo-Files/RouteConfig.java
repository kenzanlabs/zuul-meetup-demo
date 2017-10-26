package com.netflix.zuul.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RouteConfig {

	private final String name;
	private final String path;
	private final String newPath;
	private final String host;
	private final String location;
	private final int port;
	private final String protocol;

	@JsonCreator
	public RouteConfig(@JsonProperty("name") String name,
					   @JsonProperty("path") String path,
					   @JsonProperty("newPath") String newPath,
					   @JsonProperty("host") String host,
					   @JsonProperty("location") String location,
					   @JsonProperty("port") int port,
					   @JsonProperty("protocol") String protocol) {


		this.name = name;
		this.path = path;
		this.newPath = newPath;
		this.host = host;
		this.location = location;
		this.port = port;
		this.protocol = protocol;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getNewPath() {
		return newPath;
	}

	public String getHost() {
		return host;
	}

	public String getLocation() {
		return location;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	@Override
	public String toString() {
		return "RouteConfig{" +
			"name='" + name + '\'' +
			", path='" + path + '\'' +
			", newPath='" + newPath + '\'' +
			", host='" + host + '\'' +
			", location='" + location + '\'' +
			", port='" + port + '\'' +
			", protocol='" + protocol + '\'' +
			'}';
	}
}
