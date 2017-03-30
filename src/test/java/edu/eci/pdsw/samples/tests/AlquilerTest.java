/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.TipoItem;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerFactory;
//import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Calculo Multa:
 *
 * Frontera: CF1: Multas a devoluciones hechas en la fecha exacta (multa 0).
 * Clases de equivalencia: CE1: Multas hechas a devolciones realizadas en fechas
 * Clases de equivalencia: CE2: La devolución se realiza antes de la fecha
 * limite (multa 0). posteriores a la limite. (multa multa_diaria*dias_retraso)
 *
 *
 *
 */
public class AlquilerTest {

    public AlquilerTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void CF1Test() throws ExcepcionServiciosAlquiler {
        ServiciosAlquiler sa = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();

        Cliente cliente=new Cliente("Daniela Rodriguez",777736547, "3186688626", "Calle 163 #4-5", "cossio@hotmail.com");
        sa.registrarCliente(cliente);
        Item i1 = new Item(sa.consultarTipoItem(1), 44, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        sa.registrarItem(i1);

        Item item = sa.consultarItem(44);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);

        assertEquals("No se calcula correctamente la multa (0) "
                + "cuando la devolucion se realiza el dia limite.", 0, sa.consultarMultaAlquiler(44, java.sql.Date.valueOf("2005-12-25")));

    }
    
    @Test
    public void CE1Test() throws ExcepcionServiciosAlquiler {
        ServiciosAlquiler sa = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();

        Item i1 = new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        sa.registrarCliente(new Cliente("Juan Perez", 9843, "24234", "calle 123", "aa@gmail.com"));
        sa.registrarItem(i1);

        Item item = sa.consultarItem(55);
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);
        //prueba: 3 dias de retraso
        assertEquals("No se calcula correctamente la multa "
                + "cuando la devolucion se realiza varios dias despues del limite.", sa.valorMultaRetrasoxDia() * 3, sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-28")));
    }

    @Test
    public void CE2Test() throws ExcepcionServiciosAlquiler {
        ServiciosAlquiler sa = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();

        Item i1 = new Item(sa.consultarTipoItem(1), 66, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        sa.registrarCliente(new Cliente("Camilo Torres", 1018, "242334", "calle 123", "aa@gmail.com"));
        sa.registrarItem(i1);

        Item item = sa.consultarItem(66);

        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);
        //prueba: 2 días antes del límite
        long res= sa.consultarMultaAlquiler(66, java.sql.Date.valueOf("2005-12-23"));
        
        assertEquals("No debería haber multa si se entrega dentro del límite.", 0, res);

    }
}
