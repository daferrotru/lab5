package edu.eci.pdsw.sampleprj.dao.mybatis.mappers;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.ItemRentado;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("id_cliente") int id); 
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("id_cliente")int id, 
            @Param("id_item")int idit, 
            @Param("fecha_inicio")Date fechainicio,
            @Param("fecha_fin")Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();
    
    /**
     * agrega un cliente a la base de datos
     * @param c Cliente nuevo
     */
    public void agregarCliente(@Param("n_cliente")Cliente c);
    
    public List<ItemRentado> consultarItemsCliente(@Param("id_cliente")int id_cliente);

    public void devolverItem(@Param("id_item")int iditem);

    public ItemRentado consultarItemCliente(@Param("id_item")int iditem);
}
