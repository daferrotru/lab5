/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerFactory;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "AlquilerItems")
@SessionScoped
public class AlquilerItemsBean implements Serializable {

    ServiciosAlquiler sp = ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
    private Cliente newClient;
    private String nombreEsperado;
    private long idEsperado;
    private String direccionEsperada;
    private String emailEsperado;
    private String telefonoEsperado;
    private Cliente selectedClient;

    public AlquilerItemsBean() {
    }

    public List<Cliente> getClientes() throws ExcepcionServiciosAlquiler {

        return sp.consultarClientes();
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

    public Cliente getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Cliente selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void anadirCliente() throws ExcepcionServiciosAlquiler {
        newClient = new Cliente(nombreEsperado, idEsperado, telefonoEsperado, direccionEsperada, emailEsperado);
        sp.registrarCliente(newClient);
    }

    public String moveToClientItemRegistration() {
        return "RegistroClienteItem";
    }

    public String moveToClientRegistration() {
        return "RegistroClientes";
    }

    //--------------------------------------
    //Servicios Alquiler
    //--------------------------------------
    public List<ItemRentado> getItemsRentados() throws ExcepcionServiciosAlquiler {
        return sp.consultarItemsCliente(selectedClient.getDocumento());
    }

    public long getMultaItem(ItemRentado item) throws ExcepcionServiciosAlquiler {
        if(item!=null) {
            Item temp = item.getItem();
            if(temp!=null) return sp.consultarMultaAlquiler(temp.getId(), item.getFechafinrenta());
        }
        throw new ExcepcionServiciosAlquiler("El item rentado es nulo, inconsistencia en la base de datos compartida");
    }

    private Date start;
    private Date end;

    private Item selectedItem;

    public void setStartDate(java.util.Date start) {
        this.start = new java.sql.Date(start.getTime());
    }

    public void setSelectedItem(Item i) {
        this.selectedItem = i;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    private int input;

    public void setInput(int input) throws ExcepcionServiciosAlquiler {
        this.input = input;
    }

    public int getInput() {
        return input-=input;
    }

    public void search() throws ExcepcionServiciosAlquiler {
        selectedItem = sp.consultarItem(input);
    }

    public java.util.Date getStartDate() {
        return start == null ? new java.util.Date() : new java.util.Date(start.getTime());
    }

    public void setEndDate(java.util.Date end) {
        this.end = new java.sql.Date(end.getTime());
    }

    public java.util.Date getEndDate() {
        return end == null ? new java.util.Date() : new java.util.Date(end.getTime());
    }

    public String moveBackOk() throws ExcepcionServiciosAlquiler {
        if (selectedItem != null) {
            int numDays = Days.daysBetween(new LocalDate(start.getTime()), new LocalDate(end.getTime())).getDays();
            sp.registrarAlquilerCliente(start, selectedClient.getDocumento(), selectedItem, numDays);
        }
        return "RegistroClienteItem";
    }
    
    public void devolverItem() throws ExcepcionServiciosAlquiler{
        sp.registrarDevolucion(input);
    }

    public List<Item> getDisponibles() {
        return sp.consultarItemsDisponibles();
    }
}
