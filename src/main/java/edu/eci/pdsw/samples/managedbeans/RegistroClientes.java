/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.ItemRentado;
import java.util.ArrayList;

/**
 *
 * @author daferrotru
 */
public class RegistroClientes {
    private Cliente client;
    
    public RegistroClientes(Cliente client) {
        this.client=client;
    }

    public Cliente getClient() {
        return client;
    }

    public void setClient(Cliente client) {
        this.client = client;
    }
    
    public String getClientName(){return client.getNombre();}
    
    public long getClientId(){return client.getDocumento();}
    
    public String getClientAddress(){return client.getDireccion();}
    
    public String getClientPhone(){return client.getTelefono();}
    
    public String getClientEmail(){return client.getEmail();}
    
    public ArrayList<ItemRentado> getCLientItems(){return client.getRentados();}
    
    
    
    
    
    
}
