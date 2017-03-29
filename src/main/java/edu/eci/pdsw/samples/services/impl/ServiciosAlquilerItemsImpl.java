package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.pdsw.sampleprj.dao.ClienteDAO;
import edu.eci.pdsw.sampleprj.dao.ItemDAO;
import edu.eci.pdsw.sampleprj.dao.PersistenceException;
import edu.eci.pdsw.sampleprj.dao.TipoItemDAO;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.entities.TipoItem;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.Days;
import org.mybatis.guice.transactional.Transactional;

/**
 *
 * @author hcadavid
 */
@Singleton
public class ServiciosAlquilerItemsImpl implements ServiciosAlquiler {

    private static final int MULTA_DIARIA = 5000;

    @Inject
    private ItemDAO daoItem;

    @Inject
    private ClienteDAO daoCliente;

    @Inject
    private TipoItemDAO daoTipoItem;

    @Override
    public int valorMultaRetrasoxDia() {
        return MULTA_DIARIA;
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.load((int) docu);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el Cliente " + docu, ex);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.consultarItemsCliente((int) idcliente);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el Cliente " + idcliente, ex);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.loadAll();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar la lista de clientes", ex);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return daoItem.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item " + id, ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() {
        try {
            return daoItem.loadAvailable();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosAlquilerItemsImpl.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<>();
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        java.util.Date fin = getFechaInicioRenta(iditem).getFechafinrenta();
        if (fechaDevolucion.before(fin)) {
            return 0;
        }
        return Days.daysBetween(new org.joda.time.LocalDate(fin.getTime()), new org.joda.time.LocalDate(fechaDevolucion.getTime())).getDays() * valorMultaRetrasoxDia();
    }

    private ItemRentado getFechaInicioRenta(int iditem) throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.consultarItemCliente(iditem);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item rentado " + iditem, ex);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return daoTipoItem.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item " + id, ex);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return daoTipoItem.loadAll();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de items", ex);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            LocalDate ld = date.toLocalDate();
            LocalDate ld2 = ld.plusDays(numdias);
            daoCliente.agregarItemRentadoACliente((int) docu, item.getId(), date, Date.valueOf(ld2));
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosAlquilerItemsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrarCliente(Cliente p) throws ExcepcionServiciosAlquiler {
        try {
            daoCliente.save(p);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al registrar el Cliente " + p.getDocumento(), ex);
        }
    }

    @Override
    public void registrarDevolucion(int iditem) throws ExcepcionServiciosAlquiler {
        try {
            daoCliente.returnItem(iditem);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al devolver el item " + iditem, ex);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        consultarItem(id).setTarifaxDia(tarifa);
    }

    @Override
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        //FALTA
        try {
            daoItem.save(i);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al registrar item " + i, ex);
        }

    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
