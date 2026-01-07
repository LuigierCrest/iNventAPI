package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.IncidenciaDTO
import com.luigiercrest.inventapi.models.entities.IncidenciaEntity
import com.luigiercrest.inventapi.models.entities.Incidencias
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class IncidenciaRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET all
    suspend fun getAllIncidencias(): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getIncidenciaById(id: Int): IncidenciaDTO? = dbQuery {
        IncidenciaEntity.findById(id)?.toDTO()
    }
    //GET por centro
    suspend fun getIncidenciasByCentro(idCentro: Int): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { Incidencias.idCentro eq idCentro }
            .map { it.toDTO() }
    }
    //GET por dispositivo
    suspend fun getIncidenciasByDispositivo(idDispositivo: Int): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { Incidencias.idDispositivo eq idDispositivo }
            .map { it.toDTO() }
    }
    //GET por servicio técnico
    suspend fun getIncidenciasByServicioTecnico(idServicioTecnico: Int): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { Incidencias.idServicioTecnico eq idServicioTecnico }
            .map { it.toDTO() }
    }
    //GET por responsable
    suspend fun getIncidenciasByResponsable(dniResponsable: String): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { Incidencias.dniResponsable eq dniResponsable }
            .map { it.toDTO() }
    }
    //GET por estado
    suspend fun getIncidenciasByEstado(estado: String): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { Incidencias.estado eq estado }
            .map { it.toDTO() }
    }
    //GET por fecha reporte
    suspend fun getIncidenciasByFechaReporte(fechaReporte: String): List<IncidenciaDTO> = dbQuery {
        try {
            val fecha1 = LocalDate.parse(fechaReporte)
            IncidenciaEntity.find { Incidencias.fechaReporte eq fecha1 }
                .map { it.toDTO() }
        } catch (e: Exception) {
            emptyList<IncidenciaDTO>()
        }

    }
    //GET por fecha cierre
    suspend fun getIncidenciasByFechaCierre(fechaCierre: String): List<IncidenciaDTO> = dbQuery {
        try {
            val fecha2 = LocalDate.parse(fechaCierre)
            IncidenciaEntity.find { Incidencias.fechaCierre eq fecha2 }
                .map { it.toDTO() }
        } catch (e: Exception) {
            emptyList<IncidenciaDTO>()
        }

    }
    //POST crear incidencia
    suspend fun addIncidencia(incidencia: IncidenciaDTO) = dbQuery {
        IncidenciaEntity.new(incidencia.idIncidencia) {
            this.idCentro = incidencia.idCentro
            this.idDispositivo = incidencia.idDispositivo
            this.idServicioTecnico = incidencia.idServicioTecnico
            this.dniResponsable = incidencia.dniResponsable
            this.descripcion = incidencia.descripcion
            this.estado = incidencia.estado
            this.fechaReporte = incidencia.fechaReporte
            this.fechaCierre = incidencia.fechaCierre
        }.toDTO()
    }
    //PUT actualizar incidencia por id
    suspend fun updateIncidencia(id: Int, incidencia: IncidenciaDTO): Boolean = dbQuery {
        val incidenciaToUpdate = IncidenciaEntity.findById(id) ?: return@dbQuery false
        incidenciaToUpdate.idCentro = incidencia.idCentro
        incidenciaToUpdate.idDispositivo = incidencia.idDispositivo
        incidenciaToUpdate.idServicioTecnico = incidencia.idServicioTecnico
        incidenciaToUpdate.dniResponsable = incidencia.dniResponsable
        incidenciaToUpdate.descripcion = incidencia.descripcion
        incidenciaToUpdate.estado = incidencia.estado
        incidenciaToUpdate.fechaReporte = incidencia.fechaReporte
        incidenciaToUpdate.fechaCierre = incidencia.fechaCierre
        true
    }
    //PUT actualizar estado de incidencia por id
    suspend fun updateEstadoIncidencia(id: Int, estado: String): Boolean = dbQuery {
        val incidenciaToUpdate = IncidenciaEntity.findById(id) ?: return@dbQuery false
        incidenciaToUpdate.estado = estado
        true
    }
    //DELETE eliminar incidencia por id
    suspend fun deleteIncidencia(id: Int): Boolean = dbQuery {
        val incidenciaToDelete = IncidenciaEntity.findById(id) ?: return@dbQuery false
        incidenciaToDelete.delete()
        true
    }

}