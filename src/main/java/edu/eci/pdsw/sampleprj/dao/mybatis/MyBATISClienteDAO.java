/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.sampleprj.dao.ClienteDAO;
import edu.eci.pdsw.sampleprj.dao.PersistenceException;
import edu.eci.pdsw.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.ItemRentado;
import java.util.Date;
import java.util.List;

/**
 *
 * @author daferrotru
 */
public class MyBATISClienteDAO implements ClienteDAO {

    @Inject
    private ClienteMapper clienteMapper;

    @Override
    public void save(Cliente c) throws PersistenceException {
        try {
            clienteMapper.agregarCliente(c);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al registrar el cliente" + c.toString(), e);
        }

    }

    @Override
    public Cliente load(int id) throws PersistenceException {
        try {
            return clienteMapper.consultarCliente(id);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar el cliente " + id, e);
        }
    }

    @Override
    public List<Cliente> loadAll() throws PersistenceException {
        try {
            return clienteMapper.consultarClientes();
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar la lista de clientes", e);
        }
    }

    @Override
    public void agregarItemRentadoACliente(int id_cliente, int id_item, Date fechaInicio, Date fechaFin) throws PersistenceException {
        try {
            clienteMapper.agregarItemRentadoACliente(id_cliente, id_item, fechaInicio, fechaFin);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al agregar Item al cliente", e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(int id_cliente) throws PersistenceException {
        try {
            return clienteMapper.consultarItemsCliente(id_cliente);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar items del cliente " + id_cliente, e);
        }
    }

    @Override
    public void returnItem(int iditem) throws PersistenceException {
        try {
            clienteMapper.devolverItem(iditem);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al devolver el item " + iditem, e);
        }
    }

}
