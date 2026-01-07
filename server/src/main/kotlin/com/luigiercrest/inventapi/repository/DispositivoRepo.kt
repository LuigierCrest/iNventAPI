package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.models.entities.DispositivoEntity
import com.luigiercrest.inventapi.models.entities.Dispositivos
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DispositivoRepo {

    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos los dispostivos
    suspend fun getAllDispositivos(): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getDispositivoById(id: Int): DispositivoDTO? = dbQuery {
        DispositivoEntity.findById(id)?.toDTO()
    }
    //GET por centro
    suspend fun getDispositivosByCentro(idCentro: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idCentro eq idCentro }
            .map { it.toDTO() }
    }
    //GET marcaModelo
    suspend fun getDispositivosByMarcaModelo(marcaModelo: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.marcaModelo eq marcaModelo }
            .map { it.toDTO() }
    }
    //GET por actualización
    suspend fun getDispositivosByUltimaActualizacion(ultimaActualizacion: String): List<DispositivoDTO> = dbQuery {
        try {
            // Intentar parsear la fecha a LocalDate
            val fecha = LocalDate.parse(ultimaActualizacion)
            DispositivoEntity.find { Dispositivos.ultimaActualizacion eq fecha }.map { it.toDTO() }
        } catch (e: Exception) {
            // Si la fecha está mal formada, devuelve lista vacía
            emptyList<DispositivoDTO>()
        }
    }
    //GET por estado
    suspend fun getDispositivosByEstado(idEstado: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idEstado eq idEstado }
            .map { it.toDTO() }
    }
    //GET por categoria
    suspend fun getDispositivosByCategoria(idCategoria: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idCategoria eq idCategoria }
            .map { it.toDTO() }
    }
    //GET por ubicación
    suspend fun getDispositivosByUbicacion(idUbicacion: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idUbicacion eq idUbicacion }
            .map { it.toDTO() }
    }
    //GET por uso
    suspend fun getDispositivosByUso(idUso: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idUso eq idUso }
            .map { it.toDTO() }
    }
    //GET por asignación
    suspend fun getDispositivosByAsignacion(idAsignacion: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idAsignacion eq idAsignacion }
            .map { it.toDTO() }
    }
    //POST crear dispositivo
    suspend fun addDispositivo(dispositivo: DispositivoDTO) = dbQuery {
        DispositivoEntity.new(dispositivo.idDispositivo) {
            this.idCentro = dispositivo.idCentro
            this.nombre = dispositivo.nombre
            this.numSerie = dispositivo.numSerie
            this.marcaModelo = dispositivo.marcaModelo
            this.ultimaActualizacion = dispositivo.ultimaActualizacion
            this.observaciones = dispositivo.observaciones
            this.idEstado = dispositivo.idEstado
            this.idCategoria = dispositivo.idCategoria
            this.idUbicacion = dispositivo.idUbicacion
            this.idUso = dispositivo.idUso
            this.idAsignacion = dispositivo.idAsignacion
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateDispositivo(id: Int, dispositivo: DispositivoDTO): Boolean = dbQuery {
        val dispositivoToUpdate = DispositivoEntity.findById(id) ?: return@dbQuery false
        dispositivoToUpdate.idCentro = dispositivo.idCentro
        dispositivoToUpdate.nombre = dispositivo.nombre
        dispositivoToUpdate.numSerie = dispositivo.numSerie
        dispositivoToUpdate.marcaModelo = dispositivo.marcaModelo
        dispositivoToUpdate.ultimaActualizacion = dispositivo.ultimaActualizacion
        dispositivoToUpdate.observaciones = dispositivo.observaciones
        dispositivoToUpdate.idEstado = dispositivo.idEstado
        dispositivoToUpdate.idCategoria = dispositivo.idCategoria
        dispositivoToUpdate.idUbicacion = dispositivo.idUbicacion
        dispositivoToUpdate.idUso = dispositivo.idUso
        dispositivoToUpdate.idAsignacion = dispositivo.idAsignacion
        true
    }
    //DELETE eliminar por id
    suspend fun deleteDispositivo(id: Int): Boolean = dbQuery {
        val dispositivoToDelete = DispositivoEntity.findById(id) ?: return@dbQuery false
        dispositivoToDelete.delete()
        true
    }
}