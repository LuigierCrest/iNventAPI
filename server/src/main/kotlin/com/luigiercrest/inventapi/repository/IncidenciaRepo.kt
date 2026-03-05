package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.IncidenciaDTO
import com.luigiercrest.inventapi.models.entities.IncidenciaEntity
import com.luigiercrest.inventapi.models.entities.Incidencias
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

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
    // GET por centro y dispositivo
    suspend fun getIncidenciasByCentroAndDispositivo(idCentro: Int, idDispositivo: Int): List<IncidenciaDTO> = dbQuery {
        IncidenciaEntity.find { (Incidencias.idCentro eq idCentro) and (Incidencias.idDispositivo eq idDispositivo) }
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
            println("Error al parsear fechaReporte: ${e.message}")
            emptyList<IncidenciaDTO>()
        }

    }
    //GET por fecha cierre
    suspend fun getIncidenciasByFechaCierre(fechaCierre: String): List<IncidenciaDTO> = dbQuery {
        if (fechaCierre.isBlank() || fechaCierre.equals("null", ignoreCase = true)) {
            IncidenciaEntity.find { Incidencias.fechaCierre.isNull() }
                .map { it.toDTO() }
        } else {
            try {
                val fecha2 = LocalDate.parse(fechaCierre)
                IncidenciaEntity.find { Incidencias.fechaCierre eq fecha2 }
                    .map { it.toDTO() }
            } catch (e: Exception) {
                println("LOG - Error al parsear fechaCierre: ${e.message}")
                emptyList<IncidenciaDTO>()
            }
        }


    }
    //POST crear incidencia
    suspend fun addIncidencia(incidencia: IncidenciaDTO) = dbQuery {
        IncidenciaEntity.new {
            this.idCentro = incidencia.idCentro
            this.idDispositivo = incidencia.idDispositivo
            this.idServicioTecnico = incidencia.idServicioTecnico
            this.dniResponsable = incidencia.dniResponsable
            this.descripcion = incidencia.descripcion
            this.estado = incidencia.estado
        }.toDTO()
    }
    //PUT actualizar incidencia por id
    suspend fun updateIncidencia(id: Int, incidencia: IncidenciaDTO): Boolean = dbQuery {
        IncidenciaEntity.findById(id) ?: return@dbQuery false
        val rows = Incidencias.update({ Incidencias.id eq id }) {
            it[this.idCentro] = incidencia.idCentro
            it[this.idDispositivo] = incidencia.idDispositivo
            it[this.idServicioTecnico] = incidencia.idServicioTecnico
            it[this.dniResponsable] = incidencia.dniResponsable
            it[this.descripcion] = incidencia.descripcion
            it[this.fechaCierre] = incidencia.fechaCierre
            it[this.estado] = incidencia.estado
        }
        rows > 0
    }
    //PUT actualizar el estado de incidencia por id
    suspend fun updateEstadoIncidencia(id: Int, estado: String): Boolean = dbQuery {
        IncidenciaEntity.findById(id) ?: return@dbQuery false
        val rows = Incidencias.update({ Incidencias.id eq id }) {
            it[this.estado] = estado
        }
        rows > 0
    }
    //DELETE eliminar incidencia por id
    suspend fun deleteIncidencia(id: Int): Boolean = dbQuery {
        val incidenciaToDelete = IncidenciaEntity.findById(id) ?: return@dbQuery false
        incidenciaToDelete.delete()
        true
    }

}