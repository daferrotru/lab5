/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "AlquilerItems")
@SessionScoped
public class AlquilerItemsBean implements Serializable {

    ServiciosAlquiler sp = ServiciosAlquiler.getInstance();
    private List<RegistroClientes>addClient;
    private String nombreEsperado;
    private long idEsperado;
    private String direccionEsperada;
    private String emailEsperado;
    private String telefonoEsperado;    
    

    public AlquilerItemsBean() {
    }
    
     public List<Cliente> getClientes() throws ExcepcionServiciosAlquiler{
        return sp.consultarClientes();
     }

    public List<RegistroClientes> getAddClient() {
        return addClient;
    }

    public void setAddClient(List<RegistroClientes> addClient) {
        this.addClient = addClient;
    }

    public String getNombreEsperado() {
        return nombreEsperado;
    }

    public void setNombreEsperado(String nombreEsperado) {
        this.nombreEsperado = nombreEsperado;
    }

    public long getIdEsperado() {
        return idEsperado;
    }

    public void setIdEsperado(long idEsperado) {
        this.idEsperado = idEsperado;
    }

    public String getEmailEsperado() {
        return emailEsperado;
    }

    public void setEmailEsperado(String emailEsperado) {
        this.emailEsperado = emailEsperado;
    }

    public String getTelefonoEsperado() {
        return telefonoEsperado;
    }

    public void setTelefonoEsperado(String telefonoEsperado) {
        this.telefonoEsperado = telefonoEsperado;
    }

    public String getDireccionEsperada() {
        return direccionEsperada;
    }

    public void setDireccionEsperada(String direccionEsperada) {
        this.direccionEsperada = direccionEsperada;
    }
    

    public void anadirCliente() throws ExcepcionServiciosAlquiler{
        Cliente newClient=new Cliente(nombreEsperado, idEsperado, telefonoEsperado, direccionEsperada, emailEsperado);
        sp.registrarCliente(newClient);
        
    }
     
    public String moveToClientItemRegistration(){return "RegistroClienteItem";}
     

}
