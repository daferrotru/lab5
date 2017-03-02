/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *addItems1: Crea un cliente
 * 
 */
public class ClientesTest {

    public ClientesTest() {
    }
    
    @Before
    public void setUp() {
    }
    
  
    @Test
    public void additems1() throws ExcepcionServiciosAlquiler{
    	ServiciosAlquiler sa=ServiciosAlquilerItemsStub.getInstance();
        Cliente cliente=new Cliente("Daniel Rodriguez",101236547, "3186688626", "CALLE 63 #45-86", "daferrotru@hotmail.com");
        sa.registrarCliente(cliente);
        assertEquals(101236547, sa.consultarClientes().get(sa.consultarClientes().size()-1).getDocumento());
    }   
    
    
    
    
    
    
    
}
