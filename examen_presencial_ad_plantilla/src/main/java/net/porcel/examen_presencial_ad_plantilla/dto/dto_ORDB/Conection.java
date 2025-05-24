/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORDB;

/**
 *
 * @author seek_
 */
public class Conection {
    String sgdb;
    String host;
    int port;

    public Conection(String sgdb, String host, int port) {
        this.sgdb = sgdb;
        this.host = host;
        this.port = port;
    }

    public String getSgdb() {
        return sgdb;
    }

    public void setSgdb(String sgdb) {
        this.sgdb = sgdb;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Conection{" + "sgdb=" + sgdb + ", host=" + host + ", port=" + port + '}';
    }
    
    
}
